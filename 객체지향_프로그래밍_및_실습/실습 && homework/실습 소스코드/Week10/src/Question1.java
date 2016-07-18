
public class Question1 
{
	public static void main(String[] args)
	{
		Quiz per1=new Quiz(80,'B');
		Quiz per2=new Quiz(50,'C');
		Quiz per3=new Quiz(99,'A');
		
		DataSet control=new DataSet();
		control.add(per1);
		control.add(per2);
		control.add(per3);
		
		System.out.println("Average :"+control.getAverage());
		System.out.println("Maximum =="+control.getMaximum());
		
	}
}
