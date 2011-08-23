import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.*;

public class MenuTest {
    public static void main(String[] args) throws Exception {
        UIManager.put("PopupMenuUI","CustomPopupMenuUI");
        UIManager.put("MenuItemUI","CustomMenuItemUI");
        RepaintManager.setCurrentManager(new FullRepaintManager());
        
        JFrame frame = new JFrame();
        JMenuBar mb = new JMenuBar();
        frame.setJMenuBar(mb);
        JMenu menu = new JMenu("File");
        mb.add(menu);
        menu.add(new JMenuItem("Open"));
        menu.add(new JMenuItem("Save"));
        menu.add(new JMenuItem("Close"));
        menu.add(new JMenuItem("Exit"));
        menu = new JMenu("Edit");
        mb.add(menu);
        menu.add(new JMenuItem("Cut"));
        menu.add(new JMenuItem("Copy"));
        menu.add(new JMenuItem("Paste"));
        menu.add(new JMenuItem("Paste Special.."));
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add("North",new JButton("Button"));
        frame.getContentPane().add("Center",new JLabel("a label"));
        frame.getContentPane().add("South",new JCheckBox("checkbox"));
        frame.pack();
        frame.setSize(200,150);
        frame.show();
    }
    
}

