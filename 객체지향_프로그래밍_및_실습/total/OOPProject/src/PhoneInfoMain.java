
public class PhoneInfoMain {

	public static void main(String[] args) {
		PhoneInfoController controller=new PhoneInfoController();
		MenuViewer obj=new MenuViewer();
		
		while (true)
		{
			obj.ShowMenu();
			int input=obj.Keyboard.nextInt();
			obj.Keyboard.nextLine();
			
			switch (input)
			{
			case 1 :
			{
				controller.SavePhoneInfo();
				break;
			}
			case 2 :
			{
				controller.SearchPhoneInfo();
				break;
			}
			case 3 :
			{
				controller.DeletePhoneInfo();
				break;
			}
			case 4 :
			{
				System.out.println("���α׷��� �����մϴ�.");
				return;
			}
			default :
			{
				System.out.println("�߸��� �����Դϴ�.\n�ٽ� �Է��ϼ���.");
				break;
			}
			}
		}
	}

}
