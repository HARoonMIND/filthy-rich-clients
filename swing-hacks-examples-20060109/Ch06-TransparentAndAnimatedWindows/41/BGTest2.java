import javax.swing.*;
import java.awt.*;
public class BGTest2 {
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Transparent Window");
        frame.setUndecorated(true);
        TransparentBackground bg = new TransparentBackground(frame);
        bg.setLayout(new BorderLayout());
        
        JPanel panel = new JPanel() {
            public void paintComponent(Graphics g) {
                g.setColor(Color.blue);
                Image img = new ImageIcon("mp3.png").getImage();
                g.drawImage(img,0,0,null);
            }
        };
        panel.setOpaque(false);
        
        bg.add("Center",panel);
        
        
        frame.getContentPane().add("Center",bg);
        frame.pack();
        frame.setSize(200,200);
        frame.setLocation(500,500);
        frame.show();
    }

}
