import java.awt.Graphics2D;
import java.awt.Color;
import java.io.File;
import java.awt.geom.QuadCurve2D;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;
import java.awt.RenderingHints;

public class ElephantClass extends JPanel implements Animal
{
	private int x;
	private int y;
	private String CurrentMoving;
	private boolean NormalMoving;
	
	public ElephantClass(int xInput,int yInput)
	{		
		x=xInput;
		y=yInput;
		NormalMoving=true;
		CurrentMoving="right";
	}
	
	public void HowToMove()
	{
		if(x<=70)
			CurrentMoving="right";
		if(x>=1100-200)
			CurrentMoving="left"; 
	}
	
	public void LetsMove(int n)
	{
		 if(CurrentMoving.equals("right"))
			 x+=n; 
		 if(CurrentMoving.equals("left"))
			 x-=n;
	}
	
	public void run()
	{
		while (true)
		{
			try
			{
				Thread.sleep(70);
				HowToMove();
				if (NormalMoving)
				{
					LetsMove(1);
				}
				else
				{
					LetsMove(3);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void setState(boolean input)
	{
		NormalMoving=input;
	}
	
	public boolean isAnimal(int ms_x,int ms_y)
	{
		boolean LR=true;
		boolean UD=true;
		if (x-100<ms_x && ms_x<x+165)
			LR=true;
		else
			LR=false;

		if (y-100<ms_y && ms_y<y+100)
			UD=true;
		else
			UD=false;
		
		return (LR&&UD);
	}
	
	public void changePosition(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
	
	public void sound()
	{
		try
		{
			File file1=new File("MovingElephantSound.wav");
			AudioInputStream movingStream=AudioSystem.getAudioInputStream(file1);
			
			Clip movingSound=AudioSystem.getClip();
			movingSound.open(movingStream);
			movingSound.start();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void Draw(Graphics2D g2)
	{
		int corX=100;
		int corY=100;
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.setColor(Color.lightGray);
		g2.fillOval(x+50-corX, y+50-corY, 100, 100);//赣府
		g2.setColor(Color.black);
		g2.drawOval(x+50-corX, y+50-corY, 100, 100);//赣府
		g2.setColor(Color.black);
		g2.fillOval(x+75-corX, y+80-corY, 8, 8);//赣府          
		g2.setColor(Color.lightGray);
		g2.fillRect(x+50-corX, y+100-corY, 25, 70);//内
		g2.setColor(Color.black);
		g2.drawRect(x+50-corX, y+100-corY, 25, 70);//内
		g2.setColor(Color.white);
		QuadCurve2D.Double nose = new QuadCurve2D.Double(x+85-corX,y+115-corY,x-25-corX,y+80-corY,x+87-corX,y+137-corY);
		g2.fill(nose);
		
		g2.setColor(Color.black);
		g2.draw(nose);
		g2.setColor(Color.lightGray);
		g2.fillRect(x+125-corX, y+100-corY, 140, 100);//个烹
		g2.setColor(Color.black);
		g2.drawRect(x+125-corX, y+100-corY, 140, 100);//个烹        
		g2.setColor(Color.lightGray);
		g2.fillRect(x+125-corX, y+75-corY, 50, 100);//蓖
		g2.setColor(Color.black);
		g2.drawRect(x+125-corX, y+75-corY, 50,100);//蓖
		g2.setColor(Color.lightGray);
		g2.fillRect(x+125-corX,y+200-corY, 20, 25);//促府 1
		g2.setColor(Color.black);
		g2.drawRect(x+125-corX, y+200-corY, 20, 25);//促府1
		g2.setColor(Color.lightGray);
		g2.fillRect(x+165-corX,y+200-corY, 20,25);//促府 2
		g2.setColor(Color.black);
		g2.drawRect(x+165-corX, y+200-corY, 20, 25);//促府2
		g2.setColor(Color.lightGray);
		g2.fillRect(x+210-corX, y+200-corY, 20, 25);//促府 3
		g2.setColor(Color.black);
		g2.drawRect(x+210-corX, y+200-corY, 20, 25);//促府3
		g2.setColor(Color.lightGray);
		g2.fillRect(x+240-corX, y+200-corY, 20, 25);//促府 4
		g2.setColor(Color.black);
		g2.drawRect(x+240-corX, y+200-corY, 20, 25);//促府4   
		g2.setColor(Color.lightGray);
		g2.fillRect(x+265-corX, y+100-corY, 13, 75);//部府
		g2.setColor(Color.black);
		g2.drawRect(x+265-corX, y+100-corY, 13, 75);//部府
	}
	
}
