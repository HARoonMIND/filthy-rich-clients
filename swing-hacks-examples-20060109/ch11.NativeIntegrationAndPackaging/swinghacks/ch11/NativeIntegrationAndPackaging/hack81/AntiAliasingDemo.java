package swinghacks.ch11.NativeIntegrationAndPackaging.hack81;

// these properties seem to have no effect.

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class AntiAliasingDemo {

	static {
		System.setProperty("apple.awt.antialiasing", "false");
		System.setProperty("apple.awt.textantialiasing", "false");
	}

	static Polygon triangle;
	static {
		triangle = new Polygon();
		triangle.addPoint(105, 95);
		triangle.addPoint(195, 95);
		triangle.addPoint(150, 5);
	}

	public static void main(String[] args) {
		new AntiAliasingDemo();
	}

	public AntiAliasingDemo() {
		try {
			String metal = UIManager.getCrossPlatformLookAndFeelClassName();
			UIManager.setLookAndFeel(metal);
		} catch (Exception e) {
			e.printStackTrace();
		}

		JFrame frame = new JFrame("Anti-Aliasing demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new JLabel("Here are some shapes"), BorderLayout.NORTH);
		frame.getContentPane().add(new PrimPanel());
		frame.pack();
		frame.setVisible(true);
	}

	class PrimPanel extends JPanel {
		Dimension pSize = new Dimension(200, 200);

		public Dimension getPreferredSize() {
			return pSize;
		}

		public void paint(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setStroke(new BasicStroke(3.0f));
			g2d.setColor(Color.blue);
			g2d.drawLine(5, 5, 95, 80);
			g2d.setColor(Color.red);
			g2d.draw(triangle);
			g2d.setColor(Color.cyan);
			g2d.drawArc(5, 105, 190, 90, 0, -135);
		}
	}

}
