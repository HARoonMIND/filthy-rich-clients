package swinghacks.ch01.JComponents.hack12;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicMenuItemUI;

public class CustomMenuItemUI extends BasicMenuItemUI {

	public static ComponentUI createUI(JComponent c) {
		return new CustomMenuItemUI();
	}

	public void paint(Graphics g, JComponent comp) {
		// paint to the buffered image
		BufferedImage bufimg = new BufferedImage(comp.getWidth(), comp.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = bufimg.createGraphics();
		// restore the foreground color in case the super class needs it
		g2.setColor(g.getColor());
		super.paint(g2, comp);
		// do an alpha composite
		Graphics2D gx = (Graphics2D) g;
		gx.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
		gx.drawImage(bufimg, 0, 0, null);
	}

}
