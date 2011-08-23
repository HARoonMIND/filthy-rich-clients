import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.image.*;

public class TabFadeTest {

    public static void main(String[] args) {
        
        JFrame frame = new JFrame("Fade Tabs");
        
        //JTabbedPane tab = new InOutPane();
        JTabbedPane tab = new VenetianPane();
        tab.addTab("t1",new JButton("Test Button 1"));
        tab.addTab("t2",new JButton("Test Button 2"));
        
        frame.getContentPane().add(tab);
        frame.pack();
        frame.show();
    }
    
}



