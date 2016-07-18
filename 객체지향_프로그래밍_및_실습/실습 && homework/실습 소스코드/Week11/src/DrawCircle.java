import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Graphics2D;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.lang.Math;
import java.awt.geom.Ellipse2D;

public class DrawCircle extends JPanel
{
	private int x;
	private int y;
	private double radius;
	private boolean go;
	private MyMouseListener MYListener;
	
	
	public DrawCircle()
	{
		x=0;
		y=0;
		radius=0.0;
		go=false;
		MYListener=new MyMouseListener();
		this.addMouseListener(MYListener);
	}
	
	class MyMouseListener implements MouseListener
	{
		public void mouseClicked(MouseEvent event)
		{
			if (x==0 && y==0)
			{
				x=event.getX();
				y=event.getY();
			}
			else
			{
				int x1=event.getX();
				int y1=event.getY();
				radius=Math.sqrt((x-x1)*(x-x1)+(y-y1)*(y-y1));
				go=true;
			}
			if (go)
			{
				repaint();
			}
		}
		public void mouseEntered(MouseEvent event){}
		public void mouseExited(MouseEvent event){}
		public void mousePressed(MouseEvent event){}
		public void mouseReleased(MouseEvent event){}
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2=(Graphics2D)g;
		
		Ellipse2D.Double circle=new Ellipse2D.Double(x-radius,y-radius,radius*2,radius*2);
		
		g2.draw(circle);
	}
}
