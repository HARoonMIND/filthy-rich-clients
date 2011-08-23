import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import javax.swing.text.Document;

public class BackwardsJTextField extends JTextField {

    public BackwardsJTextField () { super(); }
    public BackwardsJTextField (Document doc, String text, int columns) {
        super (doc, text, columns); 
    }
    public BackwardsJTextField (int columns) { super (columns); }
    public BackwardsJTextField (String text) { super (text);}
    public BackwardsJTextField (String text, int columns) {
        super (text, columns);
    }

    public void paint (Graphics g) {
        if (g instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D) g;
            AffineTransform flipTrans = new AffineTransform();
            double widthD = (double) getWidth();
            flipTrans.setToTranslation (widthD, 0);
            flipTrans.scale (-1.0, 1);
            g2.transform (flipTrans);
            super.paint(g);
        } else {
            super.paint(g);
        }
    }


    public static void main (String[] args) {
        BackwardsJTextField field =
            new BackwardsJTextField ("Through the looking glass", 50);
        JFrame frame = new JFrame("Backwards Text");
        frame.getContentPane().add (field);
        frame.pack();
        frame.setVisible(true);
    }
}
