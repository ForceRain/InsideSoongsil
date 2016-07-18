import java.util.Scanner;

public class Ques1Tester {
	public static void main(String[] args)
	{
		Scanner in=new Scanner(System.in);
		
		System.out.println("Please type first integer array");
		String input=in.nextLine();
		ExampleArray arr1=new ExampleArray(input);
		
		System.out.println("Please type second integer array");
		String input1=in.nextLine();
		ExampleArray arr2=new ExampleArray(input1);
		
		arr2.CheckArray(arr1);
	}
}
