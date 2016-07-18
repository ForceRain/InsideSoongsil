import javax.swing.JPanel;
import java.awt.geom.Ellipse2D;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class DrawCircle extends JPanel
{
	private double x;
	private double y;
	private double radius;
	
	public DrawCircle(double x,double y,double radius)
	{
		this.x=x;
		this.y=y;
		this.radius=radius;
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2=(Graphics2D)g;
		
		Ellipse2D.Double obj=new Ellipse2D.Double(x,y,2*radius,2*radius);
		
		g2.draw(obj);
	}
}
