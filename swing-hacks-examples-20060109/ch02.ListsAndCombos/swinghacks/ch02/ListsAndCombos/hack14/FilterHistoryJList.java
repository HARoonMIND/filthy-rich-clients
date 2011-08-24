package swinghacks.ch02.ListsAndCombos.hack14;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.AbstractAction;
import javax.swing.AbstractListModel;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class FilterHistoryJList extends JList {

	private FilterField filterField;
	private int DEFAULT_FIELD_WIDTH = 20;

	public FilterHistoryJList() {
		super();
		setModel(new FilterModel());
		filterField = new FilterField(DEFAULT_FIELD_WIDTH);
		filterField.textField.requestFocus();
	}

	public void setModel(ListModel m) {
		if (!(m instanceof FilterModel))
			throw new IllegalArgumentException();
		super.setModel(m);
	}

	public void addItem(Object o) {
		((FilterModel) getModel()).addElement(o);
	}

	public FilterField getFilterField() {
		return filterField;
	}

	// test filter list
	public static void main(String[] args) {
		String[] listItems = { "Chris", "Joshua", "Daniel", "Michael", "Don", "Kimi", "Kelly", "Keagan" };
		JFrame frame = new JFrame("FilterHistoryJList");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		// populate list
		FilterHistoryJList list = new FilterHistoryJList();
		for (int i = 0; i < listItems.length; i++)
			list.addItem(listItems[i]);
		// add to gui
		JScrollPane pane = new JScrollPane(list, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		frame.getContentPane().add(pane, BorderLayout.CENTER);
		FilterField filterField = list.getFilterField();
		frame.getContentPane().add(filterField, BorderLayout.NORTH);
		frame.pack();
		frame.setVisible(true);
		filterField.textField.requestFocus();
	}

//	public static URL getImage(String filepath) {
//		return FilterHistoryJList.class.getResource(filepath);
//	}

	// inner class to provide filtered model
	class FilterModel extends AbstractListModel {
		ArrayList items;
		ArrayList filterItems;

		public FilterModel() {
			super();
			items = new ArrayList();
			filterItems = new ArrayList();
		}

		public Object getElementAt(int index) {
			if (index < filterItems.size())
				return filterItems.get(index);
			else
				return null;
		}

		public int getSize() {
			return filterItems.size();
		}

		public void addElement(Object o) {
			items.add(o);
			refilter();
		}

		private void refilter() {
			filterItems.clear();
			String term = getFilterField().textField.getText();
			for (int i = 0; i < items.size(); i++)
				if (items.get(i).toString().indexOf(term, 0) != -1)
					filterItems.add(items.get(i));
			fireContentsChanged(this, 0, getSize());
		}
	}

	// inner class provides filter-by-keystroke field
	class FilterField extends JComponent implements DocumentListener, ActionListener {
		LinkedList prevSearches;
		JTextField textField;
		JButton prevSearchButton;
		JPopupMenu prevSearchMenu;

		public FilterField(int width) {
			super();
			setLayout(new BorderLayout());
			textField = new JTextField(width);
			textField.getDocument().addDocumentListener(this);
			textField.addActionListener(this);
			prevSearchButton = new JButton(new ImageIcon(getImage("mag-glass.png")));
			prevSearchButton.setBorder(null);
			prevSearchButton.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent me) {
					popMenu(me.getX(), me.getY());
				}
			});
			add(prevSearchButton, BorderLayout.WEST);
			add(textField, BorderLayout.CENTER);
			prevSearches = new LinkedList();
		}

		public void popMenu(int x, int y) {
			prevSearchMenu = new JPopupMenu();
			Iterator it = prevSearches.iterator();
			while (it.hasNext())
				prevSearchMenu.add(new PrevSearchAction(it.next().toString()));
			prevSearchMenu.show(prevSearchButton, x, y);
		}

		public void actionPerformed(ActionEvent e) {
			// called on return/enter, adds term to prevSearches
			if (e.getSource() == textField) {
				prevSearches.addFirst(textField.getText());
				if (prevSearches.size() > 10)
					prevSearches.removeLast();
			}
		}

		public void changedUpdate(DocumentEvent e) {
			((FilterModel) getModel()).refilter();
		}

		public void insertUpdate(DocumentEvent e) {
			((FilterModel) getModel()).refilter();
		}

		public void removeUpdate(DocumentEvent e) {
			((FilterModel) getModel()).refilter();
		}
	}

	class PrevSearchAction extends AbstractAction {
		String term;

		public PrevSearchAction(String s) {
			term = s;
			putValue(Action.NAME, term);
		}

		public String toString() {
			return term;
		}

		public void actionPerformed(ActionEvent e) {
			getFilterField().textField.setText(term);
			// don't need this - setText fires a DocumentEvent
			// that FilterField handles
			// ((FilterModel)getModel()).refilter();
		}
	}
	
	public static URL getImage(String filepath){
		return FilterHistoryJList.class.getResource(filepath);
	}
}
