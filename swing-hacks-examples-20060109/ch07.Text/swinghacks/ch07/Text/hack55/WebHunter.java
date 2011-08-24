package swinghacks.ch07.Text.hack55;
import java.io.*;
import java.net.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class WebHunter extends JFrame implements ActionListener {
  private Box centerPane;
  private JTextField sourceFile;

  public WebHunter() {
    super("WebHunter");

    build();

    pack();
    setResizable(false);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
  }

  public void actionPerformed(ActionEvent evt) {
    URL source;
    try {
       source = new URL(sourceFile.getText());
    } catch (MalformedURLException me) {
       JOptionPane.showMessageDialog(this, "Invalid URL.", "Download error", JOptionPane.ERROR_MESSAGE);
       return;
    }

    JFileChooser chooser = new JFileChooser();
    chooser.setSelectedFile(new File(new File(source.getFile()).getName()));
    chooser.showSaveDialog(this);
    File target = chooser.getSelectedFile();

    JProgressBar bar;
    JPanel panel = new JPanel(new GridLayout(3, 2));  

    panel.add(new JLabel("Source: "));
    panel.add(new JLabel(sourceFile.getText()));
    panel.add(new JLabel("Target: "));
    panel.add(new JLabel(target.getAbsolutePath()));
    panel.add(new JLabel("Progress: "));
    panel.add(bar = new JProgressBar());
    panel.setBorder(new EmptyBorder(0, 3, 0, 3));
 
    bar.setStringPainted(true);
   
    centerPane.add(Box.createVerticalStrut(3));
    centerPane.add(new JSeparator());
    centerPane.add(Box.createVerticalStrut(3));
    centerPane.add(panel);
    centerPane.add(Box.createVerticalStrut(3));

    pack();
  }

  private void build() {
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(BorderLayout.NORTH,  buildDownloadPane());
    getContentPane().add(BorderLayout.CENTER, centerPane = Box.createVerticalBox());
  }

  private Container buildDownloadPane() {
    JPanel panel = new JPanel();
    JButton button;

    panel.add(new JLabel("File to download: "));
    panel.add(sourceFile = new JTextField(15));
    panel.add(button = new JButton("Download..."));

    sourceFile.setText("http://jext.free.fr/macos7.jpg");
    button.addActionListener(this);

    return panel;
  }

  public static void main(String[] args) {
    new WebHunter();
  }
}
