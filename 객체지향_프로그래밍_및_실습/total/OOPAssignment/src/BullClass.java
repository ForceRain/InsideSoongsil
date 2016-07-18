import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D.Double;
import javax.swing.JPanel;
import javax.swing.Timer;

public class BullClass extends JPanel //implements Animal
{
	private int x;
	private int y;
	private String CurrentStateLR;
	private String CurrentStateUD;
	private static final int FRAME_WIDTH=1100;
	private static final int FRAME_HEIGHT=700;

	private static final int LEFT_SIDE_COW=100;
	private static final int RIGHT_SIDE_COW=90;
	private static final int UPPER_SIDE_COW=80;
	private static final int LOWER_SIDE_COW=110;

	public BullClass(int xInput,int yInput)
	{
		CurrentStateLR="right";
		CurrentStateUD="down";
		x=xInput;
		y=yInput;
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
	
	
	public void Draw(Graphics2D g2)
	{
		//moving to the left
	
		if (CurrentStateLR.equals("left"))
		{
			Rectangle2D.Double Lhead=new Rectangle2D.Double(x-50,y-50,50,60);
			g2.setColor(new Color(139,69,19));
			g2.fill(Lhead);
			g2.draw(Lhead);
		
			Ellipse2D.Double LEye1=new Ellipse2D.Double(x-40,y-27,10,10);
			Ellipse2D.Double LEye2=new Ellipse2D.Double(x-18,y-27,10,10);
			g2.setColor(Color.black);
			g2.fill(LEye1);
			g2.fill(LEye2);
			g2.draw(LEye1);
			g2.draw(LEye2);
		
			Rectangle2D.Double Lbody=new Rectangle2D.Double(x-30,y-10,110,60);
			g2.setColor(new Color(139,69,19));
			g2.fill(Lbody);
			g2.draw(Lbody);
		
			Rectangle2D.Double Lmouth=new Rectangle2D.Double(x-50,y+10,50,20);
			g2.setColor(new Color(160,82,45));
			g2.fill(Lmouth);
			g2.draw(Lmouth);
		
			Rectangle2D.Double LleftBull=new Rectangle2D.Double(x-90,y-50,40,20);
			g2.setColor(new Color(255,255,255));
			g2.fill(LleftBull);
			g2.draw(LleftBull);
		
			Rectangle2D.Double LrightBull=new Rectangle2D.Double(x,y-50,40,20);
			g2.setColor(new Color(255,255,255));
			g2.fill(LrightBull);
			g2.draw(LrightBull);
		
		
			Rectangle2D.Double Lleg1=new Rectangle2D.Double(x-25,y+50,15,30);
			g2.setColor(new Color(139,69,19));
			g2.fill(Lleg1);
			g2.draw(Lleg1);
		
			Rectangle2D.Double Lleg2=new Rectangle2D.Double(x-5,y+50,15,30);
			g2.setColor(new Color(139,69,19));
			g2.fill(Lleg2);
			g2.draw(Lleg2);
		
			Rectangle2D.Double Lleg3=new Rectangle2D.Double(x+45,y+50,15,30);
			g2.setColor(new Color(139,69,19));
			g2.fill(Lleg3);
			g2.draw(Lleg3);
		
			Rectangle2D.Double Lleg4=new Rectangle2D.Double(x+65,y+50,15,30);
			g2.setColor(new Color(139,69,19));
			g2.fill(Lleg4);
			g2.draw(Lleg4);
		
		
			int LlBullX[]={x-90,x-70,x-80};
			int LlBullY[]={y-50,y-50,y-70};
		
			g2.setColor(Color.WHITE);
			g2.fillPolygon(LlBullX,LlBullY,3);
			g2.drawPolygon(LlBullX,LlBullY,3);
		
			int LrBullX[]={x+20,x+40,x+30};
			int LrBullY[]={y-50,y-50,y-70};
		
			g2.setColor(Color.WHITE);
			g2.fillPolygon(LrBullX,LrBullY,3);
			g2.drawPolygon(LrBullX,LrBullY,3);
		
			int Lfoot1_1x[]={x-25,x-25,x-18};
			int Lfoot1_1y[]={y+80,y+90,y+80};
			int Lfoot1_2x[]={x-18,x-10,x-10};
			int Lfoot1_2y[]={y+80,y+90,y+80};
		
			g2.setColor(Color.BLACK);
			g2.fillPolygon(Lfoot1_1x,Lfoot1_1y,3);
			g2.drawPolygon(Lfoot1_1x,Lfoot1_1y,3);
			g2.fillPolygon(Lfoot1_2x,Lfoot1_2y,3);
			g2.drawPolygon(Lfoot1_2x,Lfoot1_2y,3);
		
			int Lfoot2_1x[]={x-5,x-5,x+3};
			int Lfoot2_1y[]={y+80,y+90,y+80};
			int Lfoot2_2x[]={x+3,x+10,x+10};
			int Lfoot2_2y[]={y+80,y+90,y+80};
		
			g2.fillPolygon(Lfoot2_1x,Lfoot2_1y,3);
			g2.drawPolygon(Lfoot2_1x,Lfoot2_1y,3);
			g2.fillPolygon(Lfoot2_2x,Lfoot2_2y,3);
			g2.drawPolygon(Lfoot2_2x,Lfoot2_2y,3);
		
			int Lfoot3_1x[]={x+45,x+45,x+53};
			int Lfoot3_1y[]={y+80,y+90,y+80};
			int Lfoot3_2x[]={x+53,x+60,x+60};
			int Lfoot3_2y[]={y+80,y+90,y+80};
		
			g2.fillPolygon(Lfoot3_1x,Lfoot3_1y,3);
			g2.drawPolygon(Lfoot3_1x,Lfoot3_1y,3);
			g2.fillPolygon(Lfoot3_2x,Lfoot3_2y,3);
			g2.drawPolygon(Lfoot3_2x,Lfoot3_2y,3);
		
			int Lfoot4_1x[]={x+65,x+65,x+73};
			int Lfoot4_1y[]={y+80,y+90,y+80};
			int Lfoot4_2x[]={x+73,x+80,x+80};
			int Lfoot4_2y[]={y+80,y+90,y+80};
		
			g2.fillPolygon(Lfoot4_1x,Lfoot4_1y,3);
			g2.drawPolygon(Lfoot4_1x,Lfoot4_1y,3);
			g2.fillPolygon(Lfoot4_2x,Lfoot4_2y,3);
			g2.drawPolygon(Lfoot4_2x,Lfoot4_2y,3);
			
			//line
			g2.setColor(Color.black);
			int LleftBulllnx[]={x-80,x-90,x-90,x-50,x-50,x-70};
			int LleftBulllny[]={y-70,y-50,y-30,y-30,y-50,y-50};
			
			int LrightBulllnx[]={x,x+20,x+30,x+40,x+40,x};
			int LrightBulllny[]={y-50,y-50,y-70,y-50,y-30,y-30};
			
			g2.drawPolygon(LleftBulllnx,LleftBulllny,6);
			g2.drawPolygon(LrightBulllnx,LrightBulllny,6);					//bull line
			
			Line2D.Double Lheadln1=new Line2D.Double(x-50,y-50,x-50,y+30);
			Line2D.Double Lheadln2=new Line2D.Double(x,y-50,x,y+30);
			Line2D.Double Lheadln3=new Line2D.Double(x-50,y-50,x,y-50);
			Line2D.Double Lheadln4=new Line2D.Double(x-50,y+30,x,y+30);
			Line2D.Double Lheadln5=new Line2D.Double(x-50,y+10,x,y+10);
			
			g2.draw(Lheadln1);
			g2.draw(Lheadln2);
			g2.draw(Lheadln3);
			g2.draw(Lheadln4);
			g2.draw(Lheadln5);
			
			int Lbodyx[]={x-30,x,x,x+80,x+80,x-30};
			int Lbodyy[]={y+30,y+30,y-10,y-10,y+50,y+50};
			
			g2.drawPolygon(Lbodyx,Lbodyy,6);
			
			Rectangle2D.Double Lleg1ln=new Rectangle2D.Double(x-25,y+50,15,30);
			Rectangle2D.Double Lleg2ln=new Rectangle2D.Double(x-5,y+50,15,30);
			Rectangle2D.Double Lleg3ln=new Rectangle2D.Double(x+45,y+50,15,30);
			Rectangle2D.Double Lleg4ln=new Rectangle2D.Double(x+65,y+50,15,30);
			
			g2.draw(Lleg1ln);
			g2.draw(Lleg2ln);
			g2.draw(Lleg3ln);
			g2.draw(Lleg4ln);
		}
		else
		{
			 Rectangle2D.Double Rhead=new Rectangle2D.Double(x,y-50,50,60);
			 g2.setColor(new Color(139,69,19));
			 g2.fill(Rhead);
			 g2.draw(Rhead);
			 
			 Ellipse2D.Double REye1=new Ellipse2D.Double(x+13,y-27,10,10);
			 Ellipse2D.Double REye2=new Ellipse2D.Double(x+32,y-27,10,10);
			 g2.setColor(Color.black);
			 g2.fill(REye1);
			 g2.fill(REye2);
			 g2.draw(REye1);
			 g2.draw(REye2);
				 
			 Rectangle2D.Double RleftBull=new Rectangle2D.Double(x-40,y-50,40,20);
			 g2.setColor(Color.white);
			 g2.fill(RleftBull);
			 g2.draw(RleftBull);
			 
			 Rectangle2D.Double RrightBull=new Rectangle2D.Double(x+50,y-50,40,20);
			 g2.setColor(Color.white);
			 g2.fill(RrightBull);
			 g2.draw(RrightBull);
			 
			 Rectangle2D.Double Rbody=new Rectangle2D.Double(x-80,y-10,110,60);
			 g2.setColor(new Color(139,69,19));
			 g2.fill(Rbody);
			 g2.draw(Rbody);
			 
			 Rectangle2D.Double Rmouth=new Rectangle2D.Double(x,y+10,50,20);
			 g2.setColor(new Color(160,82,45));
			 g2.fill(Rmouth);
			 g2.draw(Rmouth);
			 
			 Rectangle2D.Double Rleg1=new Rectangle2D.Double(x-80,y+50,15,30);
			 g2.setColor(new Color(139,69,19));
			 g2.fill(Rleg1);
			 g2.draw(Rleg1);
			 
			 Rectangle2D.Double Rleg2=new Rectangle2D.Double(x-60,y+50,15,30);
			 g2.setColor(new Color(139,69,19));
			 g2.fill(Rleg2);
			 g2.draw(Rleg2);
			 
			 Rectangle2D.Double Rleg3=new Rectangle2D.Double(x-10,y+50,15,30);
			 g2.setColor(new Color(139,69,19));
			 g2.fill(Rleg3);
			 g2.draw(Rleg3);
			 
			 Rectangle2D.Double Rleg4=new Rectangle2D.Double(x+10,y+50,15,30);
			 g2.setColor(new Color(139,69,19));
			 g2.fill(Rleg4);
			 g2.draw(Rleg4);
			 
			 int RlBullX[]={x-40,x-20,x-30};
			 int RlBullY[]={y-50,y-50,y-70};
			 g2.setColor(Color.white);
			 g2.fillPolygon(RlBullX,RlBullY,3);
			 g2.drawPolygon(RlBullX,RlBullY,3);
			 
			 int RrBullX[]={x+70,x+90,x+80};
			 int RrBullY[]={y-50,y-50,y-70};
			 g2.setColor(Color.white);
			 g2.fillPolygon(RrBullX,RrBullY,3);
			 g2.drawPolygon(RrBullX,RrBullY,3);
			 
			 int Rfoot1_1x[]={x-80,x-80,x-73};
			 int Rfoot1_1y[]={y+80,y+90,y+80};
			 int Rfoot1_2x[]={x-73,x-65,x-65};
			 int Rfoot1_2y[]={y+80,y+90,y+80};
			 
			 g2.setColor(Color.black);
			 g2.fillPolygon(Rfoot1_1x,Rfoot1_1y,3);
			 g2.fillPolygon(Rfoot1_2x,Rfoot1_2y,3);
			 g2.drawPolygon(Rfoot1_1x,Rfoot1_1y,3);
			 g2.drawPolygon(Rfoot1_2x,Rfoot1_2y,3);
			 
			 int Rfoot2_1x[]={x-60,x-60,x-53};
			 int Rfoot2_1y[]={y+80,y+90,y+80};
			 int Rfoot2_2x[]={x-53,x-45,x-45};
			 int Rfoot2_2y[]={y+80,y+90,y+80};
			 
			 g2.fillPolygon(Rfoot2_1x,Rfoot2_1y,3);
			 g2.fillPolygon(Rfoot2_2x,Rfoot2_2y,3);
			 g2.drawPolygon(Rfoot2_1x,Rfoot2_1y,3);
			 g2.drawPolygon(Rfoot2_2x,Rfoot2_2y,3);
			
			 int Rfoot3_1x[]={x-10,x-10,x-3};
			 int Rfoot3_1y[]={y+80,y+90,y+80};
			 int Rfoot3_2x[]={x-3,x+5,x+5};
			 int Rfoot3_2y[]={y+80,y+90,y+80};
			 
			 g2.fillPolygon(Rfoot3_1x,Rfoot3_1y,3);
			 g2.fillPolygon(Rfoot3_2x,Rfoot3_2y,3);
			 g2.drawPolygon(Rfoot3_1x,Rfoot3_1y,3);
			 g2.drawPolygon(Rfoot3_2x,Rfoot3_2y,3);
			 
			 int Rfoot4_1x[]={x+10,x+10,x+18};
			 int Rfoot4_1y[]={y+80,y+90,y+80};
			 int Rfoot4_2x[]={x+18,x+25,x+25};
			 int Rfoot4_2y[]={y+80,y+90,y+80};
			 
			 g2.fillPolygon(Rfoot4_1x,Rfoot4_1y,3);
			 g2.fillPolygon(Rfoot4_2x,Rfoot4_2y,3);
			 g2.drawPolygon(Rfoot4_1x,Rfoot4_1y,3);
			 g2.drawPolygon(Rfoot4_2x,Rfoot4_2y,3);
			 
			 //line
			 
			 g2.setColor(Color.black);
			 
			 int RleftBulllnx[]={x,x-20,x-30,x-40,x-40,x};
			 int RleftBulllny[]={y-50,y-50,y-70,y-50,y-30,y-30};
			
			 int RrightBulllnx[]={x+50,x+70,x+80,x+90,x+90,x+50};
			 int RrightBulllny[]={y-50,y-50,y-70,y-50,y-30,y-30};
			
			 g2.drawPolygon(RleftBulllnx,RleftBulllny,6);
			 g2.drawPolygon(RrightBulllnx,RrightBulllny,6);					//bull line
			
			 Line2D.Double Rheadln1=new Line2D.Double(x,y-50,x,y+30);
			 Line2D.Double Rheadln2=new Line2D.Double(x,y+30,x+50,y+30);
			 Line2D.Double Rheadln3=new Line2D.Double(x+50,y+30,x+50,y-50);
			 Line2D.Double Rheadln4=new Line2D.Double(x,y-50,x+50,y-50);
			 Line2D.Double Rheadln5=new Line2D.Double(x,y+10,x+50,y+10);
			
			 g2.draw(Rheadln1);
			 g2.draw(Rheadln2);
			 g2.draw(Rheadln3);
			 g2.draw(Rheadln4);
			 g2.draw(Rheadln5);
			
			 int Rbodyx[]={x+30,x+30,x,x,x-80,x-80};
			 int Rbodyy[]={y+50,y+30,y+30,y-10,y-10,y+50};
			
			 g2.drawPolygon(Rbodyx,Rbodyy,6);
			
			 Rectangle2D.Double Rleg1ln=new Rectangle2D.Double(x-80,y+50,15,30);
			 Rectangle2D.Double Rleg2ln=new Rectangle2D.Double(x-60,y+50,15,30);
			 Rectangle2D.Double Rleg3ln=new Rectangle2D.Double(x-10,y+50,15,30);
			 Rectangle2D.Double Rleg4ln=new Rectangle2D.Double(x+10,y+50,15,30);
			
			 g2.draw(Rleg1ln);
			 g2.draw(Rleg2ln);
			 g2.draw(Rleg3ln);
			 g2.draw(Rleg4ln);
		}
	}
}
