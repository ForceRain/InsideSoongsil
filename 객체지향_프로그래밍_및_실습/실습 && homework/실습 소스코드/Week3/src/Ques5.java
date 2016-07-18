import java.util.Scanner;

public class Ques5 {
	public static void main(String[] args)
	{
		Scanner in=new Scanner(System.in);
		String input;
		System.out.println("Please enter the String");
		input=in.nextLine();
		System.out.println("CAPITAL LETTER:"+input.toUpperCase());
		System.out.println("SMALL CAPITAL LETTER:"+input.toLowerCase());
		System.out.println("Number of Characters:"+input.length());
		System.out.println("AFTER REPLACEMENT:"+input.replace('o', 'k'));
	}
}
