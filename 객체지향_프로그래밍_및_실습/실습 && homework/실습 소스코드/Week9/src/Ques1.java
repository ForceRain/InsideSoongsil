import java.util.Scanner;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.BorderLayout;

public class Ques1 extends JPanel
{
	private String name;
	
	public Ques1()
	{
		Scanner in=new Scanner(System.in);
		System.out.print("Please enter your name :");
		name=in.nextLine();
		this.setLayout(new BorderLayout());
		name="GoodDay!"+name;
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2=(Graphics2D)g;
		g2.setFont(new Font(name,Font.BOLD,100));
		g2.drawString(name,50,100);
	}
	
}
