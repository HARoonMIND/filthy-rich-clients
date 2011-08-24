package swinghacks.ch11.NativeIntegrationAndPackaging.hack85;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class JarResourceLoading extends JFrame implements ActionListener {

	JButton button;
	ImageIcon buttonIcon;
	Clip buhClip;

	public final static String SOUND_PATH = "sounds/buhbuhbuh.aiff";
	public final static String IMAGE_PATH = "images/keagan-buh.jpeg";

	public JarResourceLoading() {
		super("Resources from .jar");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// get image and make button
		URL imageURL = getClass().getClassLoader().getResource(IMAGE_PATH);
		System.out.println("found image at " + imageURL);
		buttonIcon = new ImageIcon(imageURL);
		button = new JButton("Click to Buh!", buttonIcon);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.addActionListener(this);
		getContentPane().add(button);
		// load sound into Clip
		try {
			URL soundURL = getClass().getClassLoader().getResource(SOUND_PATH);
			System.out.println("found sound at " + soundURL);
			Line.Info linfo = new Line.Info(Clip.class);
			Line line = AudioSystem.getLine(linfo);
			buhClip = (Clip) line;
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL);
			buhClip.open(ais);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println("click!");
		if (buhClip != null) {
			buhClip.setFramePosition(0);
			buhClip.start();
		} else
			JOptionPane.showMessageDialog(this, "Couldn't load sound", "Error", JOptionPane.ERROR_MESSAGE);
	}

	public static final void main(String[] args) {
		JFrame frame = new JarResourceLoading();
		frame.pack();
		frame.setVisible(true);
	}

}
