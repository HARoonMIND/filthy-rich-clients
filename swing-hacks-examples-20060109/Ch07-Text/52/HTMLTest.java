import java.awt.*;
import javax.swing.*;

public class HTMLTest {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Hack blah");
        //JButton b1 = new JButton("my button");
        JButton b1a = new JButton("<html><i>my button</i>");
        JButton b2 = new JButton("<html><i>my</i> button</html>");
        JLabel l1 = new JLabel("<html><u>underlined</u></html>");
        JLabel l2 = new JLabel("<html>my multi-<br>line text</html>");
        
        JCheckBox cb1 = new JCheckBox("<html>The <i>real</i> thing");
        JRadioButton rb1 = new JRadioButton("<html>Even <font color=\"#ff0000\">better</font>");
        
        String[] vals = { "<html><i>better</i>",
                          "<html><u>still</u>" };
        JComboBox combo1 = new JComboBox(vals);
        
        
        StringBuffer sb = new StringBuffer();
        sb.append("<html><head><style type='text/css'>");
        sb.append("li { font-style: italic; font-size: 30pt; }");
        sb.append("li { font-family: serif; color: #ff5555; }");
        sb.append("ul { border-width: 4px; border-style: solid; border-color: #ff0000; } ");
        sb.append("ul { background-color: #ffeeee; }");
        sb.append("</style></head>");
        sb.append("<h3>H3 Header</h3>");
        sb.append("<ul><li>large serifed text</li><li>as list items</li>");
        sb.append("</html>");
        
        JLabel l3 = new JLabel(sb.toString());
        
        
        StringBuffer css = new StringBuffer();
        css.append("<html><head><style type='text/css'>");
        css.append("body { color: #4444ff; font-weight: normal;}");
        css.append("</head><body>");
        
        JLabel l4 = new JLabel(css+"Cartman");
        JLabel l5 = new JLabel(css+"Stan");

        
        Container root = frame.getContentPane();
        root.setLayout(new BoxLayout(root,BoxLayout.Y_AXIS));
        //root.add(b1);
        //root.add(b1a);
        //root.add(b2);
        // root.add(l1);
        // root.add(l2);
        // root.add(cb1);
        // root.add(rb1);
        // root.add(combo1);
        // root.add(l3);
        root.add(l4);
        root.add(l5);
        
        frame.pack();
        frame.setVisible(true);
    }

}

/*

If you want to mix fonts or colors within the text, or to have formatting
such as multiple lines, you can use HTML. HTML formatting can be used in
all Swing buttons, menu items, labels, tool tips, and tabbed panes, as well
as in components such as trees and tables that use labels as renderers.

creating italics, underlined, and other text effects in labels and buttons using HTML

quick way to set italics
full example code

underlined text
multi-lined text

crazy stuff

what does html editor kit support.
more info at.

since it's css we can start to make it reusable.
sb css example

more available information about css support in the javax.swing.text.html.CSS class javadoc.
*/


