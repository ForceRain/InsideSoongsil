import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.awt.Font;

public class MovingClock extends JPanel
{
	private Timer time;
	private int x;
	private int y;
	private String line;
	private MouseMoved MmListener;
	private MovingTimer MTListener;
	
	public MovingClock()
	{		
		this.setBackground(Color.GREEN);
		MmListener=new MouseMoved();
		this.addMouseMotionListener(MmListener);
		
		MTListener=new MovingTimer();
		time=new Timer(100,MTListener);
		
		time.start();
	}
		
	class MouseMoved implements MouseMotionListener
	{
		public void mouseDragged(MouseEvent e){}
		public void mouseMoved(MouseEvent e)
		{
			x=e.getX();
			y=e.getY();
			repaint();
		}
	}
	
	class MovingTimer implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			repaint();
		}
	}
	
	public String outHour()
	{
		Date date=new Date();
		
		String out=Integer.toString(date.getHours())+" : "+Integer.toString(date.getMinutes())+" : "+Integer.toString(date.getSeconds());
		return out;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;
		
		g2.setBackground(Color.GREEN);
		
		line=outHour();
		
		g2.setFont(new Font(line,Font.BOLD,45));
		g2.setColor(Color.RED);
		g2.drawString(line,x,y);
	}
}
