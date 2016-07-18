
class Greetings {
	public static void main(String[] args)
	{
		String[][] table=new String[4][2];
		table[0][0]="Good morning";
		table[0][1]="좋은 아침입니다.";
		table[1][0]="It is a pleasure to meet you.";
		table[1][1]="당신을 만나뵙게 되어 영광입니다.";
		table[2][0]="Please call me tomorrow.";
		table[2][1]="내일 저에게 연락주세요.";
		table[3][0]="Have a nice day!";
		table[3][1]="좋은 하루 되세요!";
		
		System.out.println("Lise of Phrases to Translate");
		for (int i=0;i<4;i++)
		{
			System.out.println(table[i][0]+"      "+table[i][1]);
		}
	}
}
