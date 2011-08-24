package swinghacks.ch04.FileChoosers.hack32;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipFileProxy extends DebugFile {
	protected Map hash;
	private ZipFile zipfile;
	private File real_file;

	public ZipFileProxy(File file) {
		super(file.getAbsolutePath());
		try {
			this.hash = new HashMap();
			this.real_file = file;
			zipfile = new ZipFile(file, ZipFile.OPEN_READ);
			hash.put("", new HashMap());
			Enumeration en = zipfile.entries();
			parse(en);
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
	}

	public String getPath() {
		return real_file.getPath();
	}

	public boolean exists() {
		return real_file.exists();
	}

	public String getName() {
		return real_file.getName();
	}

	/* create a hashtable of the entries and their paths */
	private void parse(Enumeration en) {
		while (en.hasMoreElements()) {
			ZipEntry ze = (ZipEntry) en.nextElement();
			String full_name = ze.getName();
			String name = full_name;
			if (ze.isDirectory()) {
				name = full_name.substring(0, full_name.length() - 1);
			}

			int brk = name.lastIndexOf("/");

			String parent = "";
			if (brk != -1) {
				parent = name.substring(0, brk + 1);
			}

			String node_name = name;
			if (brk != -1) {
				node_name = full_name.substring(brk + 1);
			}

			if (ze.isDirectory()) {
				HashMap children = new HashMap();
				hash.put(full_name, children);
			}
			Map parent_children = (Map) hash.get(parent);
			parent_children.put(full_name, "");
		}
	}

	public File[] getFiles(String dir) {
		Map children = (Map) hash.get(dir);
		File[] files = new File[children.size()];
		Iterator it = children.keySet().iterator();
		int count = 0;
		while (it.hasNext()) {
			String name = (String) it.next();
			files[count] = new ZipEntryFileProxy(this, zipfile, name, this);
			count++;
		}
		return files;
	}
}
