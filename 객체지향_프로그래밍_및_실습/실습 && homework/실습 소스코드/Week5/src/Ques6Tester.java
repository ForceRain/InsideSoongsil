import java.util.Scanner;

public class Ques6Tester {
		public static void main(String[] args)
		{
				Scanner in=new Scanner(System.in);
				String input;
				System.out.print("Please type string :");
				input=in.nextLine();
				MyUppercase obj1=new MyUppercase();
				
				obj1.getString(input);
				obj1.capitalize();
		}
}
