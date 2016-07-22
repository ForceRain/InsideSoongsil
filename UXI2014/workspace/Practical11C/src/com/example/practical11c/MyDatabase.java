package com.example.practical11c;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabase extends SQLiteOpenHelper
{
	public static final String COUNTRY="Country";
	public static final String COUNTRY_ID="_id";
	public static final String COUNTRY_NAME="_name";
	public static final String COUNTRY_APPLE="_apple";
	
	private static final String DATABASE_NAME="Countries.db";
	private static final int DATABASE_VERSION=1;
	
	private static final String DATABASE_CREATE="create table "+COUNTRY+"("+COUNTRY_ID+" integer primary key autoincrement, "
			+COUNTRY_NAME+" text not null, "+COUNTRY_APPLE+" integer);";
	
	public MyDatabase(Context context)
	{
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		// TODO Auto-generated method stub
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS "+COUNTRY);
		onCreate(db);
	}
	
}
