import javax.swing.JFrame;

public class Ques2Viewer 
{
	public static void main(String[] args)
	{
		JFrame frame=new JFrame();
		frame.setSize(450,150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Practical Test");
		
		Ques2Panel component=new Ques2Panel();
		frame.add(component);
		
		frame.setVisible(true);
	}
}
