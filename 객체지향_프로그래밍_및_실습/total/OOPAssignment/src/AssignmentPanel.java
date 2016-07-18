import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class AssignmentPanel extends JPanel
{
	private ArrayList<Animal> animalContainer;
	private Timer timer;
	private MoveTimer MVTimer;
	private Image img;
	private MyMouseListener MSListener;
	private SelectPanel ChoosePanel;
	private Animal tempAnimal;
	private boolean changePos;
	private boolean isMoving;
	
	public AssignmentPanel()
	{
		String location="C:\\Users\\이진우\\workspace\\OOPAssignment.BackGroundPicture.jpg";
		File f=new File(location);
		
		changePos=false;
		
		System.out.println(f.getAbsolutePath());
		
		Toolkit tk=Toolkit.getDefaultToolkit();
		img=tk.getImage("C:\\Users\\이진우\\workspace\\OOPAssignment\\BackGroundPicture.jpg");
		
		this.setLayout(new BorderLayout());
		
		ChoosePanel=new SelectPanel();
		this.add(ChoosePanel,BorderLayout.NORTH);
		
		animalContainer=new ArrayList<Animal>();
		
		MVTimer=new MoveTimer();
		timer=new Timer(10,MVTimer);
		
		timer.start();
		
		MSListener=new MyMouseListener();
		this.addMouseListener(MSListener);
		
	}
	
	class MyMouseMTlistener implements MouseMotionListener
	{
		public void mouseDragged(MouseEvent event)
		{
			
		}
		public void mouseMoved(MouseEvent event){}
	}
	
	class MyMouseListener implements MouseListener
	{
		public void mouseClicked(MouseEvent event)
		{
			boolean plus=true;
			int curX=event.getX();int curY=event.getY();
			
			plus=!thereAnimal(curX,curY);
			
			if (plus)
				AddAnimal(curX,curY);
		}
		public void mouseEntered(MouseEvent event){}
		public void mouseExited(MouseEvent event){}
		public void mousePressed(MouseEvent event){}
		public void mouseReleased(MouseEvent event)
		{
			if (changePos)
			{
				changePos=false;
				tempAnimal.changePosition(event.getX(), event.getY());
			}
		}
	}
	
	public void AddAnimal(int x,int y)
	{
		String temp=ChoosePanel.WhatAnimal();
		/*
		if (temp.equals("Bull"))
			animalContainer.add(new BullClass(x,y));
		if (temp.equals("Lion"))
			animalContainer.add(new LionClass(x,y));*/
	}
	
	public boolean thereAnimal(int X,int Y)
	{
		for (int i=0;i<animalContainer.size();i++)
		{
			System.out.println("Check!");
			Animal obj=animalContainer.get(i);
			if (obj.isAnimal(X,Y))
			{
				tempAnimal=obj;
				changePos=true;
				System.out.println("There is animal!");
				return true;//found
			}
		}
		//not found
		return false;
	}
	
	class MoveTimer implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			repaint();
		}
	}
		
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;
		g2.drawImage(img,0,30,1100,700,this);
		
		for (int i=0;i<animalContainer.size();i++)
		{
			System.out.println("Called & size is :"+animalContainer.size());
			Animal obj=animalContainer.get(i);
			obj.HowToMove();
			obj.LetsMove();
			obj.Draw(g2);
		}
	}
}
