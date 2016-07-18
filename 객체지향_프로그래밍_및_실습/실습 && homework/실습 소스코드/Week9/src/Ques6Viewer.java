import javax.swing.JFrame;

public class Ques6Viewer 
{
	public static void main(String[] args)
	{
		JFrame frame=new JFrame();
		frame.setSize(500,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Question6");
		
		Question6 component=new Question6();
		frame.add(component);
		
		frame.setVisible(true);		
	}
}
