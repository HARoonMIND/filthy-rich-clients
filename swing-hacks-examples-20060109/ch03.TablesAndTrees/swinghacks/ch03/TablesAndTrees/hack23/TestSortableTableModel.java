package swinghacks.ch03.TablesAndTrees.hack23;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class TestSortableTableModel extends JPanel implements ActionListener {

	DefaultTableModel myModel;
	SortableTableModel mySortableModel;
	JButton sort1, sort2, sort3, bonus;

	static Object[] headers = { "Letter", "Number", "Color" };
	static Object[][] data = { { "A", new Integer(2), Color.gray.darker().darker() }, { "B", new Integer(3), Color.gray }, { "C", new Integer(1), Color.gray.darker() }, };

	static Object[] bonusData = {
			// "D", "0", Color.red  // this is the (buggy) book version
			"D", new Integer(0), Color.red };

	public TestSortableTableModel(DefaultTableModel m) {
		super(new BorderLayout());
		myModel = m;
		mySortableModel = new SortableTableModel(myModel);
		mySortableModel.setComparatorForColumn(new MyColorComparator(), 2);
		JTable table = new JTable(mySortableModel);
		table.setDefaultRenderer(java.awt.Color.class, new ColorRenderer());
		JScrollPane scroller = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		table.setPreferredScrollableViewportSize(new Dimension(400, 200));
		setLayout(new BorderLayout());
		add(scroller, BorderLayout.CENTER);
		// add sort buttons
		JPanel buttonPanel = new JPanel();
		sort1 = new JButton("Sort 1");
		buttonPanel.add(sort1);
		sort1.addActionListener(this);
		sort2 = new JButton("Sort 2");
		buttonPanel.add(sort2);
		sort2.addActionListener(this);
		sort3 = new JButton("Sort 3");
		buttonPanel.add(sort3);
		sort3.addActionListener(this);
		bonus = new JButton("More data");
		buttonPanel.add(bonus);
		bonus.addActionListener(this);
		add(buttonPanel, BorderLayout.SOUTH);

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == sort1) {
			mySortableModel.setSortColumn(0);
		} else if (e.getSource() == sort2) {
			mySortableModel.setSortColumn(1);
		} else if (e.getSource() == sort3) {
			mySortableModel.setSortColumn(2);
		} else if (e.getSource() == bonus) {
			myModel.addRow(bonusData);
		}

	}

	public static void main(String[] args) {
		DefaultTableModel aModel = new DefaultTableModel(data, headers);
		JFrame frame = new JFrame("Sortable Table");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new TestSortableTableModel(aModel), BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}

	class MyColorComparator implements Comparator {
		float[] hsb = new float[3];

		public int compare(Object o1, Object o2) {
			if ((!(o1 instanceof Color)) || (!(o2 instanceof Color)))
				return 0;
			else {
				Color c1 = (Color) o1;
				Color c2 = (Color) o2;
				Color.RGBtoHSB(c1.getRed(), c1.getGreen(), c1.getBlue(), hsb);
				float bright1 = hsb[2];
				Color.RGBtoHSB(c2.getRed(), c2.getGreen(), c2.getBlue(), hsb);
				float bright2 = hsb[2];
				if (bright1 == bright2)
					return 0;
				else
					return ((bright1 - bright2) < 0) ? -1 : 1;
			}
		}

		public boolean equals(Object obj) {
			return super.equals(obj);
		}
	}

	class ColorRenderer extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
			Component returnMe = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

			// background only version

			if (value instanceof Color) {
				Color color = (Color) value;
				returnMe.setBackground(color);
				if (returnMe instanceof JLabel) {
					JLabel jl = (JLabel) returnMe;
					jl.setOpaque(true);
					jl.setText("");
				}
			}
			return returnMe;
		}

	}

}
