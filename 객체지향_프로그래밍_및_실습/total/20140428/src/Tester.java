import java.util.StringTokenizer;
import java.util.Scanner;

public class Tester
{
    public static void main(String[] args)
    {
        Scanner in=new Scanner(System.in);
        String input=in.nextLine();
        StringTokenizer stk=new StringTokenizer(input);
        int cnt=0;
        while (stk.hasMoreTokens())
        {
        	stk.nextToken();
            cnt++;
        }
        System.out.println(cnt);
    }
}