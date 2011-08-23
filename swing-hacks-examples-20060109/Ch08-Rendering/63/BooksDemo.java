import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;

import javax.media.j3d.*;
import javax.vecmath.*;

import java.util.*;

import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.*;
import com.sun.j3d.utils.universe.*;
 
public class BooksDemo extends JFrame
{
    private static final int CANVAS3D_WIDTH  = 400;
    private static final int CANVAS3D_HEIGHT = 400;

    private boolean front = true;
    private JPanel centerPanel = new JPanel(); 
    private Canvas3D c3d;
    private RotationInterpolator rotator1;
    private Alpha rotor1Alpha;
    private com.sun.j3d.utils.geometry.Box book;
    private HashMap textures = new HashMap(6);
    
    public BooksDemo()
    {
        super("AmazonPick");

        JButton cover1 = UIHelper.createButton("", "cover1_small_button", true);
        cover1.addActionListener(new CoverSwitcher("cover1"));
        JButton cover2 = UIHelper.createButton("", "cover2_small_button", true);
        cover2.addActionListener(new CoverSwitcher("cover2"));
        JButton cover3 = UIHelper.createButton("", "cover3_small_button", true);
        cover3.addActionListener(new CoverSwitcher("cover3"));

        JPanel buttons = new JPanel();
        buttons.add(cover1);
        buttons.add(cover2);
        buttons.add(cover3);
        buttons.setOpaque(false);
        
        c3d = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        c3d.setSize(CANVAS3D_WIDTH, CANVAS3D_HEIGHT);

        centerPanel.add(c3d);
        centerPanel.setOpaque(false);

        this.setContentPane(new GradientPanel());
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(centerPanel, BorderLayout.CENTER);
        this.getContentPane().add(buttons, BorderLayout.SOUTH);

        JPopupMenu.setDefaultLightWeightPopupEnabled(false);
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(new JMenuItem("New Search..."));
        fileMenu.add(new JMenuItem("Save Search..."));
        fileMenu.addSeparator();
        fileMenu.add(new JMenuItem("Quit"));
        menuBar.add(fileMenu);
        this.setJMenuBar(menuBar);

        this.pack();
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        UIHelper.centerOnScreen(this);
    }

    private boolean isBookShowingFront()
    {
      return front;
    }

    private void rotateBook()
    {
        if (!isBookShowingFront())
        {
            rotator1.setMinimumAngle((float) Math.PI * 1.0f);
            rotator1.setMaximumAngle(0.0f);
        } else {
            rotator1.setMinimumAngle(0.0f);
            rotator1.setMaximumAngle((float) Math.PI * 1.0f);
        }

        front = !front;
        rotor1Alpha.setStartTime(System.currentTimeMillis());
    }

    private class CoverSwitcher implements ActionListener
    {
        private String coverName;

        CoverSwitcher(String coverName)
        {
            this.coverName = coverName;
        }

        public void actionPerformed(ActionEvent evt)
        {
            book.getShape(isBookShowingFront() ? book.BACK  : book.FRONT).setAppearance((Appearance) textures.get(coverName));
            rotateBook();
        }
    }

    protected Background createBackground()
    {
        BufferedImage image = new BufferedImage(getContentPane().getWidth(),
                                                getContentPane().getHeight(),
                                                BufferedImage.TYPE_INT_RGB);
        getContentPane().paint(image.getGraphics());
    
        BufferedImage subImage = new BufferedImage(CANVAS3D_WIDTH,
                                                   CANVAS3D_HEIGHT,
                                                   BufferedImage.TYPE_INT_RGB);
        Graphics2D subGraphics = (Graphics2D) subImage.getGraphics();
        subGraphics.drawImage(image, null, -c3d.getX(), -c3d.getY());
    
        Background bg = new Background(new ImageComponent2D(ImageComponent2D.FORMAT_RGB, subImage));
        BoundingSphere bounds = new BoundingSphere();
        bounds.setRadius(100.0);
        bg.setApplicationBounds(bounds);
    
        return bg;
    }

