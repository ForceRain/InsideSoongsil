import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.Graphics;

public class TestPanel extends JPanel
{
	public TestPanel()
	{
		this.getWidth();
		this.getHeight();
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2=(Graphics2D)g;
		
		System.out.println("Called!");
	}
}
