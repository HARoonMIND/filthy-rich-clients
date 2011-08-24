package swinghacks.ch01.JComponents.hack01;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;

public class ImageTest {
	public static URL getImage(String filepath) {
		return ImageTest.class.getResource(filepath);
	}

	public static void main(String[] args) {
		ImagePanel panel = new ImagePanel(new ImageIcon(getImage("images/background.png")).getImage());

		ImageLabel label = new ImageLabel(new ImageIcon(getImage("images/reactor.png")));
		label.setLocation(29, 37);
		panel.add(label);

		final ImageButton button = new ImageButton(new ImageIcon(getImage("images/button.png")));
		button.setPressedIcon(new ImageIcon(getImage("images/button-down.png")));
		button.setRolloverIcon(new ImageIcon(getImage("images/button-over.png")));
		button.setSelectedIcon(new ImageIcon(getImage("images/button-sel.png")));
		button.setRolloverSelectedIcon(new ImageIcon(getImage("images/button-sel-over.png")));
		button.setDisabledIcon(new ImageIcon(getImage("images/button-disabled.png")));
		button.setDisabledSelectedIcon(new ImageIcon(getImage("images/button-disabled-selected.png")));
		button.setLocation(60, 74);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				button.setSelected(!button.isSelected());
				System.out.println("selecting");
			}
		});
		//button.setSelected(true);
		//button.setDisabled(false);
		panel.add(button);

		final JCheckBox checkbox = new JCheckBox("Disable");
		checkbox.setLocation(70, 150);
		checkbox.setOpaque(false);
		checkbox.setSize(checkbox.getPreferredSize());
		panel.add(checkbox);
		checkbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				button.setEnabled(!checkbox.isSelected());
			}
		});

		JFrame frame = new JFrame("Hack #XX: Image Components");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
	}
}
