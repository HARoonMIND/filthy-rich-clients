package swinghacks.ch01.JComponents.hack05;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JTextField;

// put a texture in the background

public class WatermarkTextField extends JTextField {
	BufferedImage img;
	TexturePaint texture;

	public WatermarkTextField(File file) throws IOException {
		super();
		img = ImageIO.read(file);
		Rectangle rect = new Rectangle(0, 0, img.getWidth(null), img.getHeight(null));
		texture = new TexturePaint(img, rect);
		setOpaque(false);
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(texture);
		g.fillRect(0, 0, getWidth(), getHeight());
		super.paintComponent(g);
	}

	public static URL getFile(String filepath) {
		return WatermarkTextField.class.getResource(filepath);
	}

	public static void main(String[] args) throws Exception {
		JFrame frame = new JFrame("Watermark JTextField Hack");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTextField textfield = new WatermarkTextField(new File(getFile("red.png").toURI()));
		textfield.setText("A Text Field");
		frame.getContentPane().add(textfield);
		frame.pack();
		frame.setVisible(true);
	}

}
