package com.jw.graker;
/*
 * Copyright 2014 Graker

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
   */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class BarScaleSQLiteOpenHelper extends SQLiteOpenHelper{
	public BarScaleSQLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version){
		super(context, name, factory, version);
	}
	
	public void onCreate(SQLiteDatabase db){
		String sql = "create table barScale (" + "_id, " 
				+ "title text, " 
				+ "xData float, "
				+ "yData float);";
		db.execSQL(sql);
	}
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		String sql = "drop table if exists barScale";
		db.execSQL(sql);
		onCreate(db);
	}
}
