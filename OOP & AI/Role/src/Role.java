import java.util.Random;


public class Role 
{
	public static void main(String [] args)
	{
		Random dice=new Random();
		String []roles=new String[]{"�̽�����","������ ����","����ī","�Ҿƹ�","�����̼�"};
		String []name=new String[]{"���伺","���ǥ","�Խ¿�","������","�ֿ���"};
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
				System.out.println("�̸� : "+name[name_ptr]+", ���� : "+roles[di]);
				name_ptr++;
			}
		}
	}
}
