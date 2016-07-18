
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
			System.out.println("더 이상 데이터 추가가 불가능합니다.");
			System.out.println("더 추가하려면 데이터 삭제를 진행하십시오.");
			return;
		}
		
		System.out.println("데이터 입력을 시작합니다..\n");
		System.out.println("1. 일반, 2. 대학, 3. 회사");
		System.out.print("선택>>");choose=Integer.parseInt(MenuViewer.Keyboard.nextLine());
		System.out.print("이름 : ");name=MenuViewer.Keyboard.nextLine();
		System.out.print("전화번호 : ");phone=MenuViewer.Keyboard.nextLine();
		if (choose==2)
		{
			System.out.print("전공 :");major=MenuViewer.Keyboard.nextLine();
			System.out.print("학년 :");year=MenuViewer.Keyboard.nextInt();
			
			arr[max++]=new PhoneUnivInfo(name,phone,major,year);
		}
		else if (choose==3)
		{
			System.out.print("회사 :");company=MenuViewer.Keyboard.nextLine();
			
			arr[max++]=new PhoneCompanyInfo(name,phone,company);
		}
		else
		{
			arr[max++]=new PhoneInfo(name,phone);
		}
		System.out.println("데이터 입력이 완료되었습니다.");
		
	}
	
	public void SearchPhoneInfo()

	{
		String input;
		
		if (max==0)
		{
			System.out.println("데이터가 존재하지 않습니다!");
			System.out.println("데이터 입력을 진행하십시오.");
			return;
		}
		
		System.out.println("데이터 검색을 시작합니다.");
		System.out.print("이름 :");input=MenuViewer.Keyboard.nextLine();
		
		for (int i=0;i<max;i++)
		{
			if (arr[i].GetName().equals(input))
			{
				arr[i].ShowPhoneInfo();
				System.out.println("데이터 검색이 완료되었습니다.");
				return;
			}
		}
		
		System.out.println("데이터가 존재하지 않습니다.");
	}
	
	public void DeletePhoneInfo()
	{
		String input;
		
		if (max==0)
		{
			System.out.println("삭제할 데이터가 존재하지 않습니다.");
			System.out.println("데이터 입력을 진행하고 오십시오.");
			return;
		}
		
		System.out.println("데이터 삭제를 진행합니다..");
		System.out.print("이름 :");input=MenuViewer.Keyboard.nextLine();
		
		for (int i=0;i<max;i++)
		{
			if (arr[i].GetName().equals(input))
			{
				for (int j=i;j<max-1;j++)
				{
					arr[j]=arr[j+1];
				}
				max--;
				System.out.println("데이터 삭제가 완료되었습니다.");
				return;
			}
		}
		
		System.out.println("데이터를 발견하지 못했습니다.");
	}
}
