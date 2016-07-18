import java.awt.Rectangle;

class CheckGrwo {
	public static void main(String[] args)
	{
		Rectangle obj1=new Rectangle(5,10,20,30);
		
		System.out.println(obj1);
		
		Rectangle obj2=new Rectangle(5,10,20,30);
		
		System.out.println(obj2);
		
		obj2.grow(10,20);
		
		System.out.println(obj2);
	}
}
