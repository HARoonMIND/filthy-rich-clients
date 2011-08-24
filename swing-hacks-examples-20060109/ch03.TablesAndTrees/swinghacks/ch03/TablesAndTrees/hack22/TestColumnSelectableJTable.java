package swinghacks.ch03.TablesAndTrees.hack22;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class TestColumnSelectableJTable extends Object {

    private static final  Object[][] items= {
        {"Monday", "Cheeseburgers", "French Fries", "Peaches"},
        {"Tuesday", "Catfish", "Rice", "Starfruit"},
        {"Wednesday", "Tortellini", "Garlic Bread", "Pears"},
        {"Thursday", "Chicken", "Potatoes", "Strawberries"},
        {"Friday", "Pizza", null, "Fruit Cocktail"}
    };

    private static final Object[] headers = {
        "Day", "Main course", "Side dish", "Fruit"
    };

    public static void main (String[] args) {
        JFrame f = new JFrame ("Selectable columns");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ColumnSelectableJTable table = 
            new ColumnSelectableJTable(items, headers);
        JScrollPane scroller =
            new JScrollPane (table,
                             ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                             ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        f.getContentPane().add (scroller);
        f.pack();
        f.setVisible(true);
    }



}
