package com.example.homework;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowFrag extends Fragment
{
	private TextView mText;
	private ImageView mImage;
	public View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{		
		View view=inflater.inflate(R.layout.show_fragment,container,false);
		
		return view;
	}
	
	public void change(String name)
	{
		mText=(TextView)getView().findViewById(R.id.textView1);
		mImage=(ImageView)getView().findViewById(R.id.imageView1);
		mText.setText(name);
		mImage.setBackgroundResource(R.drawable.ic_launcher);
	}
}
