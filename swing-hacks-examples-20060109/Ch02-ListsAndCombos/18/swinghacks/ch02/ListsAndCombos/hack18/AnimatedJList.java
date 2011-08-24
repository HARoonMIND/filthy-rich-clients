package swinghacks.ch02.ListsAndCombos.hack18;

import java.awt.Color;
import java.awt.Component;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class AnimatedJList extends JList implements ListSelectionListener {

	static java.util.Random rand = new java.util.Random();

	static Color listForeground, listBackground, listSelectionForeground, listSelectionBackground;
	static float[] foregroundComps, backgroundComps, foregroundSelectionComps, backgroundSelectionComps;

	static {
		UIDefaults uid = UIManager.getLookAndFeel().getDefaults();
		listForeground = uid.getColor("List.foreground");
		listBackground = uid.getColor("List.background");
		listSelectionForeground = uid.getColor("List.selectionForeground");
		listSelectionBackground = uid.getColor("List.selectionBackground");
		foregroundComps = listForeground.getRGBColorComponents(null);
		foregroundSelectionComps = listSelectionForeground.getRGBColorComponents(null);
		backgroundComps = listBackground.getRGBColorComponents(null);
		backgroundSelectionComps = listSelectionBackground.getRGBColorComponents(null);
	}
	public Color colorizedSelectionForeground, colorizedSelectionBackground;

	public static final int ANIMATION_DURATION = 500;
	public static final int ANIMATION_REFRESH = 50;

	public AnimatedJList() {
		super();
		addListSelectionListener(this);
		setCellRenderer(new AnimatedCellRenderer());
	}

	public void valueChanged(ListSelectionEvent lse) {
		if (!lse.getValueIsAdjusting()) {
			HashSet selections = new HashSet();
			// System.out.println ("got last lse");
			for (int i = 0; i < getModel().getSize(); i++) {
				if (getSelectionModel().isSelectedIndex(i))
					selections.add(new Integer(i));
			}
			CellAnimator animator = new CellAnimator(selections.toArray());
			animator.start();
		}
	}

	public static void main(String[] args) {
		JList list = new AnimatedJList();
		DefaultListModel defModel = new DefaultListModel();
		list.setModel(defModel);
		String[] listItems = { "Chris", "Joshua", "Daniel", "Michael", "Don", "Kimi", "Kelly", "Keagan" };
		Iterator it = Arrays.asList(listItems).iterator();
		while (it.hasNext())
			defModel.addElement(it.next());
		// show list
		JScrollPane scroller = new JScrollPane(list, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		JFrame frame = new JFrame("Checkbox JList");
		frame.getContentPane().add(scroller);
		frame.pack();
		frame.setVisible(true);
	}

	class CellAnimator extends Thread {
		Object[] selections;
		long startTime;
		long stopTime;

		public CellAnimator(Object[] s) {
			selections = s;
		}

		public void run() {
			startTime = System.currentTimeMillis();
			stopTime = startTime + ANIMATION_DURATION;
			while (System.currentTimeMillis() < stopTime) {
				colorizeSelections();
				repaint();
				try {
					Thread.sleep(ANIMATION_REFRESH);
				} catch (InterruptedException ie) {
				}
			}
			// one more, at 100% selected color
			colorizeSelections();
			repaint();
		}

		public void colorizeSelections() {
			// calculate % completion relative to start/stop times
			float elapsed = (float) (System.currentTimeMillis() - startTime);
			float completeness = Math.min((elapsed / ANIMATION_DURATION), 1.0f);
			// System.out.println ("completeness = " + completeness);
			// calculate scaled color
			float colorizedForeComps[] = new float[3];
			float colorizedBackComps[] = new float[3];
			for (int i = 0; i < 3; i++) {
				colorizedForeComps[i] = foregroundComps[i] + (completeness * (foregroundSelectionComps[i] - foregroundComps[i]));
				colorizedBackComps[i] = backgroundComps[i] + (completeness * (backgroundSelectionComps[i] - backgroundComps[i]));
			}
			colorizedSelectionForeground = new Color(colorizedForeComps[0], colorizedForeComps[1], colorizedForeComps[2]);
			colorizedSelectionBackground = new Color(colorizedBackComps[0], colorizedBackComps[1], colorizedBackComps[2]);
			// System.out.println ("fore = " +colorizedSelectionForeground);
			// System.out.println ("back = " +colorizedSelectionBackground);
		}
	}

	class AnimatedCellRenderer extends DefaultListCellRenderer {
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
			Component returnMe = super.getListCellRendererComponent(list, value, index, isSelected, hasFocus);
			if (isSelected) {
				returnMe.setForeground(colorizedSelectionForeground);
				returnMe.setBackground(colorizedSelectionBackground);
				/* this might be necessary if you have more
				   elaborate cells
				if (returnMe instanceof Container) {
				    Component[] children =
				        ((Container)returnMe).getComponents ();
				    System.out.println (children.length + " children");
				    for (int i=0;
				         (children != null ) && (i<children.length);
				         i++) {
				        children[i].setForeground (colorizedSelectionForeground);
				    children[i].setBackground (colorizedSelectionBackground);
				    }
				}
				*/
				if (returnMe instanceof JComponent)
					((JComponent) returnMe).setOpaque(true);
			}
			return returnMe;
		}
	}
}
