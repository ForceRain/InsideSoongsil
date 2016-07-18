import java.lang.Math;

class QuadraticEquation {
		private double a;
		private double b;
		private double c;
		
		public QuadraticEquation(double a,double b, double c)
		{
				this.a=a;
				this.b=b;
				this.c=c;
		}
		
		public double getSolution1()
		{
				return (-b+Math.sqrt(b*b-4*a*c))/2*a;
		}
		
		public double getSolution2()
		{
				return (-b-Math.sqrt(b*b-4*a*c))/2*a;
		}
}
