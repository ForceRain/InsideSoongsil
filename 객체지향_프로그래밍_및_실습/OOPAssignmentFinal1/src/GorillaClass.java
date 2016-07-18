import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;

import java.awt.geom.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.File;

public class GorillaClass extends JPanel implements Animal
{
	private int x,y;
	private String CurrentStateLR, CurrentStateUD;
	private Ellipse2D.Double eyeL,eyeR,IeyeL,IeyeR,handL,handR,titL,titR;
	private RoundRectangle2D.Double head, face, mouth, body, chest, abs;
	private RoundRectangle2D.Double LeftUpperArm, RightUpperArm, LeftForeArm, RightForeArm;
	private RoundRectangle2D.Double legL,legR, footL, footR;
	private RoundRectangle2D.Double absl1,absl2,absl3,absc;
	private Color Gal, Sal;
	
	private boolean NormalMoving;
	
	public GorillaClass(int x, int y)
	{
		this.x=x;
		this.y=y;
		CurrentStateUD="up";
		CurrentStateLR="right";
		
		NormalMoving=true;
	}
	
	public void HowToMove()
	{
		if (x-70<0)
			CurrentStateLR="right";
		if (x+90>1070)
			CurrentStateLR="left";
		if (y<80)
			CurrentStateUD="down";
		if (y+260>700)
			CurrentStateUD="up";
	}
	
	public void LetsMove(int n)
	{
		if(CurrentStateLR.equals("right"))
			x+=n;
	    if(CurrentStateLR.equals("left"))
	    	x-=n;
	    if(CurrentStateUD.equals("up"))
	    	y-=n;
	    if(CurrentStateUD.equals("down"))
	    	y+=n;
	}
	
	public void run()
	{
		while (true)
		{
			try
			{
				Thread.sleep(30);
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
	
	public boolean isAnimal(int ms_x,int ms_y)
	{
		boolean LR=true;
		boolean UD=true;
		if (x-70<ms_x && ms_x<x+90)
			LR=true;
		else
			LR=false;

		if (y<ms_y && ms_y<y+210)
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
			File file1=new File("MovingGorillaSound.wav");
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
	
	public void setState(boolean input)
	{
		NormalMoving=input;
	}
	
	public void Draw(Graphics2D g2)
	{
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		
		Gal=new Color(51,25,0);
		Sal=new Color(255,178,102);
		head=new RoundRectangle2D.Double(x, y, 50, 75, 30, 30);
		face=new RoundRectangle2D.Double(x+5, y+15, 40, 45, 15, 15);
		mouth=new RoundRectangle2D.Double(x+17, y+45, 15, 10, 3, 3);
		body=new RoundRectangle2D.Double(x-25, y+70, 100, 120, 15, 15);
		chest=new RoundRectangle2D.Double(x-20, y+90, 90, 40, 15, 15);
		abs=new RoundRectangle2D.Double(x, y+130, 50, 55, 7, 7);
		LeftUpperArm=new RoundRectangle2D.Double(x-60, y+80, 60, 30, 30, 30);
		RightUpperArm=new RoundRectangle2D.Double(x+50, y+80, 60, 30, 30, 30);
		LeftForeArm=new RoundRectangle2D.Double(x-60, y+35, 30, 70, 15, 15);
		RightForeArm=new RoundRectangle2D.Double(x+80, y+35, 30, 70, 15, 15);
		legL=new RoundRectangle2D.Double(x-20, y+190, 30, 20, 3, 7);
		legR=new RoundRectangle2D.Double(x+40, y+190, 30, 20, 3, 7);
		footL=new RoundRectangle2D.Double(x-50, y+210, 60, 10, 15, 15);
		footR=new RoundRectangle2D.Double(x+40, y+210, 60, 10, 15, 15);
		handL=new Ellipse2D.Double(x-65, y+30,40,10);
		handR=new Ellipse2D.Double(x+75, y+30,40,10);
		eyeR=new Ellipse2D.Double(x+10, y+20,10,10);
		eyeL=new Ellipse2D.Double(x+30, y+20,10,10);
		IeyeL=new Ellipse2D.Double(x+10, y+20,5,5);
		IeyeR=new Ellipse2D.Double(x+30, y+20,5,5);
		titL=new Ellipse2D.Double(x-10, y+120,5,5);
		titR=new Ellipse2D.Double(x+55, y+120,5,5);
		absl1=new RoundRectangle2D.Double(x+5, y+130, 40, 1, 15, 15);
		absl2=new RoundRectangle2D.Double(x+5, y+150, 40, 1, 15, 15);
		absl3=new RoundRectangle2D.Double(x+5, y+170, 40, 1, 15, 15);
		absc=new RoundRectangle2D.Double(x+25, y+130, 1, 40, 15, 15);
		
		g2.setColor(Gal);
		g2.draw(head);
		g2.fill(head);
		g2.draw(body);
		g2.fill(body);
		g2.draw(LeftUpperArm);
		g2.fill(LeftUpperArm);
		g2.draw(RightUpperArm);
		g2.fill(RightUpperArm);
		g2.draw(LeftForeArm);
		g2.fill(LeftForeArm);
		g2.draw(RightForeArm);
		g2.fill(RightForeArm);
		g2.draw(legL);
		g2.fill(legL);
		g2.draw(legR);
		g2.fill(legR);
		g2.setColor(Sal);
		g2.draw(face);
		g2.fill(face);
		g2.draw(chest);
		g2.fill(chest);
		g2.draw(abs);
		g2.fill(abs);
		g2.draw(footL);
		g2.fill(footL);
		g2.draw(footR);
		g2.fill(footR);
		g2.draw(handL);
		g2.fill(handL);
		g2.draw(handR);
		g2.fill(handR);
		g2.setColor(Color.RED);
		g2.draw(mouth);
		g2.fill(mouth);
		g2.setColor(Color.WHITE);
		g2.draw(eyeL);
		g2.fill(eyeL);
		g2.draw(eyeR);
		g2.fill(eyeR);
		g2.setColor(Color.BLACK);
		g2.draw(IeyeL);
		g2.fill(IeyeL);
		g2.draw(IeyeR);
		g2.fill(IeyeR);
		g2.draw(titL);
		g2.fill(titL);
		g2.draw(titR);
		g2.fill(titR);
		g2.draw(absl1);
		g2.fill(absl1);
		g2.draw(absl2);
		g2.fill(absl2);
		g2.draw(absl3);
		g2.fill(absl3);
		g2.draw(absc);
		g2.fill(absc);
	}
}
