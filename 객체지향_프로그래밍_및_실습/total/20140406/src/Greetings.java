
class Greetings {
	public static void main(String[] args)
	{
		String[][] table=new String[4][2];
		table[0][0]="Good morning";
		table[0][1]="���� ��ħ�Դϴ�.";
		table[1][0]="It is a pleasure to meet you.";
		table[1][1]="����� �����˰� �Ǿ� �����Դϴ�.";
		table[2][0]="Please call me tomorrow.";
		table[2][1]="���� ������ �����ּ���.";
		table[3][0]="Have a nice day!";
		table[3][1]="���� �Ϸ� �Ǽ���!";
		
		System.out.println("Lise of Phrases to Translate");
		for (int i=0;i<4;i++)
		{
			System.out.println(table[i][0]+"      "+table[i][1]);
		}
	}
}
