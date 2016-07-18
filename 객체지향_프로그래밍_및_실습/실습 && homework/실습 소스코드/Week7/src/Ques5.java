import java.util.StringTokenizer;
import java.lang.Character;

public class Ques5 {
	private String ans="$";
	
	
	public Ques5(String input)
	{
		StringTokenizer obj=new StringTokenizer(input);
		while (obj.hasMoreTokens())
		{
			String temp=obj.nextToken();
			if (Character.isDigit(temp.charAt(0)))
				ans=ans.concat(temp);
		}
	}
	
	public String toString()
	{
		return ans;
	}
}
