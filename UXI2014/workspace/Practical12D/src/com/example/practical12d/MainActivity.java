package com.example.practical12d;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

public class MainActivity extends Activity {
	private TextSwitcher mSwitcher;
	String textToShow[]={"CEYAP","H-Dragon","JunHo"};
	int txtMessageCount=textToShow.length;
	int txtCurrentIdx=-1;
	
	private ImageSwitcher imageSwitcher;
	int imageIds[]={R.drawable.ceyap,R.drawable.hdragon,R.drawable.junho};
	int imgMessageCount=imageIds.length;
	int imgCurrentIdx=-1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mSwitcher=(TextSwitcher)findViewById(R.id.textSwitcher1);
		mSwitcher.setFactory(new ViewFactory()
		{
			@Override
			public View makeView() {
				// TODO Auto-generated method stub
				TextView myText=new TextView(MainActivity.this);
				myText.setGravity(Gravity.TOP|Gravity.CENTER);
				myText.setTextSize(30);
				myText.setTextColor(Color.RED);
				return myText;
			}
		});
		Animation in=AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left);
		Animation out=AnimationUtils.loadAnimation(this,android.R.anim.slide_out_right);
		mSwitcher.setInAnimation(in);
		mSwitcher.setOutAnimation(out);
		
		imageSwitcher=(ImageSwitcher)findViewById(R.id.imageSwitcher1);
		imageSwitcher.setFactory(new ViewFactory()
		{
			@Override
			public View makeView() {
				// TODO Auto-generated method stub
				ImageView imageView=new ImageView(getApplicationContext());
				imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
				imageView.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
				
				return imageView;
			}
		});
		Animation in1=AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left);
		Animation out1=AnimationUtils.loadAnimation(this,android.R.anim.slide_out_right);
		imageSwitcher.setInAnimation(in1);
		imageSwitcher.setOutAnimation(out1);
				
		move obj=new move(txtMessageCount,imgMessageCount);
		obj.start();
	}
	
	class move extends Thread
	{
		private int txtMessageCount;
		private int imgMessageCount;
		private int txtCurrentIdx=-1;
		private int imgCurrentIdx=-1;
		Handler handler=new Handler();
		
		public move(int a,int b)
		{
			txtMessageCount=a;
			imgMessageCount=b;
		}
		public void run() {
			super.run();
			// TODO Auto-generated method stub
			while (true)
			{
				try {
					Thread.sleep(1000);
					handler.post(new Runnable()
					{
						@Override
						public void run() {
							// TODO Auto-generated method stub
							txtCurrentIdx++;
							if (txtCurrentIdx==txtMessageCount)
							{
								txtCurrentIdx=0;
							}
							mSwitcher.setText(textToShow[txtCurrentIdx]);
							
							imgCurrentIdx++;
							if (imgCurrentIdx==imgMessageCount)
							{
								imgCurrentIdx=0;
							}
							imageSwitcher.setImageResource(imageIds[imgCurrentIdx]);
						}
					});
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
		}
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
