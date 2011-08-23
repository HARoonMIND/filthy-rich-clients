import java.io.File;
import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;

public class ShortcutFileSystemView extends FileSystemView {
    
    
    /* =================== FileSystemView implementation ===================== */
    public Boolean isTraversable(File f) {
        if(isDirLink(f)) {
            return new Boolean(true);
        }
        return super.isTraversable(f);
    }
    
    public File[] getFiles(File dir, boolean useFileHiding) {
        if(isDirLink(dir)) {
            dir =  getRealFile(dir);
        }
        
        return super.getFiles(dir,useFileHiding);
    }
    
    
    /* =================== FileSystemView implementation ===================== */
    private boolean isDirLink(File f) {
        try {
            if(f.getName().toLowerCase().endsWith(".lnk")) {
                if(new LnkParser(f).isDirectory()) {
                    return true;
                }
            }
        } catch (Exception ex) {
            System.out.println("ex: " + ex);
            ex.printStackTrace();
        }
        return false;
    }
    
    private File getRealFile(File file) {
       try {
           return new File(new LnkParser(file).getRealFilename());
       } catch (Exception ex) {
           System.out.println("ex: " + ex);
           ex.printStackTrace();
           return null;
       }
    }
    
    
    public File createNewFolder(File dir) {
        return null;
    }

    
    public static void p(String str) {
        System.out.println(str);
    }
    
}

