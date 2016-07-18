import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner in=new Scanner(System.in);
        String input1=in.next(); String input2=in.next();
        int wcnt1=0;
        int wcnt2=0;
        int dif=input2.length()-input1.length();
        
        for (int i=0;i<input1.length();i++)
        {
            if (input1.charAt(i)!=input2.charAt(i))
                wcnt1++;
        }
        
        for (int i=input1.length()-1;i>=0;i--)
        {
            if (input1.charAt(i)!=input2.charAt(i+dif))
                wcnt2++;
        }
        
        if (wcnt1>wcnt2)
            System.out.println(wcnt2);
        else
            System.out.println(wcnt1);
    }
}