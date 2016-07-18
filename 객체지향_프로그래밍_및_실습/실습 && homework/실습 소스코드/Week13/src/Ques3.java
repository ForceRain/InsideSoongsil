import javax.swing.JFrame;

public class Ques3 
{
	public static void main(String[] args)
	{
		JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500,500);
		frame.setTitle("MovingClock");
		
		MovingClock component=new MovingClock();
		frame.add(component);
		
		frame.setVisible(true);
	}
}
