import javax.swing.JFrame;

public class Question5Viewer {
	public static void main(String[] args)
	{
		JFrame frame=new JFrame();
		frame.setTitle("Question5");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300,150);
		
		TwoButton component=new TwoButton();
		frame.add(component);
		
		frame.setVisible(true);
	}
}
