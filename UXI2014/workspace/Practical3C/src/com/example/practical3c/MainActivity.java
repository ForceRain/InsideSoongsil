package com.example.practical3c;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	Button mButton;
	EditText mEdit;
	TextView mText;
	boolean isCel=true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		RadioGroup group=(RadioGroup)findViewById(R.id.orientation);
		mButton=(Button)findViewById(R.id.show_button);
		
		mButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mEdit=(EditText)findViewById(R.id.input1);
				mText=(TextView)findViewById(R.id.textView1);
				
				if (isCel)
					mText.setText(""+convertFahrenheitToCelsius(Float.parseFloat(mEdit.getText().toString())));
				else
					mText.setText(""+convertCelsiusToFahrenheit(Float.parseFloat(mEdit.getText().toString())));
			}
		});
		
		group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
			public void onCheckedChanged(RadioGroup groups,int checkedId)
			{
				switch(checkedId)
				{
				case R.id.cel:
					isCel=true;
					break;
				case R.id.fah:
					isCel=false;
					break;
				}
			}
		}
			);
	}
	
	public static float convertFahrenheitToCelsius(float fahrenheit) {
	    return ((fahrenheit - 32) * 5 / 9);
	  }
	public static float convertCelsiusToFahrenheit(float celsius) {
	    return ((celsius * 9) / 5) + 32;
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
