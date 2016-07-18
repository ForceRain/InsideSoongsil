import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.awt.Color;
import java.util.Random;
import java.awt.RenderingHints;

public class StarClass extends JPanel
{
	private boolean currentStateUD;		//false==up, true==down
	private boolean currentStateLR;		//false==left, true==right
	private int x;
	private int y;
	private int changeColor;
	private String currentColor;
	private static final int WIDTH=1100;
	private static final int HEIGHT=700;
	private Random rand;
	
	public StarClass(int xInput, int yInput)
	{
		x=xInput;
		y=yInput;
		
		rand=new Random();
		changeColor=0;
		
		if ((rand.nextInt())%2==0)
			currentColor="Red";
		else
			currentColor="Yellow";
		
		currentStateUD=true;
		currentStateLR=true;			//right,down
	}
	
	public void starDirection()
	{
		if (x>WIDTH-75)
			currentStateLR=false;
		if (x<75)
			currentStateLR=true;
		if (y<120)
			currentStateUD=true;
		if (y>HEIGHT-75)
			currentStateUD=false;
		changeColor++;
	}
	
	public void starMove()
	{
		if (currentStateLR)
			x+=3;
		else
			x-=3;
		if (currentStateUD)
			y+=3;
		else
			y-=3;
		changeColor++;
		if (changeColor==100)
		{
			changeColor=0;
			swapColor();
		}
	}
	
	public void swapColor()
	{
		if (currentColor.equals("Red"))
			currentColor="Yellow";
		else
			currentColor="Red";
	}
	
	public void draw(Graphics2D g2)
	{
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		
		int starX[]={x,x+20,x+75,x+25,x+37,x,x-37,x-25,x-75,x-20};
		int starY[]={y-75,y-20,y-20,y+20,y+75,y+25,y+75,y+20,y-20,y-20};
		
		if (currentColor.equals("Red"))
			g2.setColor(Color.RED);
		else
			g2.setColor(Color.YELLOW);
		
		g2.fillPolygon(starX,starY,10);
		g2.drawPolygon(starX,starY,10);
	}
}
