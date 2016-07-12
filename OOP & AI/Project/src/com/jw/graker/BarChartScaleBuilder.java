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
import org.achartengine.chart.BarChart;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;




import com.jw.graker.R;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
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

@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
public class BarChartScaleBuilder extends Activity {
	private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset(); // XYSeries들을 저장하는container.
	private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer(); // 각 XYSeries들 별로 각각의 눈에 보이는 특성을 담는 Renderer들의 container.
	private XYSeries mCurrentSeries; // 현재 XYSeries를 가리키는 변수
	private XYSeriesRenderer mCurrentRenderer; // 현재 XYSeries의 Renderer를 가리키는 변수
	private GraphicalView mChartView; // chart를 담을 일종의 View
	private static final int BAR_CHART_SCALE_RESULT = 1; // startActivityForResult에서 requestCode로 사용
	private double xMax = -1;
	private double yMax = -1; // x와 y의 최댓값을 알아내어 초기 화면에 한눈에 모든 그래프를 보기 위한 변수
	private int isEdit;
	private float correctionVal = 0; // 화면 크기에 따라 글씨 크기 조절과 차트 크기를 조절하기 위한 보정변수
	private ArrayList<ScaleInput> data;
	private ArrayList<Integer> colorSet = new ArrayList<Integer>(); // color들을 저장해 둘 ArrayList. 0번째는 무조건 BackgroundColor이다.
	private String XaxisName = "";
	private String YaxisName = "";
	private String chartTitleName = ""; // Intent로 String 받아옴. 위에서부터 x축, y축, 차트제목받는
								// string
	private String prevTitle = "";
	private LinearLayout layout;
	private SQLiteDatabase graphdb, bardb;
	private GraphSQLiteOpenHelper graphHepler;
	private BarScaleSQLiteOpenHelper barHelper;
	private BarScaleSQLiteOpenHelper editHelper;
	private SQLiteDatabase editDB;
	private View dialogView;

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// save data in case of changing
		outState.putSerializable("dataset", mDataset);
		outState.putSerializable("renderer", mRenderer);
		outState.putSerializable("current_series", mCurrentSeries);
		outState.putSerializable("current_renderer", mCurrentRenderer);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedState) {
		super.onRestoreInstanceState(savedState);
		// get data when application resume.
		mDataset = (XYMultipleSeriesDataset) savedState
				.getSerializable("dataset");
		mRenderer = (XYMultipleSeriesRenderer) savedState
				.getSerializable("renderer");
		mCurrentSeries = (XYSeries) savedState
				.getSerializable("current_series");
		mCurrentRenderer = (XYSeriesRenderer) savedState
				.getSerializable("current_renderer");
	}

	@SuppressWarnings("unchecked")
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_graph);
		System.out.println("onCreate()");

		Random dice = new Random();
		int color = Color.argb(dice.nextInt(128) + 128, dice.nextInt(64) + 192,
				dice.nextInt(64) + 192, dice.nextInt(64) + 192);
		Bundle bundle = getIntent().getExtras();
		XaxisName = bundle.getString("XAXIS", "Noname");
		YaxisName = bundle.getString("YAXIS", "Noname");
		chartTitleName = bundle.getString("TITLE", "Noname");
		isEdit = bundle.getInt("ISEDIT", -1);
		prevTitle = bundle.getString("PREVTITLE", "");

		if (isEdit == 1) {
			data = new ArrayList<ScaleInput>();
			editHelper = new BarScaleSQLiteOpenHelper(
					BarChartScaleBuilder.this, prevTitle + ".db", null, 1);
			editDB = editHelper.getReadableDatabase();
			Cursor editC = editDB.query("barScale", null, null, null, null,
					null, null);
			editC.moveToFirst();
			while (!editC.isAfterLast()) {
				ScaleInput tmp = new ScaleInput();
				tmp.setX(editC.getFloat(editC.getColumnIndex("xData")));
				tmp.setY(editC.getFloat(editC.getColumnIndex("yData")));
				data.add(tmp);
				editC.moveToNext();
			}
			editDB.close();
		} 
		else {
			data = (ArrayList<ScaleInput>) bundle.getSerializable("DATA");
		}
		for (int i = 0; i < data.size(); i++) {
			System.out.println(data.get(i).getX());
		}

		sorting();

		int backGroundColor = Color.argb(0, 255, 255, 255);
		colorSet.add(backGroundColor);
		colorSet.add(color);

		System.out.println("data get!");
		initializing();
		System.out.println("initialzing end!");

		ImageButton share = (ImageButton) findViewById(R.id.showShareBT);
		share.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					Bitmap bit = mChartView.toBitmap();
					String path="/sdcard/"+chartTitleName+".png";
					FileOutputStream fout = new FileOutputStream(path);
					bit.compress(Bitmap.CompressFormat.PNG, 100, fout);
					fout.close();
					
					Intent intent = new Intent(android.content.Intent.ACTION_SEND);
					intent.setType("image/png");
					intent.putExtra(android.content.Intent.EXTRA_TEXT,"Content to share");
					intent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(new File(path)));
					intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
				    startActivity(Intent.createChooser(intent, "Send :: "+chartTitleName));
				} catch (Exception e) 
				{
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
				// edit pressed
				Intent settingIntent = new Intent(BarChartScaleBuilder.this,
						SettingActivity.class);
				settingIntent.putExtra("TITLE", chartTitleName);
				settingIntent.putExtra("DATA", data);
				settingIntent.putExtra("XAXIS", XaxisName);
				settingIntent.putExtra("YAXIS", YaxisName);
				settingIntent.putExtra("GRAPHTYPE", "bar_scale");
				settingIntent.putExtra("COLORSET", colorSet);
				System.out.println(chartTitleName);
				System.out.println(XaxisName);
				System.out.println(YaxisName);

				startActivityForResult(settingIntent, BAR_CHART_SCALE_RESULT);
			}
		});

		ImageButton save = (ImageButton) findViewById(R.id.showSaveBT);
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(isEdit == 1){
					editDB = editHelper.getWritableDatabase();
					editDB.delete("barScale", null, null);
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
				graphHepler = new GraphSQLiteOpenHelper(
						BarChartScaleBuilder.this, "graph.db", null, 1);
				graphdb = graphHepler.getWritableDatabase();
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
				insertGraph(chartTitleName, 0, XaxisName, YaxisName);

				barHelper = new BarScaleSQLiteOpenHelper(
						BarChartScaleBuilder.this, chartTitleName + ".db",
						null, 1);
				bardb = barHelper.getWritableDatabase();
				bardb.delete("barScale", null, null);
				bardb.close();
				for (int i = 0; i < data.size(); i++) {
					float xTmp;
					float yTmp;
					xTmp = data.get(i).getX();
					yTmp = data.get(i).getY();
					insertScale(chartTitleName, xTmp, yTmp);
					Log.i("db", "title : " + chartTitleName + ", xData : "
							+ xTmp + ", yData : " + yTmp);
				}
				bardb = barHelper.getReadableDatabase();
				Cursor c = bardb.query("barScale", null, null, null, null,
						null, null);
				c.moveToFirst();
				while (!c.isAfterLast()) {
					String titleTmp;
					float xTmp, yTmp;
					titleTmp = c.getString(c.getColumnIndex("title"));
					xTmp = c.getFloat(c.getColumnIndex("xData"));
					yTmp = c.getFloat(c.getColumnIndex("yData"));
					Log.i("db", "title : " + titleTmp + ", xData : " + xTmp
							+ ", yData : " + yTmp);
					c.moveToNext();
				}
				Toast.makeText(BarChartScaleBuilder.this, "Saved :: "+chartTitleName,
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
				dialogView=inflater.inflate(R.layout.description_scale, null);
				aDialog.setView(dialogView);
				ShowDescriptionClass calculator=new ShowDescriptionClass(0);
				calculator.setScaleData(data);
				TextView box=(TextView)dialogView.findViewById(R.id.scaleMax);
				box.setText(calculator.getXScaleMax()+"\n"+calculator.getYScaleMax());
				box=(TextView)dialogView.findViewById(R.id.scaleMin);
				box.setText(calculator.getXScaleMin()+"\n"+calculator.getYScaleMin());
				box=(TextView)dialogView.findViewById(R.id.scaleAver);
				box.setText(calculator.getXYAverage());
				box=(TextView)dialogView.findViewById(R.id.scaleVar);
				box.setText(calculator.getXYVar());
				box=(TextView)dialogView.findViewById(R.id.scaleStdev);
				box.setText(calculator.getXYStdev());
				box=(TextView)dialogView.findViewById(R.id.scaleMed);
				box.setText(calculator.getXYMedian());
				box=(TextView)dialogView.findViewById(R.id.scaleRange);
				box.setText(calculator.getXYRange());
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

	protected void sorting() {
		int size = data.size();
		for (int i = 0; i < size - 1; i++) {
			for (int j = size - 1; j > i; j--) {
				if (data.get(j - 1).getX() > data.get(j).getX()) {
					ScaleInput tmp;
					tmp = data.get(j - 1);
					data.set(j - 1, data.get(j));
					data.set(j, tmp);
				}
			}
		}
	}

	public void initializing() {
		DisplayMetrics metrics = getApplicationContext().getResources()
				.getDisplayMetrics();
		correctionVal = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				13, metrics); // 크기조정위한 보정변수설정
		TextView chartTitle = (TextView) findViewById(R.id.showtitle);
		chartTitle.setText(chartTitleName);
		mRenderer.setPanEnabled(true, false);
		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setBackgroundColor(colorSet.get(0));
		mRenderer.setAxisTitleTextSize(correctionVal);
		mRenderer.setChartTitleTextSize(correctionVal);
		mRenderer.setLabelsTextSize(correctionVal);
		mRenderer.setLegendTextSize(correctionVal);
		mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
		mRenderer.setZoomButtonsVisible(true);
		mRenderer.setBarWidth(correctionVal / 2); // set bar width
		System.out.println(XaxisName);
		System.out.println(YaxisName);
		mRenderer.setXTitle(XaxisName);
		mRenderer.setYTitle(YaxisName);
		mRenderer.setLabelsColor(Color.WHITE);

		for (int i = 0; i < data.size(); i++) {
			System.out.println(data.get(i).getX());
		}

		XYSeries series = new XYSeries(chartTitleName);

		mDataset.addSeries(series);
		mCurrentSeries = series;
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		mRenderer.addSeriesRenderer(renderer);

		renderer.setChartValuesSpacing((correctionVal / 1.5f));
		renderer.setColor(colorSet.get(1));
		renderer.setDisplayChartValues(true);
		renderer.setDisplayChartValuesDistance(10);
		renderer.setDisplayBoundingPoints(true);
		renderer.setChartValuesTextSize(correctionVal);
		mCurrentRenderer = renderer;

		for (int i = 0; i < data.size(); i++) {
			ScaleInput tmp = data.get(i);
			mCurrentSeries.add(tmp.getX(), tmp.getY());
			if (xMax < tmp.getX())
				xMax = tmp.getX();
			if (yMax < tmp.getY())
				yMax = tmp.getY();
		}
		System.out.println(mCurrentSeries.getItemCount());
		setScreenScale();
	}

	protected void setScreenScale() {
		mRenderer.setXAxisMin(data.get(0).getX() - correctionVal / 4);
		mRenderer.setYAxisMin(0.0);
		mRenderer.setXAxisMax(xMax + correctionVal / 2); // set X axis range 0 to Xmax+correctionVal/2, same as Y axis
		mRenderer.setYAxisMax(yMax + correctionVal / 4);
	}

	@Override
	protected void onResume() {
		super.onResume();
		System.out.println("onResume()");
		if (mChartView == null) {
			layout = (LinearLayout) findViewById(R.id.showchart);
			mChartView = ChartFactory.getBarChartView(this, mDataset,
					mRenderer, BarChart.Type.DEFAULT);
			System.out.println("wtf?");
			mRenderer.setClickEnabled(true);
			mRenderer.setSelectableBuffer(10);
			mChartView.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					SeriesSelection seriesSelection = mChartView
							.getCurrentSeriesAndPoint();
					if (seriesSelection != null) {
						Toast.makeText(
								BarChartScaleBuilder.this,
								"X Value : " + seriesSelection.getXValue()
										+ ", Y Value : "
										+ seriesSelection.getValue(),
								Toast.LENGTH_SHORT).show();
					}
				}
			});
			layout.addView(mChartView, new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		System.out.println("받고있냐");
		if ((resultCode == RESULT_OK)
				&& (requestCode == BAR_CHART_SCALE_RESULT)) {
			changeSetting(data);
		}// after finishing activity
	}

	public void changeSetting(Intent bundleIntent) {
		System.out.println("받아왔는데");
		Bundle B = bundleIntent.getExtras();
		data = (ArrayList<ScaleInput>) B.getSerializable("DATA");
		XaxisName = B.getString("XAXIS");
		YaxisName = B.getString("YAXIS");
		colorSet = (ArrayList<Integer>) B.getSerializable("COLORSET");
		chartTitleName = B.getString("TITLE");
		System.out.println(chartTitleName);
		System.out.println(XaxisName);
		System.out.println(YaxisName);
		mDataset.clear();
		mRenderer.removeAllRenderers();
		initializing();
	}

	public void insertGraph(String title, int type, String xAxis, String yAxis) {
		graphdb = graphHepler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("title", title);
		values.put("type", type);
		values.put("xAxis", xAxis);
		values.put("yAxis", yAxis);
		graphdb.insert("graph", null, values);
		graphHepler.close();
	}

	public void insertScale(String title, float xData, float yData) {
		bardb = barHelper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("title", title);
		values.put("xData", xData);
		values.put("yData", yData);
		bardb.insert("barScale", null, values);
		barHelper.close();
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
}