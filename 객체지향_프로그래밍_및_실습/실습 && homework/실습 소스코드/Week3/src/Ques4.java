import java.lang.Math;
import java.util.Scanner;

public class Ques4 {
	public static void main(String[] args)
	{
		Scanner in=new Scanner(System.in);
		int a=0,b=0;
		System.out.println("Please type 2 numbers");
		a=in.nextInt();
		b=in.nextInt();
		System.out.println(Math.max(a,b));
	}
}
