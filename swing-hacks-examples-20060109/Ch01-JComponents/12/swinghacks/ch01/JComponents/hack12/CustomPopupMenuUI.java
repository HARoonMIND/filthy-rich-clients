package swinghacks.ch01.JComponents.hack12;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.Popup;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicPopupMenuUI;

public class CustomPopupMenuUI extends BasicPopupMenuUI {

	public static ComponentUI createUI(JComponent c) {
		return new CustomPopupMenuUI();
	}

	public void installUI(JComponent c) {
		super.installUI(c);
		popupMenu.setOpaque(false);
	}

	public Popup getPopup(JPopupMenu popup, int x, int y) {
		Popup pp = super.getPopup(popup, x, y);
		JPanel panel = (JPanel) popup.getParent();
		panel.setOpaque(false);
		return pp;
	}

}
