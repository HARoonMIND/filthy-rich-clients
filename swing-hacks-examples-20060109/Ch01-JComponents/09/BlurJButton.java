import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;

public class BlurJButton extends JButton {

    public BlurJButton(String text) {
        super(text);
    }
    
    public void paintComponent(Graphics g) {
        if(isEnabled()) {
            super.paintComponent(g);
            return;
        }
        
        float[] my_kernel = {
            0.10f, 0.10f, 0.10f,
            0.10f, 0.20f, 0.10f,
            0.10f, 0.10f, 0.10f };
        ConvolveOp op = new ConvolveOp(new Kernel(3,3, my_kernel));
        BufferedImage buf = new BufferedImage(getWidth(),getHeight(),
            BufferedImage.TYPE_INT_RGB);
        super.paintComponent(buf.getGraphics());
        Image img = op.filter(buf,null);
        g.drawImage(img,0,0,null);
        
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Blurred Button Hack");
        final JButton button = new BlurJButton("A Blurred Button");
        JButton control = new JButton("Switch");
        control.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                button.setEnabled(!button.isEnabled());
            }
        });
        
        frame.getContentPane().add(button);
        frame.getContentPane().add("South",control);
        frame.pack();
        frame.show();
    }
}
