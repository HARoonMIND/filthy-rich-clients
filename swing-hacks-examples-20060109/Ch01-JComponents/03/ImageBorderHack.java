import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;

public class ImageBorderHack {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Hack #59: Image Border");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        JButton button = new JButton("Image Border Test");
        panel.add(button);
        
        ImageBorder image_border = new ImageBorder(
            new ImageIcon("images/upper_left.png").getImage(),
            new ImageIcon("images/upper.png").getImage(),
            new ImageIcon("images/upper_right.png").getImage(),

            new ImageIcon("images/left_center.png").getImage(),
            new ImageIcon("images/right_center.png").getImage(),

            new ImageIcon("images/bottom_left.png").getImage(),
            new ImageIcon("images/bottom_center.png").getImage(),
            new ImageIcon("images/bottom_right.png").getImage()
            );
        panel.setBorder(image_border);

        
        frame.getContentPane().add(panel);
        frame.pack();
        //frame.setSize(200,200);
        frame.setVisible(true);
    }

}


