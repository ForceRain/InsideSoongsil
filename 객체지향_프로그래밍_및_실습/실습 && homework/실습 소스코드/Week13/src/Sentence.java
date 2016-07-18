
public class Sentence 
{
	private String line;
	private String out;
	private int l_cnt;
	
	public Sentence(String input)
	{
		line=input;
	}
	
	public void reverse()
	{
		out=Recreverse(line);
	}
	
	public String Recreverse(String input)
	{
		if (input.length()==1)
			return input;
		else
			return Recreverse(input.substring(1))+input.substring(0,1);
		
	}
	
	public String getText()
	{
		return out;
	}
	
	public boolean find(String input)
	{
		l_cnt=input.length();
		if (recfind(line,input))
			return true;
		else
			return false;
	}
	
	public boolean recfind(String line,String input)
	{
		if (line.length()<l_cnt)
			return false;
		
		if (line.substring(0,l_cnt).equals(input))
			return true;
		else
			return recfind(line.substring(1),input);
	}
}
