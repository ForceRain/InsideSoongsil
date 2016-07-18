import java.awt.geom.Line2D;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import java.util.Random;
import java.awt.Color;
import java.awt.BasicStroke;

public class LineClass extends JComponent
{
	public void paintComponent(Graphics g)
	{
		Graphics2D g2= (Graphics2D) g;
		Random re=new Random();
		
		int count=1+re.nextInt(10);
		for (int i=0;i<count;i++)
		{
			double x1,x2,y1,y2;
			x1=500*re.nextDouble();
			y1=500*re.nextDouble();
			x2=500*re.nextDouble();
			y2=500*re.nextDouble();
			
			Line2D.Double Line=new Line2D.Double(x1,y1,x2,y2);
			g2.setColor(new Color(re.nextInt(256),re.nextInt(256),re.nextInt(256)));
			g2.setStroke(new BasicStroke(10*re.nextFloat()));
			g2.draw(Line);
		}
	}
}
