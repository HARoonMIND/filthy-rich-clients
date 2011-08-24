package swinghacks.ch07.Text.hack51;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class BackwardsJLabel extends JLabel {

	public BackwardsJLabel() {
		super();
	}

	public BackwardsJLabel(Icon image) {
		super(image);
	}

	public BackwardsJLabel(Icon image, int align) {
		super(image, align);
	}

	public BackwardsJLabel(String text) {
		super(text);
	}

	public BackwardsJLabel(String text, Icon icon, int align) {
		super(text, icon, align);
	}

	public BackwardsJLabel(String text, int align) {
		super(text, align);
	}

	public void paint(Graphics g) {
		if (g instanceof Graphics2D) {
			Graphics2D g2 = (Graphics2D) g;
			AffineTransform flipTrans = new AffineTransform();
			double widthD = (double) getWidth();
			flipTrans.setToTranslation(widthD, 0);
			flipTrans.scale(-1.0, 1);
			g2.transform(flipTrans);
			super.paint(g);
		} else {
			super.paint(g);
		}
	}

	public static void main(String[] args) {
		BackwardsJLabel field = new BackwardsJLabel("Through the looking glass");
		JFrame frame = new JFrame("Backwards Text");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(field);
		frame.pack();
		frame.setVisible(true);
	}
}
