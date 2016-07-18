import java.awt.Graphics2D;
import java.awt.Color;
import java.io.File;
import java.awt.geom.CubicCurve2D;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.Polygon;

public class BearClass extends JPanel implements Animal
{
	private int x;
	private int y;
	private String CurrentMoving;
	private boolean NormalMoving;
	
	public BearClass(int xInput,int yInput)
	{
		x=xInput;
		y=yInput;
		NormalMoving=true;
		CurrentMoving="right";
	}
	
	public void run()
	{
		while (true)
		{
			try
			{
				Thread.sleep(50);
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
	
	public void HowToMove()
	{
		if(x<=0)
		    CurrentMoving="right";
		if(x>=1100-150)
		    CurrentMoving="left";
	}
	
	public void LetsMove(int n)
	{
		if(CurrentMoving.equals("right"))
			x+=n;
		if(CurrentMoving.equals("left"))
			x-=n;
	}	
	
	public boolean isAnimal(int ms_x,int ms_y)
	{
		boolean LR=true;
		boolean UD=true;
		if (x<ms_x && ms_x<x+200)
			LR=true;
		else
			LR=false;

		if (y<ms_y && ms_y<y+200)
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
			File file1=new File("MovingBearSound.wav");
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
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		
		int corX=100;
		int corY=50;
		
		Color color =new Color(161,105,88);
		Color color1 = new Color(148,84,59);
		Color color2 = new Color(89,30,12);
		
		super.paintComponent(g2);
		RoundRectangle2D.Double ear1 = new RoundRectangle2D.Double(x+135-corX, y+35-corY, 15, 15,15,15);
		g2.setColor(Color.darkGray);
		g2.fill(ear1);//蓖1
		RoundRectangle2D.Double ear2 = new RoundRectangle2D.Double(x+160-corX,y+35-corY, 15, 15,15,15);
		g2.setColor(Color.darkGray);
		g2.fill(ear2);//蓖2
		g2.setColor(color1);
		RoundRectangle2D.Double head = new RoundRectangle2D.Double(x+130-corX,y+50-corY, 50, 50, 15, 15);
		g2.fill(head);//赣府
		g2.setColor(Color.black);
		g2.fillOval(x+137-corX,y+60-corY, 8, 8);//传
		g2.setColor(Color.black);
		g2.fillOval(x+163-corX,y+60-corY, 8, 8);//传
		RoundRectangle2D.Double nose = new RoundRectangle2D.Double(x+138-corX,y+75-corY, 35, 20,30,30);
		g2.setColor(Color.orange);
		g2.fill(nose);//涝
		g2.setColor(Color.black);
		g2.fillOval(x+150-corX,y+80-corY, 10, 10);//内
		g2.setColor(color);
		CubicCurve2D.Double hand = new CubicCurve2D.Double(x+126-corX,y+126-corY,x+93-corX,y+59-corY,x+63-corX,y+82-corY,x+114-corX,y+150-corY);
		g2.fill(hand);
		CubicCurve2D.Double hand2= new CubicCurve2D.Double(x+188-corX,y+126-corY,x+221-corX,y+59-corY,x+251-corX,y+82-corY,x+200-corX,y+150-corY);
		g2.fill(hand2);
		g2.setColor(color);
		g2.setColor(color);
		CubicCurve2D.Double hand3 = new CubicCurve2D.Double(x+122-corX,y+213-corY,x+117-corX,y+240-corY,x+147-corX,y+240-corY,x+142-corX,y+213-corY);
		g2.fill(hand3);
		g2.fillRect(x+122-corX,y+190-corY, 20, 30);//颊2
		g2.setColor(color);
		g2.fillRect(x+170-corX,y+190-corY, 20, 30);//颊2
		CubicCurve2D.Double hand4 = new CubicCurve2D.Double(x+170-corX,y+213-corY,x+165-corX,y+240-corY,x+195-corX,y+240-corY,x+190-corX,y+213-corY);
		g2.fill(hand4);
		g2.setColor(color2);
    	g2.fillRect(x+130-corX,y+100-corY,50, 100);//个烹
		CubicCurve2D.Double body = new CubicCurve2D.Double(x+130-corX,y+100-corY,x+100-corX,y+100-corY,x+100-corX,y+200-corY,x+130-corX,y+200-corY);
		CubicCurve2D.Double body2 = new CubicCurve2D.Double(x+180-corX,y+100-corY,x+210-corX,y+100-corY,x+210-corX,y+200-corY,x+180-corX,y+200-corY);
		g2.fill(body);
		g2.fill(body2);
		Polygon p =new Polygon();
		p.addPoint(x+130-corX, y+115-corY);
		p.addPoint(x+180-corX, y+115-corY);
		p.addPoint(x+155-corX, y+130-corY);
		g2.setColor(Color.white);
		g2.fillPolygon(p);
	}
}
