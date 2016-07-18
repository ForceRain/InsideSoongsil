import javax.swing.JFrame;

public class Ques1Tester 
{
	public static void main(String[] args)
	{
		JFrame frame=new JFrame();
		frame.setSize(1000,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Ques1 component=new Ques1();
		
		frame.add(component);
		
		frame.setVisible(true);
	}
}
