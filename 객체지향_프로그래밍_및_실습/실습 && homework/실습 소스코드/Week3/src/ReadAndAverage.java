import java.util.Scanner;

public class ReadAndAverage {
	public static void main(String[] args)
	{
		int arr[]=new int[3];
		Scanner in=new Scanner(System.in);
		System.out.println("Please enter 3 integers");
		arr[0]=in.nextInt();
		arr[1]=in.nextInt();
		arr[2]=in.nextInt();
		System.out.println((arr[0]+arr[1]+arr[2])/3.0);
	}
}
