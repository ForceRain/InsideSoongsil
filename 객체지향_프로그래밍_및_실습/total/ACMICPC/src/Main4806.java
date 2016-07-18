import java.util.Scanner;

public class Main4806
{
    public static void main(String[] args)
    {
        Scanner in=new Scanner(System.in);
        
        int cnt=0;
        while (true)
        {
            String input=in.nextLine();
            if (input.equals("\n"))
                break;
            cnt++;
        }
        
        System.out.println(cnt);
    }
}