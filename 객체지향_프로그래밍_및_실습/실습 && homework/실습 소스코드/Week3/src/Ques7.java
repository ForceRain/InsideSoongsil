import java.util.Scanner;
import java.util.Random;

public class Ques7 {
	public static void main(String[] args)
	{
		Scanner in=new Scanner(System.in);
		Random wow=new Random();
		int haha=0;
		System.out.println("Please type integer");
		haha=in.nextInt();
		System.out.println("Lucky Draw Number is: "+wow.nextInt(haha));
	}
}
