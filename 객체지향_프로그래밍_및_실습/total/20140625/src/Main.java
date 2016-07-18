import java.util.Scanner;
import java.lang.Math;
import java.util.ArrayList;

public class Main
{
    public static void main(String[] args)
    {
        ArrayList<Integer> arr=new ArrayList<Integer>();
        Scanner in=new Scanner(System.in);
        
        while (true)
        {
            int a=in.nextInt();
            int b=in.nextInt();
            int c=in.nextInt();
            int d=in.nextInt();
            int cnt=0;
            
            if (a==0 && b==0 && c==0 && d==0)
            	break;
            
            while (true)
            {
            	 if (a==b && b==c && c==d)
                 {
                     arr.add(cnt);
                     break;
                 }
            	
                int n_a=Math.abs(a-b);
                int n_b=Math.abs(b-c);
                int n_c=Math.abs(c-d);
                int n_d=Math.abs(d-a);
                
                a=n_a;b=n_b;c=n_c;d=n_d;
                cnt++;
            }
        }
        
        for (int i=0;i<arr.size();i++)
        {
            System.out.println(arr.get(i));
        }
        
    }
}