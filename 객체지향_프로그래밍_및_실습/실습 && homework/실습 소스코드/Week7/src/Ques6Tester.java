import java.util.Scanner;

public class Ques6Tester {
	public static void main(String[] args)
	{
		Scanner in=new Scanner(System.in);
		SecL obj1=new SecL();
		
		for (int i=0;i<20;i++)
		{
			System.out.print("Type Integers :");
			int input=in.nextInt();
			if (input==-999)
				break;
			else
				obj1.SetData(input);
		}
		
		System.out.println("Second Largest Integer :"+obj1.GetSecData());
	}
}
