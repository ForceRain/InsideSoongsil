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
import java.util.ArrayList;

import javax.xml.transform.Templates;

import com.jw.graker.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class InputControlActivity extends Activity {
	// data input layout control activity
	private static final int AFTER_DRAW_GRAPH = 10;

	private LinearLayout layout;
	private Button plusbutton;
	private Button okbutton;
	private ScrollView scrollview;

	private ArrayList<LinearLayout> linearlayoutlist;
	private ArrayList<ScaleInput> datalist_scale;
	private ArrayList<CategoryInput> datalist_category;
	private ArrayList<ScaleInput> getlist_scale;
	private ArrayList<CategoryInput> getlist_category;

	private int ID = 0; // like layout count
	private int TYPE = 0;
	// get intent in front of activity
	// type of scale = 0
	// type of category = 1
	private String XAXIS;
	private String YAXIS;
	private String TITLE;
	private long graphType;
	private int isEdit;

	private View.OnClickListener deletelistener;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.data_input);

		Intent intent = getIntent();
		TYPE = intent.getIntExtra("TYPE", 0);
		XAXIS = intent.getStringExtra("XAXIS");
		YAXIS = intent.getStringExtra("YAXIS");
		TITLE = intent.getStringExtra("TITLE");
		isEdit = intent.getIntExtra("ISEDIT", -1);

		linearlayoutlist = new ArrayList<LinearLayout>();
		datalist_scale = new ArrayList<ScaleInput>();
		datalist_category = new ArrayList<CategoryInput>();
		getlist_scale = new ArrayList<ScaleInput>();
		getlist_category = new ArrayList<CategoryInput>();
		layout = (LinearLayout) findViewById(R.id.linearlayout);
		plusbutton = (Button) findViewById(R.id.plusbutton);
		okbutton = (Button) findViewById(R.id.okbutton);
		scrollview = (ScrollView) findViewById(R.id.myscroll);
		System.out.println("인풋에서 받고있냐");
		System.out.println("에딧 값 : " + isEdit);
		if (isEdit == 2) {
			okbutton.setText("Edit");
			System.out.println("TYPE : " + TYPE);
			switch (TYPE) {
			case 0:
				BarScaleSQLiteOpenHelper scaleHelper;
				SQLiteDatabase scaleDB;
				scaleHelper = new BarScaleSQLiteOpenHelper(
						InputControlActivity.this, TITLE + ".db", null, 1);
				scaleDB = scaleHelper.getReadableDatabase();
				Cursor scaleC = scaleDB.query("barScale", null, null, null,
						null, null, null);
				scaleC.moveToFirst();
				while (!scaleC.isAfterLast()) {
					ScaleInput scaleTmp = new ScaleInput();
					scaleTmp.setX(scaleC.getFloat(scaleC
							.getColumnIndex("xData")));
					scaleTmp.setY(scaleC.getFloat(scaleC
							.getColumnIndex("yData")));
					getlist_scale.add(scaleTmp);
					scaleC.moveToNext();
				}
				graphType = 0;
				break;
			case 1:
				BarCategorySQLiteOpenHelper categoryHelper;
				SQLiteDatabase categoryDB;
				categoryHelper = new BarCategorySQLiteOpenHelper(
						InputControlActivity.this, TITLE + ".db", null, 1);
				categoryDB = categoryHelper.getReadableDatabase();
				Cursor categoryC = categoryDB.query("barCategory", null, null,
						null, null, null, null);
				categoryC.moveToFirst();
				while (!categoryC.isAfterLast()) {
					CategoryInput categoryTmp = new CategoryInput();
					categoryTmp.setName(categoryC.getString(categoryC
							.getColumnIndex("xData")));
					categoryTmp.setFreq(categoryC.getInt(categoryC
							.getColumnIndex("yData")));
					getlist_category.add(categoryTmp);
					categoryC.moveToNext();
				}
				graphType = 0;
				break;
			case 2:
				LineSQLiteOpenHelper lineHelper;
				SQLiteDatabase lineDB;
				lineHelper = new LineSQLiteOpenHelper(
						InputControlActivity.this, TITLE + ".db", null, 1);
				lineDB = lineHelper.getReadableDatabase();
				Cursor lineC = lineDB.query("line", null, null, null, null,
						null, null);
				lineC.moveToFirst();
				while (!lineC.isAfterLast()) {
					ScaleInput lineTmp = new ScaleInput();
					lineTmp.setX(lineC.getFloat(lineC.getColumnIndex("xData")));
					lineTmp.setY(lineC.getFloat(lineC.getColumnIndex("yData")));
					getlist_scale.add(lineTmp);
					lineC.moveToNext();
				}
				graphType = 1;
				break;
			case 3:
				PieSQLiteOpenHelper pieHelper;
				SQLiteDatabase pieDB;
				pieHelper = new PieSQLiteOpenHelper(InputControlActivity.this,
						TITLE + ".db", null, 1);
				pieDB = pieHelper.getReadableDatabase();
				Cursor pieC = pieDB.query("pie", null, null, null, null, null,
						null);
				pieC.moveToFirst();
				while (!pieC.isAfterLast()) {
					CategoryInput pieTmp = new CategoryInput();
					pieTmp.setName(pieC.getString(pieC.getColumnIndex("name")));
					pieTmp.setFreq(pieC.getInt(pieC.getColumnIndex("value")));
					getlist_category.add(pieTmp);
					pieC.moveToNext();
				}
				graphType = 2;
				break;
			}
			if (TYPE % 2 == 0) {
				for (int i = 0; i < getlist_scale.size(); i++) {
					LinearLayout templinear = new LinearLayout(
							InputControlActivity.this);
					Button tempbutton = new Button(InputControlActivity.this);
					DataInputLayout templayout = new DataInputLayout(
							InputControlActivity.this, TYPE);
					templinear.setOrientation(0);
					templinear.setLayoutParams(new LayoutParams(
							ViewGroup.LayoutParams.FILL_PARENT,
							ViewGroup.LayoutParams.FILL_PARENT));
					templinear.setGravity(Gravity.CENTER_HORIZONTAL);
					templinear.setWeightSum(10f);

					tempbutton.setText("X");
					tempbutton.setId(ID);
					tempbutton.setTextColor(Color.WHITE);
					tempbutton.setGravity(Gravity.CENTER_HORIZONTAL
							| Gravity.CENTER_VERTICAL);
					tempbutton.setLayoutParams(new LayoutParams(0,
							ViewGroup.LayoutParams.FILL_PARENT, 2f));
					tempbutton.setOnClickListener(deletelistener);

					templayout.settingSetScaleData(getlist_scale.get(i));

					templinear.addView(templayout);
					templinear.addView(tempbutton);
					linearlayoutlist.add(templinear);
					layout.addView(templinear, ID++);

					scrollview.post(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							scrollview.fullScroll(ScrollView.FOCUS_DOWN);
						}
					});
				}
			} else {
				for (int i = 0; i < getlist_category.size(); i++) {
					LinearLayout templinear = new LinearLayout(
							InputControlActivity.this);
					Button tempbutton = new Button(InputControlActivity.this);
					DataInputLayout templayout = new DataInputLayout(
							InputControlActivity.this, TYPE);
					templinear.setOrientation(0);
					templinear.setLayoutParams(new LayoutParams(
							ViewGroup.LayoutParams.FILL_PARENT,
							ViewGroup.LayoutParams.FILL_PARENT));
					templinear.setGravity(Gravity.CENTER_HORIZONTAL);
					templinear.setWeightSum(10f);

					tempbutton.setText("X");
					tempbutton.setId(ID);
					tempbutton.setTextColor(Color.WHITE);
					tempbutton.setGravity(Gravity.CENTER_HORIZONTAL
							| Gravity.CENTER_VERTICAL);
					tempbutton.setLayoutParams(new LayoutParams(0,
							ViewGroup.LayoutParams.FILL_PARENT, 2f));
					tempbutton.setOnClickListener(deletelistener);

					templayout.settingSetCategoryData(getlist_category.get(i));

					templinear.addView(templayout);
					templinear.addView(tempbutton);
					linearlayoutlist.add(templinear);
					layout.addView(templinear, ID++);

					scrollview.post(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							scrollview.fullScroll(ScrollView.FOCUS_DOWN);
						}
					});
				}
			}
		} else {
			graphType = checkGraphType(intent.getStringExtra("graphType"));
			okbutton.setText("OK");
		}

		deletelistener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				layout.removeViewAt(v.getId());
				linearlayoutlist.remove(v.getId());
				for (int i = v.getId(); i < linearlayoutlist.size(); i++) {
					((Button) (linearlayoutlist.get(i).getChildAt(1))).setId(i);
				}
				ID--;
			}
		};

		plusbutton.setOnClickListener(new View.OnClickListener() {
			// add layout
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				LinearLayout templinear = new LinearLayout(
						InputControlActivity.this);
				Button tempbutton = new Button(InputControlActivity.this);
				DataInputLayout templayout = new DataInputLayout(
						InputControlActivity.this, TYPE);
				templinear.setOrientation(0);
				templinear.setLayoutParams(new LayoutParams(
						ViewGroup.LayoutParams.FILL_PARENT,
						ViewGroup.LayoutParams.FILL_PARENT));
				templinear.setGravity(Gravity.CENTER_HORIZONTAL);
				templinear.setWeightSum(10f);

				tempbutton.setText("X");
				tempbutton.setId(ID);
				tempbutton.setTextColor(Color.WHITE);
				tempbutton.setGravity(Gravity.CENTER_HORIZONTAL
						| Gravity.CENTER_VERTICAL);
				tempbutton.setLayoutParams(new LayoutParams(0,
						ViewGroup.LayoutParams.FILL_PARENT, 2f));
				tempbutton.setOnClickListener(deletelistener);

				templinear.addView(templayout);
				templinear.addView(tempbutton);
				linearlayoutlist.add(templinear);
				layout.addView(templinear, ID++);

				scrollview.post(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						scrollview.fullScroll(ScrollView.FOCUS_DOWN);
					}
				});
			}
		});

		okbutton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (linearlayoutlist.size() == 0) {
					Toast.makeText(InputControlActivity.this, "No Data",
							Toast.LENGTH_LONG).show();
				} else {
					for (int i = 0; i < linearlayoutlist.size(); i++) {
						((DataInputLayout) (linearlayoutlist.get(i)
								.getChildAt(0))).setData();
						if (TYPE == 0)
							datalist_scale
									.add(((DataInputLayout) (linearlayoutlist
											.get(i).getChildAt(0)))
											.getScaleData());

						else if (TYPE == 1)
							datalist_category
									.add(((DataInputLayout) (linearlayoutlist
											.get(i).getChildAt(0)))
											.getCategoryData());
					}
					if ((graphType == 0) && (TYPE == 0)) {
						Intent moveIntent = new Intent(
								InputControlActivity.this,
								BarChartScaleBuilder.class);
						Bundle B = new Bundle();
						B.putSerializable("DATA", datalist_scale);
						B.putString("XAXIS", XAXIS);
						B.putString("YAXIS", YAXIS);
						B.putInt("TYPE", TYPE);
						B.putString("TITLE", TITLE);
						System.out.println("bar_scale!");
						moveIntent.putExtras(B);
						startActivityForResult(moveIntent, AFTER_DRAW_GRAPH);
					} else if ((graphType == 0) && (TYPE == 1)) {
						Intent moveIntent = new Intent(
								InputControlActivity.this,
								BarChartCategoryBuilder.class);
						Bundle B = new Bundle();
						B.putSerializable("DATA", datalist_category);
						B.putInt("TYPE", TYPE);
						B.putString("TITLE", TITLE);
						System.out.println("bar_category!");
						moveIntent.putExtras(B);
						startActivityForResult(moveIntent, AFTER_DRAW_GRAPH);
					} else if (graphType == 1) {
						Intent moveIntent = new Intent(
								InputControlActivity.this,
								LineChartBuilder.class);
						Bundle B = new Bundle();
						B.putSerializable("DATA", datalist_scale);
						B.putString("XAXIS", XAXIS);
						B.putString("YAXIS", YAXIS);
						System.out.println("line!");
						B.putInt("TYPE", TYPE);
						B.putString("TITLE", TITLE);
						moveIntent.putExtras(B);
						startActivityForResult(moveIntent, AFTER_DRAW_GRAPH);
					} else {
						Intent moveIntent = new Intent(
								InputControlActivity.this,
								PieChartBuilder.class);
						Bundle B = new Bundle();
						B.putSerializable("DATA", datalist_category);
						System.out.println("pie!");
						B.putInt("TYPE", TYPE);
						B.putString("TITLE", TITLE);
						moveIntent.putExtras(B);
						startActivityForResult(moveIntent, AFTER_DRAW_GRAPH);
					}
					finish();
				}
			}
		});
	}

	public long checkGraphType(String type) {
		if (type.equals("bar"))
			return 0;
		else if (type.equals("line"))
			return 1;
		else
			return 2;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == AFTER_DRAW_GRAPH) {
			datalist_scale = new ArrayList<ScaleInput>();
			datalist_category = new ArrayList<CategoryInput>();
		}
	}
}
