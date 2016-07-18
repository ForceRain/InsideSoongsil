import java.util.Scanner;
import java.lang.StringBuffer;

public class Main6502
{
    public static void main(String[] args)
    {
        Scanner in=new Scanner(System.in);
        String in1=in.next();
        String in2=in.next();
        
        StringBuffer tmp1=new StringBuffer(in1);
        StringBuffer tmp2=new StringBuffer(in2);
        
        tmp1=tmp1.reverse();
        tmp2=tmp2.reverse();
      
        int temp=Integer.parseInt(tmp1.toString())+Integer.parseInt(tmp2.toString());

        StringBuffer tmp3=new StringBuffer(Integer.toString(temp));
        tmp3=tmp3.reverse();
        
        System.out.println(tmp3.toString());
    }
}