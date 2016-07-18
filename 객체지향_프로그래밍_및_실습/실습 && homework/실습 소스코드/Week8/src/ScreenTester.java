import javax.swing.JFrame;

public class ScreenTester {
	public static void main(String[] args)
	{
		JFrame frame=new JFrame();
		
		frame.setSize(1200,700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("ScreenSaver");
		ScreenSaver component=new ScreenSaver();
		
		frame.add(component);
		
		frame.setVisible(true);
	}
}
