import java.util.*;

public class Main1620
{
    public static void main(String[] args)
    {
        Scanner in=new Scanner(System.in);
        String check=in.nextLine();
        StringTokenizer obj=new StringTokenizer(check);
        
        int pokemon=Integer.parseInt(obj.nextToken());
        int find=Integer.parseInt(obj.nextToken());
        
        HashMap<String,Integer> data1=new HashMap<String,Integer>();
        HashMap<Integer,String> data2=new HashMap<Integer,String>();
        
        for (int i=0;i<pokemon;i++)
        {
            String tmp=in.nextLine();
            data1.put(tmp,new Integer(i+1));
            data2.put(new Integer(i+1),tmp);
        }
        
        System.out.println();
        System.out.println(data1.keySet());
        System.out.println(data1.values());
        System.out.println(data2.keySet());
        System.out.println(data2.values());
        
        for (int i=0;i<find;i++)
        {
            String input=in.nextLine();
            if (!Character.isDigit(input.charAt(0)))
                System.out.println(data1.get(input));
            else
                System.out.println(data2.get(Integer.parseInt(input)));
        }
    }
}