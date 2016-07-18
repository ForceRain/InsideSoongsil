import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.ArrayList;

class Wow
{
    private ArrayList<String> data;
    public Wow()
    {
        data=new ArrayList<String>();
    }
    
    public void push(String input)
    {
        StringTokenizer obj=new StringTokenizer(input);
        String tmp1=obj.nextToken()+" "+obj.nextToken();
        String tmp2="";
        
        while (obj.hasMoreTokens())
        {
            tmp2+=obj.nextToken()+" ";
        }
        tmp2+=tmp1;
        data.add(tmp2);
    }
    
    public void show()
    {
        for (int i=0;i<data.size();i++)
        {
            System.out.println(data.get(i));
        }
    }
}

public class Main5363
{
    public static void main(String[] args)
    {
        Scanner in=new Scanner(System.in);
        int times=Integer.parseInt(in.nextLine());
        Wow obj=new Wow();
        
       	for (int i=0;i<times;i++)
        {
            obj.push(in.nextLine());
        }
        
        obj.show();
    }
}