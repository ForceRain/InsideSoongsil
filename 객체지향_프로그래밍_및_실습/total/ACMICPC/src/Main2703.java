import java.util.Scanner;
import java.util.ArrayList;

class Cryp
{
   	private ArrayList<String> data;
    public Cryp()
    {
        data=new ArrayList<String>();
    }
    
    public void push(String tar,String pat)
    {
    	int length=tar.length();
    	String tmp="";
    	
        for (int i=0;i<length;i++)
        {        	
        	if (tar.charAt(i)==' ')
        		tmp+=" ";
        	else
        		tmp+=pat.charAt(tar.charAt(i)-'A');
        }
        data.add(tmp);
    }
    
    public void show()
    {
        for (int i=0;i<data.size();i++)
        {
            System.out.println(data.get(i));
        }
    }
}

public class Main2703
{
    public static void main(String[] args)
    {
        Scanner in=new Scanner(System.in);
        int times=Integer.parseInt(in.nextLine());
        Cryp obj=new Cryp();
        
        for (int i=0;i<times;i++)
        {
            String target=in.nextLine();
            String pattern=in.nextLine();
            obj.push(target,pattern);
        }
        
        obj.show();
    }
}