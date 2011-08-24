package swinghacks.ch06.TransparentAndAnimatedWindows.hack41;
import javax.swing.*;
import java.awt.*;
public class BGTest1 {
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Transparent Window");
        TransparentBackground bg = new TransparentBackground(frame);
        bg.setLayout(new BorderLayout());
        JButton button = new JButton("This is a button");
        bg.add("North",button);
        JLabel label = new JLabel("This is a label");
        bg.add("South",label);
        frame.getContentPane().add("Center",bg);
        frame.pack();
        frame.setSize(150,100);
        frame.show();
    }

}
