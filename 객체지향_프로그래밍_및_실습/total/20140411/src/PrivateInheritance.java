
class PrivateInheritance 
{
	public static void main(String[] args)
	{
		SavingAccount sa=new SavingAccount(1000);
		sa.saveMoney(1000);
		sa.saveMoney(1000);
		sa.showSavedMoney();
	}
}
