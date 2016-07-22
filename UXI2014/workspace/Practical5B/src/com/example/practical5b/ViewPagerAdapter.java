package com.example.practical5b;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewPagerAdapter extends PagerAdapter
{
	int size;
	Activity act;
	View layout;
	TextView pagenumber;
	Button click;
	public ViewPagerAdapter(MainActivity mainActivity, int noofsize)
	{
		size=noofsize;
		act=mainActivity;
	}
	public int getCount()
	{
		return size;
	}
	public Object instantiateItem(View container, int position)
	{
		LayoutInflater inflater=(LayoutInflater)act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout=inflater.inflate(R.layout.pages,null);
		pagenumber=(TextView)layout.findViewById(R.id.pagenumber);
		int pagenumberTxt=position+1;
		pagenumber.setText("Now your in Page No   "+pagenumberTxt);
		((ViewPager)container).addView(layout,0);
		return layout;
	}
	public void destroyItem(View arg0,int arg1, Object arg2)
	{
		((ViewPager)arg0).removeView((View)arg2);
	}
	public boolean isViewFromObject(View arg0, Object arg1)
	{
		return arg0==((View)arg1);
	}
	public Parcelable saveState()
	{
		return null;
	}
}
