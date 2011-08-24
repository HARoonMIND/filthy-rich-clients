package swinghacks.ch11.NativeIntegrationAndPackaging.hack81;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class GrowBoxDemo {

	static {
		System.setProperty("apple.awt.showGrowBox", "true");
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Grow Box Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTextField field = new JTextField("Intruder Alert! Intruder Alert!");
		frame.getContentPane().add(field, BorderLayout.CENTER);
		frame.getContentPane().add(Box.createVerticalStrut(15), BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
	}

}
