package com.example.practical11b;

public class Student {
	private int id;
	private String name;
	private int studentId;
	
	public long getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id=id;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setName(String name)
	{
		this.name=name;
	}
	
	public long getStuId()
	{
		return studentId;
	}
	
	public void setStuId(int stuId)
	{
		studentId=stuId;
	}
	
	public String toString()
	{
		return name+" "+studentId;
	}
}
