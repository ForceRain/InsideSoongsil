
class PhoneInfoController {
	private int max;
	private PhoneInfo[] arr;
	
	public PhoneInfoController()
	{
		max=0;
		arr=new PhoneInfo[100];
	}
	
	public void SavePhoneInfo()
	{
		String name,phone,major,company;
		int choose,year;
		
		if (max==100)
		{
			System.out.println("�� �̻� ������ �߰��� �Ұ����մϴ�.");
			System.out.println("�� �߰��Ϸ��� ������ ������ �����Ͻʽÿ�.");
			return;
		}
		
		System.out.println("������ �Է��� �����մϴ�..\n");
		System.out.println("1. �Ϲ�, 2. ����, 3. ȸ��");
		System.out.print("����>>");choose=Integer.parseInt(MenuViewer.Keyboard.nextLine());
		System.out.print("�̸� : ");name=MenuViewer.Keyboard.nextLine();
		System.out.print("��ȭ��ȣ : ");phone=MenuViewer.Keyboard.nextLine();
		if (choose==2)
		{
			System.out.print("���� :");major=MenuViewer.Keyboard.nextLine();
			System.out.print("�г� :");year=MenuViewer.Keyboard.nextInt();
			
			arr[max++]=new PhoneUnivInfo(name,phone,major,year);
		}
		else if (choose==3)
		{
			System.out.print("ȸ�� :");company=MenuViewer.Keyboard.nextLine();
			
			arr[max++]=new PhoneCompanyInfo(name,phone,company);
		}
		else
		{
			arr[max++]=new PhoneInfo(name,phone);
		}
		System.out.println("������ �Է��� �Ϸ�Ǿ����ϴ�.");
		
	}
	
	public void SearchPhoneInfo()

	{
		String input;
		
		if (max==0)
		{
			System.out.println("�����Ͱ� �������� �ʽ��ϴ�!");
			System.out.println("������ �Է��� �����Ͻʽÿ�.");
			return;
		}
		
		System.out.println("������ �˻��� �����մϴ�.");
		System.out.print("�̸� :");input=MenuViewer.Keyboard.nextLine();
		
		for (int i=0;i<max;i++)
		{
			if (arr[i].GetName().equals(input))
			{
				arr[i].ShowPhoneInfo();
				System.out.println("������ �˻��� �Ϸ�Ǿ����ϴ�.");
				return;
			}
		}
		
		System.out.println("�����Ͱ� �������� �ʽ��ϴ�.");
	}
	
	public void DeletePhoneInfo()
	{
		String input;
		
		if (max==0)
		{
			System.out.println("������ �����Ͱ� �������� �ʽ��ϴ�.");
			System.out.println("������ �Է��� �����ϰ� ���ʽÿ�.");
			return;
		}
		
		System.out.println("������ ������ �����մϴ�..");
		System.out.print("�̸� :");input=MenuViewer.Keyboard.nextLine();
		
		for (int i=0;i<max;i++)
		{
			if (arr[i].GetName().equals(input))
			{
				for (int j=i;j<max-1;j++)
				{
					arr[j]=arr[j+1];
				}
				max--;
				System.out.println("������ ������ �Ϸ�Ǿ����ϴ�.");
				return;
			}
		}
		
		System.out.println("�����͸� �߰����� ���߽��ϴ�.");
	}
}
