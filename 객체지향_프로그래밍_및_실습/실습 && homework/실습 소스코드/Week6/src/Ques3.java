import java.util.Scanner;
import java.lang.Math;

public class Ques3 {

	public static void main(String[] args) {
			Scanner in=new Scanner(System.in);
			final double EPSILON=1E-02;
				
			System.out.println("First floating-point number :");
			double in1=in.nextDouble();
			
			System.out.println("Second floating-point number :");
			double in2=in.nextDouble();
			
			if (Math.round(in1*100)==Math.round(in2*100))
			{
				System.out.println("They are the same when rounded to two decimal places.");
			}
			else
			{
				System.out.println("They are different when rounded to two decimal places.");
			}
			
			if (Math.abs(in1-in2)<EPSILON)
			{
				System.out.println("They differ by less than 0.01");
			}
			else
			{
				System.out.println("They differ by larger than 0.01");
			}
	}
}
