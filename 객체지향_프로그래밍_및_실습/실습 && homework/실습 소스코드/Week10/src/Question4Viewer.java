import javax.swing.JFrame;

public class Question4Viewer 
{
	public static void main(String[] args)
	{
		JFrame frame=new JFrame();
		
		frame.setSize(150,150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Question4");
		
		Button component=new Button();
		frame.add(component);
		
		frame.setVisible(true);
	}
}
