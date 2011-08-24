package swinghacks.ch04.FileChoosers.hack32;

import java.io.File;
import java.io.IOException;

import javax.swing.filechooser.FileSystemView;

public class ZipFileSystemView extends FileSystemView {

	public ZipFileSystemView() throws IOException {
	}

	public File createNewFolder(File file) {
		return null;
	}

	public File createFileObject(File dir, String filename) {
		if (dir instanceof ZipEntryFileProxy) {
			ZipEntryFileProxy zdir = (ZipEntryFileProxy) dir;
			return new ZipEntryFileProxy(zdir.zip, zdir.zipfile, filename, dir);
		}
		return super.createFileObject(dir, filename);
	}

	public File getChild(File dir, String filename) {
		if (dir instanceof ZipEntryFileProxy) {
			ZipEntryFileProxy zdir = (ZipEntryFileProxy) dir;
			return new ZipEntryFileProxy(zdir.zip, zdir.zipfile, dir.getPath() + filename, dir);
		}
		return super.getChild(dir, filename);
	}

	public String getSystemDisplayName(File f) {
		if (f instanceof ZipEntryFileProxy) {
			return f.getName();
		}
		return super.getSystemDisplayName(f);
	}

	public File getParentDirectory(File dir) {
		if (dir instanceof ZipEntryFileProxy) {
			return dir.getParentFile();
		}
		return super.getParentDirectory(dir);
	}

	public File[] getFiles(File dir, boolean useFileHiding) {
		if (dir.getName().endsWith(".zip")) {
			ZipFileProxy proxy = new ZipFileProxy(dir);
			File[] fs = proxy.getFiles("");
			return fs;
		}

		if (dir instanceof ZipEntryFileProxy) {
			return dir.listFiles();
		}

		return super.getFiles(dir, useFileHiding);
	}

	public Boolean isTraversable(File f) {
		if (f.getName().endsWith(".zip")) {
			return new Boolean(true);
		}
		if (f instanceof ZipEntryFileProxy) {
			boolean b = ((ZipEntryFileProxy) f).isDirectory();
			return new Boolean(b);
		}
		return super.isTraversable(f);
	}

	/* debuggin */

	// public File createFileObject(String path) {
	// p("create file object: " + path);
	//     return super.createFileObject(path);
	// }

	// public static void p(String str) {
	//     System.out.println(str);
	// }
}
