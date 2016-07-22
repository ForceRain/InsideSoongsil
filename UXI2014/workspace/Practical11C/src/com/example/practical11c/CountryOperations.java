package com.example.practical11c;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CountryOperations 
{
	private MyDatabase dbHelper;
	private String[] COUNTRY_TABLE_COLUMNS={MyDatabase.COUNTRY_ID,MyDatabase.COUNTRY_NAME,MyDatabase.COUNTRY_APPLE};
	private SQLiteDatabase database;
	
	public CountryOperations(Context context)
	{
		dbHelper=new MyDatabase(context);
	}
	
	public void open() throws SQLException
	{
		database=dbHelper.getWritableDatabase();
	}
	
	public void close()
	{
		dbHelper.close();
	}
	
	public AppleCountry addApple(String country,int num)
	{
		ContentValues values=new ContentValues();
		values.put(MyDatabase.COUNTRY_NAME,country);
		values.put(MyDatabase.COUNTRY_APPLE,num);
		long countryId=database.insert(MyDatabase.COUNTRY,null,values);
		
		Cursor cursor=database.query(MyDatabase.COUNTRY,COUNTRY_TABLE_COLUMNS,MyDatabase.COUNTRY_ID+" = "+countryId,null,null,null,null);
		cursor.moveToFirst();
		AppleCountry newComment=parseCountry(cursor);
		cursor.close();
		return newComment;
	}
	
	public int getAppleNum(String country)
	{
		int output=0;
		
		Cursor cursor=database.query(MyDatabase.COUNTRY,COUNTRY_TABLE_COLUMNS,null,null,null,null,null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			AppleCountry tmp=parseCountry(cursor);
			if (tmp.getCountry().equals(country))
			{
				System.out.println(tmp);
				output+=tmp.getAppleNum();
			}
			cursor.moveToNext();
		}
		cursor.close();
		return output;
	}
	
	public AppleCountry parseCountry(Cursor cursor)
	{
		AppleCountry aCountry=new AppleCountry();
		aCountry.setId(cursor.getInt(0));
		aCountry.setCountry(cursor.getString(1));
		aCountry.setAppleNum(cursor.getInt(2));
		return aCountry;
	}
}
