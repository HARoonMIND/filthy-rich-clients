package swinghacks.ch04.FileChoosers.hack32;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class DebugFile extends File {

	public DebugFile(String name) {
		super(name);
	}

	public boolean canRead() {
		p("can read");
		return super.canRead();
	}

	public boolean canWrite() {
		p("can write");
		return super.canWrite();
	}

	public int compareTo(File pathname) {
		p("compare to file called: this = " + this + " to = " + pathname);
		p("returning: " + super.compareTo(pathname));
		return super.compareTo(pathname);
	}

//    public int compareTo(Object pathname) {
//        p("compare to object called on: " + this);
//        p("returning: " + super.compareTo(pathname));
//        return super.compareTo(pathname);
//    }

	public boolean createNewFile() throws IOException {
		p("create new file");
		return super.createNewFile();
	}

	public boolean delete() {
		p("delete");
		return super.delete();
	}

	public void deleteOnExit() {
		p("delete on exit");
		super.deleteOnExit();
	}

	public boolean equals(Object obj) {
		p("equals called on : " + obj);
		p("returning: " + super.equals(obj));
		return super.equals(obj);
	}

	public boolean exists() {
		p("exists called");
		return super.exists();
	}

	public File getAbsoluteFile() {
		p("get abs file: " + this);
		return super.getAbsoluteFile();
	}

	public String getAbsolutePath() {
		p("get abs path : " + this);
		return super.getAbsolutePath();
	}

	public File getCanonicalFile() throws IOException {
		p("get canonical file");
		return super.getCanonicalFile();
	}

	public String getCanonicalPath() throws IOException {
		p("get canonical path");
		return super.getCanonicalPath();
	}

	public String getName() {
		p("debug: get name " + super.getName());
		return super.getName();
	}

	public String getParent() {
		p("get parent");
		return super.getParent();
	}

	public File getParentFile() {
		p("get parent file");
		return super.getParentFile();
	}

	public String getPath() {
		p("debug: get path");
		return super.getPath();
	}

	public int hashCode() {
		p("get hashcode");
		return super.hashCode();
	}

	public boolean isAbsolute() {
		p("is absolute");
		return super.isAbsolute();
	}

	public boolean isDirectory() {
		p("is directory");
		return super.isDirectory();
	}

	public boolean isFile() {
		p("is file");
		return super.isFile();
	}

	public boolean isHidden() {
		p("is hidden");
		return super.isHidden();
	}

	public long lastModified() {
		p("last modified");
		return super.lastModified();
	}

	public long length() {
		p("length");
		return super.length();
	}

	public String[] list() {
		p("list called");
		return super.list();
	}

	public String[] list(FilenameFilter filter) {
		p("list called");
		return super.list(filter);
	}

	public File[] listFiles() {
		p("list files called");
		return super.listFiles();
	}

	public File[] listFiles(java.io.FileFilter filter) {
		p("list files called");
		return super.listFiles(filter);
	}

	public File[] listFiles(FilenameFilter filter) {
		p("list files called");
		return super.listFiles(filter);
	}

	public boolean mkdir() {
		p("mkdir");
		return super.mkdir();
	}

	public boolean mkdirs() {
		p("mkdirs");
		return super.mkdirs();
	}

	public boolean setLastModified(long time) {
		p("set last modified");
		return super.setLastModified(time);
	}

	public boolean setReadOnly() {
		p("set read only");
		return super.setReadOnly();
	}

	public String toString() {
		//p("to string");
		return super.toString();
	}

	public URI toURI() {
		p("to URI");
		return super.toURI();
	}

	public URL toURL() throws MalformedURLException {
		p("to URL");
		return super.toURL();
	}

	/*
	public File[] listRoots() {
	    p("roots");
	    return super.listRoots();
	}
	*/
	/*
	      Returns an array of abstract pathnames denoting the files in the directory denoted by this abstract pathname.
	File[] 	listFiles(FileFilter filter)
	      Returns an array of abstract pathnames denoting the files and directories in the directory denoted by this abstract pathname that satisfy the specified filter.
	File[] 	listFiles(FilenameFilter filter)
	      Returns an array of abstract pathnames denoting the files and directories in the directory denoted by this abstract pathname that satisfy the specified filter.
	static File[] 	listRoots()
	      List the available filesystem roots.
	      */

	public static void p(String str) {
		System.out.println(str);
	}

	public static void p(Object[] objs) {
		for (int i = 0; i < objs.length; i++) {
			p(":: " + objs[i]);
		}
	}
}
