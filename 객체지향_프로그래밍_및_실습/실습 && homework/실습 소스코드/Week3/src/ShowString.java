import java.util.Scanner;

public class ShowString {
	public static void main(String[] args)
	{
		Scanner in=new Scanner(System.in);
		String input;
		System.out.println("Please type String");
		input=in.nextLine();
		System.out.println("position 1 :"+input.charAt(0));
		System.out.println("position 3 :"+input.charAt(2));
		System.out.println("position 5 :"+input.charAt(4));
	}
}
