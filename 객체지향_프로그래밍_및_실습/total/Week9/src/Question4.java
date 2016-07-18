import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JTextField;

public class Question4 extends JPanel
{
	private JButton button;
	private String wholesale;
	private String percentage;
	private JPanel inside;
	private JTextField up;
	private JTextField down;
	private ButtonListener listen;
	
	public Question4()
	{
		wholesale="0";
		percentage="0";
		inside=new JPanel();
		up=new JTextField(20);
		down=new JTextField(10);
		button=new JButton("Click!");
		listen=new ButtonListener();
		
		this.setLayout(new BorderLayout());
		this.add(inside,BorderLayout.SOUTH);
		inside.setLayout(new BorderLayout());
		inside.add(up,BorderLayout.WEST);
		inside.add(down,BorderLayout.CENTER);
		inside.add(button,BorderLayout.EAST);
		
		button.addActionListener(listen);
	}
	
	public class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			wholesale=up.getText();
			percentage=down.getText();			
			repaint();
		}
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;
		
		String out1="Wholesale Price";
		String out2="Markup Percentage";
		String result="Retail price is : "+(1.0+Double.parseDouble(percentage)*0.01)*Double.parseDouble(wholesale);
		
		g2.setFont(new Font(out1,Font.BOLD,20));
		g2.setFont(new Font(out2,Font.BOLD,20));
		g2.setFont(new Font(result,Font.BOLD,20));
		
		g2.drawString(out1, 0, 20);
		g2.drawString(out2, 250, 20);
		g2.drawString(result, 450, 20);
		
		
	}
}
