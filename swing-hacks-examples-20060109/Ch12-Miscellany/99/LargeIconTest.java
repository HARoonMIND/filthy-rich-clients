import javax.swing.*;
import java.io.*;

public class LargeIconTest {

    public static void main(String[] args) throws Exception {

        // Create a File instance of an existing file
        File file = new File(args[0]);
        
        // Get large icon
        sun.awt.shell.ShellFolder sf = sun.awt.shell.ShellFolder.getShellFolder(file);
        Icon icon = new ImageIcon(sf.getIcon(true));
        System.out.println("type = " + sf.getFolderType());
        
        // show the icon
        JLabel label = new JLabel(icon);
        JFrame frame = new JFrame();
        frame.getContentPane().add(label);
        frame.pack();
        frame.show();
    
    }

}
