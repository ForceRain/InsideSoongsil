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
import com.jw.graker.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class GuidePagerAdapter extends PagerAdapter
{
	int pageSize;
	Activity activity;
	View layout;
	TextView guideTitle;
	ImageView guideImage;
	TextView guideText;
	String guideT[]={
			"Main",
			"Select Graph Type",
			"Title Input",
			"Data Input",
			"Graph",
			"Edit"
			};
	String guide[]={
			"New Graph : make new graph.\nEdit Graph : load the saved data and image.\nExit : quit this application.",
			"You can choose Bar(Scale, Category), Line and Pie chart.",
			"You can give chart title and also X,Y axis name in scale chart.",
			"You can input data pressing + button.",
			"You can do some setting, get description, sharing by SNS and Save the data",
			"After you save, you can modify your data.",
			};
	int guideImg[]={
			R.drawable.guide_main,
			R.drawable.guide_select,
			R.drawable.guide_titleinput,
			R.drawable.guide_datainput,
			R.drawable.guide_graph,
			R.drawable.guide_edit,
			};
	
	public GuidePagerAdapter(GuideActivity guideActivity,int pgSize)
	{
		pageSize=pgSize;
		activity=guideActivity;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pageSize;
	}
	public Object instantiateItem(View container, int position)
	{
		LayoutInflater inflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout=inflater.inflate(R.layout.guide_pager,null);
		guideTitle=(TextView)layout.findViewById(R.id.guideTitle);
		guideImage=(ImageView)layout.findViewById(R.id.guideImage);
		guideImage.setScaleType(ScaleType.FIT_START);
		guideText=(TextView)layout.findViewById(R.id.guideText);
		guideTitle.setText(guideT[position]);
		guideImage.setBackgroundResource(guideImg[position]);
		guideText.setText(guide[position]);
		
		((ViewPager)container).addView(layout,0);
		return layout;
	}
	public void destroyItem(View arg0,int arg1,Object arg2)
	{
		((ViewPager)arg0).removeView((View)arg2);
	}
	
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==((View)arg1);
	}
	
	public Parcelable saveState()
	{
		return null;
	}
}
