package swinghacks.ch01.JComponents.hack12;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.RepaintManager;
import javax.swing.UIManager;

public class MenuTest {
	public static void main(String[] args) throws Exception {
		UIManager.put("PopupMenuUI", "swinghacks.ch01.JComponents.hack12.CustomPopupMenuUI");
		UIManager.put("MenuItemUI", "swinghacks.ch01.JComponents.hack12.CustomMenuItemUI");
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
		frame.getContentPane().add("North", new JButton("Button"));
		frame.getContentPane().add("Center", new JLabel("a label"));
		frame.getContentPane().add("South", new JCheckBox("checkbox"));
		frame.pack();
		frame.setSize(200, 150);
		frame.setVisible(true);
	}

}
