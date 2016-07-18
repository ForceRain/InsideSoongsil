import javax.swing.JFrame;

public class Ques2 {
	public static void main(String[] args)
	{
		JFrame frame=new JFrame();
		
		frame.setSize(500,500);
		frame.setTitle("Random Rectangle");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		RectangleClas component=new RectangleClas();
		frame.add(component);
		
		frame.setVisible(true);
	}
}
