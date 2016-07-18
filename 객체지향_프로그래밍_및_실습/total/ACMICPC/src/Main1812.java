import java.util.Scanner;
import java.util.ArrayList;

public class Main1812
{
    public static void main(String[] args)
    {
        Scanner in=new Scanner(System.in);
        int times=Integer.parseInt(in.nextLine());
        ArrayList<Integer> data=new ArrayList<Integer>();
        int tmp;
        int total=0;
        
        for (int i=0;i<times;i++)
        {
            tmp=in.nextInt();
            data.add(tmp);
            total+=tmp;
        }
        total/=2;
        
        for (int i=1;i<times;i++)
        {
            System.out.println(total-data.get(i));
        }
        System.out.println(total-data.get(0));
    }
}