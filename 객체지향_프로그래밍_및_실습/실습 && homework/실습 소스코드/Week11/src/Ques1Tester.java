
public class Ques1Tester 
{
	public static void main(String[] args)
	{
		AnotherRectangle r1= new AnotherRectangle(3,4);
		AnotherRectangle r2= new AnotherRectangle(3,4);
		AnotherRectangle r3= new AnotherRectangle(5,6);
		
		System.out.println("r1.equals(r2):"+r1.equals(r2));
		System.out.println("r1.equals(r3):"+r1.equals(r3));
		System.out.println("r1==r2: "+(r1==r2));
	}
}
