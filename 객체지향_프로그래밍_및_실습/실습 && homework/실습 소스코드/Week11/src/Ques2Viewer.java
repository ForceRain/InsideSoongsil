import javax.swing.JFrame;

public class Ques2Viewer {
	public static void main(String[] args)
	{
		JFrame frame=new JFrame();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(550,550);
		frame.setTitle("Question2");
		
		DrawCircle obj=new DrawCircle();
		frame.add(obj);
		
		frame.setVisible(true);
	}
}
