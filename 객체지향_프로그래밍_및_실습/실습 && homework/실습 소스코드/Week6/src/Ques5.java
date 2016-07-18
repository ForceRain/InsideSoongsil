import java.util.Scanner;

public class Ques5 {
	public static void main(String[] args)
	{
		Scanner in=new Scanner(System.in);
		System.out.println("Please type one integer :");
		int input=in.nextInt();
		
		for (int i=1;i<=input;i++)
		{
			int cnt=0;
			for (int j=1;j<=i;j++)
			{
				if (i%j==0)
					cnt++;
			}
			if (cnt==2)
				System.out.println(i);
		}
	}
}
