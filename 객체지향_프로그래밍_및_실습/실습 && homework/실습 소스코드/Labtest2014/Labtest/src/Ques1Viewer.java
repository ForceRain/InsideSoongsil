import javax.swing.JFrame;

public class Ques1Viewer 
{
	public static void main(String[] args)
	{
		JFrame frame=new JFrame();
		frame.setSize(300,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Practical Test 2014");
		
		Ques1Panel component=new Ques1Panel();
		frame.add(component);
		
		frame.setVisible(true);
	}
}
