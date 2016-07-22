package com.example.practical11a;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabase extends SQLiteOpenHelper
{
	public static final String STUDENTS="Students";
	public static final String STUDENT_ID="_id";
	public static final String STUDENT_NAME="_name";
	
	private static final String DATABASE_NAME="Students.db";
	private static final int DATABASE_VERSION=1;
	
	private static final String DATABASE_CREATE="create table "+STUDENTS+"("
			+STUDENT_ID+" integer primary key autoincrement, "+STUDENT_NAME+" text not null);";
	
	public MyDatabase(Context context)
	{
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
	}
	
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(DATABASE_CREATE);
	}
	
	public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion)
	{
		db.execSQL("DROP TABLE IF EXISTS "+STUDENTS);
		onCreate(db);
	}
}
