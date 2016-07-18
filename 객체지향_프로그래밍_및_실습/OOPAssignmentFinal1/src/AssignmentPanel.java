import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.util.Random;

public class AssignmentPanel extends JPanel
{
	private ArrayList<Animal> animalContainer;
	private ArrayList<StarClass> starContainer;
	private Timer timer;
	private MoveTimer MVTimer;
	private Image img;
	private MyMouseListener MSListener;
	private MyMouseMTListener MSMTListener;
	private MyMSWheelListener MSWHListener;
	private SelectPanel ChoosePanel;
	private Animal tempAnimal;
	private boolean changePos;
	private boolean isFeverTime;
	private boolean starNumChecked;
	private Random rand;
	private int starTotal;
	
	public AssignmentPanel()
	{		
		changePos=false;
		this.setLayout(new BorderLayout());
		
		ChoosePanel=new SelectPanel();
		this.add(ChoosePanel,BorderLayout.NORTH);
		
		animalContainer=new ArrayList<Animal>();
		starContainer=new ArrayList<StarClass>();
		
		rand=new Random();
		makeStar();		
		
		MVTimer=new MoveTimer();
		timer=new Timer(10,MVTimer);
		
		timer.start();
		
		MSListener=new MyMouseListener();
		this.addMouseListener(MSListener);
		MSMTListener=new MyMouseMTListener();
		this.addMouseMotionListener(MSMTListener);
		MSWHListener=new MyMSWheelListener();
		this.addMouseWheelListener(MSWHListener);
		
		isFeverTime=false;
		starNumChecked=false;
	}
	
	public void makeStar()
	{
		for (int i=0;i<20;i++)
		{
			StarClass star=new StarClass(rand.nextInt(1000),rand.nextInt(500));
			starContainer.add(star);
		}
	}
	
	public void moveStar()
	{
		for (int i=0;i<starContainer.size();i++)
		{
			StarClass tmp=starContainer.get(i);
			tmp.starDirection();
			tmp.starMove();
		}
	}
	
	class MoveTimer implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			chooseBackGround();
			setStateAnimal();
			moveStar();
			repaint();
		}
	}
	
	class MyMouseMTListener implements MouseMotionListener
	{
		public void mouseDragged(MouseEvent event)
		{
			try
			{
				tempAnimal.changePosition(event.getX(), event.getY());
				repaint();
			}
			catch (Exception E)
			{}
		}
		public void mouseMoved(MouseEvent event){}
	}
	
	class MyMouseListener implements MouseListener
	{
		public void mouseClicked(MouseEvent event){}
		public void mouseEntered(MouseEvent event){}
		public void mouseExited(MouseEvent event){}
		public void mousePressed(MouseEvent event)
		{
			boolean plus=true;
			int curX=event.getX();int curY=event.getY();
			
			plus=!thereAnimal(curX,curY);
			
			if (plus && (0<curX && curX<1100) && (80<curY && curY<700))
				AddAnimal(curX,curY);
		}
		public void mouseReleased(MouseEvent event)
		{
			if (changePos)
			{
				changePos=false;
				tempAnimal.sound();
				tempAnimal.changePosition(event.getX(), event.getY());
			}
		}
	}
	
	class MyMSWheelListener implements MouseWheelListener
	{
		public void mouseWheelMoved(MouseWheelEvent event)
		{
			int x=event.getX();int y=event.getY();
			if (thereAnimal(x,y))
			{
				changePos=false;
				tempAnimal.sound();
			}
		}
	}
	
	public void setTotalStar()
	{
		if (!starNumChecked)
		{
			starNumChecked=true;
			starTotal=ChoosePanel.getRandNum(starContainer.size());
		}
	}
	
	public void setStateAnimal()
	{
		if (ChoosePanel.getMovingState())				//true==normalMove!
		{
			for (Animal obj: animalContainer)
			{
				isFeverTime=false;
				starNumChecked=false;
				obj.setState(true);						//false==animal with FEVERTIME
			}
		}
		else
		{
			for (Animal obj: animalContainer)
			{
				isFeverTime=true;
				setTotalStar();
				obj.setState(false);
			}
		}
	}
	
	public void AddAnimal(int x,int y)
	{
		String temp=ChoosePanel.WhatAnimal();
		
		if (temp.equals("Bull"))
		{
			Animal obj=new BullClass(x,y);
			animalContainer.add(obj);
			Thread tmp=new Thread(obj);
			repaint();
			tmp.start();
		}
		if (temp.equals("Lion"))
		{
			Animal obj=new LionClass(x,y);
			animalContainer.add(obj);
			Thread tmp=new Thread(obj);
			repaint();
			tmp.start();
		}
		
		if (temp.equals("Elephant"))
		{
			Animal obj=new ElephantClass(x,y);
			animalContainer.add(obj);
			Thread tmp=new Thread(obj);
			repaint();
			tmp.start();
		}
		
		if (temp.equals("Bear"))
		{
			Animal obj=new BearClass(x,y);
			animalContainer.add(obj);
			Thread tmp=new Thread(obj);
			repaint();
			tmp.start();
		}
		
		if (temp.equals("Frog"))
		{
			Animal obj=new FrogClass(x,y);
			animalContainer.add(obj);
			Thread tmp=new Thread(obj);
			repaint();
			tmp.start();
		}
		
		if (temp.equals("Gorilla"))
		{
			Animal obj=new GorillaClass(x,y);
			animalContainer.add(obj);
			Thread tmp=new Thread(obj);
			repaint();
			tmp.start();
		}
	}
	
	public boolean thereAnimal(int X,int Y)
	{
		for (int i=0;i<animalContainer.size();i++)
		{
			Animal obj=animalContainer.get(i);
			if (obj.isAnimal(X,Y))
			{
				tempAnimal=obj;
				changePos=true;
				return true;			//found
			}
		}
		return false;					//not found
	}
	
	public void chooseBackGround()
	{
		int key=ChoosePanel.getBackNum();
		
		if (key==1)
		{
			Toolkit tk=Toolkit.getDefaultToolkit();
			img=tk.getImage("BackgroundPicture.jpg");
		}
		else if (key==2)
		{
			Toolkit tk=Toolkit.getDefaultToolkit();
			img=tk.getImage("BackgroundPicture1.jpg");
		}
		else if (key==3)
		{
			Toolkit tk=Toolkit.getDefaultToolkit();
			img=tk.getImage("BackgroundPicture2.jpg");
		}
		else
		{
			Toolkit tk=Toolkit.getDefaultToolkit();
			img=tk.getImage("BackgroundPicture3.jpg");
		}
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;
		g2.drawImage(img,0,30,1100,700,this);
				
		if (isFeverTime)
		{
			for (int j=0;j<starTotal;j++)
			{
				StarClass tmp=starContainer.get(j);
				tmp.draw(g2);
			}
		}
		
		for (int i=0;i<animalContainer.size();i++)
		{
			Animal obj=animalContainer.get(i);
			obj.Draw(g2);
		}
	}
}
