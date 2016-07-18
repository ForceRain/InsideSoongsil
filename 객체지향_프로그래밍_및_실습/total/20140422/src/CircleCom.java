import java.lang.Math;
import javax.swing.JComponent;
import java.awt.geom.Ellipse2D;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

public class CircleCom extends JComponent
{
	public void paintComponent(Graphics g)
	{
		Graphics2D g2=(Graphics2D)g;
		Random re=new Random();
		
		for (int i=0;i<20;i++)
		{
			int radius=re.nextInt(150);
			Ellipse2D.Double ellipse=new Ellipse2D.Double(re.nextInt(300),re.nextInt(300),2*radius,2*radius);
			g2.draw(ellipse);
		}
	}
}
