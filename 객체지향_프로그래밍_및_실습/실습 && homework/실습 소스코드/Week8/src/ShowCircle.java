import java.lang.Math;
import javax.swing.JComponent;
import java.awt.geom.Ellipse2D;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class ShowCircle extends JComponent
{
	private int radius;
	
	public ShowCircle(int input)
	{
		radius=input;
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2=(Graphics2D)g;
		
		Ellipse2D.Double ellipse=new Ellipse2D.Double(radius+10,radius+10,2*radius,2*radius);
		
		g2.draw(ellipse);
		
		g2.drawString("Area="+Math.PI*radius*radius,10, 2*radius+50);
		g2.drawString("Diameter="+2*radius,10,2*radius+70);
		g2.drawString("Circumference="+2*radius*Math.PI,10,2*radius+90);
		
		
	}
}
