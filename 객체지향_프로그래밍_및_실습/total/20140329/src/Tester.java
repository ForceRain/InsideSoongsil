import java.util.Scanner;

public class Tester {
	public static void main(String[] args)
	{
		Scanner in=new Scanner(System.in);
		MinMax obj=new MinMax();
		
		int[] arr1;
		int[] arr2;
		
		System.out.print("arr1�� ũ�⸦ �Է��ϼ��� :");
		int tmp=in.nextInt();
		arr1=new int[tmp];
		
		System.out.print("arr1�� ������ �Է��ϼ��� :");
		for (int i=0;i<tmp;i++)
			arr1[i]=in.nextInt();
		
		System.out.print("arr2�� ũ�⸦ �Է��ϼ��� :");
		tmp=in.nextInt();
		arr2=new int[tmp];
		
		System.out.print("arr2�� ������ �Է��ϼ��� :");
		for (int i=0;i<tmp;i++)
			arr2[i]=in.nextInt();
		
		System.out.println("Max. arr1 :"+MinMax.MaxValue(arr1));
		System.out.println("Min. arr1 :"+MinMax.MinValue(arr1));
		System.out.println("Max. arr2 :"+MinMax.MaxValue(arr2));
		System.out.println("Min. arr2 :"+MinMax.MinValue(arr2));
		
	}
}
