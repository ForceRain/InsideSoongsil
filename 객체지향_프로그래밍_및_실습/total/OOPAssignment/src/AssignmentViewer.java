import javax.swing.JFrame;

public class AssignmentViewer
{
	public static void main(String[] args)
	{	
		JFrame frame=new JFrame();
		frame.setSize(1100,700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Object Oriented Programming and Practical using Java:::Assignment[My Mini Zoo]");
		
		TempAssignmentPanel panel=new TempAssignmentPanel();
		frame.add(panel);
		
		frame.setVisible(true);
	}
}
