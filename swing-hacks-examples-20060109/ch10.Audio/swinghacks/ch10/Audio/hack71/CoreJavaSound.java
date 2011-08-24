package swinghacks.ch10.Audio.hack71;
import javax.sound.sampled.*;
import java.io.*;
import javax.swing.*;

public class CoreJavaSound extends Object
    implements LineListener {

    File soundFile;
    JDialog playingDialog;
    Clip clip;

    public static void main (String[] args) {
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        try {
            CoreJavaSound s = new CoreJavaSound (f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CoreJavaSound (File f) 
        throws LineUnavailableException, IOException,
               UnsupportedAudioFileException {
        soundFile = f;
        // prepare a dialog to display while playing
        JOptionPane pane = new JOptionPane ("Playing " + f.getName(),
                                            JOptionPane.PLAIN_MESSAGE);
        playingDialog = pane.createDialog (null, "Application Sound");
        playingDialog.pack();

        // get and play sound
        Line.Info linfo = new Line.Info (Clip.class);
        Line line = AudioSystem.getLine (linfo);
        clip = (Clip) line;
        clip.addLineListener (this);
        AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
        clip.open (ais);
        clip.start();
    }

    // LineListener
    public void update (LineEvent le) {
        LineEvent.Type type = le.getType();
        if (type == LineEvent.Type.OPEN) {
            System.out.println ("OPEN");
        } else if (type == LineEvent.Type.CLOSE) {
            System.out.println ("CLOSE");
            System.exit (0);
        } else if (type == LineEvent.Type.START) {
            System.out.println ("START");
            playingDialog.setVisible(true);
        } else if (type == LineEvent.Type.STOP) {
            System.out.println ("STOP");
            playingDialog.setVisible(false);
            clip.close();
        }

    }


}
