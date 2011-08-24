package swinghacks.ch01.JComponents.hack08;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class TabFadeTest {

	public static void main(String[] args) {

		JFrame frame = new JFrame("Fade Tabs");

		JTabbedPane tab = new VenetianPane();
		tab.addTab("t1", new JButton("Test Button 1"));
		tab.addTab("t2", new JButton("Test Button 2"));

		frame.getContentPane().add(tab, BorderLayout.WEST);

		JTabbedPane tab2 = new InOutPane();
		tab2.addTab("t1", new JButton("Test Button 3"));
		tab2.addTab("t2", new JButton("Test Button 4"));

		frame.getContentPane().add(tab2);
		frame.pack();
		frame.setVisible(true);
	}

}
