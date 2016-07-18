import java.util.Scanner;
import java.util.ArrayList;
import java.lang.StringBuilder;
import java.util.StringTokenizer;

public class Main9093
{
    public static void main(String[] args)
    {
        Scanner in=new Scanner(System.in);
        int times=Integer.parseInt(in.nextLine());
        ArrayList<String> data=new ArrayList<String>();
        
        for (int i=0;i<times;i++)
        {
            StringTokenizer obj=new StringTokenizer(in.nextLine());
            String tmp="";
            
            while (obj.hasMoreTokens())
            {
                StringBuilder t=new StringBuilder(obj.nextToken());
                t=t.reverse();
                tmp+=t.toString()+" ";
            }
            
            data.add(tmp);
        }
        
        for (int i=0;i<data.size();i++)
        {
            System.out.println(data.get(i));
        }
    }
}