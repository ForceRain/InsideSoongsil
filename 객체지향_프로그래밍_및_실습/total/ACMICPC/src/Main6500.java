import java.util.Scanner;
import java.util.ArrayList;

public class Main6500
{
    public static void main(String[] args)
    {
        Scanner in=new Scanner(System.in);
        ArrayList<Integer> data=new ArrayList<Integer>();
        
        while (true)
        {
            String input=in.nextLine();
            String tmp=input;
            int cnt=0;
            
            if (input.equals("0"))
                break;
                
            int temp=Integer.parseInt(tmp);
            
            System.out.println("check");
            
            tmp=Integer.toString(temp*temp);
            
            System.out.println(tmp);
            
            while (tmp.length()<8)
            {
            	tmp="0"+tmp;
            }
			
            while (!tmp.equals(input))
            {
                tmp=tmp.substring(2,6);
                
                //System.out.println(tmp);
                
                temp=Integer.parseInt(tmp);
                
                tmp=Integer.toString(temp*temp);
                
                
                while (tmp.length()<8)
                {
                    tmp="0"+tmp;
                }
                cnt++;
            }
            
            data.add(cnt);
        }
        
        for (int i=0;i<data.size();i++)
        {
            System.out.println(data.get(i));
        }
        
    }
}