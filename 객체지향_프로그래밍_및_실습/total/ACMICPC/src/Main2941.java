import java.util.Scanner;

public class Main2941
{
    public static void main(String[] args)
    {
        Scanner in=new Scanner(System.in);
        String input=in.nextLine();
        
        int length=input.length();
        int word_len=0;
        int word=0;
        int dzcheck=0;
        
        if (input.contains("c="))
        {
            word++;
            word_len+=2;
        }
        if (input.contains("c-"))
        {
            word++;
            word_len+=2;
        }
        if (input.contains("dz="))
        {
            word++;
            word_len+=3;
            dzcheck=input.indexOf("dz=");
        }
        if (input.contains("d-"))
        {
            word++;
            word_len+=2;
        }
        if (input.contains("lj"))
        {
            word++;
            word_len+=2;
        }
        if (input.contains("nj"))
        {
            word++;
            word_len+=2;
        }
        if (input.contains("s="))
        {
            word++;
            word_len+=2;
        }
        if (input.contains("z=") && input.indexOf("z=") != dzcheck+1)
        {
            word++;
            word_len+=2;
        }
        
        System.out.println(length-word_len+word);
    }
}