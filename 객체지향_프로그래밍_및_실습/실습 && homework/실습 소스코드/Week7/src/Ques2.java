import java.util.Random;

public class Ques2 {
	public static void main(String[] args)
	{
		Random obj1=new Random();
		int[] ans=new int[13];
		
		int Roll_num=0;
		
		for (int i=0;i<13;i++)
		{
			ans[i]=0;
		}
		while(true)
		{
			if (Roll_num==100)
				break;
			
			int num1=(obj1.nextInt(6))+1;
			int num2=(obj1.nextInt(6))+1;
			
			ans[num1+num2]++;
			Roll_num++;
		}
		
		for (int i=2;i<13;i++)
		{
			System.out.println("Sum : "+i+" "+"Occurence :"+ans[i]);
		}
		
	}
}
