import java.util.Scanner;

class BoomBBai {
	public static void main(String[] args)
	{
		Scanner in=new Scanner(System.in);
		int people,money;
		System.out.println("�� �� ���°�?");
		people=in.nextInt();
		System.out.println("�� ���Դ�?");
		money=in.nextInt();
		
		System.out.println("�� ����� ������ �� :"+(double)money/people);
		System.out.println("�� ����� ������ ����� �� :"+money*0.15/people);
	}
}
