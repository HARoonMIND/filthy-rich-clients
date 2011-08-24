package swinghacks.ch10.Audio.hack75;
import javax.swing.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.awt.*;
import java.io.*;

public class WaveformDisplaySimulator {

    public static void main(String[] args) {
        try {

            JFrame frame = new JFrame("Waveform Display Simulator");
            frame.setBounds(200,200, 500, 350);

            File file = new File(args[0]);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream (new FileInputStream (file)));

            WaveformPanelContainer container = new WaveformPanelContainer();
            container.setAudioToDisplay(audioInputStream);

            frame.getContentPane().setLayout(new BorderLayout());
            frame.getContentPane().add(container, BorderLayout.CENTER);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.show();
            frame.validate();
            frame.repaint();


        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
