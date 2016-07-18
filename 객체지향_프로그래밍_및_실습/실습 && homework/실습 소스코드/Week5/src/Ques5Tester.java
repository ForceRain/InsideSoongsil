public class Ques5Tester {
		public static void main(String[] args)
		{
				Employee obj1=new Employee("John",3000);
				Employee obj2=new Employee("Kim",3200);
				
				System.out.println("obj1 name :"+obj1.getName());
				System.out.println("obj1 salary :"+obj1.getSalary());
				System.out.println("obj2 name :"+obj2.getName());
				System.out.println("obj2 salary :"+obj2.getSalary());
				
				obj1.increment(10);
				obj2.increment(10);
				
				System.out.println("After increment");
				System.out.println("obj1 name :"+obj1.getName());
				System.out.println("obj1 salary :"+obj1.getSalary());
				System.out.println("obj2 name :"+obj2.getName());
				System.out.println("obj2 salary :"+obj2.getSalary());
		}
}
