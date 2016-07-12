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
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import com.jw.graker.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PieChartBuilder extends Activity {
	private CategorySeries mSeries = new CategorySeries(""); 
	private DefaultRenderer mRenderer = new DefaultRenderer(); 
	private GraphicalView mChartView; // chart를 담을 일종의 View
	private static final int PIE_CHART_RESULT = 4; // startActivityForResult에서 requestCode로 사용(수정 필요)
	private ArrayList<CategoryInput> data;
	private ArrayList<Integer> colorSet = new ArrayList<Integer>(); //0-background
	private String chartTitleName = "";
	private float correctionVal = 0; // 화면 크기에 따라 글씨 크기 조절과 차트 크기를 조절하기 위한 보정변수
	private int befA = 0;
	private int befR = 0;
	private int befG = 0;
	private int befB = 0; // 기존의 색과 차이를 두기 위한 변수
	private boolean isFirst = true;
	private PieSQLiteOpenHelper pieHelper;
	private GraphSQLiteOpenHelper graphHelper;
	private SQLiteDatabase graphdb, piedb;
	private int isEdit;
	private String prevTitle;
	private PieSQLiteOpenHelper editHelper;
	private SQLiteDatabase editDB;
	private View dialogView;

	@Override
	protected void onRestoreInstanceState(Bundle savedState) {
		super.onRestoreInstanceState(savedState);
		mSeries = (CategorySeries) savedState.getSerializable("current_series");
		mRenderer = (DefaultRenderer) savedState
				.getSerializable("current_renderer");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable("current_series", mSeries);
		outState.putSerializable("current_renderer", mRenderer);
	}

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_graph);
		int backGroundColor = Color.argb(255, 255, 255, 255);
		colorSet.add(backGroundColor);

		Bundle bundle = getIntent().getExtras();
		isEdit = bundle.getInt("ISEDIT", -1);
		prevTitle = bundle.getString("PREVTITLE", "");

		if (isEdit == 1) {
			data = new ArrayList<CategoryInput>();
			editHelper = new PieSQLiteOpenHelper(PieChartBuilder.this,
					prevTitle + ".db", null, 1);
			editDB = editHelper.getReadableDatabase();
			Cursor editC = editDB.query("pie", null, null, null, null, null,
					null);
			editC.moveToFirst();
			while (!editC.isAfterLast()) {
				CategoryInput tmp = new CategoryInput();
				tmp.setName(editC.getString(editC.getColumnIndex("xData")));
				tmp.setFreq(editC.getInt(editC.getColumnIndex("yData")));
				data.add(tmp);
				editC.moveToNext();
			}
			editDB.close();
		} else {
			data = (ArrayList<CategoryInput>) bundle.getSerializable("DATA");
		}
		for (int i = 0; i < data.size(); i++)
			System.out.println(data.get(i).getName());
		chartTitleName = bundle.getString("TITLE");
		for (int i = 0; i < data.size(); i++) {
			int randomColor = randColor();
			colorSet.add(randomColor);
		}
		// 제목 및 데이터 받는 부분

		initializing();

		ImageButton share = (ImageButton) findViewById(R.id.showShareBT);
		share.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					Bitmap bit = mChartView.toBitmap();
					String path = "/sdcard/" + chartTitleName + ".png";
					FileOutputStream fout = new FileOutputStream(path);
					bit.compress(Bitmap.CompressFormat.PNG, 100, fout);
					fout.close();

					Intent intent = new Intent(
							android.content.Intent.ACTION_SEND);
					intent.setType("image/png");
					intent.putExtra(android.content.Intent.EXTRA_TEXT,
							"Content to share");
					intent.putExtra(Intent.EXTRA_STREAM,
							Uri.fromFile(new File(path)));
					intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
					startActivity(Intent.createChooser(intent, "Send :: "
							+ chartTitleName));
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "Reading Image Failed!",
							Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
			}
		});

		ImageButton setting = (ImageButton) findViewById(R.id.showSettingBT);
		setting.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// edit 버튼을 눌렀을 때!
				Intent settingIntent = new Intent(PieChartBuilder.this,
						SettingActivity.class);
				settingIntent.putExtra("TITLE", chartTitleName);
				settingIntent.putExtra("DATA", data);
				settingIntent.putExtra("GRAPHTYPE", "pie");
				settingIntent.putExtra("COLORSET", colorSet);
				startActivityForResult(settingIntent, PIE_CHART_RESULT);
			}
		});

		ImageButton save = (ImageButton) findViewById(R.id.showSaveBT);
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// save 버튼을 눌렀을 때!
				if(isEdit == 1){
					editDB = editHelper.getWritableDatabase();
					editDB.delete("pie", null, null);
					editDB.close();
					editHelper.close();
				}
				try {
					Bitmap bit = mChartView.toBitmap();
					String path = "/sdcard/"+chartTitleName+".png";
					FileOutputStream fout = new FileOutputStream(path);
					bit.compress(Bitmap.CompressFormat.PNG, 100, fout);
					fout.close();
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "Reading Image Failed!",
							Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
				graphHelper = new GraphSQLiteOpenHelper(PieChartBuilder.this,
						"graph.db", null, 1);
				graphdb = graphHelper.getWritableDatabase();
				Cursor gc = graphdb.query("graph", null, null, null, null,
						null, null);
				gc.moveToFirst();
				while (!gc.isAfterLast()) {
					String titleTmp;
					titleTmp = gc.getString(gc.getColumnIndex("title"));
					if (chartTitleName.equals(titleTmp)) {
						graphdb.delete("graph", "title=?",
								new String[] { titleTmp });
						Log.i("db", titleTmp + " Delete is completed");
					}
					gc.moveToNext();
				}
				insertGraph(chartTitleName, 3);

				pieHelper = new PieSQLiteOpenHelper(PieChartBuilder.this,
						chartTitleName + ".db", null, 1);
				piedb = pieHelper.getWritableDatabase();
				piedb.delete("pie", null, null);
				piedb.close();
				for (int i = 0; i < data.size(); i++) {
					String nameTmp;
					int freqTmp;
					nameTmp = data.get(i).getName();
					freqTmp = data.get(i).getFreq();
					insertPie(chartTitleName, nameTmp, freqTmp);
					Log.i("db", "title : " + chartTitleName + ", Name : "
							+ nameTmp + ", yData : " + freqTmp);
				}
				piedb = pieHelper.getReadableDatabase();
				Cursor c = piedb.query("pie", null, null, null, null, null,
						null);
				c.moveToFirst();
				while (!c.isAfterLast()) {
					String titleTmp;
					String nameTmp;
					int valueTmp;
					titleTmp = c.getString(c.getColumnIndex("title"));
					nameTmp = c.getString(c.getColumnIndex("name"));
					valueTmp = c.getInt(c.getColumnIndex("value"));
					Log.i("db", "title : " + titleTmp + ", xData : " + nameTmp
							+ ", yData : " + valueTmp);
					c.moveToNext();
				}
				Toast.makeText(PieChartBuilder.this, "Saved :: "+chartTitleName,
						Toast.LENGTH_LONG).show();
			}
		});
		
		ImageButton description=(ImageButton)findViewById(R.id.showDescriptionBT);
		description.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder aDialog=new AlertDialog.Builder(v.getContext());
				LayoutInflater inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				dialogView=inflater.inflate(R.layout.description_category, null);
				aDialog.setView(dialogView);
				ShowDescriptionClass calculator=new ShowDescriptionClass(1);
				calculator.setCategoryData(data);
				TextView box=(TextView)dialogView.findViewById(R.id.categoryMax);
				box.setText(calculator.getMaxCategory());
				box=(TextView)dialogView.findViewById(R.id.categoryMin);
				box.setText(calculator.getMinCategory());
				box=(TextView)dialogView.findViewById(R.id.categoryPer);
				box.setText(calculator.getCategoryPercentage());
				
				aDialog.setPositiveButton("OK",new DialogInterface.OnClickListener() 
				{
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});
				aDialog.create().show();
			}
		});
	}

	public int randColor() {
		Random dice = new Random();
		int n_A = (befA + 100 + dice.nextInt(30)) % 128 + 120;
		int n_R = (befR + 100 + dice.nextInt(30)) % 128 + 120;
		int n_G = (befG + 100 + dice.nextInt(30)) % 128 + 120;
		int n_B = (befB + 100 + dice.nextInt(30)) % 128 + 120;

		int col = Color.argb(n_A, n_R, n_G, n_B);

		befA = n_A;
		befR = n_R;
		befG = n_G;
		befB = n_B;

		return col;
	}
	
	public void initializing() {
		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setBackgroundColor(colorSet.get(0));
		mRenderer.setZoomButtonsVisible(true);
		mRenderer.setStartAngle(180);
		mRenderer.setDisplayValues(true);
		mRenderer.setPanEnabled(false);
		mRenderer.setLabelsColor(Color.BLACK);

		DisplayMetrics metrics = getApplicationContext().getResources()
				.getDisplayMetrics();
		correctionVal = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				13, metrics);
		mRenderer.setLabelsTextSize(correctionVal);

		TextView chartTitle = (TextView) findViewById(R.id.showtitle);
		chartTitle.setText(chartTitleName);
		
		if ((data.size()+1)>colorSet.size())
		{
			while ((data.size()+1)!=colorSet.size())
				colorSet.add(randColor());
		}
		
		for (int i = 0; i < data.size(); i++) {
			String cate = data.get(i).getName();
			mSeries.add(cate, data.get(i).getFreq());
			SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
			renderer.setColor(colorSet.get(i + 1));
			mRenderer.addSeriesRenderer(renderer);
		}
		if (isFirst)
			isFirst = false;
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mChartView == null) {
			System.out.println("처음에만?");
			LinearLayout layout = (LinearLayout) findViewById(R.id.showchart);
			mChartView = ChartFactory.getPieChartView(this, mSeries, mRenderer);
			mRenderer.setClickEnabled(true);
			mChartView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					SeriesSelection seriesSelection = mChartView
							.getCurrentSeriesAndPoint();
					if (seriesSelection != null) {
						for (int i = 0; i < mSeries.getItemCount(); i++) {
							mRenderer.getSeriesRendererAt(i).setHighlighted(
									i == seriesSelection.getPointIndex());
						}
					}
				}
			});
			layout.addView(mChartView, new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		} else {
			mChartView.repaint();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		System.out.println("HUH");
		if (resultCode == RESULT_OK && requestCode == PIE_CHART_RESULT) {
			changeSetting(data);
		}// 다 덮어쓰기
	}

	private void changeSetting(Intent bundleIntent) {
		System.out.println("작동 하셨습니다.");
		Bundle B = bundleIntent.getExtras();
		data = (ArrayList<CategoryInput>) B.getSerializable("DATA");
		colorSet = (ArrayList<Integer>) B.getSerializable("COLORSET");
		chartTitleName = B.getString("TITLE");
		System.out.println("다 받았는데 초기화하자.");
		mSeries.clear();
		mRenderer.removeAllRenderers();
		initializing();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			finish();
			return false;
		default:
			return false;
		}
	}

	public void insertGraph(String title, int type) {
		graphdb = graphHelper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("title", title);
		values.put("type", type);
		graphdb.insert("graph", null, values);
		graphHelper.close();
	}

	public void insertPie(String title, String name, int value) {
		piedb = pieHelper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("title", title);
		values.put("name", name);
		values.put("value", value);
		piedb.insert("pie", null, values);
		pieHelper.close();
	}
}
