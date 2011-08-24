package swinghacks.ch11.NativeIntegrationAndPackaging.hack81;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class MenuBarDemo {

	static {
		System.setProperty("apple.laf.useScreenMenuBar", "true");
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Menu Bar Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JMenuBar bar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Edit");
		JMenu helpMenu = new JMenu("Help");
		bar.add(fileMenu);
		bar.add(editMenu);
		bar.add(helpMenu);
		frame.setSize(300, 150);
		frame.setJMenuBar(bar);
		frame.setVisible(true);
	}

}
