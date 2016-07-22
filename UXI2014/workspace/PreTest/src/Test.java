import java.util.Scanner;

public class Test 
{
	public static void main(String[] args)
	{
		Scanner in=new Scanner(System.in);
		System.out.println("What's your name?");
		String input=in.nextLine();
		System.out.println("Welcome to Fall semester! : "+input);
	}
}
