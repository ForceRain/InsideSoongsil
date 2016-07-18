import java.util.Scanner;
import java.util.StringTokenizer;
import java.lang.Math;

public class main
{
    public static void main(String[] args)
    {
        Scanner in=new Scanner(System.in);
        int N=in.nextInt();
        in.nextLine();
        
        double[] ans=new double[N];
        
        for (int i=0;i<N;i++)
        {
            double sum=0;
            String input=in.nextLine();
            StringTokenizer check=new StringTokenizer(input,"-");
            
            String first=check.nextToken();
            String second=check.nextToken();
            
            for (int j=0;j<3;j++)
            {
                sum+=(first.charAt(j)-'A')*Math.pow(26,2-j);
            }
            
            sum-=Integer.parseInt(second);
            ans[i]=Math.abs(sum);
        }
        
        for (int i=0;i<N;i++)
        {
            if (ans[i]<=100)
                System.out.println("nice");
            else
                System.out.println("not nice");
        }
    }
}