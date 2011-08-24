package swinghacks.ch12.Miscellany.hack91;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class EmailTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Hack #68: Embedded email checking");
        JLabel status = new JLabel("You have XXX unread messages.");
        frame.getContentPane().add(status);
        frame.pack();
        status.addMouseListener(new EmailLauncher());
        
        EmailChecker email = new EmailChecker(status);
        new Thread(email).start();
        
        frame.setVisible(true);
    }
}
