import java.util.Scanner;

public class Ques2Tester {
	public static void main(String args[])
	{
		BMI obj1=new BMI();
		Scanner in=new Scanner(System.in);
		double temp;
		System.out.println("Please type your weight");
		temp=in.nextDouble();
		obj1.input_weight(temp);
		System.out.println("Please type your height");
		temp=in.nextDouble();
		obj1.input_height(temp);
		System.out.println("Your BMI is "+obj1.calculate_BMI());
	}
}
