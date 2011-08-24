package swinghacks.ch04.FileChoosers.hack30;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileView;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.FileChooserUI;

public class ShortcutFileView extends FileView {

	public boolean isDirLink(File f) {
		try {
			if (f.getName().toLowerCase().endsWith(".lnk")) {
				LnkParser parser = new LnkParser(f);
				if (parser.isDirectory()) {
					return true;
				}
			}
		} catch (Exception ex) {
			System.out.println("exception: " + ex.getMessage());
			ex.printStackTrace();
		}
		return false;
	}

	public Boolean isTraversable(File f) {
		if (isDirLink(f)) {
			return new Boolean(true);
		}
		return null;
	}

	public Icon getIcon(File f) {
		if (isDirLink(f)) {
			/* all of this nonsense is to get to the 
			default file view */
			System.out.println("get icon for: " + f);
			JFileChooser chooser = new JFileChooser();
			ComponentUI ui = UIManager.getUI(chooser);
			System.out.println("got : " + ui);
			FileChooserUI fcui = (FileChooserUI) ui;
			fcui.installUI(chooser);
			FileView def = fcui.getFileView(chooser);

			// get the standard icon for a folder
			File tmp = new File("C:\\windows");
			Icon folder = def.getIcon(tmp);
			int w = folder.getIconWidth();
			int h = folder.getIconHeight();

			// create a buffered image the same size as the icon
			Image img = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
			Graphics g = img.getGraphics();

			// draw the normal icon
			folder.paintIcon(chooser, g, 0, 0);
			// draw the shortcut image on top of the icon
			Image shortcut = new ImageIcon("shortcut.png").getImage();
			g.drawImage(shortcut, 0, 0, null);
			g.dispose();
			return new ImageIcon(img);
		}
		return super.getIcon(f);
	}

}
