
class HangManPrinter 
{
	public void Showmenu()
	{
		for (int i=0;i<32;i++)
			System.out.print("*");
		System.out.println();
		
		System.out.println("* OOP Homework 1-------Hangman *");
		
		for (int i=0;i<32;i++)
			System.out.print("*");
		System.out.println("\n");
		
		
		System.out.print("   ");
		for (int i=0;i<25;i++)
			System.out.print("-");
		System.out.println();
		
		System.out.println("   | 1. Setup the data List |");
		System.out.println("   |------------------------");
		System.out.println("   | 2. Start Game          |");
		
		System.out.print("   ");
		for (int i=0;i<25;i++)
			System.out.print("-");
		System.out.println();
	}
	/*
	System.out.println(" -------");
	System.out.println("|       |");
	System.out.println("|       O");
	System.out.println("|       |");
	System.out.println("|      ---");
	System.out.println("|       |");
	System.out.println("|      / \");
	System.out.println("|");
	System.out.println("---");
	" -------\n|       |\n|       O\n|       |\n|      ---\n|       |\n|      / \ \n|\n---\n"
	*/
	
	public String ShowHangMan(int input)
	{
		switch(input)
		{
		case 0 :
			return " -------\n|       |\n|\n|\n|\n|\n|\n|\n---\n";

		case 1 :
			return " -------\n|       |\n|       O\n|\n|\n|\n|\n|\n---\n";
			
		case 2 :
			return " -------\n|       |\n|       O\n|       |\n|\n|\n|\n|\n---\n";

		case 3 :
			return " -------\n|       |\n|       O\n|       |\n|      ---\n|\n|\n|\n---\n";

		case 4 :
			return " -------\n|       |\n|       O\n|       |\n|      ---\n|       |\n|\n|\n---\n";

		case 5 :
			return " -------\n|       |\n|       O\n|       |\n|      ---\n|       |\n|      / \\\n|\n---\n";

		default :
			return "";
		}
	}
}
