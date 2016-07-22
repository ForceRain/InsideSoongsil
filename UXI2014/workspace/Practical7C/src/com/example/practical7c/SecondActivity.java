package com.example.practical7c;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends Activity
{
	Button btn2;
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		btn2=(Button)findViewById(R.id.secondbtn);
		btn2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentgo=new Intent(SecondActivity.this,FirstActivity.class);
				startActivity(intentgo);
			}
		});
	}
}
