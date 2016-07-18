import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class Snake implements Runnable
{
	private int tailLength;
	private ArrayList<Point> data;
	
	public Snake(int width,int height)//frame의 크기에 따라 생성되는 뱀의 위치를 다르게
	{
		tailLength=3;
		data=new ArrayList<Point>();
		
		for (int i=0;i<tailLength;i++)
		{
			data.add(new Point(width/2+15,height/2+i));
		}
	}
	
	public void run()
	{
		
	}
	
	public void tailLonger(SnakeGame ground)
	{
		tailLength++;
		
		if (tailLength%3==0)
		{
			ground.levelUp();
		}
	}
	
	public void drawSnake(Graphics2D pencil)
	{
		int[] x=new int[data.size()];
		int[] y=new int[data.size()];
		
		for (int i=0;i<data.size();i++)
		{
			x[i]=(int)(data.get(i)).getX();
			y[i]=(int)(data.get(i)).getY();
		}
		
		pencil.setStroke(new BasicStroke(3));
		pencil.drawPolyline(x, y, data.size());
	}
}
