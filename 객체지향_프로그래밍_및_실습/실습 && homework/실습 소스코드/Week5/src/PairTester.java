import java.util.Scanner;

public class PairTester {
	public static void main(String[] args)
	{
		Scanner in=new Scanner(System.in);
		System.out.println("Please type first integer");
		int first=in.nextInt();
		System.out.println("Please type second integer");
		int second=in.nextInt();
		
		Pair obj=new Pair(first,second);
		
		System.out.println("Sum : "+obj.getSum());
		System.out.println("Difference : "+obj.getDif());
		System.out.println("Average : "+obj.getAverage());
		System.out.println("Multiplication : "+obj.getMul());
	}
}
