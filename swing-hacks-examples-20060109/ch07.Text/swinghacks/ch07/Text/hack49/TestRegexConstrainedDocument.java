package swinghacks.ch07.Text.hack49;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TestRegexConstrainedDocument extends JPanel implements ActionListener {

	JTextField regexField, filterField;
	JButton regexButton;
	RegexConstrainedDocument regexDoc;

	public TestRegexConstrainedDocument() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		// top - regex stuff
		JPanel topPanel = new JPanel();
		JLabel rLabel = new JLabel("regex:");
		topPanel.add(rLabel);
		regexField = new JTextField(20);
		topPanel.add(regexField);
		regexButton = new JButton("Set");
		regexButton.addActionListener(this);
		topPanel.add(regexButton);
		add(topPanel);
		// bottom - filterfield
		regexDoc = new RegexConstrainedDocument();
		filterField = new JTextField(regexDoc, "", 50);
		add(filterField);
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println("actionperformed");
		if (e.getSource() == regexButton) {
			System.out.println("regexbutton");
			regexDoc.setPatternByString(regexField.getText());
		}
	}

	public static void main(String[] args) {
		JComponent c = new TestRegexConstrainedDocument();
		JFrame f = new JFrame("Regex filtering");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(c);
		f.pack();
		f.setVisible(true);
	}

}
