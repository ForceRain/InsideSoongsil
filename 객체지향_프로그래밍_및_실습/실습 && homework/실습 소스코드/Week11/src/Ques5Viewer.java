import javax.swing.JFrame;

public class Ques5Viewer 
{
	public static void main(String[] args)
	{
		JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900,500);
		frame.setTitle("Question5");
		
		ShowClock component=new ShowClock();
		frame.add(component);
		
		frame.setVisible(true);
	}
}
