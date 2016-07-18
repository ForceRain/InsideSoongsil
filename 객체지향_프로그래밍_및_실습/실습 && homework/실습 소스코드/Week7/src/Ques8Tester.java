import java.util.Scanner;

public class Ques8Tester {
	public static void main(String[] args)
	{
		Scanner in=new Scanner(System.in);
		Ques6ArrayList obj=new Ques6ArrayList();
		
		for (int i=0;i<20;i++)
		{
			System.out.print("Type Integer ::: -999==out :");
			int input=in.nextInt();
			
			if (input==-999)
				break;
			else
				obj.SetData(input);
		}
		
		obj.OrderData();
		System.out.println("The second largest number is :"+obj.ShowResult());
	}
}
