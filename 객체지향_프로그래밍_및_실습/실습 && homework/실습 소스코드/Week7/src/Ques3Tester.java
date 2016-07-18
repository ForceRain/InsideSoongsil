import java.util.Scanner;

public class Ques3Tester {
	public static void main(String[] args)
	{
		Scanner in=new Scanner(System.in);
		CheckArray obj1=new CheckArray();
		
		for (int i=0;i<20;i++)
		{
			System.out.print("Keep type on "+(20-i)+" remaining spaces :");
			int input=in.nextInt();
			if (input==-999)
			{
				break;
			}
			else
			{
				obj1.SetData(input);
			}
		}
		obj1.OrderData();
		System.out.println("There are duplicated items :"+obj1.CheckData());
	}
}
