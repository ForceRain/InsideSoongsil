import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

class CenterClass extends JPanel implements MyRobot
{
	private MyMouseListener MSListen;
	private double x;
	private double y;
	private double x1;
	private double y1;
	private double width;
	private double height;
	private String CurrentState;
	private String CurrentStateColor;
	private String CurrentMoving;
	private ArrayList<RectangularShape> Container;
	private ArrayList<Color> ColorContainer;
	private MovingTimer MVTimer;
	private Timer MoveTimer;
	private boolean isBef;
	
	public CenterClass()
	{
		MSListen=new MyMouseListener();
		MVTimer=new MovingTimer();
		MoveTimer=new Timer(100,MVTimer);
		isBef=true;
		
		CurrentStateColor="Red";
		CurrentMoving="Stop";
		CurrentState="Rectangle";
		
		Container=new ArrayList<RectangularShape>();
		ColorContainer=new ArrayList<Color>();
	
		this.addMouseListener(MSListen);
	}
	
	public void LastColor(String inputColor)
	{
		CurrentStateColor=inputColor;
	}
	
	public void LastReceive(String inputState)
	{
		if (inputState.equals("Move"))
		{
			CurrentMoving="Move";
			MoveTimer.start();
		}
		else
		{
			CurrentMoving="Stop";
			MoveTimer.stop();
		}
	}
	
	public void LastShape(String inputShape)
	{
		CurrentState=inputShape;
		isBef=false;
		Container.remove(Container.size()-1);
		ColorContainer.remove(ColorContainer.size()-1);
	}
	
	class MovingTimer implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			move();
		}
	}
	
	class MyMouseListener implements MouseListener
	{
		public void mousePressed(MouseEvent event)
		{
			x=event.getX();
			y=event.getY();
			isBef=true;
		}
		public void mouseReleased(MouseEvent event)
		{
			x1=event.getX();
			y1=event.getY();
			if (x>x1)
			{
				double tmp=x;
				x=x1;
				x1=tmp;
			}
			if (y>y1)
			{
				double tmp=y;
				y=y1;
				y1=tmp;
			}
			width=Math.abs(x-x1);
			height=Math.abs(y-y1);
			isBef=true;
			repaint();
		}
		public void mouseClicked(MouseEvent event){}
		public void mouseEntered(MouseEvent event){}
		public void mouseExited(MouseEvent event){}
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;
		printAll(g2);
		this.draw(g2);
	}
	
	public void draw(Graphics2D g2)
	{
		if (CurrentMoving.equals("Stop"))
		{
			if (CurrentState.equals("Rectangle"))
			{
				Rectangle2D.Double obj=new Rectangle2D.Double(x,y,width,height);
				Container.add(obj);
				g2.setColor(ChooseColor(CurrentStateColor));
				g2.fill(obj);
				g2.draw(obj);
			}
			else if (CurrentState.equals("Ellipse"))
			{
				Ellipse2D.Double obj=new Ellipse2D.Double(x,y,width,height);
				Container.add(obj);
				g2.setColor(ChooseColor(CurrentStateColor));
				g2.fill(obj);
				g2.draw(obj);
			}
			else if (CurrentState.equals("Square"))
			{
				double temp;
				if (width<height)
					temp=width;
				else
					temp=height;
				Rectangle2D.Double obj=new Rectangle2D.Double(x,y,temp,temp);
				Container.add(obj);
				g2.setColor(ChooseColor(CurrentStateColor));
				g2.fill(obj);
				g2.draw(obj);
			}
			else if (CurrentState.equals("Circle"))
			{
				double temp;
				if (width<height)
					temp=width;
				else
					temp=height;
				Ellipse2D.Double obj=new Ellipse2D.Double(x,y,temp,temp);
				Container.add(obj);
				g2.setColor(ChooseColor(CurrentStateColor));
				g2.fill(obj);
				g2.draw(obj);
			}
			else if (CurrentState.equals("RoundRectangle"))
			{
				RoundRectangle2D.Double obj=new RoundRectangle2D.Double(x,y,width,height,20,20);
				Container.add(obj);
				g2.setColor(ChooseColor(CurrentStateColor));
				g2.fill(obj);
				g2.draw(obj);
			}
			else
			{
				System.out.println("Undefined Access");
			}
			ColorContainer.add(ChooseColor(CurrentStateColor));
			
			if (!isBef)
			{
				Container.remove(Container.size()-1);
				ColorContainer.remove(ColorContainer.size()-1);
			}
		}
		else
		{
			printAll(g2);
		}		
	}
	
	public Color ChooseColor(String input)
	{
		if (input.equals("Red"))
			return Color.red;
		else if (input.equals("Green"))
			return Color.green;
		else if (input.equals("Blue"))
			return Color.blue;
		else if (input.equals("Orange"))
			return Color.orange;
		else if (input.equals("Yellow"))
			return Color.yellow;
		else
			return Color.black;
	}
	
	public void move()
	{
		for (int i=0;i<Container.size();i++)
		{
			RectangularShape temp=Container.get(i);
			double x=temp.getX();
			x++;
			double y=temp.getY();
			double width=temp.getWidth();
			double height=temp.getHeight();
			temp.setFrame(x,y,width,height);
			Container.set(i, temp);
			repaint();
		}
	}
	public void printAll(Graphics2D g2)
	{
		for (int i=0;i<Container.size();i++)
		{
			g2.setColor(ColorContainer.get(i));
			g2.fill(Container.get(i));
			g2.draw(Container.get(i));
		}
	}
	
}
