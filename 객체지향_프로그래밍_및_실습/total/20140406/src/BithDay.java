import java.util.Scanner;

class BithDay {
	public static void main(String[] args)
	{
		Scanner in=new Scanner(System.in);
		System.out.println("이름과 생일을 입력하세요 :");
		String name=in.nextLine();
		String birth=in.nextLine();
		
		System.out.println("이름 :"+name+" 생일 :"+birth);
		
	}
}
