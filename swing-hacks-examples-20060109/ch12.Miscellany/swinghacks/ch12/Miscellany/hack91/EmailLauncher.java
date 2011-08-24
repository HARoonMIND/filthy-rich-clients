package swinghacks.ch12.Miscellany.hack91;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EmailLauncher extends MouseAdapter {
	public void mousePressed(MouseEvent evt) {
		if (evt.getClickCount() >= 2) {
			launchEmailReader();
			evt.consume();
		}
	}

	public void launchEmailReader() {
		try {
			Runtime rt = Runtime.getRuntime();
			rt.exec("C:\\Program Files\\Mozilla Thunderbird\\thunderbird.exe");
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
	}
}
