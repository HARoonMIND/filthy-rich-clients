import java.util.Properties;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.mail.*;
import java.awt.*;

public class EmailChecker implements Runnable {
    private JLabel label;
    public EmailChecker(JLabel label) {
        this.label = label;
    }
    
    public void run() {
        while(true) {
            try {
                checkEmail();
                Thread.currentThread().sleep(1000*60); // sleep 1 min
            } catch (Exception ex) {
                System.out.println("exception: " + ex);
                ex.printStackTrace();
            }
        }
        
    }
    public synchronized void checkEmail() throws Exception {
        String username = "joshy@code.joshy.org";
        String password = "satans";
        String hostname = "code.joshy.org";
        int port = 143;
        
        Properties props = System.getProperties();
        Session sess = Session.getDefaultInstance(props);
        sess.setDebug(true);
        Store store = sess.getStore("imap");
        store.connect(hostname, port, username, password);
        Folder inbox = store.getFolder("INBOX");
        final int new_count = inbox.getUnreadMessageCount();
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //label.setText("You have " + new_count + " unread messages.");
                if(new_count > 4) {
                    label.setFont(new Font("WebDings",Font.PLAIN,40));//label.getFont().getSize()));
                    label.setText(""+(char)0xf099);
                } else {
                    label.setFont(new Font("WingDings",Font.PLAIN,40));//label.getFont().getSize()));
                    label.setText(""+(char)0xf02a);
                }
                System.out.println("unread messages = " + new_count);
            }
        });
    }
    
    
    
    public static void p(String str) {
        System.out.println(str);
    }    
    
}
