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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class BarTitleActivitiy extends Activity{
	
	private EditText titleEdit, xEdit, yEdit; 
	private Button okbutton;
	private RadioGroup radiogroup;
	private RadioButton scale;
	private RadioButton category;
	private int type, getType, isEdit;
	private String title, xAxis, yAxis;
	private Intent getInt;
	private ArrayList<String> check;
	private GraphSQLiteOpenHelper checkHelper;
	private SQLiteDatabase checkDB;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bar_title);
		getInt = getIntent();
		title = getInt.getStringExtra("GETTITLE");
		xAxis = getInt.getStringExtra("GETXAXIS");
		yAxis = getInt.getStringExtra("GETYAXIS");
		getType = getInt.getIntExtra("GETTYPE", -1);
		isEdit = getInt.getIntExtra("ISEDIT", -1);
		titleEdit = (EditText)findViewById(R.id.barTitleEdit);
		xEdit = (EditText)findViewById(R.id.barXaxisEidt);
		yEdit = (EditText)findViewById(R.id.barYaxisEdit);
		radiogroup = (RadioGroup) findViewById(R.id.barTypeGroup);
		scale = (RadioButton) findViewById(R.id.barTypeScale);
		category = (RadioButton) findViewById(R.id.barTypeCategory);
		okbutton = (Button) findViewById(R.id.barTitleOKBT);
		if(title != null){
			titleEdit.setText(title);
		}
		if(xAxis != null){
			xEdit.setText(xAxis);
		}
		if(yAxis != null){
			yEdit.setText(yAxis);
		}
		if(getType != -1){
			if(getType == 0){
				scale.setChecked(true);
				category.setChecked(false);
				yEdit.setClickable(true);
				yEdit.setEnabled(true);
				yEdit.setFocusable(true);
				yEdit.setFocusableInTouchMode(true);
				type = 0;
			}
			else if(getType == 1){
				scale.setChecked(false);
				yEdit.setText("Frequency");
				category.setChecked(true); //에딧텍스트 비활성화
				yEdit.setClickable(false);
				yEdit.setEnabled(false);
				yEdit.setFocusable(false);
				yEdit.setFocusableInTouchMode(false);
				type = 1;
			}
		}
		if(isEdit == -1){
			okbutton.setText("OK");
			radiogroup.setClickable(true);
			radiogroup.setEnabled(true);
			radiogroup.setFocusable(true);
			radiogroup.setFocusableInTouchMode(true);
			scale.setClickable(true);
			scale.setEnabled(true);
			scale.setFocusable(true);
			scale.setFocusableInTouchMode(true);
			category.setClickable(true);
			category.setEnabled(true);
			category.setFocusable(true);
			category.setFocusableInTouchMode(true);
		}
		else{
			okbutton.setText("Edit");
			radiogroup.setClickable(false);
			radiogroup.setEnabled(false);
			radiogroup.setFocusable(false);
			radiogroup.setFocusableInTouchMode(false);
			scale.setClickable(false);
			scale.setEnabled(false);
			scale.setFocusable(false);
			scale.setFocusableInTouchMode(false);
			category.setClickable(false);
			category.setEnabled(false);
			category.setFocusable(false);
			category.setFocusableInTouchMode(false);
		}
		check = new ArrayList<String>();
		checkHelper = new GraphSQLiteOpenHelper(BarTitleActivitiy.this, "graph.db", null, 1);
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
		radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch(checkedId){
				case R.id.barTypeScale:
					type = 0;
					yEdit.setClickable(true);
					yEdit.setEnabled(true);
					yEdit.setFocusable(true);
					yEdit.setFocusableInTouchMode(true);
					yEdit.setText("");
					break;
				case R.id.barTypeCategory:
					type = 1;
					yEdit.setText("Frequency");
					yEdit.setClickable(false);
					yEdit.setEnabled(false);
					yEdit.setFocusable(false);
					yEdit.setFocusableInTouchMode(false);
					break;
				}
				
			}
		});
		
		okbutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
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
					Toast.makeText(BarTitleActivitiy.this, "This title already exists", Toast.LENGTH_LONG).show();
				}
				else {
					if (isEdit == -1) {
						Intent intent = new Intent(BarTitleActivitiy.this,
								InputControlActivity.class);
						intent.putExtra("graphType", "bar");
						intent.putExtra("TITLE", titleEdit.getText().toString());
						intent.putExtra("XAXIS", xEdit.getText().toString());
						intent.putExtra("YAXIS", yEdit.getText().toString());
						switch (type) {
						case 0:
							intent.putExtra("TYPE", 0);
							break;
						case 1:
							intent.putExtra("TYPE", 1);
							break;
						}
						startActivity(intent);
					} else {
						// 중복 예외 아직 필요
						GraphSQLiteOpenHelper graphHelper;
						SQLiteDatabase graphdb;
						graphHelper = new GraphSQLiteOpenHelper(
								BarTitleActivitiy.this, "graph.db", null, 1);
						graphdb = graphHelper.getWritableDatabase();
						String titleTmp, xAxisTmp, yAxisTmp;
						titleTmp = titleEdit.getText().toString();
						xAxisTmp = xEdit.getText().toString();
						yAxisTmp = yEdit.getText().toString();
						int typeTmp;
						typeTmp = type;
						ContentValues values = new ContentValues();
						values.put("title", titleTmp);
						values.put("xAxis", xAxisTmp);
						values.put("yAxis", yAxisTmp);
						values.put("type", typeTmp);
						graphdb.update("graph", values, "title=?",
								new String[] { title });
						graphHelper.close();
						if (typeTmp == 0) {
							Intent intent = new Intent(BarTitleActivitiy.this,
									BarChartScaleBuilder.class);
							intent.putExtra("ISEDIT", 1);
							intent.putExtra("PREVTITLE", title);
							intent.putExtra("TITLE", titleTmp);
							intent.putExtra("XAXIS", xAxisTmp);
							intent.putExtra("YAXIS", yAxisTmp);
							startActivity(intent);
						} else {
							Intent intent = new Intent(BarTitleActivitiy.this,
									BarChartCategoryBuilder.class);
							intent.putExtra("ISEDIT", 1);
							intent.putExtra("PREVTITLE", title);
							intent.putExtra("TITLE", titleTmp);
							intent.putExtra("XAXIS", xAxisTmp);
							intent.putExtra("YAXIS", yAxisTmp);
							startActivity(intent);
						}
					}
					finish();
				}
			}
		});
		
	}

}
