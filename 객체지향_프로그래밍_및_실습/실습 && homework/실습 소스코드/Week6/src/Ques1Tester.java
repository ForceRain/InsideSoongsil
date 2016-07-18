import java.util.Scanner;

public class Ques1Tester {

	public static void main(String[] args) {
			EvenOdd obj=new EvenOdd();
			Scanner in=new Scanner(System.in);
			System.out.println("Please type number :");
			int input=in.nextInt();
			
			if (obj.CheckEO(input))
				System.out.println("Even");
			else
				System.out.println("Odd");
	}

}