    public void createScene()
    {
        BranchGroup objRoot = new BranchGroup();
        objRoot.addChild(createBackground());
    
        TransformGroup objTg = new TransformGroup();
        objTg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    
        BoundingSphere bounds = new BoundingSphere();
        bounds.setRadius(100.0);

        Transform3D yAxis = new Transform3D();
        rotor1Alpha = new Alpha(1, 400);
        rotator1 = new RotationInterpolator(rotor1Alpha, objTg, yAxis, (float) Math.PI * 1.0f, (float) Math.PI * 2.0f);
        rotator1.setSchedulingBounds(bounds);
    
        textures.put("pages_top", createTexture("pages_top.jpg"));
        textures.put("pages",     createTexture("amazon.jpg"));
        textures.put("amazon",    createTexture("amazon.jpg"));
        textures.put("cover1",    createTexture("cover1.jpg"));
        textures.put("cover2",    createTexture("cover2.jpg"));
        textures.put("cover3",    createTexture("cover3.jpg"));
    
        book = new com.sun.j3d.utils.geometry.Box(0.5f, 0.7f, 0.15f, com.sun.j3d.utils.geometry.Box.GENERATE_TEXTURE_COORDS, new Appearance());
        book.getShape(book.TOP).setAppearance((Appearance) textures.get("pages_top"));
        book.getShape(book.RIGHT).setAppearance((Appearance) textures.get("pages"));
        book.getShape(book.LEFT).setAppearance((Appearance) textures.get("amazon"));
        book.getShape(book.FRONT).setAppearance((Appearance) textures.get("cover1"));

        book.getShape(book.BACK).setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
        book.getShape(book.FRONT).setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);

        objTg.addChild(book);
        objTg.addChild(rotator1);
    
        Transform3D spin = new Transform3D();
        Transform3D tempspin = new Transform3D();
        
        spin.rotX(Math.PI / 8.0d);
        tempspin.rotY(Math.PI / 7.0d);
        spin.mul(tempspin);
       
        TransformGroup objTrans = new TransformGroup(spin);
        objTrans.addChild(objTg);
    
        objRoot.addChild(objTrans);
    
        SimpleUniverse u = new SimpleUniverse(c3d);
        u.getViewingPlatform().setNominalViewingTransform();
        u.addBranchGraph(objRoot);
    
        View view = u.getViewer().getView();
        view.setSceneAntialiasingEnable(true);
    }

    private Appearance createTexture(String fileName)
    {
        Image sourceImage = UIHelper.readImage(fileName);
        if (sourceImage == null)
          System.out.println("Image could not be loaded from " + fileName);

        TextureLoader loader = new TextureLoader(sourceImage, this);
        ImageComponent2D image = loader.getImage();
        
        if (image == null)
          System.out.println("Texture could not be loaded from " + fileName);
        
        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
        texture.setImage(0, image);
        texture.setEnable(true);
        texture.setMagFilter(Texture.BASE_LEVEL_LINEAR);
        texture.setMinFilter(Texture.BASE_LEVEL_LINEAR);
 
        Appearance appearance = new Appearance();
        PolygonAttributes polyAttributes = new PolygonAttributes(PolygonAttributes.POLYGON_FILL, PolygonAttributes.CULL_NONE, 0f);
        appearance.setPolygonAttributes(polyAttributes);
        appearance.setTexture(texture);

        TextureAttributes textureAttributes = new TextureAttributes();
        appearance.setTextureAttributes(textureAttributes);

        return appearance;
    }
 
    private static class GradientPanel extends JPanel
    {
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);

            if (!isOpaque())
            {
                return;
            }

            int width  = getWidth();
            int height = getHeight();
    
            Graphics2D g2 = (Graphics2D) g;
    
            Paint storedPaint = g2.getPaint();
            g2.setPaint(new GradientPaint(0, 0, Color.WHITE, width, height, new Color(200, 200, 200)));
            g2.fillRect(0, 0, width, height);
            g2.setPaint(storedPaint);
        }
    }

    public static void main(String argv[])
    {
        final BooksDemo xframe = new BooksDemo();
        xframe.setVisible(true);
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                xframe.createScene();
            }
        });
    }
}
