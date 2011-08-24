package swinghacks.ch02.ListsAndCombos.hack20;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

public class CBTest {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Hack 4: Lists Aware Combo Box");
		Container root = frame.getContentPane();
		root.setLayout(new BoxLayout(root, BoxLayout.X_AXIS));

		// List combo box
		final List list = new ArrayList();
		list.add("Blinky");
		list.add("Pinky");
		list.add("Inky");

		final ListComboBoxModel mod2 = new ListComboBoxModel(list);
		JComboBox cb2 = new JComboBox();
		cb2.setModel(mod2);
		root.add(cb2);

		final JButton bt2 = new JButton("Add Item");
		bt2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				list.add("Clyde");
				mod2.actionPerformed(new ActionEvent(bt2, 0, "update"));

			}
		});
		root.add(bt2);

		// Map Combo Box
		final Map map = new HashMap();
		map.put("Red", "#ff0000");
		map.put("Green", "#00ff00");
		map.put("Blue", "#0000ff");

		final MapComboBoxModel mod3 = new MapComboBoxModel(map);
		final JComboBox cb3 = new JComboBox();
		cb3.setModel(mod3);
		root.add(cb3);
		final JButton bt3 = new JButton("Test Selection");
		bt3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				System.out.println("Human color: " + cb3.getSelectedItem());
				System.out.println("Computer color: " + mod3.getValue(cb3.getSelectedIndex()));
			}
		});
		root.add(bt3);

		// show the frame
		frame.pack();
		frame.setVisible(true);
	}
}
