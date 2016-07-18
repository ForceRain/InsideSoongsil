import java.util.Scanner;
import java.util.ArrayList;

public class Main9324
{
    public static void main(String[] args)
    {
        Scanner in=new Scanner(System.in);
        int times=Integer.parseInt(in.nextLine());
        ArrayList<String> data=new ArrayList<String>();
        boolean isOkay=true;
        
        for (int i=0;i<times;i++)
        {
            isOkay=true;
            int[] temp=new int[26];
            
            for (int j=0;j<26;j++)
                temp[j]=0;
            
            String tmp=in.nextLine();
            
            for (int j=0;j<tmp.length();j++)
            {
                temp[tmp.charAt(j)-'A']++;
            }
            
            for (int j=0;j<26;j++)
            {
                if (temp[j]%3==0 && temp[j]>=3)
                {
                    isOkay=false;
                }
            }
            
            if (isOkay)
                data.add("OK");
            else
                data.add("FAKE");
        }
        
        for (int i=0;i<data.size();i++)
        {
            System.out.println(data.get(i));
        }
    }
}