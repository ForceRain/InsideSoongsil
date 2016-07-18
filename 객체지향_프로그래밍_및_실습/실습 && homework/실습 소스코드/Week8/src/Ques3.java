import java.util.Scanner;
import javax.swing.JFrame;

public class Ques3 {
	public static void main(String[] args)
	{
		Scanner in =new Scanner(System.in);
		
		System.out.println("Please type radius :");
		int input=in.nextInt();
		
		ShowCircle circle=new ShowCircle(input);
		
		JFrame frame=new JFrame();
		
		frame.setSize(500,500);
		frame.setTitle("Radius");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(circle);
		
		frame.setVisible(true);
	}
			
}
