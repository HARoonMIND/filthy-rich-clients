import java.awt.Font;
import java.util.*;
import javax.swing.*;

public class ChangeAllFonts {

    final static String[] LIST_ITEMS =
    { "JList", "with", "new Font" }; 

    public static void main (String[] args) {
        try {
            // get user's font
            if (args.length < 1) {
                System.out.println ("Usage: ChangeAllFonts font-name");
                return;
            }
            String fontName = args[0];
            Font font = new Font (fontName, Font.PLAIN, 12);
            
            // put this font in the defaults table for every
            // ui font resource key
            Hashtable defaults = UIManager.getDefaults();
            Enumeration keys = defaults.keys();
            while (keys.hasMoreElements()) {
                Object key = keys.nextElement();
                if ((key instanceof String) &&
                    (((String) key).endsWith(".font"))) {
                    System.out.println (key);
                    defaults.put (key, font);
                }
            }

            // now bring up a GUI to show this off
            JPanel panel = new JPanel();
            panel.setLayout (new BoxLayout (panel, BoxLayout.Y_AXIS));
            panel.add (new JLabel ("JLabel with font " + fontName));
            panel.add (new JTextField ("JTextField with font " + fontName));
            panel.add (new JButton ("JButton with font " + fontName));
            JList list = new JList (LIST_ITEMS);
            JScrollPane pane =
                new JScrollPane (list,
                      ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                      ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            panel.add (pane);

            JFrame frame = new JFrame ("Changing default fonts");
            frame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
            frame.getContentPane().add(panel);
            frame.pack();
            frame.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
