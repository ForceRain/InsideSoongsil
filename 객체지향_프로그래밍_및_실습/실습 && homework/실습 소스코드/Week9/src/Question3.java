import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class Question3 extends JPanel
{
	private JButton button;
	private JTextField up;
	private JTextField down;
	private String upper;
	private String downer;
	private JPanel inside;
	private ButtonListener check;
	
	public Question3()
	{
		button=new JButton("Calculate!");
		inside=new JPanel();
		up=new JTextField(10);
		down=new JTextField(10);
		
		upper="0";
		downer="1";
		
		this.setLayout(new BorderLayout());
		this.add(inside,BorderLayout.SOUTH);
		
		inside.setLayout(new FlowLayout());
		inside.add(up);
		inside.add(down);
		inside.add(button);
		
		check=new ButtonListener();
		button.addActionListener(check);
	}
	
	public class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			upper=up.getText();
			downer=down.getText();
			repaint();
		}
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2=(Graphics2D)g;
		
		String out="Type Gallons";
		String out1="Type Miles";
		String result="MPG is :"+(Double.parseDouble(upper)/Double.parseDouble(downer));
		
		g2.setFont(new Font(out,Font.BOLD,20));
		g2.setFont(new Font(out1,Font.BOLD,20));
		g2.setFont(new Font(result,Font.BOLD,20));
		
		g2.drawString(out, 50, 50);
		g2.drawString(out1, 200, 50);
		g2.drawString(result,320,50);
	}
}
