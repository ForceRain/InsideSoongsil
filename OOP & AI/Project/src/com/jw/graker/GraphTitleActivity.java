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
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GraphTitleActivity extends Activity{
	Button barBT, lineBT, pieBT;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graph_menu);
		barBT = (Button) findViewById(R.id.menuBarBT);
		lineBT = (Button)findViewById(R.id.menuLineBT);
		pieBT = (Button)findViewById(R.id.menuPieBT);
		
		barBT.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(GraphTitleActivity.this, BarTitleActivitiy.class);
				startActivity(intent);
			}
		});
		
		lineBT.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(GraphTitleActivity.this, LineTitleActivity.class);
				startActivity(intent);
			}
		});
		
		pieBT.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent =new Intent(GraphTitleActivity.this, PieTitleActivity.class);
				startActivity(intent);
			}
		});
	}
}
