import javax.swing.JFrame;
import javax.swing.*;

public class Viewer 
{
	public static void main(String[] args)
	{
		JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		while (true)
		{
			String input=JOptionPane.showInputDialog("난이도를 설정하세요 (Easy(E),Well(W),HELL(H))");
			if (input==null)
				return;
			
			if (input.equals("Easy") || input.equals("E") || input.equals("e"))
			{
				frame.setSize(500,500);
				break;
			}
			else if (input.equals("Well") || input.equals("W") || input.equals("w"))
			{
				frame.setSize(450,450);
				break;
			}
			else if (input.equals("HELL") || input.equals("H") || input.equals("h"))
			{
				frame.setSize(400,400);
				break;
			}
			else
				JOptionPane.showMessageDialog(null,"잘못된 입력입니다. 다시 입력하세요.","난이도 설정 실패",JOptionPane.PLAIN_MESSAGE);
		}
		frame.setTitle("Snake Game");
		SnakeGame obj=new SnakeGame(frame);
		frame.add(obj);		
		
		frame.setVisible(true);
	}
}
