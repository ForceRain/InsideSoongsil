package com.example.practical6a;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	Button dfragbutton;
	Button alertdfragbutton;
	FragmentManager fm=getFragmentManager();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		dfragbutton=(Button)findViewById(R.id.dfragbutton);
		alertdfragbutton=(Button)findViewById(R.id.alertdfragbutton);
		
		dfragbutton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				DFragment dFragment=new DFragment();
				dFragment.show(fm,"Dialog Fragment");
			}
		});
		
		alertdfragbutton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				AlertDFragment alertdFragment=new AlertDFragment();
				alertdFragment.show(fm,"Alert Dialog Fragment");
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
