import javax.swing.JFrame;

public class Ques2Viewer {
	public static void main(String[] args)
	{
		JFrame frame=new JFrame();
		frame.setSize(500,300);
		frame.setTitle("Question2");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Question2 component=new Question2();
		frame.add(component);
		
		frame.setVisible(true);
	}
}
