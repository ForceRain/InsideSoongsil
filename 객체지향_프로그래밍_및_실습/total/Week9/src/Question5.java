import javax.swing.JPanel;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
import java.awt.Color;

public class Question5 extends JPanel
{
	private Random rand;
	private int drawing;
	
	public Question5(String input)
	{
		rand=new Random();
		drawing=Integer.parseInt(input);
		repaint();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;
		
		for (int i=0;i<drawing;i++)
		{
			Rectangle obj=new Rectangle(rand.nextInt(300),rand.nextInt(300),rand.nextInt(300),rand.nextInt(300));
			g2.setColor(new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256)));
			g2.draw(obj);
		}
	}
}
