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
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import com.jw.graker.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class EditMainActivity extends Activity {
	private ListView graphListView;
	private ArrayAdapter<String> adapter;
	private ArrayList<String> titleList = new ArrayList<String>();
	private SQLiteDatabase db;
	private GraphSQLiteOpenHelper helper;
	private ImageView graph;
	private String[] list, editList;
	private String save;
	private ImageButton editTitleBT, editDataBT, deleteBT;
	final static int CustomDialog = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_main);

		helper = new GraphSQLiteOpenHelper(EditMainActivity.this, "graph.db",
				null, 1);
		db = helper.getReadableDatabase();
		Cursor c = db.query("graph", null, null, null, null, null, null);
		c.moveToFirst();
		while (!c.isAfterLast()) {
			titleList.add(c.getString(c.getColumnIndex("title")));
			Log.i("db", "title : " + c.getString(c.getColumnIndex("title"))
					+ ", xAxis : " + c.getString(c.getColumnIndex("xAxis"))
					+ ", yAxis : " + c.getString(c.getColumnIndex("yAxis")));
			c.moveToNext();
		}
		db.close();
		list = titleList.toArray(new String[titleList.size()]);
		adapter = new ArrayAdapter<String>(this, R.layout.simplerow, list);
		graphListView = (ListView) findViewById(R.id.listView1);
		graphListView.setAdapter(adapter);
		graphListView.setChoiceMode(graphListView.CHOICE_MODE_SINGLE);
		graphListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub]
				save = (String) adapter.getItem(position);
				change(save);
			}

		});
		editTitleBT = (ImageButton) findViewById(R.id.editTitleBT);
		editTitleBT.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				db = helper.getReadableDatabase();
				Cursor cs = db.query("graph", null, null, null, null, null,
						null);
				cs.moveToFirst();
				while (!cs.isAfterLast()) {
					String title = cs.getString(cs.getColumnIndex("title"));
					if (title.equals(save)) {
						int type = cs.getInt(cs.getColumnIndex("type"));
						String xAxis = cs.getString(cs.getColumnIndex("xAxis"));
						String yAxis = cs.getString(cs.getColumnIndex("yAxis"));
						Intent intent;
						switch (type) // type 0:barScale, 1:barCategory, 2:line,
										// 3:pie
						{
						case 0:
							intent = new Intent(EditMainActivity.this,
									BarTitleActivitiy.class);
							intent.putExtra("GETTITLE", title);
							intent.putExtra("GETXAXIS", xAxis);
							intent.putExtra("GETYAXIS", yAxis);
							intent.putExtra("GETTYPE", 0);
							intent.putExtra("ISEDIT", 1);
							startActivity(intent);
							break;
						case 1:
							intent = new Intent(EditMainActivity.this,
									BarTitleActivitiy.class);
							intent.putExtra("GETTITLE", title);
							intent.putExtra("GETXAXIS", xAxis);
							intent.putExtra("GETTYPE", 1);
							intent.putExtra("ISEDIT", 1);
							startActivity(intent);
							break;
						case 2:
							intent = new Intent(EditMainActivity.this,
									LineTitleActivity.class);
							intent.putExtra("GETTITLE", title);
							intent.putExtra("GETXAXIS", xAxis);
							intent.putExtra("GETYAXIS", yAxis);
							intent.putExtra("ISEDIT", 1);
							startActivity(intent);
							break;
						case 3:
							intent = new Intent(EditMainActivity.this,
									PieTitleActivity.class);
							intent.putExtra("GETTITLE", title);
							intent.putExtra("ISEDIT", 1);
							startActivity(intent);
							break;
						}
						editListView();
						break;
					}
					cs.moveToNext();
				}
				db.close();
			}
		});
		editDataBT = (ImageButton) findViewById(R.id.editDataBT);
		editDataBT.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int type = -1;
				String xAxisTmp = null, yAxisTmp = null;
				db = helper.getReadableDatabase();
				Cursor cd = db.query("graph", null, null, null, null, null,
						null);
				cd.moveToFirst();
				while (!cd.isAfterLast()) {
					String title = cd.getString(cd.getColumnIndex("title"));
					if (title.equals(save)) {
						type = cd.getInt(cd.getColumnIndex("type"));
						xAxisTmp = cd.getString(cd.getColumnIndex("xAxis"));
						yAxisTmp = cd.getString(cd.getColumnIndex("yAxis"));
						break;
					}
					cd.moveToNext();
				}
				System.out.println("인풋으로 가고있냐");
				Intent intent = new Intent(EditMainActivity.this,
						InputControlActivity.class);
				intent.putExtra("TYPE", type);
				intent.putExtra("TITLE", save);
				intent.putExtra("XAXIS", xAxisTmp);
				intent.putExtra("YAXIS", yAxisTmp);
				intent.putExtra("ISEDIT", 2);
				startActivity(intent);
				editListView();
			}
		});
		deleteBT = (ImageButton) findViewById(R.id.editDeleteBT);
		deleteBT.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(CustomDialog);
			}
		});
	}

	// @SuppressLint("SdCardPath")
	@SuppressLint("NewApi")
	public void change(String txt) {
		for (int i = 0; i < titleList.size(); i++) {
			if (txt.equals(list[i])) {
				try {
					String path = "/sdcard/" + txt + ".png";
					FileInputStream load = new FileInputStream(path);
					Bitmap bit = BitmapFactory.decodeStream(load);
					System.out.println(bit);
					FragmentImage fragImage = (FragmentImage) getFragmentManager()
							.findFragmentById(R.id.imageFragment);
					fragImage.setImage(txt, bit);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void editListView() {
		ArrayList<String> editTitleList = new ArrayList<String>();
		db = helper.getReadableDatabase();
		Cursor editCursor = db.query("graph", null, null, null, null, null,
				null);
		editCursor.moveToFirst();
		while (!editCursor.isAfterLast()) {
			editTitleList.add(editCursor.getString(editCursor
					.getColumnIndex("title")));
			editCursor.moveToNext();
		}
		db.close();
		editList = editTitleList.toArray(new String[editTitleList.size()]);
		adapter = new ArrayAdapter<String>(this, R.layout.simplerow, editList);
		graphListView.setAdapter(adapter);
		graphListView.setChoiceMode(graphListView.CHOICE_MODE_SINGLE);
	}

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case CustomDialog:
			LayoutInflater inflater = getLayoutInflater();
			final View customView = inflater.inflate(
					R.layout.edit_delete_confirm, null);
			return new AlertDialog.Builder(this)
					.setView(customView)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								private String title;

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									int type = -1;
									db = helper.getReadableDatabase();
									Cursor deleteC = db.query("graph", null,
											null, null, null, null, null);
									deleteC.moveToFirst();
									while (!deleteC.isAfterLast()) {
										title = deleteC.getString(deleteC
												.getColumnIndex("title"));
										if (title.equals(save)) {
											type = deleteC.getInt(deleteC
													.getColumnIndex("type"));
										}
										deleteC.moveToNext();
									}
									db.close();
									db = helper.getWritableDatabase();
									db.delete("graph", "title=?",
											new String[] { save });
									db.close();
									switch (type) {
									case 0:
										BarScaleSQLiteOpenHelper deleteScaleHelper;
										SQLiteDatabase deleteScaleDB;
										deleteScaleHelper = new BarScaleSQLiteOpenHelper(
												EditMainActivity.this, save
														+ ".db", null, 1);
										deleteScaleDB = deleteScaleHelper
												.getWritableDatabase();
										deleteScaleDB.delete("barScale", null,
												null);
										deleteScaleDB.close();
										deleteScaleHelper.close();
										break;

									case 1:
										BarCategorySQLiteOpenHelper deleteCategoryHelper;
										SQLiteDatabase deleteCategoryDB;
										deleteCategoryHelper = new BarCategorySQLiteOpenHelper(
												EditMainActivity.this, save
														+ ".db", null, 1);
										deleteCategoryDB = deleteCategoryHelper
												.getWritableDatabase();
										deleteCategoryDB.delete("barCategory",
												null, null);
										deleteCategoryDB.close();
										deleteCategoryHelper.close();
										break;

									case 2:
										LineSQLiteOpenHelper deleteLineHelper;
										SQLiteDatabase deleteLineDB;
										deleteLineHelper = new LineSQLiteOpenHelper(
												EditMainActivity.this, save
														+ ".db", null, 1);
										deleteLineDB = deleteLineHelper
												.getWritableDatabase();
										deleteLineDB.delete("line", null, null);
										deleteLineDB.close();
										deleteLineHelper.close();
										break;

									case 3:
										PieSQLiteOpenHelper deletePieHelper;
										SQLiteDatabase deletePieDB;
										deletePieHelper = new PieSQLiteOpenHelper(
												EditMainActivity.this, save
														+ ".db", null, 1);
										deletePieDB = deletePieHelper
												.getWritableDatabase();
										deletePieDB.delete("pie", null, null);
										deletePieDB.close();
										deletePieHelper.close();
										break;
									default:
										break;
									}
									File file = new File("/sdcard/" + title
											+ ".png");
									file.delete();
									Toast.makeText(EditMainActivity.this,
											save + " is completely deleted",
											Toast.LENGTH_LONG).show();
									editListView();

									db = helper.getReadableDatabase();
									Cursor checkCursor = db.query("graph",
											null, null, null, null, null, null);
									checkCursor.moveToFirst();
									if (checkCursor.isAfterLast()) {
										Intent goMain = new Intent(
												EditMainActivity.this,
												MainActivity.class);
										startActivity(goMain);
										finish();
									}
								}
							})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub

								}
							}).create();
		}
		return null;
	}

}
