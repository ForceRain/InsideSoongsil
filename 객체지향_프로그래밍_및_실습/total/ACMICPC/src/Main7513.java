import java.util.Scanner;
import java.util.ArrayList;

public class Main7513
{
    public static void main(String[] args)
    {
        Scanner in=new Scanner(System.in);
        int times=Integer.parseInt(in.nextLine());
        
        for (int i=0;i<times;i++)
        {
            ArrayList<String> data=new ArrayList<String>();
        	int strs=Integer.parseInt(in.nextLine());
            
            for (int j=0;j<strs;j++)
            {
                String tmp=in.nextLine();
                data.add(tmp);
            }
            
            int cases=Integer.parseInt(in.next());
            
            System.out.println("Scenario #"+(i+1)+":");
            for (int k=0;k<cases;k++)
            {
                int out=Integer.parseInt(in.next());
                
                for (int d=0;d<out;d++)
                {
                    System.out.print(data.get(Integer.parseInt(in.next())));
                }
                
                System.out.println();
            }
            in.nextLine();
            System.out.println();
        }
    }
}