import javax.swing.JFrame;

public class Show
{
	public static void main(String[] args)
	{
		JFrame frame=new JFrame();
		frame.setSize(500,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Test");
		
		MenuCheck component=new MenuCheck();
		frame.add(component);
		
		frame.setVisible(true);
	}
}