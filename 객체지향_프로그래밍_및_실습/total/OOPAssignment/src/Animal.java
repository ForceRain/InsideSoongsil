import java.awt.Graphics2D;

public interface Animal extends Runnable
{
	public void HowToMove();
	public void LetsMove();
	public void Draw(Graphics2D g2);
	public boolean isAnimal(int ms_x,int ms_y);
	public void changePosition(int x,int y);
	public void sound();
}
