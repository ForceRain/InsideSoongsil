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
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.jw.graker.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
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
import android.os.Bundle;
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

public class LineChartBuilder extends Activity {
	private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
	private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
	private XYSeries mCurrentSeries; // 현재 XYSeries를 가리키는 변수
	private XYSeriesRenderer mCurrentRenderer; // 현재 XYSeries의 Renderer를 가리키는 변수
	private GraphicalView mChartView; // chart를 담을 일종의 View
	private ArrayList<ScaleInput> data;
	private ArrayList<Integer> colorSet = new ArrayList<Integer>(); // 0 = background
	private static final int LINE_CHART_RESULT = 3; // startActivityForResult에서 requestCode로 사용(수정 필요)
	private double xMax = -1;
	private double yMax = -1; // x와 y의 최댓값을 알아내어 초기 화면에 한눈에 모든 그래프를 보기 위한 변수
	private float correctionVal = 0; // 화면 크기에 따라 글씨 크기 조절과 차트 크기를 조절하기 위한 보정변수
	private String XaxisName = "";
	private String YaxisName = "";
	private String chartTitleName = ""; // 차트 제목
	private GraphSQLiteOpenHelper graphHelper;
	private LineSQLiteOpenHelper lineHelper;
	private SQLiteDatabase graphdb, linedb;
	private int isEdit;
	private String prevTitle;
	private LineSQLiteOpenHelper editHelper;
	private SQLiteDatabase editDB;
	private View dialogView;

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// 화면 바뀔때 되돌아 오면 쓰게 데이터 저장해두기.
		outState.putSerializable("dataset", mDataset);
		outState.putSerializable("renderer", mRenderer);
		outState.putSerializable("current_series", mCurrentSeries);
		outState.putSerializable("current_renderer", mCurrentRenderer);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedState) {
		super.onRestoreInstanceState(savedState);
		// 되돌아 왔을 때 데이터 저장한거 회복.
		mDataset = (XYMultipleSeriesDataset) savedState
				.getSerializable("dataset");
		mRenderer = (XYMultipleSeriesRenderer) savedState
				.getSerializable("renderer");
		mCurrentSeries = (XYSeries) savedState
				.getSerializable("current_series");
		mCurrentRenderer = (XYSeriesRenderer) savedState
				.getSerializable("current_renderer");
	}

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_graph);

		DisplayMetrics metrics = getApplicationContext().getResources()
				.getDisplayMetrics();
		correctionVal = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				13, metrics); // 기기 환경에 따라 글씨 크기 조절위함.
		Random dice = new Random();
		int color = Color.argb(dice.nextInt(128) + 128, dice.nextInt(64) + 192,
				dice.nextInt(64) + 192, dice.nextInt(64) + 192);

		Bundle bundle = getIntent().getExtras();
		XaxisName = bundle.getString("XAXIS"); // 지우지 마시길
		YaxisName = bundle.getString("YAXIS");
		chartTitleName = bundle.getString("TITLE");
		isEdit = bundle.getInt("ISEDIT", -1);
		prevTitle = bundle.getString("PREVTITLE", "");

		if (isEdit == 1) {
			data = new ArrayList<ScaleInput>();
			editHelper = new LineSQLiteOpenHelper(LineChartBuilder.this,
					prevTitle + ".db", null, 1);
			editDB = editHelper.getReadableDatabase();
			Cursor editC = editDB.query("line", null, null, null, null, null,
					null);
			editC.moveToFirst();
			while (!editC.isAfterLast()) {
				ScaleInput tmp = new ScaleInput();
				tmp.setX(editC.getFloat(editC.getColumnIndex("xData")));
				tmp.setY(editC.getFloat(editC.getColumnIndex("yData")));
				data.add(tmp);
				editC.moveToNext();
			}
			editDB.close();
		} else {
			data = (ArrayList<ScaleInput>) bundle.getSerializable("DATA");
		}
		int backGroundColor = Color.argb(100, 50, 50, 50);
		colorSet.add(backGroundColor);
		colorSet.add(randColor());

		sorting();
		initializing();

		ImageButton share = (ImageButton) findViewById(R.id.showShareBT);
		share.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// save 버튼을 눌렀을 때!
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
				Intent settingIntent = new Intent(LineChartBuilder.this,
						SettingActivity.class);
				settingIntent.putExtra("TITLE", chartTitleName);
				settingIntent.putExtra("DATA", data);
				settingIntent.putExtra("XAXIS", XaxisName);
				settingIntent.putExtra("YAXIS", YaxisName);
				settingIntent.putExtra("GRAPHTYPE", "line");
				settingIntent.putExtra("COLORSET", colorSet);
				startActivityForResult(settingIntent, LINE_CHART_RESULT);
			}
		});

		ImageButton save = (ImageButton) findViewById(R.id.showSaveBT);
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isEdit == 1){
					editDB = editHelper.getWritableDatabase();
					editDB.delete("line", null, null);
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
				graphHelper = new GraphSQLiteOpenHelper(LineChartBuilder.this,
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
				insertGraph(chartTitleName, 2, XaxisName, YaxisName);

				lineHelper = new LineSQLiteOpenHelper(LineChartBuilder.this,
						chartTitleName + ".db", null, 1);
				linedb = lineHelper.getWritableDatabase();
				linedb.delete("line", null, null);
				linedb.close();
				for (int i = 0; i < data.size(); i++) {
					float xTmp;
					float yTmp;
					xTmp = data.get(i).getX();
					yTmp = data.get(i).getY();
					insertLine(chartTitleName, xTmp, yTmp);
					Log.i("db", "title : " + chartTitleName + ", xData : "
							+ xTmp + ", yData : " + yTmp);
				}
				linedb = lineHelper.getReadableDatabase();
				Cursor c = linedb.query("line", null, null, null, null, null,
						null);
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
				Toast.makeText(LineChartBuilder.this, "Saved :: "+chartTitleName,
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

	protected void setScreenScale() {
		mRenderer.setXAxisMax(xMax + correctionVal / 3);
		mRenderer.setXAxisMin(data.get(0).getX() - correctionVal / 3);
		mRenderer.setYAxisMax(yMax + correctionVal / 3);
		mRenderer.setYAxisMin(0.0);
	}

	public int randColor() {
		Random dice = new Random();
		int n_A = (100 + dice.nextInt(30)) % 128 + 120;
		int n_R = (100 + dice.nextInt(30)) % 128 + 120;
		int n_G = (100 + dice.nextInt(30)) % 128 + 120;
		int n_B = (100 + dice.nextInt(30)) % 128 + 120;

		int col = Color.argb(n_A, n_R, n_G, n_B);
		return col;
	}

	protected void initializing() {
		TextView chartTitle = (TextView) findViewById(R.id.showtitle);
		chartTitle.setText(chartTitleName); 
		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setBackgroundColor(colorSet.get(0));

		mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
		mRenderer.setZoomButtonsVisible(true); // background 설정
		mRenderer.setAxisTitleTextSize(correctionVal);
		mRenderer.setChartTitleTextSize(correctionVal);
		mRenderer.setLabelsTextSize(correctionVal);
		mRenderer.setLegendTextSize(correctionVal);
		mRenderer.setPointSize((correctionVal / 4) * 3);
		mRenderer.setPanEnabled(true, false); 
		mRenderer.setXTitle(XaxisName);
		mRenderer.setYTitle(YaxisName);
		mRenderer.setLabelsColor(Color.WHITE);

		XYSeries series = new XYSeries(chartTitleName);
		mDataset.addSeries(series);
		mCurrentSeries = series;

		XYSeriesRenderer renderer = new XYSeriesRenderer();
		mRenderer.addSeriesRenderer(renderer);

		renderer.setLineWidth((correctionVal/3)*2
				);
		renderer.setPointStyle(PointStyle.CIRCLE);
		renderer.setFillPoints(true);
		renderer.setDisplayChartValues(true);
		renderer.setDisplayChartValuesDistance(10);
		renderer.setChartValuesSpacing(correctionVal);
		renderer.setChartValuesTextSize(correctionVal);
		renderer.setColor(colorSet.get(1));

		mCurrentRenderer = renderer;

		xMax = (data.get(data.size() - 1)).getX();
		yMax = -1;
		for (int i = 0; i < data.size(); i++) {
			mCurrentSeries.add(data.get(i).getX(), data.get(i).getY());

			if (yMax < data.get(i).getY())
				yMax = data.get(i).getY();
		}
		setScreenScale();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mChartView == null) {
			LinearLayout layout = (LinearLayout) findViewById(R.id.showchart);
			mChartView = ChartFactory.getLineChartView(this, mDataset,
					mRenderer);
			mRenderer.setClickEnabled(true);
			mRenderer.setSelectableBuffer(10);
			mChartView.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					SeriesSelection seriesSelection = mChartView
							.getCurrentSeriesAndPoint();
					if (seriesSelection != null) {
						Toast.makeText(
								LineChartBuilder.this,
								"X Value : " + seriesSelection.getXValue()
										+ ", Y Value : "
										+ seriesSelection.getValue(),
								Toast.LENGTH_SHORT).show();
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
		if ((requestCode == LINE_CHART_RESULT) && (resultCode == RESULT_OK)) {
			changeSetting(data);
		}// 덮어 씌우자
	}

	protected void changeSetting(Intent bundleIntent) {
		Bundle B = bundleIntent.getExtras();
		data = (ArrayList<ScaleInput>) B.getSerializable("DATA");
		XaxisName = B.getString("XAXIS");
		YaxisName = B.getString("YAXIS");
		colorSet = (ArrayList<Integer>) B.getIntegerArrayList("COLORSET");
		chartTitleName = B.getString("TITLE");
		mDataset.clear();
		mRenderer.removeAllRenderers();
		sorting();
		initializing();
	}

	public void insertGraph(String title, int type, String xAxis, String yAxis) {
		graphdb = graphHelper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("title", title);
		values.put("type", type);
		values.put("xAxis", xAxis);
		values.put("yAxis", yAxis);

		graphdb.insert("graph", null, values);
		graphHelper.close();
	}

	public void insertLine(String title, float xData, float yData) {
		linedb = lineHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("title", title);
		values.put("xData", xData);
		values.put("yData", yData);
		linedb.insert("line", null, values);
		lineHelper.close();
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