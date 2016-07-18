import java.util.Scanner;
import java.util.ArrayList;

public class Main9494
{
    public static void main(String[] args)
    {
        Scanner in=new Scanner(System.in);
     	ArrayList<Integer> data=new ArrayList<Integer>();
        int index=0;
        
        while (true)
        {
        	int times=Integer.parseInt(in.nextLine());
        	if (times==0)
        		break;
        	
        	for (int j=0;j<times;j++)
        	{
	            String tmp=in.nextLine();
	            
	            if (tmp.length()>index)
	            {
		        	while (tmp.charAt(index)!=' ')
		                index++;
	            }
        	}
        	
            data.add(index+1);
        }
        
        for (int i=0;i<data.size();i++)
        {
            System.out.println(data.get(i));
        }
    }
}