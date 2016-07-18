import java.util.Scanner;

public class MainURI
{
	public static void main(String[] args)
	{
		Scanner in=new Scanner(System.in);
		int times=Integer.parseInt(in.nextLine());
		
		for (int i=0;i<times;i++)
		{
			String tmp=in.nextLine();
			String bef="";
			do 
			{
				bef=tmp;
				tmp=tmp.replace("%20"," ");
				tmp=tmp.replace("%21","!");
				tmp=tmp.replace("%24","$");
				tmp=tmp.replace("%25","%");
				tmp=tmp.replace("%28","(");
				tmp=tmp.replace("%29",")");
				tmp=tmp.replace("%2a","*");
			} 
			while (!bef.equals(tmp));
			
			
			System.out.println(tmp);
		}
	}
}