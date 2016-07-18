import javax.swing.JFrame;

public class Ques5 {
	public static void main(String[] args)
	{
		JFrame frame =new JFrame();
		ComboQues5 compo=new ComboQues5();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Question 5");
		frame.add(compo);
		
		frame.setSize(400,400);
		frame.setVisible(true);
	}
}
