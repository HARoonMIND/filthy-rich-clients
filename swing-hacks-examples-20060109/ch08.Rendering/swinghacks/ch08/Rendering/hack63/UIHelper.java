package swinghacks.ch08.Rendering.hack63;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public final class UIHelper {
	public static JButton createButton(String text) {
		JButton button = new JButton(text);
		button.setFocusPainted(true);
		button.setBorderPainted(true);
		button.setContentAreaFilled(true);
		return button;
	}

	public static JButton createButton(String text, String icon) {
		return createButton(text, icon, false);
	}

	public static JButton createButton(String text, String icon, boolean flat) {
		ImageIcon iconNormal = readImageIcon(icon + ".png");
		ImageIcon iconHighlight = readImageIcon(icon + "_highlight.png");
		ImageIcon iconPressed = readImageIcon(icon + "_pressed.png");

		JButton button = new JButton(text, iconNormal);
		button.setFocusPainted(!flat);
		button.setBorderPainted(!flat);
		button.setContentAreaFilled(!flat);
		if (iconHighlight != null) {
			button.setRolloverEnabled(true);
			button.setRolloverIcon(iconHighlight);
		}
		if (iconPressed != null)
			button.setPressedIcon(iconPressed);
		return button;
	}

	public static void centerOnScreen(Component component) {
		Dimension paneSize = component.getSize();
		Dimension screenSize = component.getToolkit().getScreenSize();
		component.setLocation((screenSize.width - paneSize.width) / 2, (screenSize.height - paneSize.height) / 2);
	}

	public static ImageIcon readImageIcon(String fileName) {
		Image image = readImage(fileName);
		if (image == null)
			return null;

		return new ImageIcon(image);
	}

	public static Image readImage(String fileName) {
		URL url = UIHelper.class.getResource("images/" + fileName);
		if (url == null)
			return null;

		return java.awt.Toolkit.getDefaultToolkit().getImage(url);
	}

}
