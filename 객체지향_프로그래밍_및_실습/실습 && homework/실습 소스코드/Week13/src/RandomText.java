import java.util.Scanner;
import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;
import java.awt.Font;
import java.awt.Color;
import javax.swing.Timer;

public class RandomText extends JPanel
{
	private String line;
	private Random rand;
	private Timer time;
	private PTimer goTimer;
	
	public RandomText()
	{
		this.setBackground(Color.BLACK);
		rand=new Random();
		Scanner in=new Scanner(System.in);
		System.out.println("Enter your message");
		line=in.nextLine();
		
		in.close();
		
		goTimer=new PTimer();
		time=new Timer(300,goTimer);
		
		time.start();
	}
	
	class PTimer implements ActionListener
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
		int roll=rand.nextInt(20);
		
		for (int i=0;i<roll;i++)
		{
			g2.setFont(new Font(line,Font.ITALIC,rand.nextInt(45)));
			g2.setColor(new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256)));
			g2.drawString(line, rand.nextInt(500), rand.nextInt(500));
		}
		
	}
}
