import java.text.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.filechooser.*;
import javax.swing.plaf.*;

public class DisplayShortcutTest {
    public static void main(String[] args) throws Exception {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileView(new ShortcutFileView());
        chooser.showOpenDialog(null);
    }
}



