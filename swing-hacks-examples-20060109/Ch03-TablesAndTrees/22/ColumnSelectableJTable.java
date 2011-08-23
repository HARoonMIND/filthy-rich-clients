import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;

public class ColumnSelectableJTable extends JTable {

    public ColumnSelectableJTable (Object[][] items, Object[] headers) {
        super (items, headers);
        setColumnSelectionAllowed (true);
        setRowSelectionAllowed (false);
        // set up action listener on table header
        final JTableHeader header = getTableHeader();
        header.addMouseListener (new MouseAdapter() {
                public void mouseReleased (MouseEvent e) {
                    /*
                    System.out.println ("mouseReleased: " + e);
                    System.out.println ("col = " +
                                        getColumnModel().getColumn(header.columnAtPoint (e.getPoint())).getIdentifier());
                    */
                    if (! e.isShiftDown())
                        clearSelection();
                    int pick = header.columnAtPoint(e.getPoint());
                    addColumnSelectionInterval (pick, pick);
                }
            });
        
    }

}
