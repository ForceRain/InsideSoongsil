import java.util.Scanner;

class BoomBBai {
	public static void main(String[] args)
	{
		Scanner in=new Scanner(System.in);
		int people,money;
		System.out.println("몇 명 갔는고?");
		people=in.nextInt();
		System.out.println("얼마 나왔니?");
		money=in.nextInt();
		
		System.out.println("한 사람당 내야할 돈 :"+(double)money/people);
		System.out.println("한 사람당 팁으로 줘야할 돈 :"+money*0.15/people);
	}
}
