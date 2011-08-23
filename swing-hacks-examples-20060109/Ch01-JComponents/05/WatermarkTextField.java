
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.awt.image.*;
import javax.swing.table.*;

// put a texture in the background

public class WatermarkTextField extends JTextField {
    BufferedImage img;
    TexturePaint texture;
    public WatermarkTextField(File file) throws IOException {
        super();
        img = ImageIO.read(file);
        Rectangle rect = new Rectangle(0,0,
                img.getWidth(null),img.getHeight(null));
        texture = new TexturePaint(img, rect);
        setOpaque(false);
    }
    
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setPaint(texture);
        g.fillRect(0,0,getWidth(),getHeight());
        super.paintComponent(g);
    }
    
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Watermark JTextField Hack");
        
        JTextField textfield = new WatermarkTextField(new File("red.png"));
        textfield.setText("A Text Field");
        frame.getContentPane().add(textfield);
        frame.pack();
        frame.show();
    }
    
}
