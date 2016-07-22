import java.util.Scanner;
public class AreaCalculationTester 
{
	public static void main(String []args)
	{
		String input;
		double length;
		double width;
		
		Scanner in = new Scanner(System.in); 
		System.out.println("Enter the length ");
		input=in.next();
		length=Integer.parseInt(input);
		System.out.println("Enter the width ");
		input=in.next();
		width=Integer.parseInt(input);
		AreaCalculation obj1=new AreaCalculation(length,width);
		System.out.println(obj1.getArea());
	}
}