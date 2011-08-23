import java.awt.*;
import javax.swing.*;
import java.io.*;

public class TestDetachedMagnifyingGlass extends Object {

    public TestDetachedMagnifyingGlass(File f) {
        // image frame
        ImageIcon i = new ImageIcon (f.getPath());
        JLabel l = new JLabel (i);
        JFrame imgFrame = new JFrame ("Image");
        imgFrame.getContentPane().add(l);
        imgFrame.pack();
        imgFrame.setVisible(true);
        // magnifying glass frame
        JFrame magFrame = new JFrame ("Mag");
        DetachedMagnifyingGlass mag =
            new DetachedMagnifyingGlass (l, new Dimension (150, 150), 2.0);
        magFrame.getContentPane().add (mag);
        magFrame.pack();
        magFrame.setLocation (new Point (
                             imgFrame.getLocation().x + imgFrame.getWidth(), 
                             imgFrame.getLocation().y));
        magFrame.setVisible(true);
    }

    public static void main (String[] args) {
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        new TestDetachedMagnifyingGlass (f);
    }

}


