package swinghacks.ch11.NativeIntegrationAndPackaging.hack86;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class CustomLaFDefaults {

	public static void main(String[] args) throws Exception {
		MetalLookAndFeel.setCurrentTheme(new InverseTheme());
		UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		/*
		Color bg = Color.green.brighter();
		Color fg = Color.green.darker();
		 UIManager.put("Button.background",bg);
		 UIManager.put("Button.foreground",fg);
		 UIManager.put("Label.background",bg);
		 UIManager.put("Label.foreground",fg);
		 UIManager.put("TextField.background",bg);
		 UIManager.put("TextField.foreground",fg);
		 UIManager.put("Panel.background",bg);
		 UIManager.put("Panel.foreground",fg);
		*/
		/*
		Font font = Font.createFont(Font.TRUETYPE_FONT,
		    new FileInputStream("dungeon.ttf"));
		font = font.deriveFont(Font.BOLD,16f);
		UIManager.put("Label.font",font);
		*/

		/*
		Color trans = new Color(0,0,255,128);
		UIManager.put("Button.foreground",trans);

		Color sysbg = SystemColor.control;
		Color sysfg = SystemColor.controlText;
		UIManager.put("Button.background",sysbg);
		UIManager.put("Button.foreground",sysfg);
		*/
		/*
		Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		UIManager.put("MenuItem.borderPainted", new Boolean(true));
		UIManager.put("MenuItem.border", border);
		UIManager.put("TextField.margin", new Insets(25,25,25,25));
		*/

		JTextArea jta = new JTextArea();
		jta.setText("text\ntext\ntext\ntext\ntext\ntext" + "\ntext\ntext\ntext\ntext\ntext");
		JScrollPane scroll = new JScrollPane(jta);

		JButton button = new JButton("A Button");
		JLabel label = new JLabel("A Label");
		JTextField text = new JTextField("A TextField");

		JMenuBar mb = new JMenuBar();
		JMenu file = new JMenu("File");
		file.add(new JMenuItem("Open"));
		file.add(new JMenuItem("Close"));
		mb.add(file);

		JFrame frame = new JFrame("Custom LaF Defaults");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
		top.add(button);
		top.add(label);
		top.add(text);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add("North", top);
		panel.add("Center", scroll);

		frame.getContentPane().add(panel);
		frame.setJMenuBar(mb);

		frame.pack();
		frame.setSize(300, 200);
		frame.setVisible(true);
	}

}
