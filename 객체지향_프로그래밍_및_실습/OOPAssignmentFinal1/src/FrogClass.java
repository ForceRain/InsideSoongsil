import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;

import java.awt.Color;
import java.io.File;

public class FrogClass extends JPanel implements Animal
{
	private int x,y;
 	private String CurrentStateLR, CurrentStateUD;
 	private Ellipse2D.Double head,eyeL,eyeR,IeyeL,IeyeR,IIeyeL,IIeyeR;
   	private Ellipse2D.Double cheekL,cheekR,body,stomach;
   	private RoundRectangle2D.Double armR,armL,legL,legR,shoulder;
   	private Ellipse2D.Double finger1,finger2,finger3,finger4;
   	private Ellipse2D.Double toe1, toe2,toe3,toe4;
   	private QuadCurve2D mouth;
   	private Color BrightG,DarkG,MediumG;
   	
   	private boolean NormalMoving;
	
	public FrogClass(int x,int y)
	{
		this.x=x;
	    this.y=y;
	    CurrentStateUD="up";
	    CurrentStateLR="right";
	    
	    NormalMoving=true;
	}
	
	public void HowToMove()
	{
		if (x<0)
			CurrentStateLR="right";
		if (x+135>1070)
			CurrentStateLR="left";
		if (y-30<80)
			CurrentStateUD="down";
		if (y+230>700)
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
		if (x<ms_x && ms_x<x+135)
			LR=true;
		else
			LR=false;

		if (y-30<ms_y && ms_y<y+190)
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
			File file1=new File("MovingFrogSound.wav");
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
		
	    BrightG=new Color(102,204,0);
	    DarkG=new Color(51,102,0);
	    MediumG=new Color(76,153,0);
	    head=new Ellipse2D.Double(x, y, 150, 100);
	    cheekL=new Ellipse2D.Double(x+20, y+50, 25, 25);
	    cheekR=new Ellipse2D.Double(x+105, y+50, 25, 25);
	    eyeL=new Ellipse2D.Double(x, y-30, 65, 65);
	    eyeR=new Ellipse2D.Double(x+85, y-30, 65, 65);
	    IeyeL=new Ellipse2D.Double(x+10, y-20, 50, 50);
	    IeyeR=new Ellipse2D.Double(x+90, y-20, 50, 50);
	    IIeyeL=new Ellipse2D.Double(x+10, y-20, 30, 30);
	    IIeyeR=new Ellipse2D.Double(x+90, y-20, 30, 30);
	    mouth=new QuadCurve2D.Double(x+35, y+65, x+85, y+85, x+115, y+65);
	    body=new Ellipse2D.Double(x+25, y+90, 100, 80);
	    stomach=new Ellipse2D.Double(x+40, y+120, 70, 40);
	    legL=new RoundRectangle2D.Double(x+40, y+140, 15, 50, 7, 25);
	    legR=new RoundRectangle2D.Double(x+95, y+140, 15, 50, 7, 25);
	    armL=new RoundRectangle2D.Double(x+10, y+110, 15, 50, 7, 25);
	    armR=new RoundRectangle2D.Double(x+125, y+110, 15, 50, 7, 25);
	    shoulder=new RoundRectangle2D.Double(x+10, y+110, 130, 15, 7, 25);
	    toe1=new Ellipse2D.Double(x+38, y+185, 10, 10);
	    toe2=new Ellipse2D.Double(x+48, y+185, 10, 10);
	    toe3=new Ellipse2D.Double(x+93, y+185, 10, 10);
	    toe4=new Ellipse2D.Double(x+103, y+185, 10, 10);
	    finger1=new Ellipse2D.Double(x+8, y+155, 10, 10);
	    finger2=new Ellipse2D.Double(x+18, y+155, 10, 10);
	    finger3=new Ellipse2D.Double(x+123, y+155, 10, 10);
	    finger4=new Ellipse2D.Double(x+133, y+155, 10, 10);
		
	    g2.setColor(MediumG);
	    g2.draw(legL);
	    g2.fill(legL);
	    g2.draw(legR);
	    g2.fill(legR);
	    g2.draw(armL);
	    g2.fill(armL);
	    g2.draw(armR);
	    g2.fill(armR);
	    g2.draw(shoulder);
	    g2.fill(shoulder);
	    g2.setColor(BrightG);
	    g2.draw(head);
	    g2.fill(head);
	    g2.draw(body);
	    g2.fill(body);
	    g2.draw(toe1);
	    g2.fill(toe1);
	    g2.draw(toe2);
	    g2.fill(toe2);
	    g2.draw(toe3);
	    g2.fill(toe3);
	    g2.draw(toe4);
	    g2.fill(toe4);
	    g2.draw(finger1);
	    g2.fill(finger1);
	    g2.draw(finger2);
	    g2.fill(finger2);
	    g2.draw(finger3);
	    g2.fill(finger3);
	    g2.draw(finger4);
	    g2.fill(finger4);
	    g2.setColor(Color.PINK);
	    g2.draw(mouth);
	    g2.fill(mouth);
	    g2.setColor(Color.RED);
	    g2.draw(cheekL);
	    g2.fill(cheekL);
	    g2.draw(cheekR);
	    g2.fill(cheekR);
	    g2.setColor(DarkG);
	    g2.draw(eyeL);
	    g2.fill(eyeL);
	    g2.draw(eyeR);
	    g2.fill(eyeR);
	    g2.setColor(Color.WHITE);
	    g2.draw(IeyeL);
	    g2.fill(IeyeL);
	    g2.draw(IeyeR);
	    g2.fill(IeyeR);
	    g2.draw(stomach);
	    g2.fill(stomach);
	    g2.setColor(Color.BLACK);
	    g2.draw(IIeyeL);
	    g2.fill(IIeyeL);
	    g2.draw(IIeyeR);
	    g2.fill(IIeyeR);
	}
}
