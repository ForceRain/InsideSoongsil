import java.util.Scanner;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main5698
{
    public static void main(String[] args)
    {
        Scanner in=new Scanner(System.in);
        ArrayList<String> data=new ArrayList<String>();
        
        while(true)
        {
            String tmp=in.nextLine();
            if (tmp.equals("*"))
                break;
            
            StringTokenizer obj=new StringTokenizer(tmp);
            char bef;
            boolean YorN=true;
            bef=(obj.nextToken()).charAt(0);
            while (obj.hasMoreTokens())
            {
                String t=obj.nextToken();
                if (bef!=t.charAt(0)+'a'-'A' && bef!=t.charAt(0) && bef!=t.charAt(0)+'A'-'a')
                {
                    YorN=false;
                }
            }
            
            if (YorN)
                data.add("Y");
            else
                data.add("N");
        }
        
        for (int i=0;i<data.size();i++)
            System.out.println(data.get(i));
    }
}