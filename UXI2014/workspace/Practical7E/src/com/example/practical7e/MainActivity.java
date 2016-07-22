package com.example.practical7e;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.provider.MediaStore;

public class MainActivity extends Activity {
	Button photoBtn;
	Button callBtn;
	Button browserBtn;
	Button dialBtn;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		photoBtn=(Button)findViewById(R.id.photo);
		photoBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentgo=new Intent("android.media.action.IMAGE_CAPTURE");
				startActivity(intentgo);
			}
		});
		
		callBtn=(Button)findViewById(R.id.call);
		callBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentgo=new Intent(Intent.ACTION_CALL);
				intentgo.setData(Uri.parse("tel:028287171"));
				startActivity(intentgo);
			}
		});
		
		browserBtn=(Button)findViewById(R.id.browser);
		browserBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentgo=new Intent(android.content.Intent.ACTION_VIEW,Uri.parse("http://www.google.com"));
				startActivity(intentgo);
			}
		});
		
		dialBtn=(Button)findViewById(R.id.dial);
		dialBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentgo=new Intent(android.content.Intent.ACTION_DIAL,Uri.parse("tel:028287171"));
				startActivity(intentgo);
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
