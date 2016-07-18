import javax.swing.JFrame;

public class ViewTester 
{
	public static void main(String[] args)
	{
		JFrame frame=new JFrame();
		frame.setSize(500,500);
		frame.setTitle("Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		TestPanel component=new TestPanel();
		frame.add(component);
		
		frame.setVisible(true);
	}
}
