import javax.swing.*;
import java.awt.*;

public class SnakeGame extends JPanel
{
	private Snake snake;
	private int level;
	
	public SnakeGame(JFrame frame)
	{
		level=1;
		snake=new Snake(frame.getWidth(),frame.getHeight());
		System.out.println(frame.getWidth()+" "+frame.getHeight());
		this.play();
	}
	
	public void levelUp()
	{
		level++;
	}
	
	public void play()
	{
		repaint();
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2=(Graphics2D)g;
		snake.drawSnake(g2);
		String showStatus="Level : "+this.level;
		
		g2.setFont(new Font(showStatus,Font.BOLD,15));
		g2.drawString(showStatus, 10, 20);
	}
}
