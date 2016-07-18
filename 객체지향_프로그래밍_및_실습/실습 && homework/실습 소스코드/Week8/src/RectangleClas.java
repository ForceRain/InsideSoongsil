import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.Graphics;
import javax.swing.JComponent;
import java.util.Random;
import java.awt.Color;
import java.awt.BasicStroke;

public class RectangleClas extends JComponent
{
	public void paintComponent(Graphics g)
	{
		Graphics2D g2= (Graphics2D) g;
		Random re=new Random();
		
		int count=1+re.nextInt(10);
		for (int i=0;i<count;i++)
		{
			int x,y,width,height;
			x=re.nextInt(500);
			y=re.nextInt(500);
			width=re.nextInt(100);
			height=re.nextInt(100);
			
			Rectangle rec=new Rectangle(x,y,width,height);
			g2.setColor(new Color(re.nextInt(256),re.nextInt(256),re.nextInt(256)));
			g2.setStroke(new BasicStroke(10*re.nextFloat()));
			g2.draw(rec);
			
		}
	}
}
