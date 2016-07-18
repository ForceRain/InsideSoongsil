import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.geom.Ellipse2D;
import java.util.Random;
import java.awt.Rectangle;

public class ComboQues4 extends JPanel
{
	private JComboBox comBox;
	private String input;
	
	public ComboQues4()
	{
		this.setLayout(new BorderLayout());
		
		class Listener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				int check=comBox.getSelectedIndex();
				if (check==1)
					input="Circle";
				else
					input="Rectangle";
				repaint();
			}
		}
		Listener listen=new Listener();
		
		input="Rectangle";
		comBox=new JComboBox();
		this.add(comBox,BorderLayout.SOUTH);
		
		comBox.addItem("Rectangle");
		comBox.addItem("Circle");
		comBox.addActionListener(listen);
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Random ran=new Random();
		Graphics2D g2=(Graphics2D)g;
		
		if (input.equals("Rectangle"))
		{
			for (int i=0;i<20;i++)
			{
				Rectangle rec=new Rectangle(ran.nextInt(300),ran.nextInt(300),ran.nextInt(300),ran.nextInt(300));
				g2.draw(rec);
			}
			
		}
		else
		{
			for (int i=0;i<20;i++)
			{
				int radius=ran.nextInt(150);
				Ellipse2D.Double ellipse=new Ellipse2D.Double(ran.nextInt(300),ran.nextInt(300),2*radius,2*radius);
				g2.draw(ellipse);
			}
		}
	}
}
