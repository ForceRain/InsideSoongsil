package com.example.practical6e;

import android.app.Activity;
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

public class MainActivity extends Activity {
	int mDate=7;
	int mMonth=3;
	int mYear=2014;
	
	Handler mHandler=new Handler()
	{
		public void handleMessage(Message m)
		{
			Bundle b=m.getData();
			mDate=b.getInt("set_date");
			mMonth=b.getInt("set_month");
			mYear=b.getInt("set_year");
			
			Toast.makeText(getBaseContext(),b.getString("date"), Toast.LENGTH_SHORT).show();
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button btn=(Button)findViewById(R.id.btn);
		OnClickListener listener=new OnClickListener()
		{
			public void onClick(View v)
			{
				Bundle b=new Bundle();
				b.putInt("set_date", mDate);
				b.putInt("set_month",mMonth);
				b.putInt("set_year",mYear);
				
				DatePickerFragment DPF=new DatePickerFragment(mHandler);
				
				DPF.setArguments(b);
				FragmentManager fm=getFragmentManager();
				FragmentTransaction ft=fm.beginTransaction();
				
				ft.add(DPF,"date_picker");
				ft.commit();
			}
		};
		
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
