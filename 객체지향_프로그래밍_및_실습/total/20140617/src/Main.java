class Boss
{
	public void X()
	{
		System.out.println("B X");
	}
	public static void Y()
	{
		System.out.println("B Y");
	}
}

class Worker extends Boss
{
	public void X()
	{
		System.out.println("W X");
	}
	public static void Y()
	{
		System.out.println("W Y");
	}
}


public class Main 
{
	public static void main(String[] args)
	{
		Boss ob1=new Worker();
		ob1.X();
		ob1.Y();
		Worker ob2=new Worker();
		ob2.X();
		ob2.Y();
	}
}
