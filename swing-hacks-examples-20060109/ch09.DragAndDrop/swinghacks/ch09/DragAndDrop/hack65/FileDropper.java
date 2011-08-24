package swinghacks.ch09.DragAndDrop.hack65;
import java.awt.*;
import javax.swing.*;
import java.awt.datatransfer.*;
import javax.swing.filechooser.*;
import java.io.*;
import java.awt.dnd.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.image.*;

public class FileDropper {

    /*
    
        //create frame and label and text editor and quit corner
        //create drag support to filesystem to provide a file
        //set proper icon and add file image (resized properly)
        ??fix image translucency problem
        //adjust cursor when over valid or invalid drop point.
    */
    
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Drag and Drop File Hack");
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        
        FileSystemView fsv = FileSystemView.getFileSystemView();
        Icon icon = fsv.getSystemIcon(File.createTempFile("myfile.",".txt"));
		System.out.println("icon = " + icon);
        //ImageIcon iicn = (ImageIcon)icon;
        
        frame.getContentPane().setLayout(new BorderLayout());
        JTextArea text = new JTextArea();

        JLabel label = new JLabel("myfile.txt",icon,SwingConstants.CENTER);
        DragSource ds = DragSource.getDefaultDragSource();
        DragGestureRecognizer dgr = ds.createDefaultDragGestureRecognizer(
            label,
            DnDConstants.ACTION_MOVE,
            new FileDragGestureListener(text));
            
        frame.getContentPane().add("North",label);
        frame.getContentPane().add("Center",text);
        
        frame.pack();
        frame.setSize(400,300);
        frame.setVisible(true);
    }
}


class FileDragGestureListener extends DragSourceAdapter implements DragGestureListener {
    JTextArea text;
    public FileDragGestureListener(JTextArea text) {
        this.text = text;
    }
    Cursor cursor;
    public void dragGestureRecognized(DragGestureEvent evt) {
        try { 
            
            // generate the temp file
            File temp_dir = File.createTempFile("tempdir",".dir",null);
            File temp = new File(temp_dir.getParent(),"myfile.txt");
            FileOutputStream out = new FileOutputStream(temp);
            out.write(text.getText().getBytes());
            out.close();
    
            // get the right icon
            FileSystemView fsv = FileSystemView.getFileSystemView();
            Icon icn = fsv.getSystemIcon(temp);
            
            // we could cast to an image icon, but it might not be one.
            // painting to a buffer first also solves the problem of passing in the
            // the right sized buffer because the cursor might scale it
            // convert to the right sized image
            Toolkit tk = Toolkit.getDefaultToolkit();
            Dimension dim = tk.getBestCursorSize(icn.getIconWidth(),icn.getIconHeight());
            BufferedImage buff = new BufferedImage(dim.width,dim.height,BufferedImage.TYPE_INT_ARGB);
            icn.paintIcon(text,buff.getGraphics(),0,0);
            
            // set up drag image
            if(DragSource.isDragImageSupported()) {
                evt.startDrag(DragSource.DefaultCopyDrop, buff, new Point(0,0),
                        new TextFileTransferable(temp),
                        this);
            } else {
                cursor = tk.createCustomCursor(buff,new Point(0,0),"billybob");
                evt.startDrag(cursor, null, new Point(0,0),
                        new TextFileTransferable(temp),
                        this);
            }
            
        } catch (IOException ex) {
            System.out.println("exception: " + ex.getMessage());
        }
    }
    
    
    public void dragEnter(DragSourceDragEvent evt) {
        DragSourceContext ctx = evt.getDragSourceContext();
        //System.out.println("doing a move: " + evt.getDropAction());
        ctx.setCursor(cursor);
    }
    
    public void dragExit(DragSourceEvent evt) {
        //        p("exit");
        DragSourceContext ctx = evt.getDragSourceContext();
        ctx.setCursor(DragSource.DefaultCopyNoDrop);
    }
    
    /*
    public void dragOver(DragSourceDragEvent evt) {
        
    }
    
    public void dragDropEnd(DragSourceDropEvent evt) {
        //p("drag drop end");
    }
    */
    
    public static void p(String str) {
        System.out.println(str);
    }
}

// create a transferable for the right data flavor
class TextFileTransferable implements Transferable {
    File temp;
    
    public TextFileTransferable(File temp) throws IOException {
        this.temp = temp;
    }
    
    public Object getTransferData(DataFlavor flavor) {
        p("get trans data called");
        List list = new ArrayList();
        list.add(temp);
        return list;
    }
    
    public DataFlavor[] getTransferDataFlavors() {
        DataFlavor[] df = new DataFlavor[1];
        df[0] = DataFlavor.javaFileListFlavor;
        return df;
    }
    
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        if(flavor == DataFlavor.javaFileListFlavor) {
            return true;
        }
        return false;
    }
    
    public static void p(String str) {
        System.out.println(str);
    }
}



