package com.jw.graker;
/*
 * Copyright 2014 Graker

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
   */
import com.jw.graker.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	Button newBT, editBT, exitBT, guideBT;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		newBT = (Button)findViewById(R.id.mainNewBT);
		editBT = (Button)findViewById(R.id.mainEditBT);
		guideBT = (Button)findViewById(R.id.mainGuideBT);
		exitBT = (Button)findViewById(R.id.mainExitBT);
		
		newBT.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent menu = new Intent(MainActivity.this, GraphTitleActivity.class);
				startActivity(menu);
			}
		});
		editBT.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				GraphSQLiteOpenHelper graphHelper;
				SQLiteDatabase graphDB;
				graphHelper = new GraphSQLiteOpenHelper(MainActivity.this, "graph.db", null, 1);
				graphDB = graphHelper.getReadableDatabase();
				Cursor c = graphDB.query("graph",null,null,null,null,null,null);
				c.moveToFirst();
				if(!c.isAfterLast()){
					Intent edit = new Intent(MainActivity.this,EditMainActivity.class);
					startActivity(edit);
				}
				else
				{
					AlertDialog.Builder aDialog=new AlertDialog.Builder(v.getContext());
					aDialog.setTitle("Warning");
					aDialog.setMessage("There is no saved data.");
					aDialog.setPositiveButton("Confirm",new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
								Toast.makeText(getApplicationContext(),"Please save AT LEAST one data.",Toast.LENGTH_LONG).show();
						}
					});
					aDialog.create().show();
				}
				graphDB.close();
				graphHelper.close();
			}
		});
		guideBT.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent guideIntent=new Intent(MainActivity.this,GuideActivity.class);
				startActivity(guideIntent);
			}
		});
		
		exitBT.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


}
