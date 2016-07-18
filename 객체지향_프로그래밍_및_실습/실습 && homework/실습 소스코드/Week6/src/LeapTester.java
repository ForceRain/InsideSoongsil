import java.util.Scanner;

public class LeapTester {
	public static void main(String[] args)
	{
		Scanner in=new Scanner(System.in);
		System.out.println("Give me year :");
		int year=in.nextInt();
		LeapYear obj=new LeapYear(year);
		
		if (obj.IsLeapYear())
			System.out.println("Leap year");
		else
			System.out.println("Not Leap year");
	}
}
