package com.example.practical11c;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends Activity 
{
	private CountryOperations countryDBoperation;
	RadioGroup rGroup;
	EditText input;
	TextView result;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		countryDBoperation=new CountryOperations(this);
		countryDBoperation.open();
		
		rGroup=(RadioGroup)findViewById(R.id.countrygroup);
		
		Button insert=(Button)findViewById(R.id.insert);
		Button checkTotal=(Button)findViewById(R.id.checktotal);
		input=(EditText)findViewById(R.id.input);
		result=(TextView)findViewById(R.id.result);
		
		insert.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int num=0;
				switch (rGroup.getCheckedRadioButtonId())
				{
				case R.id.newzealand :
				{
					num=Integer.parseInt(input.getText().toString());
					countryDBoperation.addApple("NewZealand",num);
					break;
				}
				case R.id.korea :
				{
					num=Integer.parseInt(input.getText().toString());
					countryDBoperation.addApple("Korea",num);
					break;
				}
				case R.id.japan :
				{
					num=Integer.parseInt(input.getText().toString());
					countryDBoperation.addApple("Japan",num);
					break;
				}
				default :
				{
					break;
				}
				}
				input.setText("");
				result.setText("num : "+num+" Recorded Successfully.");
			}
		});
		
		checkTotal.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String output="";
				output="New Zealand : "+countryDBoperation.getAppleNum("NewZealand")+
						"\nKorea : "+countryDBoperation.getAppleNum("Korea")+
						"\nJapan : "+countryDBoperation.getAppleNum("Japan");
				
				result.setText(output);
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
	
	protected void onResume()
	{
		countryDBoperation.open();
		super.onResume();
	}
	
	protected void onPause()
	{
		countryDBoperation.close();
		super.onPause();
	}
}
