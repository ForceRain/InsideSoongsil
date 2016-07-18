import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Ques5Viewer 
{
	public static void main(String[] args)
	{
		String input=JOptionPane.showInputDialog("How many rectangles do you want?");
		
		JFrame frame=new JFrame();
		frame.setSize(500,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Question5");
		
		Question5 component=new Question5(input);
		frame.add(component);
		
		frame.setVisible(true);
	}
}
