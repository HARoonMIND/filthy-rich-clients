package swinghacks.ch04.FileChoosers.hack32;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipEntryFileProxy extends DebugFile {
	ZipFileProxy zip;
	ZipFile zipfile;
	String name, path;
	File parent;
	ZipEntry entry;

	public ZipEntryFileProxy(ZipFileProxy zip, ZipFile zipfile, String path, File parent) {
		super("");
		this.zip = zip;
		this.zipfile = zipfile;
		this.path = path;
		this.parent = parent;
		this.entry = zipfile.getEntry(path);

		// determine if the entry is a directory
		String tmp = path;

		if (entry.isDirectory()) {
			tmp = path.substring(0, path.length() - 1);
		}

		// then calculate the name
		int brk = tmp.lastIndexOf("/");
		name = path;
		if (brk != -1) {
			name = tmp.substring(brk + 1);
		}
	}

	public boolean exists() {
		return true;
	}

	public int hashCode() {
		return name.hashCode() ^ 1234321;
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	public boolean isDirectory() {
		return entry.isDirectory();
	}

	public boolean isAbsolute() {
		return true;
	}

	public String getAbsolutePath() {
		return path;
	}

	public File getAbsoluteFile() {
		return this;
	}

	public File getCanonicalFile() {
		return this;
	}

	public File getParentFile() {
		return parent;
	}

	public boolean equals(Object obj) {
		if (obj instanceof ZipEntryFileProxy) {
			ZipEntryFileProxy zo = (ZipEntryFileProxy) obj;
			if (zo.getAbsolutePath().equals(getAbsolutePath())) {
				return true;
			}
		}
		return false;
	}

	public File[] listFiles() {
		Map children = (Map) zip.hash.get(path);
		File[] files = new File[children.size()];
		Iterator it = children.keySet().iterator();
		int count = 0;
		while (it.hasNext()) {
			String name = (String) it.next();
			files[count] = new ZipEntryFileProxy(zip, zipfile, name, this);
			count++;
		}
		return files;
	}

	public InputStream getInputStream() throws IOException {
		return zipfile.getInputStream(entry);
	}

}
