package com.example.practical9a;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	private static final int SETTINGS_RESULT=1;
	Button settingButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		settingButton=(Button)findViewById(R.id.setting);
		settingButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),settingActivity.class);
				startActivityForResult(i,SETTINGS_RESULT);
			}
		});
	}

	public void onActivityResult(int requestCode,int resultCode,Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==SETTINGS_RESULT)
		{
			displaySettings();
		}
	}
	
	private void displaySettings()
	{
		SharedPreferences sharedPrefs=PreferenceManager.getDefaultSharedPreferences(this);
		String settings="";
		settings+="Password :"+sharedPrefs.getString("prefUserPassword","NOPASSWORD");
		settings+="\nRemind For Update :"+sharedPrefs.getBoolean("prefLockScreen", false);
		settings+="\nUpdate Frequency :"+sharedPrefs.getString("prefUpdateFrequency","NOUPDATE");
		TextView textViewString=(TextView)findViewById(R.id.details);
		textViewString.setText(settings);
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
