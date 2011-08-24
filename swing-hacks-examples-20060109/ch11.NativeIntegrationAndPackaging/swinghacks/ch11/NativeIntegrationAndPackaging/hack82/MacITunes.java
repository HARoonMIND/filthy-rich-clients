package swinghacks.ch11.NativeIntegrationAndPackaging.hack82;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
public class MacITunes implements ActionListener {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Mac iTunes Hack");
		JButton button = new JButton("Play/Pause");
		button.addActionListener(new MacITunes());
		
		frame.getContentPane().add(button);
		frame.pack();
		frame.setVisible(true);		
	}
	
	
	public void actionPerformed(ActionEvent evt) {
		try {
			Runtime rt = Runtime.getRuntime();
			String[] args = { "osascript", "-e","tell app \"iTunes\" to playpause"};
//			String[] args = { "osascript", "-e","tell app \"iTunes\" to artist of current track as string"};
			System.out.println("running: " + args[0] + " " + args[1] + " " + args[2]);
			final Process proc = rt.exec(args);
			
			/*
			new Thread(new Runnable() {
				public void run() {
					printStream(proc.getErrorStream());
				}
			}).start();
			new Thread(new Runnable() {
				public void run() {
					printStream(proc.getInputStream());
				}
			}).start();
			*/
			
			InputStream in = proc.getInputStream();
			String str = new DataInputStream(in).readLine();
			System.out.println("got: " + str);
		
		} catch (IOException ex) {
			System.out.println("exception : " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public static void printStream(InputStream stream) {
		try { 
			byte[] buff = new byte[256];
			while(true) {
				int n = stream.read(buff);
				if(n == -1) {
					break;
				}
				System.out.println(new String(buff,0,n));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
