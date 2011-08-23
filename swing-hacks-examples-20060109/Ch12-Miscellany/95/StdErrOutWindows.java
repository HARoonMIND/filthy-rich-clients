import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class StdErrOutWindows extends Object {

    JTextArea outArea, errArea;

    public StdErrOutWindows () {
        // out
        outArea = new JTextArea (20, 50);
        JScrollPane pain =
            new JScrollPane (outArea,
                             ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                             ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        JFrame outFrame = new JFrame ("out");
        outFrame.getContentPane().add (pain);
        outFrame.pack();
        outFrame.setVisible(true);
        // err
        errArea = new JTextArea (20, 50);
        pain =
            new JScrollPane (errArea,
                             ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                             ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        JFrame errFrame = new JFrame ("err");
        errFrame.getContentPane().add (pain);
        errFrame.pack();
        errFrame.setLocation (errFrame.getLocation().x + 20,
                              errFrame.getLocation().y + 20);
        errFrame.setVisible (true);
        // set up streams
        System.setOut (new PrintStream (new JTextAreaOutputStream (outArea)));
        System.setErr (new PrintStream (new JTextAreaOutputStream (errArea)));
    }

    public static void main (String[] args) {
        new StdErrOutWindows();
        // test
        System.out.println ("test to out");
        System.out.println ("another test to out");
        try {
            throw new Exception ("Test exception");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class JTextAreaOutputStream extends OutputStream {
        JTextArea ta;
        public JTextAreaOutputStream (JTextArea t) {
            super();
            ta = t;
        }
        public void write (int i) {
            char[] chars = new char[1];
            chars[0] = (char) i;
            String s = new String (chars);
            ta.append(s);
        }
        public void write (char[] buf, int off, int len) {
            String s = new String (buf, off, len);
            ta.append(s);
        }

    }

}
