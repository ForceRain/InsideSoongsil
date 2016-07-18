import java.util.Scanner;

public class Ques1Tester {

	public static void main(String[] args) {
		SquarePyramid obj1=new SquarePyramid();
		Scanner in=new Scanner(System.in);
		int temp;
		System.out.println("Please type height");
		temp=in.nextInt();
		obj1.getHeight(temp);
		System.out.println("Please type length of the side");
		temp=in.nextInt();
		obj1.getLength_of_side(temp);
		System.out.println("Volume of the pyramid is "+obj1.calculate_area());
	}
}
