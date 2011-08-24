package swinghacks.ch08.Rendering.hack61;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class FontLoadingDemo {

	public static void main(String[] args) {
		try {
			// get font from path in args[0]
			if (args.length < 1) {
				System.out.println("usage: FontLoadingDemo path-to-ttf");
				return;
			}
			File f = new File(args[0]);
			FileInputStream in = new FileInputStream(f);
			Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT, in);
			Font dynamicFont32Pt = dynamicFont.deriveFont(32f);

			// draw something with it
			JLabel testLabel = new JLabel("Dynamically loaded font \"" + dynamicFont.getName() + "\"");
			testLabel.setFont(dynamicFont32Pt);
			JFrame frame = new JFrame("Font Loading Demo");
			frame.setVisible(true);
			frame.getContentPane().add(testLabel);
			frame.pack();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
