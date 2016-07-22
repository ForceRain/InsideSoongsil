package com.example.practical9b;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends Activity {
	private static final int SETTINGS_RESULT=1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent i=new Intent(getApplicationContext(),settingActivity.class);
		startActivityForResult(i,SETTINGS_RESULT);
	}
	
	public void onActivityResult(int requestCode,int resultCode,Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==SETTINGS_RESULT)
			showAll();
	}

	public void showAll()
	{
		SharedPreferences sharedPrefs=PreferenceManager.getDefaultSharedPreferences(this);
		TextView obj=(TextView)findViewById(R.id.mainshow);
		CheckBox obj1=(CheckBox)findViewById(R.id.checkbox);
		
		String output=sharedPrefs.getString("editTextPref", "DEFAULT");
		boolean output1=sharedPrefs.getBoolean("checkBoxPref",true);
		
		obj.setText(output);
		obj1.setChecked(output1);
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
