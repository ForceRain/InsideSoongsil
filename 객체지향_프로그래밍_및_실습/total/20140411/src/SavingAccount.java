
class SavingAccount extends Accumulator
{
	public SavingAccount(int initDep)
	{
		super(initDep);
	}
	public void saveMoney(int money)
	{
		accumulate(money);
	}
	public void showSavedMoney()
	{
		System.out.print("���ݱ����� �����ݾ� :");
		System.out.println(getAccVal());
	}
}
