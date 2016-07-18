import javax.swing.JFrame;

public class Ques4Viewer 
{
	public static void main(String[] args)
	{
		JFrame frame=new JFrame();
		
		frame.setSize(750,100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Question3");
		
		Question4 component=new Question4();
		frame.add(component);
		
		frame.setVisible(true);
	}
}
