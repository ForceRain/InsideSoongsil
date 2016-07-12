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
import com.jw.graker.ColorPickerDialog.OnColorSelectedListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class SettingColorActivity extends ListActivity
{
	private ListView mListView=null;
	private ListViewAdapter mAdapter=null;
	private ArrayList<ScaleInput> scaleData;
	private ArrayList<CategoryInput> categoryData;
	private ArrayList<Integer> colorSet;
	private int dataType;
	private SettingColorListData mData;
	private int pos;
	
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_color);
		
		mListView=(ListView)findViewById(android.R.id.list);
		
		mAdapter=new ListViewAdapter(this);
		mListView.setAdapter(mAdapter);
		
		dataType=getIntent().getIntExtra("TYPE",-1);
		
		if (dataType==1)
		{
			categoryData=(ArrayList<CategoryInput>)getIntent().getSerializableExtra("DATA");
		}
		else
		{
			scaleData=(ArrayList<ScaleInput>)getIntent().getSerializableExtra("DATA");
		}
		
		colorSet=(ArrayList<Integer>)getIntent().getSerializableExtra("COLORSET");	
		
		if (dataType==1)
		{
			mAdapter.addItem(colorSet.get(0),"Background","Click here to change background color");
			for (int i=1;i<colorSet.size();i++)
			{
				mAdapter.addItem(colorSet.get(i),"Class Name :"+categoryData.get(i-1).getName(),"Frequency : "+categoryData.get(i-1).getFreq());
			}
		}
		else
		{
			mAdapter.addItem(colorSet.get(0),"Background","Click here to change background color");
			mAdapter.addItem(colorSet.get(1),"Graph Color","Click here to change color");
		}
		
		mListView.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> parent, View v, int position, long id)
			{
				System.out.println(position);
				pos=position;
				mData=mAdapter.mListData.get(position);
				int initialColor=mData.mColor;
				
				ColorPickerDialog colorPickerDialog=new ColorPickerDialog(v.getContext(),initialColor,new OnColorSelectedListener()
				{
					public void onColorSelected(int color)
					{
						changeColor(color);	
						mAdapter.mListData.set(pos,mData);
						refreshSelection();
						if (dataType==1)
						{
							Toast.makeText(getApplicationContext(),mAdapter.mListData.get(pos).mTitle+" color changed!",Toast.LENGTH_SHORT).show();
						}
						else
						{
							Toast.makeText(getApplicationContext(),"Graph color changed!",Toast.LENGTH_SHORT).show();
						}
					}
				});
				colorPickerDialog.show();
			}
		}
		);
	}
	
	public void refreshSelection()
	{
		this.setListAdapter(mAdapter);
	}
	
	public void changeColor(int color)
	{
		mData.mColor=color;
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
	 switch(keyCode)
	 {
		case KeyEvent.KEYCODE_BACK :
			result();
		  	return false;
		default :
			return false;
	  }
	}
	
	public void changeToList()
	{
		colorSet=new ArrayList<Integer>();
		
		for (int i=0;i<mAdapter.getCount();i++)
		{
			colorSet.add(mAdapter.mListData.get(i).mColor);
		}
	}
	
	public void result()
	{
		changeToList();
		Bundle B=new Bundle();
		Intent resultIntent=new Intent();
		B.putSerializable("COLORSET",colorSet);
		resultIntent.putExtras(B);
		this.setResult(RESULT_OK,resultIntent);
		finish();
	}
	
	public class ViewHolder
	{
		public ImageView mFrame;
		public int mColor;
		public TextView mTitle;
		public TextView mSummary;
	}
	
	private class ListViewAdapter extends BaseAdapter
	{
		private Context mContext=null;
		private ArrayList<SettingColorListData> mListData=new ArrayList<SettingColorListData>();
		public ListViewAdapter(Context mContext)
		{
			super();
			this.mContext=mContext;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mListData.size();
		}
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mListData.get(position);
		}
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) 
		{
			// TODO Auto-generated method stub
			ViewHolder holder;
			if (convertView==null)
			{
				holder=new ViewHolder();
				
				LayoutInflater inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView=inflater.inflate(R.layout.color_list,null);
				
				holder.mFrame=(ImageView)convertView.findViewById(R.id.settingColor);
				holder.mTitle=(TextView)convertView.findViewById(R.id.settingColorTitle);
				holder.mSummary=(TextView)convertView.findViewById(R.id.settingColorDescription);
				
				convertView.setTag(holder);
			}
			else
			{
				holder=(ViewHolder)convertView.getTag();
			}
			SettingColorListData mData=mListData.get(position);
			
			holder.mColor=mData.mColor;
			holder.mFrame.setBackgroundColor(mData.mColor);
			holder.mTitle.setText(mData.mTitle);
			holder.mSummary.setText(mData.mSummary);
			
			return convertView;
		}
		
		public void addItem(int color, String title, String summary)
		{
			SettingColorListData addInfo=null;
			addInfo=new SettingColorListData();
			
			addInfo.mColor=color;
			addInfo.mTitle=title;
			addInfo.mSummary=summary;
			
			mListData.add(addInfo);
		}
	}
}
