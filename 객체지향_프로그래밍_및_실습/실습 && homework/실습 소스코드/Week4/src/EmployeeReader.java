
public class EmployeeReader {
	public static void main(String[] args)
	{
		Employee obj1=new Employee("John Smith",1001244888,"010-8818-2818");
		Employee obj2=new Employee("John Jones",1001234888,"010-8348-3814");
		Employee obj3=new Employee("John Hell",1001515288,"010-8658-818");
		
		obj1.getDetail();
		obj2.getDetail();
		obj3.getDetail();
		
		obj1.change_number("010-2222-2222");
		obj1.getDetail();
	}
}
