package com.example.practical7d;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FirstActivity extends Activity {
	Button btn;
	EditText getId;
	EditText getPass;
	String user="CEYAP";
	String pass="dev&2014";			
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first);
		getId=(EditText)findViewById(R.id.input_id);
		getPass=(EditText)findViewById(R.id.input_pass);
		btn=(Button)findViewById(R.id.confirm);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String Id=getId.getText().toString();
				String Pass=getPass.getText().toString();
				
				if ((Id.equals(user)) && (Pass.equals(pass)))
				{
					Intent intentgo=new Intent(FirstActivity.this,SecondActivity.class);
					startActivity(intentgo);
				}
				else
				{
					Intent intentgo=new Intent(FirstActivity.this,ThirdActivity.class);
					startActivity(intentgo);
				}
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.first, menu);
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
