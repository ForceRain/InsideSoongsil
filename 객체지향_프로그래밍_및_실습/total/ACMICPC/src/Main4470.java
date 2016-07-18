import java.util.Scanner;

public class Main4470
{
    public static void main(String[] args)
    {
        Scanner in=new Scanner(System.in);
        int times=Integer.parseInt(in.nextLine());
        String output="";
        
        for (int i=0;i<times;i++)
        {
            String tmp=in.nextLine();
            output+=""+(i+1)+". "+tmp+"\n";
        }
        
        System.out.println(output);
    }
}