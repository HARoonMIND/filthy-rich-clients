package swinghacks.ch12.Miscellany.hack92;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

public class AWTBlockDemo extends JFrame {

	JButton blockButton, dontBlockButton;
	JMenuItem blockMenuItem, dontBlockMenuItem, quitMenuItem;
	JTextField urlField;
	JTextArea contentArea;
	final static String DEFAULT_URL = "http://java.sun.com/j2se/1.4.2/docs/api/java/awt/Component.html";
	Thread loaderThread;

	public AWTBlockDemo() {
		super("AWT Thread Blocking");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initMainLayout();
		initMenus();
		initActions();
	}

	private void initMainLayout() {
		urlField = new JTextField(DEFAULT_URL, 60);
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		topPanel.add(urlField);
		JPanel buttonPanel = new JPanel();
		blockButton = new JButton("Load (blocking)");
		dontBlockButton = new JButton("Load (non-blocking)");
		buttonPanel.add(blockButton);
		buttonPanel.add(dontBlockButton);
		topPanel.add(buttonPanel);
		contentArea = new JTextArea(25, 60);
		JScrollPane scroller = new JScrollPane(contentArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(topPanel, BorderLayout.NORTH);
		getContentPane().add(scroller, BorderLayout.CENTER);
	}

	private void initMenus() {
		JMenuBar bar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		blockMenuItem = new JMenuItem("Load (blocking)");
		dontBlockMenuItem = new JMenuItem("Load (non-blocking)");
		fileMenu.add(blockMenuItem);
		fileMenu.add(dontBlockMenuItem);
		fileMenu.addSeparator();
		quitMenuItem = new JMenuItem("Quit");
		fileMenu.add(quitMenuItem);
		bar.add(fileMenu);
		setJMenuBar(bar);
	}

	private void initActions() {
		quitMenuItem.addActionListener(new QuitAction());
		BlockingLoadAction blocker = new BlockingLoadAction();
		blockButton.addActionListener(blocker);
		blockMenuItem.addActionListener(blocker);
		NonBlockingLoadAction nonBlocker = new NonBlockingLoadAction();
		dontBlockButton.addActionListener(nonBlocker);
		dontBlockMenuItem.addActionListener(nonBlocker);
	}

	public static void main(String[] args) {
		AWTBlockDemo awtbd = new AWTBlockDemo();
		awtbd.pack();
		awtbd.setVisible(true);
	}

	public void loadURL(boolean useWorker) {
		try {
			URL url = new URL(urlField.getText());
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer sbuf = new StringBuffer();
			char[] buffy = new char[16 * 1024];
			int bytesRead = 0;
			while ((bytesRead = in.read(buffy, 0, buffy.length)) > -1) {
				sbuf.append(buffy, 0, bytesRead);
				// if your net connection is too fast to see blocking
				// add the following here:
				// Thread.sleep (50);
			}
			if (!useWorker) {
				contentArea.setText(sbuf.toString());
				contentArea.setCaretPosition(0);
			} else {
				final StringBuffer finalSBuf = sbuf;
				Thread worker = new Thread() {
					public void run() {
						contentArea.setText(finalSBuf.toString());
						contentArea.setCaretPosition(0);
					}
				};
				SwingUtilities.invokeLater(worker);
			}
		} catch (Exception e) {
			CharArrayWriter writer = new CharArrayWriter();
			e.printStackTrace(new PrintWriter(writer));
			contentArea.setText(writer.toString());
			contentArea.setCaretPosition(0);
		}
	}

	class QuitAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	class BlockingLoadAction extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			// note that threaded version doesn't offer a means of
			// being interrupted so it refuses second launch instead
			if (loaderThread != null)
				return;
			loadURL(false);
		}
	}

	class NonBlockingLoadAction extends AbstractAction implements Runnable {
		// note that this doesn't offer a means of being interrupted
		// so it refuses second launch instead
		public void actionPerformed(ActionEvent e) {
			if (loaderThread != null)
				return;
			loaderThread = new Thread((Runnable) this);
			loaderThread.start();
		}

		public void run() {
			loadURL(true);
			loaderThread = null;
		}
	}

}
