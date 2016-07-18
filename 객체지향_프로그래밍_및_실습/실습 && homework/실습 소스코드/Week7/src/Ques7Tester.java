import java.util.Scanner;

public class Ques7Tester {
	public static void main(String[] args)
	{
		Scanner in=new Scanner(System.in);
		Ques3ArrayList obj=new Ques3ArrayList();
		
		for (int i=0;i<20;i++)
		{
			System.out.print("Type Integer :: -999 == out :");
			int input=in.nextInt();
			
			if (input==-999)
				break;
			else
				obj.SetData(input);
		}
		obj.OrderData();
		System.out.println("There are duplicated integers :"+obj.CheckData());
	}
}
