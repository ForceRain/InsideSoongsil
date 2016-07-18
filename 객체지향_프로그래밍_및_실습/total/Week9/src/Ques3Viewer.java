import javax.swing.JFrame;

public class Ques3Viewer {
	public static void main(String []args)
	{
		JFrame frame=new JFrame();
		frame.setSize(500,150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Question3");
		
		Question3 component=new Question3();
		frame.add(component);
		
		frame.setVisible(true);
	}
}
