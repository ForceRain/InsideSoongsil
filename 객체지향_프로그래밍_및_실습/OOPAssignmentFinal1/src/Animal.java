import java.awt.Graphics2D;

public interface Animal extends Runnable
{
	public void HowToMove();
	public void LetsMove(int n);
	public boolean isAnimal(int ms_x,int ms_y);
	public void changePosition(int x,int y);
	public void sound();
	public void Draw(Graphics2D g2);
	public void setState(boolean input);
}
