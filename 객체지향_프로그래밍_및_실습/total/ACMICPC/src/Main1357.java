import java.util.Scanner;
import java.lang.StringBuffer;

public class Main1357
{
    public static void main(String[] args)
    {
        Scanner in=new Scanner(System.in);
        String in1=in.next();
        String in2=in.next();
        
        if (in1.equals("1"))
        {
            while (in1.length()<4)
                in1="0"+in1;
        }
        else
        {
       		while (in1.length()<3)
        	{
            	in1="0"+in1;
        	}        
        }
        
        if (in2.equals("1"))
        {
        	while (in2.length()<4)
        	{
            	in2="0"+in2;
        	}
        }
        else
        {
            while (in2.length()<3)
                in2="0"+in2;
        }
        
        StringBuffer tmp1=new StringBuffer(in1);   
        StringBuffer tmp2=new StringBuffer(in2);
                 
        tmp1=tmp1.reverse();
        tmp2=tmp2.reverse();
      
        System.out.println(Integer.parseInt(tmp1.toString()));
        System.out.println(Integer.parseInt(tmp2.toString()));
        
        int temp=Integer.parseInt(tmp1.toString())+Integer.parseInt(tmp2.toString());

        StringBuffer tmp3=new StringBuffer(Integer.toString(temp));
        tmp3=tmp3.reverse();
        
        System.out.println(tmp3.toString());
    }
}