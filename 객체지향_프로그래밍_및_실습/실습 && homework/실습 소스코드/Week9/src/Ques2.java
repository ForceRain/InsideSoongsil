import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.FlowLayout;

public class Ques2 extends JPanel
{
	private JButton Go;
	private String top;
	protected JTextField line;
	private JPanel inside;
	private ButtonListener button;
	private String bottom;
	private String num;
	private double wow;
	
	public Ques2()
	{
		line=new JTextField(20);
		inside=new JPanel();
		button=new ButtonListener();
		wow=0;
		
		top="Enter the reading in Celcius";
		
		Go=new JButton("Convert");
		this.setLayout(new BorderLayout());
		inside.setLayout(new FlowLayout());
		
		this.add(inside,BorderLayout.SOUTH);
		
		Go.addActionListener(button);
		
		inside.add(line);
		inside.add(Go);
	}
	
	public class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			num=line.getText();
			wow=Double.parseDouble(num);
			repaint();
		}
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		bottom="Fahrenheit="+(wow*(9/5.0)+32);
		
		Graphics2D g2=(Graphics2D) g;
		
		g2.setFont(new Font(top,Font.BOLD,30));
		g2.drawString(top, 50, 50);
		
		g2.setFont(new Font(bottom,Font.BOLD,20));
		g2.drawString(bottom, 50, 100);
	}
	
}
