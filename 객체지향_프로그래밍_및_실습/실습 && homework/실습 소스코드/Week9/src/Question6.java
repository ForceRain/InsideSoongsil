import javax.swing.JPanel;
import javax.swing.JRadioButton;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import java.util.Random;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.ButtonGroup;

public class Question6 extends JPanel
{
	private ButtonListener listen;
	private JButton button;
	private JRadioButton red;
	private JRadioButton green;
	private JRadioButton blue;
	private JPanel upper;
	private String output;
	private Random rand;
	private ButtonGroup group;
	
	public Question6()
	{
		output="BLUE";
		button=new JButton("Change Color");
		red=new JRadioButton("RED");
		green=new JRadioButton("GREEN");
		blue=new JRadioButton("BLUE");
		upper=new JPanel();
		listen=new ButtonListener();
		rand=new Random();
		group=new ButtonGroup();
		
		this.setLayout(new BorderLayout());
		group.add(red);
		group.add(green);
		group.add(blue);
		upper.add(red);
		upper.add(green);
		upper.add(blue);
		
		this.add(upper,BorderLayout.NORTH);
		this.add(button,BorderLayout.SOUTH);
		
		button.addActionListener(listen);
		
	}
	
	public class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if (red.isSelected())
				output="RED";
			if (green.isSelected())
				output="GREEN";
			if (blue.isSelected())
				output="BLUE";
			repaint();
		}
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;
		Rectangle obj=new Rectangle(rand.nextInt(300),rand.nextInt(300),rand.nextInt(300),rand.nextInt(300));
		
		if (output.equals("RED"))
		{
			g2.setColor(Color.RED);
			g2.fill(obj);
			g2.draw(obj);
		}
		else if (output.equals("GREEN"))
		{
			g2.setColor(Color.GREEN);
			g2.fill(obj);
			g2.draw(obj);
		}
		else
		{
			g2.setColor(Color.BLUE);
			g2.fill(obj);
			g2.draw(obj);
		}
	}
}
