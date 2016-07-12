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
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

public class GuideActivity extends Activity
{
	int guidePageNumber=6;
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide_activity);
		
		GuidePagerAdapter myAdapter=new GuidePagerAdapter(GuideActivity.this,guidePageNumber);
		ViewPager myPager=(ViewPager)findViewById(R.id.guidePager);
		
		myPager.setAdapter(myAdapter);
		myPager.setCurrentItem(0);
	}
	
	public boolean onCreateOptionMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id=item.getItemId();
		return super.onOptionsItemSelected(item);
	}
}
