package com.example.practical12b;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher.ViewFactory;

public class MainActivity extends Activity {
	private ImageSwitcher imageSwitcher;
	Button btnNext;
	int imageIds[]={R.drawable.ceyap,R.drawable.hdragon,R.drawable.junho};
	int messageCount=imageIds.length;
	int currentIndex=-1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnNext=(Button)findViewById(R.id.buttonNext);
		imageSwitcher=(ImageSwitcher)findViewById(R.id.imageSwitcher1);
		
		imageSwitcher.setFactory(new ViewFactory() {

			@Override
			public View makeView() {
				// TODO Auto-generated method stub
				ImageView imageView=new ImageView(getApplicationContext());
				imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
				imageView.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
				return imageView;
			}
			
		});
		
		Animation in=AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
		Animation out=AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
		//imageSwitcher.setInAnimation(in);
		//imageSwitcher.setInAnimation(out);
		btnNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				currentIndex++;
				if (currentIndex==messageCount)
					currentIndex=0;
				imageSwitcher.setImageResource(imageIds[currentIndex]);
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
