package swinghacks.ch01.JComponents.hack03;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ImageBorderHack {
	public static URL getImage(String filepath) {
		return ImageBorderHack.class.getResource(filepath);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Hack #59: Image Border");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		JButton button = new JButton("Image Border Test");
		panel.add(button);

		ImageBorder image_border = new ImageBorder(
				new ImageIcon(getImage("images/upper_left.png")).getImage(),
				new ImageIcon(getImage("images/upper.png")).getImage(), 
				new ImageIcon(getImage("images/upper_right.png")).getImage(),
				new ImageIcon(getImage("images/left_center.png")).getImage(), 
				new ImageIcon(getImage("images/right_center.png")).getImage(),
				new ImageIcon(getImage("images/bottom_left.png")).getImage(), 
				new ImageIcon(getImage("images/bottom_center.png")).getImage(), 
				new ImageIcon(getImage("images/bottom_right.png")).getImage());
		panel.setBorder(image_border);

		frame.getContentPane().add(panel);
		frame.pack();
		//frame.setSize(200,200);
		frame.setVisible(true);
	}

}
