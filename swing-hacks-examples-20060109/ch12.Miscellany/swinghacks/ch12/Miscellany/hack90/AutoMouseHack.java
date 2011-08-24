package swinghacks.ch12.Miscellany.hack90;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

public class AutoMouseHack implements ActionListener {
    public JButton info, new_dir, delete;
    
    public  void createDemo() {
        JFrame frame = new JFrame("File Flipper");
        
        String[] dirs = {".","..","build","docs","lib","src","www"};
        String[] files = {"build.xml","readme.txt"};
        JList dir_list = new JList(dirs);
        JList files_list = new JList(files);
        
        JSplitPane split = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            dir_list,files_list);
        
        info = new JButton("Info");
        new_dir = new JButton("New Dir");
        delete = new JButton("Delete");
        
        JPanel toolbar = new JPanel();
        toolbar.setLayout(new FlowLayout());
        toolbar.add(info);
        toolbar.add(new_dir);
        toolbar.add(delete);
        
        
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add("North",toolbar);
        frame.getContentPane().add("Center",split);
        
        frame.pack();
        frame.show();
    }
    
    JButton showme;
    public void createHelp() throws IOException {
        JFrame frame = new JFrame("Help");
        JButton close = new JButton("Close");
        showme = new JButton("Show Me");
        showme.addActionListener(this);
        
        JEditorPane html = new JEditorPane("text/html",
        "<html><body>" +
        "<p>Use the toolbar buttons to interact with the current window.</p>"+
        "<p><b>Info</b> display properties of the current file.</p>" +
        "<p><b>New Dir</b> create a new directory</p>"+
        "<p><b>Delete</b> delete the currently selected file</p>" +
        "<p>click <i><b>Show Me</b></i> below to see how it works</p>"
            );
        
        
        
        frame.getContentPane().setLayout(new BorderLayout(  ));
        frame.getContentPane().add("North",close);
        frame.getContentPane().add("Center",html);
        frame.getContentPane().add("South",showme);
        
        frame.pack();
        frame.setLocation(400,50);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) throws Exception {
        AutoMouseHack hack = new AutoMouseHack();
        hack.createDemo();
        hack.createHelp();
    }

    
    public void actionPerformed(ActionEvent evt) {
    try {
        moveMouse(showme, info, 2000);
        moveMouse(info, info, 1000);
        moveMouse(info, new_dir, 1000);
        moveMouse(new_dir, new_dir, 1000);
        moveMouse(new_dir, delete, 1000);
        moveMouse(delete, delete, 1000);
        moveMouse(delete, showme, 500);
    } catch (Exception ex) {
        System.out.println(""+ex);
    }
    }
    
    
    public void moveMouse(JComponent start, JComponent end, final int duration) throws Exception {
        final Robot robot = new Robot();
        
        // get middle of start
        final Point start_coords = start.getLocationOnScreen();
        start_coords.translate(start.getWidth()/2,
            start.getHeight()/2);
            
        // get middle of end
        final Point end_coords = end.getLocationOnScreen();
        end_coords.translate(end.getWidth()/2,
            end.getHeight()/2);
        
        // create interpolation point and offsets
        int steps = duration/50;
        //Point current = new Point(start_coords);
        int distx = (end_coords.x - start_coords.x);
        int disty = (end_coords.y - start_coords.y);
        
        // move the mouse over 10 steps
        for(int i=1; i<=steps; i++) {
            int x = start_coords.x + i*distx/steps;
            int y = start_coords.y + i*disty/steps;
            robot.mouseMove(x,y);
            try { Thread.currentThread().sleep(50);
            } catch (Exception ex) {}
        }
    }
    
    
}
