import java.util.Scanner;

public class Ques8 {
	public static void main(String[] args)
	{
		Scanner in=new Scanner(System.in);
		System.out.println("Please type one integer :");
		String input=in.nextLine();
		StringBuffer obj=new StringBuffer(input);
		obj.reverse();
		System.out.println(Integer.parseInt(obj.substring(0)));
	}
}
