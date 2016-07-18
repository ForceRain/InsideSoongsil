import java.util.Scanner;

public class Main1
{
    public static void main(String[] args)
    {
        Scanner in=new Scanner(System.in);
        
        int N=in.nextInt();
        int[] arr=new int[N];
        
        for (int i=0;i<N;i++)
        {
            int temp=in.nextInt();
            arr[i]=temp;
        }
        
        int total=0;
        
        for (int j=0;j<N;j++)
        {
            for (int i=j;i<N;i++)
            {
                total+=(arr[j]^arr[i]);
            }
        }
        
        System.out.println(total);
    }
}