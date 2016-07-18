import java.util.Scanner;

public class Ques3 {
	public static void main(String[] args)
	{
		int hour=0,minute=0,second=0;
		
		Scanner in=new Scanner(System.in);
		System.out.println("Please type hours");
		hour=in.nextInt();
		System.out.println("Please type minutes");
		minute=in.nextInt();
		System.out.println("Please type seconds");
		second=in.nextInt();
		System.out.println("Total:"+(hour*3600+minute*60+second)+"seconds");
	}
}
