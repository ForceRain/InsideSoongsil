import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.Timer;

public class ScreenSaver extends JPanel
{
	private int cnt=0;
	private Timer time;
	
	public ScreenSaver()
	{
		this.setLayout(new BorderLayout());
	
		class TimeFlow implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				cnt++;
				repaint();
			}
		}
		
		TimeFlow timer=new TimeFlow();
		time=new Timer(1000,timer);
		
		time.start();
	}
	
	public void paintComponent(Graphics g)
	{
		Random ran=new Random();
		if (cnt==100)
		{
			cnt=0;
			super.paintComponent(g);
		}
		Graphics2D g2=(Graphics2D)g;
		
		g2.drawLine(ran.nextInt(1200),ran.nextInt(700),ran.nextInt(1200),ran.nextInt(700));
	}
}
