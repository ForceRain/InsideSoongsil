interface TV
{
	public void onTV();
}

class TVImpl
{
	public void onTV()
	{
		System.out.println("영상 출력 중");
	}
}

interface Computer
{
	public void dataReceive();
}

class ComputerImpl
{
	public void dataReceive()
	{
		System.out.println("영상 데이터 수신중");
	}
}

class IPTV implements TV,Computer
{
	ComputerImpl comp=new ComputerImpl();
	TVImpl comp1=new TVImpl();
	
	public void dataReceive()
	{
		comp.dataReceive();
	}
	public void onTV()
	{
		comp1.onTV();
	}
	public void powerOn()
	{
		dataReceive();
		onTV();
	}
}

public class Ques17_1 
{
	public static void main(String[] args)
	{
		IPTV iptv=new IPTV();
		iptv.powerOn();
		
		TV tv=iptv;
		Computer comp=iptv;
	}
}
