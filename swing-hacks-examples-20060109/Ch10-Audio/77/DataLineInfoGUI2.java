import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.sound.sampled.*;


public class DataLineInfoGUI2 extends JPanel {

    PCMFilePlayerLeveler player;
    JButton startButton;

    public DataLineInfoGUI2 (File f) {
        super();
        try {
            player = new PCMFilePlayerLeveler (f);
        } catch (Exception ioe) {
            add (new JLabel ("Error: " +
                             ioe.getMessage()));
            return;
        }
        DataLine line = player.getLine();
        // layout
        // line 1: name
        setLayout (new BoxLayout (this, BoxLayout.Y_AXIS));
        add (new JLabel ("File:  " + 
                         player.getFile().getName()));
        // line 2: levels
        add (new DataLineLevelMeter (line));
        // line 3: format info as textarea
        AudioFormat format = line.getFormat();
        JTextArea ta = new JTextArea();
        ta.setBorder (new TitledBorder ("Format"));
        ta.append ("Encoding: " + 
                   format.getEncoding().toString() + "\n");
        ta.append ("Bits/sample: " +
                   format.getSampleSizeInBits() + "\n");
        ta.append ("Channels: " +
                   format.getChannels() + "\n");
        ta.append ("Endianness: " + 
                   (format.isBigEndian() ? " big " : "little") + "\n");
        ta.append ("Frame size: " + 
                   format.getFrameSize() + "\n");
        ta.append ("Frame rate: " +
                   format.getFrameRate() + "\n");
        add (ta);

        // now start playing
        player.start();

    }

    public static void main (String[] args) {
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile();
        DataLineInfoGUI2 demo = 
            new DataLineInfoGUI2 (file);
        
        JFrame f = new JFrame ("JavaSound info");
        f.getContentPane().add (demo);
        f.pack();
        f.setVisible(true);
    }


    class DataLineLevelMeter extends JPanel {
        DataLine line;
        float level = 0.0f;
        public DataLineLevelMeter (DataLine l) {
            line = l;
            Timer timer =
                new Timer (50,
                           new ActionListener (){
                               public void actionPerformed (ActionEvent e) {
                                   // level = line.getLevel();
                                   level = player.getLevel();
                                   repaint();
                               }
                           });
            timer.start();
        }
        public void paint (Graphics g) {
            Dimension d = getSize();
            // clear component
            g.clearRect (0, 0, d.width, d.height);
            // paint
            g.setColor (Color.green);
            int meterWidth = (int) (level * (float) d.width);
            /*
            System.out.println ("level = " + level +
                                ", meterWidth = " + meterWidth);
            */
            g.fillRect (0, 0, meterWidth, d.height);
        }
        
    }

}
