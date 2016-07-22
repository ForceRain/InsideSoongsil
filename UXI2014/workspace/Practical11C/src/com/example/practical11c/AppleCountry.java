package com.example.practical11c;

public class AppleCountry 
{
	private int id;
	private String country;
	private int appleNum;
	
	public long getId()
	{
		return id;
	}
	
	public void setId(int Id)
	{
		this.id=Id;
	}
	
	public String getCountry()
	{
		return country;
	}
	
	public void setCountry(String country)
	{
		this.country=country;
	}
	
	public long getAppleNum()
	{
		return appleNum;
	}
	
	public void setAppleNum(int appleNum)
	{
		this.appleNum=appleNum;
	}
	
	public void addAppleNum(int num)
	{
		appleNum+=num;
	}
	
	public String toString()
	{
		return country+" "+appleNum;
	}
}
