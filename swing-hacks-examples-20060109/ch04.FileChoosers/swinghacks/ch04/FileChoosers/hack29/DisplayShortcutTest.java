package swinghacks.ch04.FileChoosers.hack29;

import javax.swing.JFileChooser;

public class DisplayShortcutTest {
	public static void main(String[] args) throws Exception {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileView(new ShortcutFileView());
		chooser.showOpenDialog(null);
	}
}
