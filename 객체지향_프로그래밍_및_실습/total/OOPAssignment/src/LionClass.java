import javax.swing.JPanel;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.BasicStroke;

public class LionClass extends JPanel //implements Animal
{
	private int x;
	private int y;
	private String CurrentStateUD;
	private String CurrentStateLR;
	
	private static final int FRAME_WIDTH=1100;
	private static final int FRAME_HEIGHT=700;
	
	private static final int LEFT_SIDE_LION=50;
	private static final int RIGHT_SIDE_LION=160;
	private static final int UPPER_SIDE_LION=70;
	private static final int LOWER_SIDE_LION=100;
	
	public LionClass(int x,int y)
	{
		this.x=x;
		this.y=y;
		
		CurrentStateUD="up";
		CurrentStateLR="right";
	}

	public boolean isAnimal(int ms_x,int ms_y)
	{
		boolean LR=true;
		boolean UD=true;
		if (x-LEFT_SIDE_LION<ms_x && ms_x<x+RIGHT_SIDE_LION)
			LR=true;
		else
			LR=false;
		
		if (y-UPPER_SIDE_LION<ms_y && ms_y<y+LOWER_SIDE_LION)
			UD=true;
		else
			UD=false;
		
		return (LR&&UD);
	}
		
	public void HowToMove()
	{
		if (x-LEFT_SIDE_LION<0)
			CurrentStateLR="right";
		if (x+RIGHT_SIDE_LION+20>FRAME_WIDTH)
			CurrentStateLR="left";
		if (y-UPPER_SIDE_LION<70)
			CurrentStateUD="down";
		if (y+LOWER_SIDE_LION+20>FRAME_HEIGHT)
			CurrentStateUD="up";
	}
	
	public void LetsMove()
	{
		if (CurrentStateLR.equals("right"))
			x++;
		if (CurrentStateLR.equals("left"))
			x--;
		if (CurrentStateUD.equals("up"))
			y--;
		if (CurrentStateUD.equals("down"))
			y++;
	}
	
