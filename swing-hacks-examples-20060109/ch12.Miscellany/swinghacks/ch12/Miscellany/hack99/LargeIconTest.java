package swinghacks.ch12.Miscellany.hack99;

import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(label);
		frame.pack();
		frame.setVisible(true);

	}

}
