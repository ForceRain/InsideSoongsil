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
import java.util.ArrayList;

import com.jw.graker.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PieTitleActivity extends Activity
{
	private EditText titleEdit;
	private String title;
	private int isEdit;
	private Intent getInt;
	private GraphSQLiteOpenHelper checkHelper;
	private SQLiteDatabase checkDB;
	private ArrayList<String> check;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pie_title);
		getInt = getIntent();

		title = getInt.getStringExtra("GETTITLE");
		isEdit = getInt.getIntExtra("ISEDIT", -1);
		
		titleEdit=(EditText)findViewById(R.id.pieTitleEdit);
		Button okButton=(Button)findViewById(R.id.pieTitleOKBT);
		
		if(title != null){
			titleEdit.setText(title);
		}
		if(isEdit != -1){
			okButton.setText("Edit");
		}
		else{
			okButton.setText("OK");
		}
		check = new ArrayList<String>();
		checkHelper = new GraphSQLiteOpenHelper(PieTitleActivity.this, "graph.db", null, 1);
		checkDB = checkHelper.getReadableDatabase();
		Cursor checkC = checkDB.query("graph", null, null,null, null,null, null);
		checkC.moveToFirst();
		while(!checkC.isAfterLast()){
			String titleTmp;
			titleTmp = checkC.getString(checkC.getColumnIndex("title"));
			check.add(titleTmp);
			checkC.moveToNext();
		}
		checkHelper.close();
		okButton.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				int isChecked = 0;
				for(int i=0; i<check.size(); i++){
					String tmpCheck = check.get(i);
					if(tmpCheck.equals(titleEdit.getText().toString())){
						if(isEdit == 1){
							if(tmpCheck.equals(title)){
								continue;
							}
						}
						isChecked = 1;
					}
				}
				if(isChecked == 1){
					Toast.makeText(PieTitleActivity.this, "This title already exists", Toast.LENGTH_LONG).show();
				}
				else {
					if(isEdit == -1){
						Intent intent=new Intent(PieTitleActivity.this,InputControlActivity.class);
						intent.putExtra("graphType","pie");
						intent.putExtra("TITLE", titleEdit.getText().toString());
						intent.putExtra("TYPE",1);
						
						startActivity(intent);
					}
					else{
						GraphSQLiteOpenHelper graphHelper;
						SQLiteDatabase graphdb;
						graphHelper = new GraphSQLiteOpenHelper(PieTitleActivity.this, "graph.db", null, 1);
						graphdb = graphHelper.getWritableDatabase();
						String titleTmp;
						titleTmp = titleEdit.getText().toString();
						ContentValues values = new ContentValues();
						values.put("title", titleTmp);
						graphdb.update("graph", values, "title=?", new String[]{title});
						graphHelper.close();
						Intent intent = new Intent(PieTitleActivity.this, PieChartBuilder.class);
						intent.putExtra("ISEDIT", 1);
						startActivity(intent);
					}
					finish();
				}
			}
		});
	}
}
