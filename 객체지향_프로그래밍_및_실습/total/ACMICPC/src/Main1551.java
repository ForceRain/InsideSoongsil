import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.ArrayList;

public class Main1551
{
    public static void main(String[] args)
    {
        Scanner in=new Scanner(System.in);
        ArrayList<Integer> data=new ArrayList<Integer>();
        StringTokenizer tmp=new StringTokenizer(in.nextLine());
                
        int times=Integer.parseInt(tmp.nextToken());
        int K=Integer.parseInt(tmp.nextToken());
        
        String input=in.nextLine();
        
        StringTokenizer obj=new StringTokenizer(input,",");
        
        while (obj.hasMoreTokens())
        {
            data.add(Integer.parseInt(obj.nextToken()));
        }
        
        for (int i=0;i<K;i++)
        {
            for (int j=0;j<data.size()-1;j++)
            {
                data.set(j,data.get(j+1)-data.get(j));
            }
            data.remove(data.size()-1);
        }
        
        for (int i=0;i<data.size();i++)
        {
            System.out.print(data.get(i));
            if ((i+1)!=data.size())
            	System.out.print(",");
        }
    }
}