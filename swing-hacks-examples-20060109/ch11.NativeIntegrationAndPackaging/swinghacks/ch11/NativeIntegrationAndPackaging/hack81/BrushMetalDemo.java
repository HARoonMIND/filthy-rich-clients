package swinghacks.ch11.NativeIntegrationAndPackaging.hack81;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class BrushMetalDemo {

	static {
		System.setProperty("apple.awt.brushMetalLook", "true");
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Brushed Metal");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new JLabel("It's so shiny!"));
		frame.setSize(200, 200);
		frame.setVisible(true);
	}

}
