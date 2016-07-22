package com.example.practical8a;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class activity2 extends Activity
{
	TextView txt;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity2);
		txt=(TextView)findViewById(R.id.txt);
		txt.setTextSize(30);
		txt.setText("This is example of Pending Intent");
	}
}
