import javax.swing.JFrame;

public class Ques4 {
	public static void main(String[] args)
	{
		JFrame frame =new JFrame();
		ComboQues4 compo=new ComboQues4();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Question 4");
		frame.add(compo);
		
		frame.setSize(400,400);
		frame.setVisible(true);
	}
}
