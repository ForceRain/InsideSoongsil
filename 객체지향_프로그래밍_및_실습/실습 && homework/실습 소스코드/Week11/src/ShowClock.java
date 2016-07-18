import javax.swing.Timer;
import java.awt.Graphics2D;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.awt.Font;

public class ShowClock extends JPanel
{
	private ShowTimer STtimer;
	private Timer go;
	private String out;
	
	public ShowClock()
	{
		STtimer=new ShowTimer();
		
		go=new Timer(10,STtimer);
		
		go.start();
	}
	
	class ShowTimer implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			repaint();
		}
	}
	
	public void paintComponent(Graphics g)
	{
		Date now=new Date();
		out=now.toString();
		super.paintComponent(g);
		
		Graphics2D g2=(Graphics2D)g;
		g2.setFont(new Font(out,Font.BOLD,50));
		
		g2.drawString(out,100,100);
	}
}
