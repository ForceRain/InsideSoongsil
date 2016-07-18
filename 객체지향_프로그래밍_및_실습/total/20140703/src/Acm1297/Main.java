package Acm1297;
import java.util.Scanner;
import java.lang.Math;

public class Main
{
    public static void main(String[] args)
    {
        Scanner in=new Scanner(System.in);
        int diag=in.nextInt();
        
        int widthR=in.nextInt();
        int heightR=in.nextInt();
        
        double width,height;
        double rate=0.0;
        
       	rate=Math.sqrt(diag*diag/(widthR*widthR+heightR*heightR));
        
        width=widthR*rate;
        height=heightR*rate;
        
        if (width>Math.round(width))//���� �ε� �Ҽ����� �� Ŭ��
        {
            width=Math.round(width);
        }
        else if (width<Math.round(width))//�ݿø� �Ǿ�����
        {
            width=Math.round(width)-1;
        }
        
        if (height>Math.round(height))
        {
            height=Math.round(height);
        }
        else if (height<Math.round(width))
        {
            height=Math.round(height)-1;
        }
        
        int resultR=(int)width;
        int resultH=(int)height;
        
        System.out.println(resultR+" "+resultH);
    }
}