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

public class LineTitleActivity extends Activity
{
	private EditText titleEdit,xEdit,yEdit;
	private Button okButton;
	private String title, xAxis, yAxis;
	private Intent getInt;
	private int isEdit;
	private ArrayList<String> check;
	private GraphSQLiteOpenHelper checkHelper;
	private SQLiteDatabase checkDB;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.line_title);
		
		getInt = getIntent();
		title = getInt.getStringExtra("GETTITLE");
		xAxis = getInt.getStringExtra("GETXAXIS");
		yAxis = getInt.getStringExtra("GETYAXIS");
		isEdit = getInt.getIntExtra("ISEDIT", -1);
		
		titleEdit=(EditText)findViewById(R.id.lineTitleEdit);
		xEdit=(EditText)findViewById(R.id.lineXaxisEdit);
		yEdit=(EditText)findViewById(R.id.lineYaxisEdit);
		okButton=(Button)findViewById(R.id.lineTitleOKBT);
		if(title != null){
			titleEdit.setText(title);
		}
		if(xAxis != null){
			xEdit.setText(xAxis);
		}
		if(yAxis != null){
			yEdit.setText(yAxis);
		}
		if(isEdit == -1){
			okButton.setText("OK");
		}
		else{
			okButton.setText("Edit");
		}
		check = new ArrayList<String>();
		checkHelper = new GraphSQLiteOpenHelper(LineTitleActivity.this, "graph.db", null, 1);
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
					Toast.makeText(LineTitleActivity.this, "This title already exists", Toast.LENGTH_LONG).show();
				}
				else {
					if(isEdit == -1){
						Intent intent=new Intent(LineTitleActivity.this,InputControlActivity.class);
						intent.putExtra("graphType","line");
						intent.putExtra("TITLE", titleEdit.getText().toString());
						intent.putExtra("XAXIS", xEdit.getText().toString());
						intent.putExtra("YAXIS", yEdit.getText().toString());
						intent.putExtra("TYPE",0);
						
						startActivity(intent);
					}
					else{
						GraphSQLiteOpenHelper graphHelper;
						SQLiteDatabase graphdb;
						graphHelper = new GraphSQLiteOpenHelper(LineTitleActivity.this, "graph.db", null, 1);
						graphdb = graphHelper.getWritableDatabase();
						String titleTmp, xAxisTmp, yAxisTmp;
						titleTmp = titleEdit.getText().toString();
						xAxisTmp = xEdit.getText().toString();
						yAxisTmp = yEdit.getText().toString();
						ContentValues values = new ContentValues();
						values.put("title", titleTmp);
						values.put("xAxis", xAxisTmp);
						values.put("yAxis", yAxisTmp);
						graphdb.update("graph", values, "title=?", new String[]{title});
						graphHelper.close();
						Intent intent = new Intent(LineTitleActivity.this, LineChartBuilder.class);
						intent.putExtra("ISEDIT", 1);
						startActivity(intent);
						finish();
					}
				}
			}
		});
	}
}
