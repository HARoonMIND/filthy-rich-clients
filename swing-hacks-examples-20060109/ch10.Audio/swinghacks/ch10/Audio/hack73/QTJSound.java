package swinghacks.ch10.Audio.hack73;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

//import quicktime.std.*;
//import quicktime.std.clocks.*;
//import quicktime.std.movies.*;
//import quicktime.*;
//import quicktime.io.*;
//import quicktime.app.time.*;

public class QTJSound extends Object {

    File soundFile;
    JDialog playingDialog;
    Movie movie;

    public static void main (String[] args) {
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        try {
            QTJSound s = new QTJSound (f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public QTJSound (File f) 
        throws QTException {
        soundFile = f;
        // prepare a dialog to display while playing
        JOptionPane pane = new JOptionPane ("Playing " + f.getName(),
                                            JOptionPane.PLAIN_MESSAGE);
        playingDialog = pane.createDialog (null, "QTJ Sound");
        playingDialog.pack();

        // get and play sound
        QTSession.open();
        QTFile qtf = new QTFile (f);
        OpenMovieFile omf = OpenMovieFile.asRead (qtf);
        movie = Movie.fromFile (omf);
        MyDemoCloser closer = new MyDemoCloser (movie);
        TaskAllMovies.addMovieAndStart ();
        movie.start();
        playingDialog.setVisible(true);
    }

    class MyDemoCloser extends ExtremesCallBack {

        public MyDemoCloser (Movie m) throws QTException {
            super (m.getTimeBase(),
                   StdQTConstants.triggerAtStop);
            callMeWhen();
        }
        public void execute() {
            playingDialog.setVisible (false);
            System.out.println ("dialog closed");
            // note: this can hang on Windows - consider
            // using QTSession.exitMovies() instead
            QTSession.close();
            System.out.println ("closed QTSession");
            System.exit(0);
        }

    }

}
