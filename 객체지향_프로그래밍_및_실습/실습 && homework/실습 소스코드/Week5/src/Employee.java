
class Employee {
		private String name;
		private double salary;
		
		public Employee(String employeeName,double salary)
		{
				this.name=employeeName;
				this.salary=salary;
		}
		
		public String getName()
		{
				return name;
		}
		
		public double getSalary()
		{
				return salary;
		}
		
		public void increment(double percent)
		{
				salary+=salary*(percent/100.0);
		}
}
