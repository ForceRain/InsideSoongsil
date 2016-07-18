import java.util.Scanner;

public class QuadraticEquationTester {
		public static void main(String[] args)
		{
				Scanner in=new Scanner(System.in);
				System.out.println("Please type a,b,c :");
				double a,b,c;
				a=in.nextDouble();
				b=in.nextDouble();
				c=in.nextDouble();				
				
				QuadraticEquation obj= new QuadraticEquation(a,b,c);
				
				System.out.println("Solution1 :"+obj.getSolution1());
				System.out.println("Solution2 :"+obj.getSolution2());
		}
}
