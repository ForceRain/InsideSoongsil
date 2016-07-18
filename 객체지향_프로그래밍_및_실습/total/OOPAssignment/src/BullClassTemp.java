import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.BasicStroke;
import javax.swing.JPanel;
import java.util.Random;
import java.io.File;
import javax.sound.sampled.*;

public class BullClassTemp extends JPanel implements Animal
{
	private int x;
	private int y;
	private String CurrentStateLR;
	private String CurrentStateUD;
	private static final int FRAME_WIDTH=1100;
	private static final int FRAME_HEIGHT=700;
	private int randomGenerator;
	private static final int LEFT_SIDE_COW=50;
	private static final int RIGHT_SIDE_COW=140;
	private static final int UPPER_SIDE_COW=50;
	private static final int LOWER_SIDE_COW=120;
	
	public BullClassTemp(int xInput,int yInput)
	{
		try
		{
			File file=new File("BullSetup.wav");
			AudioInputStream audioStream=AudioSystem.getAudioInputStream(file);
			
			Clip player=AudioSystem.getClip();
			player.open(audioStream);
			player.start();
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		
		CurrentStateLR="right";
		CurrentStateUD="down";
		x=xInput;
		y=yInput;
		randomGenerator=0;
	}
	
	public boolean isAnimal(int ms_x,int ms_y)
	{
		boolean LR=true;
		boolean UD=true;
		if (x-LEFT_SIDE_COW<ms_x && ms_x<x+RIGHT_SIDE_COW)
			LR=true;
		else
			LR=false;

		if (y-UPPER_SIDE_COW<ms_y && ms_y<y+LOWER_SIDE_COW)
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
	
	public void randomMove()
	{
		randomGenerator=0;
		Random obj=new Random();
		int pos=obj.nextInt(4);
		
		if (pos==0)
		{
			CurrentStateLR="left";
			CurrentStateUD="up";
		}
		else if (pos==1)
		{
			CurrentStateLR="right";
			CurrentStateUD="up";
		}
		else if (pos==2)
		{
			CurrentStateLR="right";
			CurrentStateUD="down";
		}
		else
		{
			CurrentStateLR="left";
			CurrentStateUD="down";
		}
	}

	public void run()
	{
		while (true)
		{
			try
			{
				Thread.sleep(10);
				randomGenerator++;
				if (randomGenerator==1000)
					randomMove();
				HowToMove();
				LetsMove();
				System.out.println("randomGenerator : "+randomGenerator);
				System.out.println("Run!!"+x+","+y);
			}
			catch (Exception exception)
			{
				exception.printStackTrace();
			}
		}
	}
	
	public void HowToMove()
	{
		if (x-LEFT_SIDE_COW<0)
			CurrentStateLR="right";
		if (x+RIGHT_SIDE_COW+20>FRAME_WIDTH)
			CurrentStateLR="left";
		if (y-UPPER_SIDE_COW<70)
			CurrentStateUD="down";
		if (y+LOWER_SIDE_COW+20>FRAME_HEIGHT)
			CurrentStateUD="up";
	}
	
	public void LetsMove()
	{
		if (CurrentStateLR.equals("left"))
			x-=2;
		if (CurrentStateLR.equals("right"))
			x+=2;
		if (CurrentStateUD.equals("up"))
			y--;
		if (CurrentStateUD.equals("down"))
			y++;
		System.out.println("Current X :"+this.x+"Current Y :"+this.y);
	}
	
	public void sound()
	{
		try
		{
			File file1=new File("MovingBullSound.wav");
			AudioInputStream movingStream=AudioSystem.getAudioInputStream(file1);
			
			Clip movingSound=AudioSystem.getClip();
			movingSound.open(movingStream);
			movingSound.start();
			System.out.println("working!");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void Draw(Graphics2D g2)
	{
		//moving to the left
		if (CurrentStateLR.equals("left"))
		{
			//body
			int LbodyX[]={x+20,x+50,x+70,x+110,x+120,x+125,x+125,x+120,x+110,x+100,x+90,x+70,x+60,x+45,x+27,x+10,x+5,x};
			int LbodyY[]={y-10,y-5,y,y-5,y,y+10,y+15,y+30,y+60,y+60,y+60,y+65,y+65,y+60,y+60,y+60,y+40,y+25};
			
			g2.setColor(new Color(139,69,19));
			g2.fillPolygon(LbodyX,LbodyY,18);
			g2.drawPolygon(LbodyX,LbodyY,18);
			
			//leg
			int Lleg1X[]={x+10,x+11,x+12,x+13,x+13,x+12,x+25,x+25,x+27};
			int Lleg1Y[]={y+60,y+68,y+76,y+84,y+92,y+100,y+100,y+80,y+60};
			
			int Lleg2X[]={x+27,x+38,x+35,x+49,x+47,x+45};
			int Lleg2Y[]={y+60,y+75,y+100,y+100,y+80,y+60};
			
			int Lleg3X[]={x+90,x+87,x+90,x+87,x+96,x+98,x+96,x+100};
			int Lleg3Y[]={y+60,y+76,y+85,y+100,y+100,y+80,y+70,y+60};
			
			int Lleg4X[]={x+100,x+105,x+103,x+115,x+110};
			int Lleg4Y[]={y+60,y+80,y+100,y+95,y+60};
			
			g2.setColor(new Color(139,69,19));
			g2.fillPolygon(Lleg1X,Lleg1Y,9);
			g2.fillPolygon(Lleg2X,Lleg2Y,6);
			g2.fillPolygon(Lleg3X,Lleg3Y,8);
			g2.fillPolygon(Lleg4X,Lleg4Y,5);
			
			g2.drawPolygon(Lleg1X,Lleg1Y,9);
			g2.drawPolygon(Lleg2X,Lleg2Y,6);
			g2.drawPolygon(Lleg3X,Lleg3Y,8);
			g2.drawPolygon(Lleg4X,Lleg4Y,5);
			
			//foot
			int Lfoot1X[]={x+12,x+11,x+10,x+25,x+25};
			int Lfoot1Y[]={y+100,y+105,y+110,y+110,y+100};
			
			int Lfoot2X[]={x+35,x+34,x+33,x+49,x+49};
			int Lfoot2Y[]={y+100,y+105,y+110,y+110,y+100};
			
			int Lfoot3X[]={x+87,x+86,x+85,x+97,x+96};
			int Lfoot3Y[]={y+100,y+105,y+110,y+110,y+100};
			
			int Lfoot4X[]={x+103,x+100,x+101,x+117,x+115};
			int Lfoot4Y[]={y+100,y+105,y+110,y+103,y+95};
			
			g2.setColor(Color.BLACK);
			g2.fillPolygon(Lfoot1X,Lfoot1Y,5);
			g2.fillPolygon(Lfoot2X,Lfoot2Y,5);
			g2.fillPolygon(Lfoot3X,Lfoot3Y,5);
			g2.fillPolygon(Lfoot4X,Lfoot4Y,5);
			
			g2.drawPolygon(Lfoot1X,Lfoot1Y,5);
			g2.drawPolygon(Lfoot2X,Lfoot2Y,5);
			g2.drawPolygon(Lfoot3X,Lfoot3Y,5);
			g2.drawPolygon(Lfoot4X,Lfoot4Y,5);
			
			//head
			int hx=x-5;
			int hy=y+10;
			
			int headX[]={hx+12,hx+24,hx+27,hx+27,hx+27,hx+26,hx+19,hx,hx-19,hx-26,hx-27,hx-27,hx-27,hx-24,hx-12};
			int headY[]={hy-30,hy-28,hy-25,hy-20,hy,hy+20,hy+18,hy+17,hy+18,hy+20,hy,hy-20,hy-25,hy-28,hy-30};
			
			g2.setColor(new Color(139,69,19));
			g2.fillPolygon(headX,headY,15);
			g2.drawPolygon(headX,headY,15);
			
			//ear
			int RearX[]={hx+27,hx+42,hx+47,hx+42,hx+27};
			int RearY[]={hy-20,hy-17,hy-15,hy-12,hy};
			
			g2.setColor(new Color(210,201,146));
			g2.fillPolygon(RearX,RearY,5);
			g2.drawPolygon(RearX,RearY,5);
			
			int LearX[]={hx-27,hx-42,hx-47,hx-42,hx-27};
			int LearY[]={hy-20,hy-17,hy-15,hy-12,hy};
			
			g2.fillPolygon(LearX,LearY,5);
			g2.drawPolygon(LearX,LearY,5);
			
			//mouth
			int mouthX[]={hx+26,hx+25,hx+23,hx+20,hx,hx-20,hx-23,hx-25,hx-26,hx-19,hx,hx+19};
			int mouthY[]={hy+20,hy+33,hy+37,hy+39,hy+40,hy+39,hy+37,hy+33,hy+20,hy+18,hy+17,hy+18};
			
			g2.setColor(new Color(160,82,45));
			g2.fillPolygon(mouthX,mouthY,12);
			g2.drawPolygon(mouthX,mouthY,12);
			
			//bull
			int RbullX[]={hx+24,hx+35,hx+38,hx+42,hx+40,hx+36,hx+27};
			int RbullY[]={hy-28,hy-34,hy-37,hy-42,hy-24,hy-21,hy-20};
			
			g2.setColor(Color.WHITE);
			g2.fillPolygon(RbullX,RbullY,7);
			g2.drawPolygon(RbullX,RbullY,7);
			
			int LbullX[]={hx-24,hx-35,hx-38,hx-42,hx-40,hx-36,hx-27};
			int LbullY[]={hy-28,hy-34,hy-37,hy-42,hy-24,hy-21,hy-20};
			
			g2.fillPolygon(LbullX,LbullY,7);
			g2.drawPolygon(LbullX,LbullY,7);
			
			//eyes
			Ellipse2D.Double Leye=new Ellipse2D.Double(hx-15,hy-15,7,7);
			
			g2.setColor(Color.BLACK);
			g2.fill(Leye);
			g2.draw(Leye);
			
			Ellipse2D.Double Reye=new Ellipse2D.Double(hx+5,hy-15,7,7);
			g2.fill(Reye);
			g2.draw(Reye);
			
			//mouthline
			g2.setStroke(new BasicStroke(2));
			
			Line2D.Double ml1=new Line2D.Double(hx+17,hy+37,hx+12,hy+36);
			Line2D.Double ml2=new Line2D.Double(hx+12,hy+36,hx+6,hy+35);
			Line2D.Double ml3=new Line2D.Double(hx+6,hy+35,hx,hy+34);
			Line2D.Double ml4=new Line2D.Double(hx,hy+34,hx-6,hy+35);
			Line2D.Double ml5=new Line2D.Double(hx-6,hy+35,hx-12,hy+36);
			Line2D.Double ml6=new Line2D.Double(hx-12,hy+36,hx-17,hy+37);
			
			g2.setColor(Color.BLACK);
			g2.draw(ml1);
			g2.draw(ml2);
			g2.draw(ml3);
			g2.draw(ml4);
			g2.draw(ml5);
			g2.draw(ml6);
			
			g2.setStroke(new BasicStroke(1));
			//nose
			
			int RnoseX[]={hx+7,hx+15,hx+14,hx+6};
			int RnoseY[]={hy+22,hy+21,hy+23,hy+25};
			
			g2.setColor(Color.BLACK);
			g2.fillPolygon(RnoseX,RnoseY,4);
			g2.drawPolygon(RnoseX,RnoseY,4);
			
			int LnoseX[]={hx-7,hx-15,hx-14,hx-6};
			int LnoseY[]={hy+22,hy+21,hy+23,hy+25};
			
			g2.fillPolygon(LnoseX,LnoseY,4);
			g2.drawPolygon(LnoseX,LnoseY,4);
			
			//tail
			int Ltail1X[]={x+125,x+130,x+134,x+138,x+139,x+132,x+134,x+132,x+129,x+124};
			int Ltail1Y[]={y+10,y+13,y+24,y+30,y+57,y+56,y+48,y+30,y+24,y+20};
			
			g2.setColor(new Color(139,69,19));
			g2.fillPolygon(Ltail1X,Ltail1Y,10);
			g2.drawPolygon(Ltail1X,Ltail1Y,10);
			
			int Ltail2X[]={x+139,x+142,x+143,x+141,x+133,x+130,x+128,x+129,x+132};
			int Ltail2Y[]={y+57,y+61,y+66,y+71,y+76,y+71,y+66,y+61,y+56};
			
			g2.setColor(Color.BLACK);
			g2.fillPolygon(Ltail2X,Ltail2Y,9);
			g2.drawPolygon(Ltail2X,Ltail2Y,9);			
		}
		else
		{
			//body
			int RbodyX[]={x+120,x+115,x+110,x+93,x+75,x+60,x+50,x+30,x+20,x+10,x,x-5,x-5,x,x+10,x+50,x+70,x+100};
			int RbodyY[]={y+25,y+40,y+60,y+60,y+60,y+65,y+65,y+60,y+60,y+60,y+30,y+15,y+10,y,y-5,y,y-5,y-10};
			
			g2.setColor(new Color(139,69,19));
			g2.fillPolygon(RbodyX,RbodyY,18);
			g2.drawPolygon(RbodyX,RbodyY,18);
			
			//leg
			int Rleg1X[]={x+110,x+109,x+108,x+107,x+107,x+108,x+95,x+95,x+93};
			int Rleg1Y[]={y+60,y+68,y+76,y+84,y+92,y+100,y+100,y+80,y+60};
			
			int Rleg2X[]={x+93,x+82,x+85,x+71,x+73,x+75};
			int Rleg2Y[]={y+60,y+75,y+100,y+100,y+80,y+60};
			
			int Rleg3X[]={x+30,x+33,x+30,x+33,x+24,x+22,x+24,x+20};
			int Rleg3Y[]={y+60,y+76,y+85,y+100,y+100,y+80,y+70,y+60};
			
			int Rleg4X[]={x+20,x+15,x+17,x+5,x+10};
			int Rleg4Y[]={y+60,y+80,y+100,y+95,y+60};
			
			g2.setColor(new Color(139,69,19));
			g2.fillPolygon(Rleg1X,Rleg1Y,9);
			g2.fillPolygon(Rleg2X,Rleg2Y,6);
			g2.fillPolygon(Rleg3X,Rleg3Y,8);
			g2.fillPolygon(Rleg4X,Rleg4Y,5);
			
			g2.drawPolygon(Rleg1X,Rleg1Y,9);
			g2.drawPolygon(Rleg2X,Rleg2Y,6);
			g2.drawPolygon(Rleg3X,Rleg3Y,8);
			g2.drawPolygon(Rleg4X,Rleg4Y,5);
			
			//foot
			int Rfoot1X[]={x+108,x+109,x+110,x+95,x+95};
			int Rfoot1Y[]={y+100,y+105,y+110,y+110,y+100};
			
			int Rfoot2X[]={x+85,x+86,x+87,x+71,x+71};
			int Rfoot2Y[]={y+100,y+105,y+110,y+110,y+100};
			
			int Rfoot3X[]={x+33,x+34,x+35,x+23,x+24};
			int Rfoot3Y[]={y+100,y+105,y+110,y+110,y+100};
			
			int Rfoot4X[]={x+17,x+20,x+19,x+3,x+5};
			int Rfoot4Y[]={y+100,y+105,y+110,y+103,y+95};
			
			g2.setColor(Color.BLACK);
			g2.fillPolygon(Rfoot1X,Rfoot1Y,5);
			g2.fillPolygon(Rfoot2X,Rfoot2Y,5);
			g2.fillPolygon(Rfoot3X,Rfoot3Y,5);
			g2.fillPolygon(Rfoot4X,Rfoot4Y,5);
			
			g2.drawPolygon(Rfoot1X,Rfoot1Y,5);
			g2.drawPolygon(Rfoot2X,Rfoot2Y,5);
			g2.drawPolygon(Rfoot3X,Rfoot3Y,5);
			g2.drawPolygon(Rfoot4X,Rfoot4Y,5);
			
			//head
			int hx=x+125;
			int hy=y+10;
			
			int headX[]={hx+12,hx+24,hx+27,hx+27,hx+27,hx+26,hx+19,hx,hx-19,hx-26,hx-27,hx-27,hx-27,hx-24,hx-12};
			int headY[]={hy-30,hy-28,hy-25,hy-20,hy,hy+20,hy+18,hy+17,hy+18,hy+20,hy,hy-20,hy-25,hy-28,hy-30};
			
			g2.setColor(new Color(139,69,19));
			g2.fillPolygon(headX,headY,15);
			g2.drawPolygon(headX,headY,15);
			
			//ear
			int RearX[]={hx+27,hx+42,hx+47,hx+42,hx+27};
			int RearY[]={hy-20,hy-17,hy-15,hy-12,hy};
			
			g2.setColor(new Color(210,201,146));
			g2.fillPolygon(RearX,RearY,5);
			g2.drawPolygon(RearX,RearY,5);
			
			int LearX[]={hx-27,hx-42,hx-47,hx-42,hx-27};
			int LearY[]={hy-20,hy-17,hy-15,hy-12,hy};
			
			g2.fillPolygon(LearX,LearY,5);
			g2.drawPolygon(LearX,LearY,5);
			
			//mouth
			int mouthX[]={hx+26,hx+25,hx+23,hx+20,hx,hx-20,hx-23,hx-25,hx-26,hx-19,hx,hx+19};
			int mouthY[]={hy+20,hy+33,hy+37,hy+39,hy+40,hy+39,hy+37,hy+33,hy+20,hy+18,hy+17,hy+18};
			
			g2.setColor(new Color(160,82,45));
			g2.fillPolygon(mouthX,mouthY,12);
			g2.drawPolygon(mouthX,mouthY,12);
			
			//bull
			int RbullX[]={hx+24,hx+35,hx+38,hx+42,hx+40,hx+36,hx+27};
			int RbullY[]={hy-28,hy-34,hy-37,hy-42,hy-24,hy-21,hy-20};
			
			g2.setColor(Color.WHITE);
			g2.fillPolygon(RbullX,RbullY,7);
			g2.drawPolygon(RbullX,RbullY,7);
			
			int LbullX[]={hx-24,hx-35,hx-38,hx-42,hx-40,hx-36,hx-27};
			int LbullY[]={hy-28,hy-34,hy-37,hy-42,hy-24,hy-21,hy-20};
			
			g2.fillPolygon(LbullX,LbullY,7);
			g2.drawPolygon(LbullX,LbullY,7);
			
			//eyes
			Ellipse2D.Double Leye=new Ellipse2D.Double(hx-15,hy-15,7,7);
			
			g2.setColor(Color.BLACK);
			g2.fill(Leye);
			g2.draw(Leye);
			
			Ellipse2D.Double Reye=new Ellipse2D.Double(hx+5,hy-15,7,7);
			g2.fill(Reye);
			g2.draw(Reye);
			
			//mouthline
			g2.setStroke(new BasicStroke(2));
			
			Line2D.Double ml1=new Line2D.Double(hx+17,hy+37,hx+12,hy+36);
			Line2D.Double ml2=new Line2D.Double(hx+12,hy+36,hx+6,hy+35);
			Line2D.Double ml3=new Line2D.Double(hx+6,hy+35,hx,hy+34);
			Line2D.Double ml4=new Line2D.Double(hx,hy+34,hx-6,hy+35);
			Line2D.Double ml5=new Line2D.Double(hx-6,hy+35,hx-12,hy+36);
			Line2D.Double ml6=new Line2D.Double(hx-12,hy+36,hx-17,hy+37);
			
			g2.setColor(Color.BLACK);
			g2.draw(ml1);
			g2.draw(ml2);
			g2.draw(ml3);
			g2.draw(ml4);
			g2.draw(ml5);
			g2.draw(ml6);
			
			g2.setStroke(new BasicStroke(1));
			//nose
			
			int RnoseX[]={hx+7,hx+15,hx+14,hx+6};
			int RnoseY[]={hy+22,hy+21,hy+23,hy+25};
			
			g2.setColor(Color.BLACK);
			g2.fillPolygon(RnoseX,RnoseY,4);
			g2.drawPolygon(RnoseX,RnoseY,4);
			
			int LnoseX[]={hx-7,hx-15,hx-14,hx-6};
			int LnoseY[]={hy+22,hy+21,hy+23,hy+25};
			
			g2.fillPolygon(LnoseX,LnoseY,4);
			g2.drawPolygon(LnoseX,LnoseY,4);
			
			//tail
			int Rtail1X[]={x-4,x-9,x-12,x-14,x-12,x-19,x-18,x-14,x-10,x-5};
			int Rtail1Y[]={y+20,y+24,y+30,y+48,y+56,y+57,y+30,y+24,y+13,y+10};
			
			g2.setColor(new Color(139,69,19));
			g2.fillPolygon(Rtail1X,Rtail1Y,10);
			g2.drawPolygon(Rtail1X,Rtail1Y,10);
			
			int Rtail2X[]={x-12,x-9,x-8,x-10,x-13,x-21,x-23,x-22,x-19};
			int Rtail2Y[]={y+56,y+61,y+66,y+71,y+76,y+71,y+66,y+61,y+57};
			
			g2.setColor(Color.BLACK);
			g2.fillPolygon(Rtail2X,Rtail2Y,9);
			g2.drawPolygon(Rtail2X,Rtail2Y,9);
		}
	}
}
