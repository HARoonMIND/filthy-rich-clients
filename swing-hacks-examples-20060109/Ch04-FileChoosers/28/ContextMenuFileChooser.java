import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class ContextMenuFileChooser extends JFileChooser {
    
    protected Component right_click_pane;

    public ContextMenuFileChooser() {
        super();

        JPopupMenu popup = new JPopupMenu();
        popup.add(new DeleteAction(this));
        popup.add(new NewFolderAction(this));
        popup.setLightWeightPopupEnabled(false);
        
        right_click_pane = new RightClickGlassPane(this,popup) {
            protected void redispatchMouseEvent(MouseEvent e, boolean repaint) {
                Component component = getRealComponent(e.getPoint());
                if(component == null) { return; }
                String chooser_class = "javax.swing.plaf.metal.MetalFileChooserUI$5";
                if(component.getClass().getName().equals(chooser_class)) {
                    super.redispatchMouseEvent(e,repaint);
                } else {
                    doDispatch(e);
                }
            }
        };
        
        setFileSelectionMode(FILES_AND_DIRECTORIES);
    }

    protected JDialog createDialog(Component parent) {
        JDialog dialog = super.createDialog(parent);
        
        // create the right click glass pane.
        dialog.setGlassPane(right_click_pane);
        right_click_pane.setVisible(true);
        
        return dialog;
    }
    
    public static void main(String[] args) {
        final JFileChooser jfc = new ContextMenuFileChooser();
        jfc.showOpenDialog(null);
        System.exit(0);
    }
    
    public static void p(String str) {
        System.out.println(str);
    }
    
}


class DeleteAction extends AbstractAction {
    protected JFileChooser chooser;
    public DeleteAction(JFileChooser chooser) {
        super("Delete");
        this.chooser = chooser;
    }
    public void actionPerformed(ActionEvent evt) {
        File file = chooser.getSelectedFile();
        if(file != null) {
            file.delete();
            chooser.rescanCurrentDirectory();
        }
    }
}


class NewFolderAction extends AbstractAction {
    protected JFileChooser chooser;
    public NewFolderAction(JFileChooser chooser) {
        super("New Folder");
        this.chooser = chooser;
    }
    public void actionPerformed(ActionEvent evt) {
        File cwd = chooser.getCurrentDirectory();
        if(cwd != null) {
            File new_dir = new File(cwd,"New Folder");
            new_dir.mkdir();
            chooser.rescanCurrentDirectory();
        }
    }
}
