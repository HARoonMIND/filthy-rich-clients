import javax.swing.*;
import javax.swing.table.*;

public class TestColumnResizer {

    final static Object[][] TABLE_DATA = {
        {new Integer(1), "ONJava", "http://www.onjava.com/"}, 
        {new Integer(2), "Joshy's Site", "http://www.joshy.org/"}, 
        {new Integer(3), "Anime Weekend Atlanta", "http://www.awa-con.com/"},
        {new Integer(4), "QTJ book", 
           "http://www.oreilly.com/catalog/quicktimejvaadn/"}
    };
    

    final static String[] COLUMN_NAMES = {
        "Count", "Name", "URL"
    };

    public static void main (String[] args) {
        // 142 mac l&f has a header bug - force metal for today
        try {
            UIManager.setLookAndFeel (
                  UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) { e.printStackTrace();}

        DefaultTableModel mod =
            new DefaultTableModel (TABLE_DATA, COLUMN_NAMES);
        JTable table = new JTable (mod);
        JScrollPane pane =
            new JScrollPane (table,
                             ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                             ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        JFrame frame = new JFrame ("JTable Column Widths");
        frame.getContentPane().add (pane);
        frame.pack();
        frame.setVisible (true);

        try {
            Thread.sleep (5000);
        } catch (Exception e) { e.printStackTrace(); }

        // now get smart about col widths
        final JTable fTable = table;
        SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    ColumnResizer.adjustColumnPreferredWidths (fTable);
                    fTable.revalidate();
                }
            });
    }

}
