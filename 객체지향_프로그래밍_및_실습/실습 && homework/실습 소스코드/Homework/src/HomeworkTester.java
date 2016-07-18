import java.util.Scanner;

class HomeworkTester 
{
	public static void main(String[] args) 
	{
		Scanner Keyboard=new Scanner(System.in);
		HangManPrinter Menu=new HangManPrinter();
		HangManController Control=new HangManController();
		
		while (true)
		{
			Menu.Showmenu();
			System.out.println("Please enter your selection :");
			int choose=Integer.parseInt(Keyboard.nextLine());
		
			if (choose==1)
			{
				System.out.print("What is the size of data list? :");
				String size=Keyboard.nextLine();
				Control.SetSize(Integer.parseInt(size));
				
				for (int i=0;i<Integer.parseInt(size);i++)
				{
					System.out.print("Start Enter the animal name # "+(i+1)+"/"+Integer.parseInt(size)+" :");
					String temp=Keyboard.nextLine();
					Control.SetData(temp);
				}
				
				System.out.println("Do you want to continue? (y/n)");
				String out=Keyboard.nextLine();
				if (Control.IsOut(out))
					break;
			}
			else												
			{
				if (Control.StartGame())
				{
					for (int i=0;i<5;i++)
					{
						System.out.println(Control);
						Control.showString();
						Control.ShowChar();
						System.out.println("Please enter a character :");
						String CharIn=Keyboard.nextLine();
						if(!Control.CheckChar(CharIn))
						{
							System.out.println("You already gave SAME character!!");
							i--;
						}
						
						if (Control.CheckAns())
						{
							System.out.println(Control);
							Control.showString();
							System.out.println("Congratulations!");
							break;
						}
					}
					if (!Control.CheckAns())
					{
						System.out.println(Control);
						Control.showString();
						System.out.println("Poor Guess!!!");
					}
					//last(5th)answer matching
				}
				
				System.out.println("Do you want to continue? (y/n)");
				String out=Keyboard.nextLine();
				if (Control.IsOut(out))
					break;
			}
		}
	}
}
