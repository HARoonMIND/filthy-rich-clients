package swinghacks.ch04.FileChoosers.hack30;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import swinghacks.ch04.FileChoosers.hack29.ShortcutFileView;

public class ShortcutTest {
	public static void main(String[] args) throws Exception {
		FileSystemView fsv = new ShortcutFileSystemView();
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSystemView(fsv);
		chooser.setFileView(new ShortcutFileView());
		chooser.showOpenDialog(null);
	}
}
