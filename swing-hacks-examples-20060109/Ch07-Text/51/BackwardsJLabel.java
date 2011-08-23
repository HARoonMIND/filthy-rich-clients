import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import javax.swing.text.Document;

public class BackwardsJLabel extends JLabel {

    public BackwardsJLabel () { super(); }
    public BackwardsJLabel (Icon image) {super (image);}
    public BackwardsJLabel (Icon image, int align) {super (image, align);}
    public BackwardsJLabel (String text) { super (text);}
    public BackwardsJLabel (String text, Icon icon, int align) {
        super (text, icon, align);
    }
    public BackwardsJLabel (String text, int align) { super (text, align);}


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
        BackwardsJLabel field =
            new BackwardsJLabel ("Through the looking glass");
        JFrame frame = new JFrame("Backwards Text");
        frame.getContentPane().add (field);
        frame.pack();
        frame.setVisible(true);
    }
}
