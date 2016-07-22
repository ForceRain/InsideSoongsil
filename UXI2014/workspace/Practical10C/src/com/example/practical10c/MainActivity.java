package com.example.practical10c;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button butinsert=(Button)findViewById(R.id.eventBtn);
		butinsert.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				insert();
			}
		});
	}
	
	public void insert()
	{
		Intent intent =new Intent(Intent.ACTION_INSERT,CalendarContract.Events.CONTENT_URI);
		intent.putExtra(CalendarContract.Events.TITLE,"CALENDAR CONTACT!");
		intent.putExtra(CalendarContract.Events.DESCRIPTION,"CREATE EVENT IN CALENDAR");
		intent.putExtra(CalendarContract.Events.EVENT_LOCATION,"SOONGSIL UNIVERSITY");
		Calendar startTime=Calendar.getInstance();
		startTime.set(2014,11,11);
		intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,startTime.getTimeInMillis());
		intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY,true);
		startActivity(intent);
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
