package swinghacks.ch01.JComponents.hack04;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CalendarHack extends JPanel {
	protected Image background, highlight, day_img;
	protected SimpleDateFormat month = new SimpleDateFormat("MMMM");
	protected SimpleDateFormat year = new SimpleDateFormat("yyyy");
	protected SimpleDateFormat day = new SimpleDateFormat("d");
	protected Date date = new Date();

	public void setDate(Date date) {
		this.date = date;
	}

	public CalendarHack() {
		background = new ImageIcon("calendar.png").getImage();
		highlight = new ImageIcon("highlight.png").getImage();
		day_img = new ImageIcon("day.png").getImage();
		this.setPreferredSize(new Dimension(300, 280));
	}

	public void paintComponent(Graphics g) {
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(background, 0, 0, null);
		g.setColor(Color.black);
		g.setFont(new Font("SansSerif", Font.PLAIN, 18));
		g.drawString(month.format(date), 34, 36);
		g.setColor(Color.white);
		g.drawString(year.format(date), 235, 36);

		Calendar today = Calendar.getInstance();
		today.setTime(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);
		cal.add(Calendar.DATE, -cal.get(Calendar.DAY_OF_WEEK) + 1);
		for (int week = 0; week < 6; week++) {
			for (int d = 0; d < 7; d++) {
				Image img = day_img;
				Color col = Color.black;
				// only draw if it's actually in this month
				if (cal.get(Calendar.MONTH) == today.get(Calendar.MONTH)) {
					if (cal.equals(today)) {
						img = highlight;
						col = Color.white;
					}
					g.drawImage(img, d * 30 + 46, week * 29 + 81, null);
					g.drawString(day.format(cal.getTime()), d * 30 + 46 + 4, week * 29 + 81 + 20);
				}
				cal.add(Calendar.DATE, +1);
			}
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		CalendarHack ch = new CalendarHack();
		ch.setDate(new Date());
		frame.getContentPane().add(ch);
		frame.setUndecorated(true);

		MoveMouseListener mml = new MoveMouseListener(ch);
		ch.addMouseListener(mml);
		ch.addMouseMotionListener(mml);

		frame.pack();
		frame.setVisible(true);
	}
}
