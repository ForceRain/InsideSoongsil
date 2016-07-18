import java.util.Scanner;
import java.util.StringTokenizer;

public class Main8949
{
    public static void main(String[] args)
    {
        Scanner in=new Scanner(System.in);
        String input=in.nextLine();
        StringTokenizer obj=new StringTokenizer(input);
        
        String first=obj.nextToken();
        String second=obj.nextToken();
        
        int fp=first.length()-1;
        int sp=second.length()-1;
        
        String output="";
        while (fp>=0 && sp>=0)
        {
            int tmp=first.charAt(fp--)+second.charAt(sp--)-2*'0';
            output=Integer.toString(tmp)+output;
        }
        
        while (fp>=0)
        {
            int tmp=first.charAt(fp--)-'0';
            output=Integer.toString(tmp)+output;
        }
        
        while (sp>=0)
        {
            int tmp=second.charAt(sp--)-'0';
            output=Integer.toString(tmp)+output;
        }
        
        System.out.println(output);
    }
}