	public void changePosition(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
	
	public void Draw(Graphics2D g2)
	{
		if (CurrentStateLR.equals("left"))
		{
			Rectangle2D.Double Lbody=new Rectangle2D.Double(x,y,110,50);
			g2.setColor(new Color(212,161,80));
			g2.fill(Lbody);
			g2.draw(Lbody);
		
			int LFur1x[]={x+11,x+33,x+28};
			int LFur1y[]={y-38,y-43,y-21};
			int LFur2x[]={x+28,x+48,x+28};
			int LFur2y[]={y-21,y-10,y+1};
			int LFur3x[]={x+28,x+33,x+11};
			int LFur3y[]={y+1,y+23,y+18};
			int LFur4x[]={x+11,x,x-11};
			int LFur4y[]={y+18,y+38,y+18};
			int LFur5x[]={x-11,x-33,x-28};
			int LFur5y[]={y+18,y+23,y+1};
			int LFur6x[]={x-28,x-48,x-28};
			int LFur6y[]={y+1,y-10,y-21};
			int LFur7x[]={x-28,x-33,x-11};
			int LFur7y[]={y-21,y-43,y-38};
			int LFur8x[]={x-11,x,x+11};
			int LFur8y[]={y-38,y-58,y-38};
		
			g2.setColor(new Color(170,66,37));				//fur
			g2.fillPolygon(LFur1x,LFur1y,3);
			g2.fillPolygon(LFur2x,LFur2y,3);
			g2.fillPolygon(LFur3x,LFur3y,3);
			g2.fillPolygon(LFur4x,LFur4y,3);
			g2.fillPolygon(LFur5x,LFur5y,3);
			g2.fillPolygon(LFur6x,LFur6y,3);
			g2.fillPolygon(LFur7x,LFur7y,3);
			g2.fillPolygon(LFur8x,LFur8y,3);
		
			g2.drawPolygon(LFur1x,LFur1y,3);
			g2.drawPolygon(LFur2x,LFur2y,3);
			g2.drawPolygon(LFur3x,LFur3y,3);
			g2.drawPolygon(LFur4x,LFur4y,3);
			g2.drawPolygon(LFur5x,LFur5y,3);
			g2.drawPolygon(LFur6x,LFur6y,3);
			g2.drawPolygon(LFur7x,LFur7y,3);
			g2.drawPolygon(LFur8x,LFur8y,3);
		
			int LFurin1x[]={x+28,x+30,x+46,x+38};
			int LFurin1y[]={y-21,y-31,y-29,y-15};
			int LFurin2x[]={x+28,x+38,x+46,x+30};
			int LFurin2y[]={y+1,y-4,y+9,y+12};
			int LFurin3x[]={x+11,x+22,x+19,x+5};
			int LFurin3y[]={y+18,y+20,y+38,y+28};
			int LFurin4x[]={x-11,x-5,x-19,x-22};
			int LFurin4y[]={y+18,y+28,y+38,y+20};
			int LFurin5x[]={x-28,x-30,x-46,x-38};
			int LFurin5y[]={y+1,y+12,y+9,y-4};
			int LFurin6x[]={x-28,x-38,x-46,x-30};
			int LFurin6y[]={y-21,y-15,y-29,y-31};
			int LFurin7x[]={x-11,x-22,x-19,x-5};
			int LFurin7y[]={y-38,y-40,y-58,y-48};
			int LFurin8x[]={x+11,x+5,x+19,x+22};
			int LFurin8y[]={y-38,y-48,y-58,y-40};
		
			g2.setColor(new Color(140,30,43));							//between fur
			g2.fillPolygon(LFurin1x,LFurin1y,4);
			g2.fillPolygon(LFurin2x,LFurin2y,4);
			g2.fillPolygon(LFurin3x,LFurin3y,4);
			g2.fillPolygon(LFurin4x,LFurin4y,4);
			g2.fillPolygon(LFurin5x,LFurin5y,4);
			g2.fillPolygon(LFurin6x,LFurin6y,4);
			g2.fillPolygon(LFurin7x,LFurin7y,4);
			g2.fillPolygon(LFurin8x,LFurin8y,4);
		
			g2.drawPolygon(LFurin1x,LFurin1y,4);
			g2.drawPolygon(LFurin2x,LFurin2y,4);
			g2.drawPolygon(LFurin3x,LFurin3y,4);
			g2.drawPolygon(LFurin4x,LFurin4y,4);
			g2.drawPolygon(LFurin5x,LFurin5y,4);
			g2.drawPolygon(LFurin6x,LFurin6y,4);
			g2.drawPolygon(LFurin7x,LFurin7y,4);
			g2.drawPolygon(LFurin8x,LFurin8y,4);
		
			//head
			Ellipse2D.Double Lhead=new Ellipse2D.Double(x-30,y-40,60,60);
			g2.setColor(new Color(212,160,80));
			g2.fill(Lhead);
			g2.draw(Lhead);
		
			//eye
			Ellipse2D.Double LEye1=new Ellipse2D.Double(x-17,y-27,10,10);
			Ellipse2D.Double LEye2=new Ellipse2D.Double(x+7,y-27,10,10);
			g2.setColor(Color.black);
			g2.fill(LEye1);
			g2.fill(LEye2);
			g2.draw(LEye1);
			g2.draw(LEye2);
		
			Rectangle2D.Double Lleg1=new Rectangle2D.Double(x+5,y+50,15,30);
			g2.setColor(new Color(212,160,80));
			g2.fill(Lleg1);
			g2.draw(Lleg1);
		
			Rectangle2D.Double Lleg2=new Rectangle2D.Double(x+30,y+50,15,30);
			g2.fill(Lleg2);
			g2.draw(Lleg2);
		
			Rectangle2D.Double Lleg3=new Rectangle2D.Double(x+70,y+50,15,30);
			g2.fill(Lleg3);
			g2.draw(Lleg3);
		
			Rectangle2D.Double Lleg4=new Rectangle2D.Double(x+95,y+50,15,30);
			g2.fill(Lleg4);
			g2.draw(Lleg4);
		
			int Lfoot1x[]={x+5,x+4,x+3,x+20,x+20};
			int Lfoot1y[]={y+80,y+85,y+90,y+90,y+80};
		
			int Lfoot2x[]={x+30,x+29,x+28,x+45,x+45};
			int Lfoot2y[]={y+80,y+85,y+90,y+90,y+80};
			
			int Lfoot3x[]={x+70,x+69,x+68,x+85,x+85};
			int Lfoot3y[]={y+80,y+85,y+90,y+90,y+80};
		
			int Lfoot4x[]={x+95,x+94,x+93,x+110,x+110};
			int Lfoot4y[]={y+80,y+85,y+90,y+90,y+80};
		
			g2.setColor(new Color(210,201,146));
			g2.fillPolygon(Lfoot1x,Lfoot1y,5);
			g2.fillPolygon(Lfoot2x,Lfoot2y,5);
			g2.fillPolygon(Lfoot3x,Lfoot3y,5);
			g2.fillPolygon(Lfoot4x,Lfoot4y,5);
			g2.drawPolygon(Lfoot1x,Lfoot1y,5);
			g2.drawPolygon(Lfoot2x,Lfoot2y,5);
			g2.drawPolygon(Lfoot3x,Lfoot3y,5);	
			g2.drawPolygon(Lfoot4x,Lfoot4y,5);
		
			int Ltail1x[]={x+110,x+110,x+130,x+140};
			int Ltail1y[]={y,y+10,y+40,y+30};
			int Ltail2x[]={x+130,x+145,x+150,x+140};
			int Ltail2y[]={y+40,y+60,y+50,y+30};
		
			g2.setColor(new Color(212,160,80));
			g2.fillPolygon(Ltail1x,Ltail1y,4);
			g2.drawPolygon(Ltail1x,Ltail1y,4);
		
			g2.setColor(new Color(140,30,43));
			g2.fillPolygon(Ltail2x,Ltail2y,4);
			g2.drawPolygon(Ltail2x,Ltail2y,4);
		
			//mouth
			Line2D.Double Lmouth1=new Line2D.Double(x,y-10,x,y+2);
			Line2D.Double Lmouth2=new Line2D.Double(x,y+2,x+10,y+8);
			Line2D.Double Lmouth3=new Line2D.Double(x,y+2,x-10,y+8);
		
			g2.setColor(Color.black);
			g2.setStroke(new BasicStroke(3));
			g2.draw(Lmouth1);
			g2.draw(Lmouth2);
			g2.draw(Lmouth3);
		
			//nose
			Ellipse2D.Double Lnose=new Ellipse2D.Double(x-3,y-10,6,6);
			g2.fill(Lnose);
			g2.draw(Lnose);
			
			g2.setStroke(new BasicStroke(1));
		}
		else
		{
			Rectangle2D.Double Rbody=new Rectangle2D.Double(x,y,110,50);
			g2.setColor(new Color(212,161,80));
			g2.fill(Rbody);
			g2.draw(Rbody);
			
			//fur
			int Rfur1x[]={x+121,x+143,x+138};
			int Rfur1y[]={y-38,y-43,y-21};			
			int Rfur2x[]={x+138,x+158,x+138};
			int Rfur2y[]={y-21,y-10,y+1};			
			int Rfur3x[]={x+138,x+143,x+121};
			int Rfur3y[]={y+1,y+23,y+18};			
			int Rfur4x[]={x+121,x+110,x+99};
			int Rfur4y[]={y+18,y+38,y+18};			
			int Rfur5x[]={x+99,x+77,x+82};
			int Rfur5y[]={y+18,y+23,y+1};			
			int Rfur6x[]={x+82,x+62,x+82};
			int Rfur6y[]={y+1,y-10,y-21};			
			int Rfur7x[]={x+82,x+77,x+99};
			int Rfur7y[]={y-21,y-43,y-38};
			int Rfur8x[]={x+99,x+110,x+121};
			int Rfur8y[]={y-38,y-58,y-38};
			
			g2.setColor(new Color(170,66,37));
			g2.fillPolygon(Rfur1x,Rfur1y,3);
			g2.fillPolygon(Rfur2x,Rfur2y,3);
			g2.fillPolygon(Rfur3x,Rfur3y,3);
			g2.fillPolygon(Rfur4x,Rfur4y,3);
			g2.fillPolygon(Rfur5x,Rfur5y,3);
			g2.fillPolygon(Rfur6x,Rfur6y,3);
			g2.fillPolygon(Rfur7x,Rfur7y,3);
			g2.fillPolygon(Rfur8x,Rfur8y,3);
			
			g2.drawPolygon(Rfur1x,Rfur1y,3);
			g2.drawPolygon(Rfur2x,Rfur2y,3);
			g2.drawPolygon(Rfur3x,Rfur3y,3);
			g2.drawPolygon(Rfur4x,Rfur4y,3);
			g2.drawPolygon(Rfur5x,Rfur5y,3);
			g2.drawPolygon(Rfur6x,Rfur6y,3);
			g2.drawPolygon(Rfur7x,Rfur7y,3);
			g2.drawPolygon(Rfur8x,Rfur8y,3);
			
			int Rfurin1x[]={x+140,x+156,x+148,x+138};
			int Rfurin1y[]={y-32,y-29,y-15,y-21};
			int Rfurin2x[]={x+148,x+156,x+140,x+138};
			int Rfurin2y[]={y-4,y+9,y+12,y+1};
			int Rfurin3x[]={x+132,x+129,x+115,x+121};
			int Rfurin3y[]={y+20,y+38,y+28,y+18};
			int Rfurin4x[]={x+104,x+91,x+88,x+99};
			int Rfurin4y[]={y+28,y+38,y+20,y+18};
			int Rfurin5x[]={x+79,x+64,x+72,x+82};
			int Rfurin5y[]={y+12,y+9,y-4,y+1};
			int Rfurin6x[]={x+72,x+64,x+79,x+82};
			int Rfurin6y[]={y-15,y-29,y-32,y-21};
			int Rfurin7x[]={x+88,x+91,x+104,x+99};
			int Rfurin7y[]={y-40,y-58,y-48,y-38};
			int Rfurin8x[]={x+115,x+129,x+132,x+121};
			int Rfurin8y[]={y-48,y-58,y-40,y-38};
			
			g2.setColor(new Color(140,30,43));
			g2.fillPolygon(Rfurin1x,Rfurin1y,4);
			g2.fillPolygon(Rfurin2x,Rfurin2y,4);
			g2.fillPolygon(Rfurin3x,Rfurin3y,4);
			g2.fillPolygon(Rfurin4x,Rfurin4y,4);
			g2.fillPolygon(Rfurin5x,Rfurin5y,4);
			g2.fillPolygon(Rfurin6x,Rfurin6y,4);
			g2.fillPolygon(Rfurin7x,Rfurin7y,4);
			g2.fillPolygon(Rfurin8x,Rfurin8y,4);
			
			g2.drawPolygon(Rfurin1x,Rfurin1y,4);
			g2.drawPolygon(Rfurin2x,Rfurin2y,4);
			g2.drawPolygon(Rfurin3x,Rfurin3y,4);
			g2.drawPolygon(Rfurin4x,Rfurin4y,4);
			g2.drawPolygon(Rfurin5x,Rfurin5y,4);
			g2.drawPolygon(Rfurin6x,Rfurin6y,4);
			g2.drawPolygon(Rfurin7x,Rfurin7y,4);
			g2.drawPolygon(Rfurin8x,Rfurin8y,4);
			
			Ellipse2D.Double Rhead=new Ellipse2D.Double(x+80,y-40,60,60);
			g2.setColor(new Color(212,160,80));
			g2.fill(Rhead);
			g2.draw(Rhead);
			
			Ellipse2D.Double REye1=new Ellipse2D.Double(x+93,y-27,10,10);
			Ellipse2D.Double REye2=new Ellipse2D.Double(x+117,y-27,10,10);
			g2.setColor(Color.black);
			g2.fill(REye1);
			g2.fill(REye2);
			g2.draw(REye1);
			g2.draw(REye2);
			
			
			Rectangle2D.Double Rleg1=new Rectangle2D.Double(x,y+50,15,30);
			Rectangle2D.Double Rleg2=new Rectangle2D.Double(x+25,y+50,15,30);			
			Rectangle2D.Double Rleg3=new Rectangle2D.Double(x+65,y+50,15,30);			
			Rectangle2D.Double Rleg4=new Rectangle2D.Double(x+90,y+50,15,30);
			g2.setColor(new Color(212,160,80));
			g2.fill(Rleg1);
			g2.fill(Rleg2);
			g2.fill(Rleg3);
			g2.fill(Rleg4);
			g2.draw(Rleg1);
			g2.draw(Rleg2);
			g2.draw(Rleg3);
			g2.draw(Rleg4);
			
			int Rtail1x[]={x,x,x-20,x-30};
			int Rtail1y[]={y,y+10,y+40,y+30};
			int Rtail2x[]={x-20,x-35,x-40,x-30};
			int Rtail2y[]={y+40,y+60,y+50,y+30};
			
			g2.setColor(new Color(212,160,80));
			g2.fillPolygon(Rtail1x,Rtail1y,4);
			g2.drawPolygon(Rtail1x,Rtail1y,4);
			
			g2.setColor(new Color(140,30,43));
			g2.fillPolygon(Rtail2x,Rtail2y,4);
			g2.drawPolygon(Rtail2x,Rtail2y,4);
			
			int Rfoot1x[]={x,x,x+17,x+16,x+15};
			int Rfoot1y[]={y+80,y+90,y+90,y+85,y+80};
			
			int Rfoot2x[]={x+25,x+25,x+42,x+41,x+40};
			int Rfoot2y[]={y+80,y+90,y+90,y+85,y+80};
			
			int Rfoot3x[]={x+65,x+65,x+82,x+81,x+80};
			int Rfoot3y[]={y+80,y+90,y+90,y+85,y+80};
			
			int Rfoot4x[]={x+90,x+90,x+107,x+106,x+105};
			int Rfoot4y[]={y+80,y+90,y+90,y+85,y+80};
			
			g2.setColor(new Color(210,201,146));
			g2.fillPolygon(Rfoot1x,Rfoot1y,5);
			g2.fillPolygon(Rfoot2x,Rfoot2y,5);
			g2.fillPolygon(Rfoot3x,Rfoot3y,5);
			g2.fillPolygon(Rfoot4x,Rfoot4y,5);
			g2.drawPolygon(Rfoot1x,Rfoot1y,5);
			g2.drawPolygon(Rfoot2x,Rfoot2y,5);
			g2.drawPolygon(Rfoot3x,Rfoot3y,5);
			g2.drawPolygon(Rfoot4x,Rfoot4y,5);
			
			Line2D.Double Rmouth1=new Line2D.Double(x+110,y-10,x+110,y+2);
			Line2D.Double Rmouth2=new Line2D.Double(x+110,y+2,x+120,y+8);
			Line2D.Double Rmouth3=new Line2D.Double(x+110,y+2,x+100,y+8);
			
			g2.setColor(Color.black);
			g2.setStroke(new BasicStroke(3));
			
			g2.draw(Rmouth1);
			g2.draw(Rmouth2);
			g2.draw(Rmouth3);
			
			Ellipse2D.Double Rnose=new Ellipse2D.Double(x+107,y-10,6,6);
			g2.draw(Rnose);
			
			g2.setStroke(new BasicStroke(1));
		}
	}
}
