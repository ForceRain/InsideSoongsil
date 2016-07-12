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
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.LinearLayout.LayoutParams;

public class SettingDataActivity extends Activity
{
	private ArrayList<Parcelable> data;
	private ArrayList<LinearLayout> linearlayoutlist;
	private ArrayList<ScaleInput> datalist_scale;
	private ArrayList<CategoryInput> datalist_category;
	private LinearLayout layout;
	private Button okbutton;
	private Button plusbutton;
	private ScrollView scrollview;
	private OnClickListener deletelistener;
	
	private int ID=0;
	private int TYPE=0;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.data_input);
		
		data=(ArrayList<Parcelable>)getIntent().getSerializableExtra("DATA");
		TYPE=getIntent().getIntExtra("TYPE",-1);
		
		linearlayoutlist = new ArrayList<LinearLayout>();
		datalist_scale = new ArrayList<ScaleInput>();
		datalist_category = new ArrayList<CategoryInput>();
		
		layout = (LinearLayout) findViewById(R.id.linearlayout);
		plusbutton = (Button) findViewById(R.id.plusbutton);
		okbutton = (Button) findViewById(R.id.okbutton);
		scrollview = (ScrollView) findViewById(R.id.myscroll);
		
		deletelistener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				layout.removeViewAt(v.getId());
				linearlayoutlist.remove(v.getId());
				for(int i = v.getId(); i < linearlayoutlist.size(); i++){
					((Button)(linearlayoutlist.get(i).getChildAt(1))).setId(i);
				}
				ID--;
			}
		};
		
		for (int i=0;i<data.size();i++)
		{
			LinearLayout templinear = new LinearLayout(SettingDataActivity.this);
			Button tempbutton = new Button(SettingDataActivity.this);
			DataInputLayout templayout = new DataInputLayout(SettingDataActivity.this, TYPE);
			templinear.setOrientation(0);
			templinear.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
			templinear.setGravity(Gravity.CENTER_HORIZONTAL);
			tempbutton.setText("X");
			tempbutton.setId(ID);
			tempbutton.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
			tempbutton.setOnClickListener(deletelistener);
			if (TYPE==0)
				templayout.settingSetScaleData((ScaleInput)data.get(i));
			else
				templayout.settingSetCategoryData((CategoryInput)data.get(i));
			templinear.addView(templayout);
			templinear.addView(tempbutton);
			linearlayoutlist.add(templinear);
			layout.addView(templinear, ID++);
			scrollview.post(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					scrollview.fullScroll(ScrollView.FOCUS_DOWN);
				}
			});
		}
		
		plusbutton.setOnClickListener(new View.OnClickListener() {
			// add layout
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LinearLayout templinear = new LinearLayout(SettingDataActivity.this);
				Button tempbutton = new Button(SettingDataActivity.this);
				DataInputLayout templayout = new DataInputLayout(SettingDataActivity.this, TYPE);
				templinear.setOrientation(0);
				templinear.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
				templinear.setGravity(Gravity.CENTER_HORIZONTAL);
				tempbutton.setText("X");
				tempbutton.setId(ID);
				tempbutton.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
				tempbutton.setOnClickListener(deletelistener);
				templinear.addView(templayout);
				templinear.addView(tempbutton);
				linearlayoutlist.add(templinear);
				layout.addView(templinear, ID++);
				
				scrollview.post(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						scrollview.fullScroll(ScrollView.FOCUS_DOWN);
					}
				});
			}
		});
		
		okbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setOutput();
				finish();
			}
		});
	}
	
	public void setOutput()
	{
		for(int i = 0; i < linearlayoutlist.size(); i++){
			((DataInputLayout)(linearlayoutlist.get(i).getChildAt(0))).setData();
			if(TYPE == 0)
				datalist_scale.add(((DataInputLayout)(linearlayoutlist.get(i).getChildAt(0))).getScaleData());
			else if(TYPE == 1)
				datalist_category.add(((DataInputLayout)(linearlayoutlist.get(i).getChildAt(0))).getCategoryData());
		}
		System.out.println("size : "+linearlayoutlist.size());
		
		Intent outputIntent=new Intent();
		if (TYPE==0)
			outputIntent.putExtra("DATA",datalist_scale);
		else
			outputIntent.putExtra("DATA",datalist_category);
		this.setResult(RESULT_OK,outputIntent);
	}
}
