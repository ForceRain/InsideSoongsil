import java.util.Scanner;

public class Main4458
{
    public static void main(String[] args)
    {
        Scanner in=new Scanner(System.in);
        int times=Integer.parseInt(in.nextLine());
        
        for (int i=0;i<times;i++)
        {
           	String tmp=in.nextLine();
            tmp=tmp.substring(0,1).toUpperCase()+tmp.substring(1);
            System.out.println(tmp);
        }
    }
}