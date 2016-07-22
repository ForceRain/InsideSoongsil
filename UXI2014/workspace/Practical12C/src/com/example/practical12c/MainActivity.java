package com.example.practical12c;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		super.onOptionsItemSelected(item);
		switch (item.getItemId())
		{
		case R.id.zoomInOut :
			Button view=(Button)findViewById(R.id.button1);
			Animation ani=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zooming);
			view.startAnimation(ani);
			return true;
		case R.id.rotate360 :
			Button view1=(Button)findViewById(R.id.button1);
			Animation ani1=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.clockwise);
			view1.startAnimation(ani1);
			return true;
		case R.id.fadeInOut :
			Button view2=(Button)findViewById(R.id.button1);
			Animation ani2=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fading);
			view2.startAnimation(ani2);
			return true;
		}
		return false;
	}
}
