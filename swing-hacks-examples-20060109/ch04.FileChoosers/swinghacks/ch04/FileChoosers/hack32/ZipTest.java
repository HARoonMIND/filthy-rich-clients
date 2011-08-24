package swinghacks.ch04.FileChoosers.hack32;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class ZipTest {
	public static void p(String str) {
		System.out.println(str);
	}

	public static void main(String[] args) throws Exception {
		FileSystemView fsv = new ZipFileSystemView();
		JFileChooser chooser = new JFileChooser(".");
		chooser.setFileSystemView(fsv);
		chooser.showOpenDialog(null);
		File file = chooser.getSelectedFile();
		System.out.println("Got the file: " + file + " " + file.getClass());

		InputStream in = null;
		if (file instanceof ZipEntryFileProxy) {
			in = ((ZipEntryFileProxy) file).getInputStream();
		} else {
			in = new FileInputStream(file);
		}
		byte[] buf = new byte[1024];
		while (true) {
			int n = in.read(buf);
			if (n < 0)
				break;
			System.out.write(buf, 0, n);
		}

		System.exit(0);
	}
}
