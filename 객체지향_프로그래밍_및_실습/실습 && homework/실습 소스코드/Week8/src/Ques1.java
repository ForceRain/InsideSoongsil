import javax.swing.JFrame;

public class Ques1 {
	public static void main(String args[])
	{
		JFrame frame=new JFrame();
		
		frame.setSize(500,500);
		frame.setTitle("Random Line");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		LineClass component=new LineClass();
		frame.add(component);
		
		frame.setVisible(true);
	}
}
