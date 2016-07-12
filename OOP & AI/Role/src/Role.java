import java.util.Random;


public class Role 
{
	public static void main(String [] args)
	{
		Random dice=new Random();
		String []roles=new String[]{"미스듀위","나머지 역할","제시카","할아범","내레이션"};
		String []name=new String[]{"전요성","김기표","함승우","이진우","최여진"};
		int []arr=new int[5];
		
		for (int i=0;i<5;i++)
			arr[i]=0;
		
		int name_ptr=0;
		while (name_ptr!=5)
		{
			int di=Math.abs(dice.nextInt()%5);
			if (arr[di]==0)
			{
				arr[di]++;
				System.out.println("이름 : "+name[name_ptr]+", 역할 : "+roles[di]);
				name_ptr++;
			}
		}
	}
}
