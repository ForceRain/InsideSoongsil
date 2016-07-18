import java.util.Scanner;

public class StudentTester {
	public static void main(String[] args)
	{
		Scanner in=new Scanner(System.in);
		String name;
		String num;
		String ID;
		System.out.println("Please type your name");
		name=in.nextLine();
		
		System.out.println("Please type your ID");
		ID=in.nextLine();
		
		System.out.println("Please type your PhoneNum");
		num=in.nextLine();
		
		Student obj1=new Student(name,ID,num);
		System.out.println("name :"+obj1.getName());
		System.out.println("ID :"+obj1.getID());
		System.out.println("PhoneNum :"+obj1.getPhone_Number());
	}
}
