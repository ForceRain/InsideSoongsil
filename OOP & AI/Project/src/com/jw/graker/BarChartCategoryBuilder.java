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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class BarChartCategoryBuilder extends Activity {
	private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
	private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer(); 
	private XYSeries mCurrentSeries; // 현재 XYSeries를 가리키는 변수
	private XYSeriesRenderer mCurrentRenderer; // 현재 XYSeries의 Renderer를 가리키는 변수
	private GraphicalView mChartView; // chart를 담을 일종의 View
	private static final int BAR_CHART_CATEGORY_RESULT = 2; // startActivityForResult에서 requestCode
	private double xMax = -1;
	private double yMax = -1; // x와 y의 최댓값을 알아내어 초기 화면에 한눈에 모든 그래프를 보기 위한 변수
	private float correctionVal = 0; // 화면 크기에 따라 글씨 크기 조절과 차트 크기를 조절하기 위한 보정변수
	private ArrayList<CategoryInput> data;
	private ArrayList<String> category;
	private ArrayList<Integer> colorSet = new ArrayList<Integer>(); // 0 - background
	private String chartTitleName = ""; // 차트 제목
	private int befA = 0;
	private int befR = 0;
	private int befG = 0;
	private int befB = 0; // 기존의 색과 차이를 두기 위한 변수
	private BarCategorySQLiteOpenHelper barHelper;
	private GraphSQLiteOpenHelper graphHelper;
	private SQLiteDatabase graphdb, bardb;
	private BarCategorySQLiteOpenHelper editHelper;
	private SQLiteDatabase editDB;
	private int isEdit;
	private String prevTitle;
	private View dialogView;
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// 다시 시작할 때를 대비해서 현재 데이터 저장.
		outState.putSerializable("dataset", mDataset);
		outState.putSerializable("renderer", mRenderer);
		outState.putSerializable("current_series", mCurrentSeries);
		outState.putSerializable("current_renderer", mCurrentRenderer);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedState) {
		super.onRestoreInstanceState(savedState);
		// 다시 시작했을 때, 저장했던 데이터 복구
		mDataset = (XYMultipleSeriesDataset) savedState
				.getSerializable("dataset");
		mRenderer = (XYMultipleSeriesRenderer) savedState
				.getSerializable("renderer");
		mCurrentSeries = (XYSeries) savedState
				.getSerializable("current_series");
		mCurrentRenderer = (XYSeriesRenderer) savedState
				.getSerializable("current_renderer");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_graph);

		int backGroundColor = Color.argb(100, 50, 50, 50);
		colorSet.add(backGroundColor);
		Bundle bundle = getIntent().getExtras();
		chartTitleName = bundle.getString("TITLE", "Noname");
		isEdit = bundle.getInt("ISEDIT", -1);
		prevTitle = bundle.getString("PREVTITLE", "");

		if (isEdit == 1) {
			data = new ArrayList<CategoryInput>();
			editHelper = new BarCategorySQLiteOpenHelper(
					BarChartCategoryBuilder.this, prevTitle + ".db", null, 1);
			editDB = editHelper.getReadableDatabase();
			Cursor editC = editDB.query("barCategory", null, null, null, null,
					null, null);
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
		for (int i = 0; i < data.size(); i++) {
			int randColor = randomColor();
			colorSet.add(randColor);
		}

		initializing();

		for (int i = 0; i < category.size(); i++)
			System.out.println(category.get(i));

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
				Intent settingIntent = new Intent(BarChartCategoryBuilder.this,
						SettingActivity.class);
				settingIntent.putExtra("TITLE", chartTitleName);
				settingIntent.putExtra("DATA", data);
				settingIntent.putExtra("GRAPHTYPE", "bar_category");
				settingIntent.putExtra("COLORSET", colorSet);
				startActivityForResult(settingIntent, BAR_CHART_CATEGORY_RESULT);
			}
		});

		ImageButton save = (ImageButton) findViewById(R.id.showSaveBT);
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// save 버튼을 눌렀을 때!
				int first = 0;
				if (isEdit == 1) {
					editDB = editHelper.getWritableDatabase();
					editDB.delete("barCategory", null, null);
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
				graphHelper = new GraphSQLiteOpenHelper(
						BarChartCategoryBuilder.this, "graph.db", null, 1);
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
				System.out.println("Check");
				insertGraph(chartTitleName, 1, "", null); // check xAxis, yAxis
				System.out.println("Checkx");
				barHelper = new BarCategorySQLiteOpenHelper(
						BarChartCategoryBuilder.this, chartTitleName + ".db",
						null, 1);
				bardb = barHelper.getWritableDatabase();
				bardb.delete("barCategory", null, null);
				bardb.close();
				for (int i = 0; i < data.size(); i++) {
					String nameTmp;
					int frqTmp;
					nameTmp = data.get(i).getName();
					frqTmp = data.get(i).getFreq();
					insertCategory(chartTitleName, nameTmp, frqTmp);
					Log.i("db", "title : " + chartTitleName + ", xData : "
							+ nameTmp + ", yData : " + frqTmp);
				}
				bardb = barHelper.getReadableDatabase();
				Cursor c = bardb.query("barCategory", null, null, null, null,
						null, null);
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
				Toast.makeText(BarChartCategoryBuilder.this,
						"Saved :: "+chartTitleName, Toast.LENGTH_LONG).show();
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

	protected void splitCategory() {
		for (int i = 0; i < data.size(); i++) {
			String cateName = data.get(i).getName();
			boolean isExist = false;
			for (int j = 0; j < category.size(); j++) {
				if (category.equals(cateName))
					isExist = true;
			}
			if (!isExist)
				category.add(cateName);
		}
	}

	public String getName(int index) {
		return category.get(index);
	}

	public int randomColor() {
		Random dice = new Random();
		int n_A = (dice.nextInt(64) + 192 + befA) % 64 + 192;
		int n_R = (dice.nextInt(64) + 192 + befR) % 64 + 192;
		int n_G = (dice.nextInt(64) + 192 + befG) % 64 + 192;
		int n_B = (dice.nextInt(64) + 192 + befB) % 64 + 192;

		int output = Color.argb(n_A, n_R, n_G, n_B);
		return output;
	}

	protected void setScreenScale() {
		mRenderer.setXAxisMin(5 - correctionVal / 2);
		mRenderer.setYAxisMin(0.0);
		mRenderer.setXAxisMax(xMax + correctionVal / 2); // 초기 화면의 x축 range를
															// 0~데이터의
															// X의최댓값+보정값/2, y축도
															// 마찬가지
		mRenderer.setYAxisMax(yMax + correctionVal / 2);
	}

	protected void initializing() {		
		category = new ArrayList<String>();

		mRenderer.setPanEnabled(false, false);
		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setBackgroundColor(colorSet.get(0));
		splitCategory();

		DisplayMetrics metrics = getApplicationContext().getResources()
				.getDisplayMetrics();
		correctionVal = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				13, metrics); // 화면 크기에 알맞게 폰트 생성을 위한 보정변수
		TextView chartTitle = (TextView) findViewById(R.id.showtitle);
		chartTitle.setText(chartTitleName); // 제목 및 데이터 받아 오는 부분

		mRenderer.setAxisTitleTextSize(correctionVal);
		mRenderer.setChartTitleTextSize(correctionVal);
		mRenderer.setLabelsTextSize(correctionVal);
		mRenderer.setLegendTextSize(correctionVal); // 화면 크기에 따른 보정변수로 초기화
		mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
		mRenderer.setZoomButtonsVisible(true);
		mRenderer.setBarWidth(2 * correctionVal); // bar하나의 width 길이 결정
		mRenderer.setLabelsColor(Color.WHITE);

		if ((category.size()+1)>colorSet.size())
		{
			while ((category.size()+1)!=colorSet.size())
				colorSet.add(randomColor());
		}
		
		int size = category.size();
		int settingXval = 5;
		int temp = 0;
		for (int i = 0; i < size; i++) {
			XYSeries series = new XYSeries(category.get(i));
			mDataset.addSeries(series);
			mCurrentSeries = series;

			XYSeriesRenderer renderer = new XYSeriesRenderer();
			mRenderer.addSeriesRenderer(renderer);
			renderer.setColor(colorSet.get(i + 1)); // 색상설정 -- scale은 랜덤한 같은 색상,
													// category는 각각 다른 색상.
			renderer.setDisplayChartValues(true);
			renderer.setDisplayChartValuesDistance(10);
			renderer.setDisplayBoundingPoints(true);
			renderer.setChartValuesTextSize(correctionVal);
			mCurrentRenderer = renderer;
			mCurrentSeries.add(settingXval, data.get(i).getFreq()); // 카테고리 추가

			if (temp < data.get(i).getFreq())
				temp = data.get(i).getFreq();
			settingXval += 10;
		}

		xMax = settingXval;
		yMax = temp;
		setScreenScale(); // 초기 화면 재지정을 위한 함수
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mChartView == null) {
			LinearLayout layout = (LinearLayout) findViewById(R.id.showchart);
			mChartView = ChartFactory.getBarChartView(this, mDataset,
					mRenderer, BarChart.Type.DEFAULT);
			mRenderer.setClickEnabled(true);
			mRenderer.setSelectableBuffer(10);
			mChartView.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					SeriesSelection seriesSelection = mChartView
							.getCurrentSeriesAndPoint();
					if (seriesSelection != null) {
						Toast.makeText(
								BarChartCategoryBuilder.this,
								"Category name: "
										+ getName(seriesSelection
												.getSeriesIndex())
										+ ", Frequency="
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

	// *setting이 끝나고
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if ((requestCode == BAR_CHART_CATEGORY_RESULT)
				&& (resultCode == RESULT_OK)) {
			changeSetting(data);
		}// 일단 다 덮어씌워
	}

	public void changeSetting(Intent bundleIntent) {
		Bundle B = bundleIntent.getExtras();
		data = (ArrayList<CategoryInput>) B.getSerializable("DATA");
		// category=B.getStringArrayList("CATEGORY");
		colorSet = (ArrayList<Integer>) B.getSerializable("COLORSET");
		chartTitleName = B.getString("TITLE");
		mRenderer.removeAllRenderers();
		mDataset.clear();

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

	public void insertCategory(String title, String xData, int yData) {
		bardb = barHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("title", title);
		values.put("name", xData);
		values.put("value", yData);
		bardb.insert("barCategory", null, values);
		barHelper.close();
	}
}