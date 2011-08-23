import java.awt.*;
import javax.swing.*;
import java.util.Properties;

public class GrowBoxDemo {

    static {
        System.setProperty ("apple.awt.showGrowBox", "true");
    }

    public static void main (String[] args) {
        JFrame frame = new JFrame ("Grow Box Demo");
        JTextField field =
            new JTextField ("Intruder Alert! Intruder Alert!");
        frame.getContentPane().add (field, BorderLayout.CENTER);
        frame.getContentPane().add (Box.createVerticalStrut(15), 
                                    BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }

}
