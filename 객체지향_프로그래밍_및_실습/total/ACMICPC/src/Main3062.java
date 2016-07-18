import java.util.Scanner;
import java.util.ArrayList;
import java.lang.StringBuffer;

class Check
{
    private ArrayList<String> data;
    private String result;
    
    public Check()
    {
        result="";
        data=new ArrayList<String>();
    }
    public void push(String input)
    {
        StringBuffer obj=new StringBuffer(input);
        obj.reverse();
        
        int dat=Integer.parseInt(input)+Integer.parseInt(obj.toString());
        
        String tmp=Integer.toString(dat);
        
        StringBuffer obj1=new StringBuffer(tmp);
        obj1.reverse();
        if (tmp.equals(obj1.toString()))
        	result+="YES\n";
        else
            result+="NO\n";
    }
    public String toString()
    {
        return result;
    }
}

public class Main3062
{
    public static void main(String[] args)
    {
        Scanner in=new Scanner(System.in);
        int times=Integer.parseInt(in.nextLine());
        Check obj=new Check();
        
        for (int i=0;i<times;i++)
        {
            obj.push(in.nextLine());
        }
        
        System.out.println(obj);
    }
}