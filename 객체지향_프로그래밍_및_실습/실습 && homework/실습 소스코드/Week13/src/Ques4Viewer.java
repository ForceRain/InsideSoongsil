import javax.swing.JFrame;

public class Ques4Viewer 
{
	public static void main(String[] args)
	{
		JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500,500);
		frame.setTitle("Random Text");
		
		RandomText component=new RandomText();
		frame.add(component);
		
		frame.setVisible(true);
	}
}
