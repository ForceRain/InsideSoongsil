import java.util.Scanner;
import java.util.StringTokenizer;

public class Ques6 {
	public static void main(String[] args)
	{
		Scanner in=new Scanner(System.in);
		System.out.println("Please type String");
		String input=in.nextLine();
		StringTokenizer out=new StringTokenizer(input);
		System.out.println("Numbers of Token:"+out.countTokens());
		while (out.hasMoreTokens())
		{
			System.out.println(out.nextToken());
		}
	}
}
