class cal
{
	private String output="34+67";
	
	public double calculate()
	{
		int i;
		if (output.equals(""))
			return 0.0;
		
		for (i=0;i<output.length();i++)
		{
			if (!('0'<=output.charAt(i) && output.charAt(i)<='9'))
				break;
		}
		
		String oper1=output.substring(0,i);
		String oper2=output.substring(i+1);
		
		System.out.println("oper1 : "+oper1+", oper2 : "+oper2);
		
		char operate=output.charAt(i);
		
		if (operate=='+')
		{
			double tmp=Double.parseDouble(oper1)+Double.parseDouble(oper2);
			return tmp;
		}
		if (operate=='-')
		{
			double tmp=Double.parseDouble(oper1)-Double.parseDouble(oper2);
			return tmp;
		}
		if (operate=='*')
		{
			double tmp=Double.parseDouble(oper1)*Double.parseDouble(oper2);
			return tmp;
		}
		if (operate=='/')
		{
			double tmp=Double.parseDouble(oper1)/Double.parseDouble(oper2);
			return tmp;
		}
		
		return Double.parseDouble(output);
	}
}

public class Check 
{
	public static void main(String[] args)
	{
		cal Cal=new cal();
		System.out.println(Cal.calculate());
	}
	
}
