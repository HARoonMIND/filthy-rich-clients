import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.awt.image.*;
import javax.swing.table.*;

// put a texture in the background
// put a translucent image in the foreground

// put a yellow gradient inthe background
// put a translucent sun in the upper right

public class ScrollPaneWatermark extends JViewport {
    BufferedImage fgimage, bgimage;
    TexturePaint texture;
    
    public ScrollPaneWatermark(){
        super();
        //setOpaque(false);
    }
    
    public void setBackgroundTexture(URL url) throws IOException {
        bgimage = ImageIO.read(url);
        Rectangle rect = new Rectangle(0,0,
                bgimage.getWidth(null),bgimage.getHeight(null));
        texture = new TexturePaint(bgimage, rect);
    }
    
    public void setForegroundBadge(URL url) throws IOException {
        fgimage = ImageIO.read(url);
    }
    
    public void paintComponent(Graphics g) {
        // do the superclass behavior first
        super.paintComponent(g);
        
        // paint the texture
        if(texture != null) {
            Graphics2D g2 = (Graphics2D)g;
            g2.setPaint(texture);
            g.fillRect(0,0,getWidth(),getHeight());
        }
    }
    
    public void paintChildren(Graphics g) {
        super.paintChildren(g);
        if(fgimage != null) {
        g.drawImage(fgimage, 
            getWidth()-fgimage.getWidth(null), 0,
            null);
        }
    }
    
    public void setView(JComponent view) {
        view.setOpaque(false);
        super.setView(view);
    }
    
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Scroll Pane Watermark Hack");
        
        JTextArea ta = new JTextArea();
        ta.setText(fileToString(new File("alice.txt")));
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        //ta.setOpaque(false);
        
        ScrollPaneWatermark watermark = new ScrollPaneWatermark();
        watermark.setBackgroundTexture(new File("clouds.jpg").toURL());
        watermark.setForegroundBadge(new File("flyingsaucer.png").toURL());
        watermark.setView(ta);
        
        JScrollPane scroll = new JScrollPane();
        scroll.setViewport(watermark);
        
        
        frame.getContentPane().add(scroll);
        frame.pack();
        frame.setSize(600,600);
        frame.show();
    }
    
    public static String fileToString( File file )
        throws FileNotFoundException, IOException {
        FileReader reader = new FileReader( file );
        StringWriter writer = new StringWriter();
        char[] buf = new char[1000];
        while ( true ) {
            int n = reader.read( buf, 0, 1000 );
            if ( n == -1 ) {
                break;
            }
            writer.write( buf, 0, n );
        }
        return writer.toString();
    }
}
