package com.example.practical12a;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

public class MainActivity extends Activity {

	private TextSwitcher mSwitcher;
	Button btnAnimate;
	String textToShow[]={"UI and Practice","Java","MFC","Soongsil University"};
	int messageCount=textToShow.length;
	int currentIndex=-1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnAnimate=(Button)findViewById(R.id.button1);
		mSwitcher=(TextSwitcher)findViewById(R.id.textSwitcher1);
		
		mSwitcher.setFactory(new ViewFactory()
		{
			public View makeView()
			{
				TextView myText=new TextView(MainActivity.this);
				myText.setGravity(Gravity.TOP|Gravity.CENTER);
				myText.setTextSize(30);
				myText.setTextColor(Color.RED);
				return myText;
			}
		});
		Animation in=AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
		Animation out=AnimationUtils.loadAnimation(this,android.R.anim.slide_out_right);
		btnAnimate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				currentIndex++;
				if (currentIndex==messageCount)
					currentIndex=0;
				mSwitcher.setText(textToShow[currentIndex]);
			}
		});
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
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
