
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
		System.out.print("지금까지의 누적금액 :");
		System.out.println(getAccVal());
	}
}
