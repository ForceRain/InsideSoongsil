import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.Graphics;
import javax.swing.JComponent;
import java.util.Random;

public class RectangleCom extends JComponent
{
	public void paintComponent(Graphics g)
	{
		Graphics2D g2=(Graphics2D)g;
		Random ran=new Random();
		
		for (int i=0;i<20;i++)
		{
			Rectangle rec=new Rectangle(ran.nextInt(300),ran.nextInt(300),ran.nextInt(300),ran.nextInt(300));
			
			g2.draw(rec);
		}
	}
}
