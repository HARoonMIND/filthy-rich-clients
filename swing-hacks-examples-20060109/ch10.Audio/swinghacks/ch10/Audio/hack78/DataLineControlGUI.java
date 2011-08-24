package swinghacks.ch10.Audio.hack78;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.sound.sampled.*;

import swinghacks.ch10.Audio.hack76.PCMFilePlayer;


public class DataLineControlGUI extends JPanel {

    PCMFilePlayer player;
    JButton startButton;

    public DataLineControlGUI (File f) {
        super();
        try {
            player = new PCMFilePlayer (f);
        } catch (Exception ioe) {
            add (new JLabel ("Error: " +
                             ioe.getMessage()));
            return;
        }
        DataLine line = player.getLine();
        // layout
        // line 0: name
        setLayout (new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.SOUTH;
        add (new JLabel ("File:  " + 
                         player.getFile().getName()), gbc);
        // subsequent lines: controls
        gbc.gridwidth = 1;
        Control[] controls = line.getControls();
        for (int i=0; i<controls.length; i++) {
            gbc.gridx = 0;
            gbc.gridy++;
            gbc.anchor = GridBagConstraints.EAST;
            add (new JLabel(controls[i].getType().toString()), gbc);
            JComponent controlComp =
                ControlComponentFactory.getComponentFor (controls[i]);
            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            add (controlComp, gbc);
        }

        // now start playing
        player.start();
    }

    public static void main (String[] args) {
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile();
        DataLineControlGUI demo = 
            new DataLineControlGUI (file);
        
        JFrame f = new JFrame ("JavaSound control");
        f.getContentPane().add (demo);
        f.pack();
        f.setVisible(true);
    }


}
