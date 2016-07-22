package com.example.practical6d;

import android.support.v7.app.ActionBarActivity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	Button btn;
	int mHour=15;
	int mMinute=15;
	MainActivity act;
	Handler mHandler=new Handler(){
		public void handleMessage(Message m)
		{
			Bundle b=m.getData();
			mHour=b.getInt("set_hour");
			mMinute=b.getInt("set_minute");
			
			Toast.makeText(getBaseContext(),b.getString("set_time").toString(),Toast.LENGTH_SHORT).show();
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		act=this;
		
		OnClickListener listener=new OnClickListener()
		{
			public void onClick(View v)
			{
				Bundle b=new Bundle();
				b.putInt("set_hour",mHour);
				b.putInt("set_minute",mMinute);
				
				TimerDialogFragment timePicker=new TimerDialogFragment(mHandler);
				timePicker.setArguments(b);
				
				FragmentManager fm=getFragmentManager();				
				FragmentTransaction ft=fm.beginTransaction();
				
				ft.add(timePicker,"time_picker");
				ft.commit();
			}
		};		
		btn=(Button)findViewById(R.id.btn);
		
		btn.setOnClickListener(listener);
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
