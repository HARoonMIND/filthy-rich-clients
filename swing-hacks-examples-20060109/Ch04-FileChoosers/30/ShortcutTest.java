import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class ShortcutTest {
    public static void main(String[] args) throws Exception {
        FileSystemView fsv = new ShortcutFileSystemView();
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSystemView(fsv);
        chooser.setFileView(new ShortcutFileView());
        chooser.showOpenDialog(null);
    }
}

