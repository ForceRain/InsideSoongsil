import java.util.Scanner;
import java.util.StringTokenizer;

class Check
{
    private int[] increase;
    private int[] decrease;
    private String incOut;
    private String decOut;    
    private int cuttingNum;
    
    public Check()
    {
        increase=new int[10];
        decrease=new int[10];
        
        for (int i=0;i<10;i++)
            increase[i]=i;
        
        for (int i=0;i<10;i++)
            decrease[i]=9-i;
    }
    
    public void setNum(int input)
    {
        cuttingNum=input+1;
    }
    
   	public void change(String input)
    {
   		StringTokenizer angle=new StringTokenizer(input);
        char temp;
        int swap;
        for (int i=0;angle.hasMoreTokens();i++)
        {
            temp=angle.nextToken().charAt(0);
                   
            if (temp=='<' && !(increase[i]<increase[i+1]))
            {
                swap=increase[i];
                increase[i]=increase[i+1];
                increase[i+1]=swap;
            }
            
            else if (temp=='>' && !(increase[i]>increase[i+1]))
            {
                swap=increase[i];
                increase[i]=increase[i+1];
                increase[i+1]=swap;
            }
            
            if (temp=='<' && !(decrease[i]<decrease[i+1]))
            {
                swap=decrease[i];
                decrease[i]=decrease[i+1];
               	decrease[i+1]=swap;
            }
            
            else if (temp=='>' && !(decrease[i]>decrease[i+1]))
            {
                swap=decrease[i];
                decrease[i]=decrease[i+1];
                decrease[i+1]=swap;
            }
        }
    }
    
	public String bigOut() 
    {
        String out="";
        for (int i=0;i<cuttingNum;i++)
            out+=decrease[i];
        
        return out;
    }
    
    public String smallOut()
    {
        String out="";
        for (int i=0;i<cuttingNum;i++)
        	out+=increase[i];
        
        return out;
    }
}

public class Main
{
    public static void main(String[] args)
    {
        Scanner in=new Scanner(System.in);
    	Check obj=new Check();    
        int num=Integer.parseInt(in.nextLine());
        
        obj.setNum(num);
        String angle=in.nextLine();
        
        obj.change(angle);
        
        System.out.println(obj.bigOut());
        System.out.println(obj.smallOut());
        
    }
}