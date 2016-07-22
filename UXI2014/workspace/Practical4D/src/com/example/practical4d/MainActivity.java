package com.example.practical4d;

import java.util.ArrayList;
import java.util.Arrays;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private ListView mainListView;
	//private ArrayAdapter<String> listAdapter;
	private MainActivity tmp;
	ArrayAdapter<String> listAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
			
		ArrayList<String> daylist=new ArrayList<String>();
		tmp=this;
		
		daylist.add("Sunday");
		daylist.add("Monday");
		daylist.add("Tuesday");
		daylist.add("Wednesday");
		daylist.add("Thursday");
		daylist.add("Friday");
		daylist.add("Saturday");
		
		listAdapter=new ArrayAdapter<String>(this,R.layout.simplerow,daylist);
		listAdapter.add("BusyDay");
		listAdapter.add("RainingDay");
		listAdapter.add("SunnyDay");
		
		mainListView=(ListView)findViewById(android.R.id.list);
		
		mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int i,long l) {
				// TODO Auto-generated method stub
				Toast.makeText(tmp,listAdapter.getItem(i), Toast.LENGTH_LONG).show();
			}
		});
		mainListView.setAdapter(listAdapter);
	}
	/*
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
	*/
}
