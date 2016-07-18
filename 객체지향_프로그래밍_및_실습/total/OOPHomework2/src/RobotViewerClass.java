import javax.swing.JFrame;

public class RobotViewerClass 
{
	public static void main(String[] args)
	{
		JFrame frame=new JFrame();
		frame.setSize(550,550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Object Oriented Programming with JAVA :: Homework2");
		
		RobotClass component=new RobotClass();
		frame.add(component);
		
		frame.setVisible(true);
	}
}
