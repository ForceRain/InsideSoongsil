import java.util.Scanner;

public class Main2857
{
    public static void main(String[] args)
    {
        Scanner in=new Scanner(System.in);
        int []arr=new int[5];
        int cnt=0;
        String output="";
        
        for (int i=0;i<5;i++)
        {
            String input=in.nextLine();
            int check=input.indexOf("FBI");
            if (check==-1)
               	cnt++;
            else
                output+=(i+1)+" ";
        }
        
        if (cnt==5)
            System.out.println("HE GOT AWAY!");
        else
        	System.out.println(output);
    }
}