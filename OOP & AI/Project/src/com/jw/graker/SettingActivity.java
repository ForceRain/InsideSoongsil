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
import java.io.Serializable;
import java.util.ArrayList;

import com.jw.graker.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingActivity extends Activity {
	private static final int SETTING_REQUEST_DATA_RESULT = 20;
	private static final int SETTING_REQUEST_COLOR_RESULT = 21;

	int chartType = -1;
	String chartTitleName = "noName";
	String graphType;
	String xAxisName = "X";
	String yAxisName = "Y";
	Serializable data;
	ArrayList<Integer> colorSet;

	View dialogView;
	EditText titleInput;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);

		// typeIntent[0]=new Intent();
		// 각각의 intent를 미리 저장해둠.
		chartTitleName = getIntent().getStringExtra("TITLE");
		graphType = getIntent().getStringExtra("GRAPHTYPE");
		xAxisName = getIntent().getStringExtra("XAXIS");
		yAxisName = getIntent().getStringExtra("YAXIS");
		data = getIntent().getSerializableExtra("DATA");
		colorSet = (ArrayList<Integer>) getIntent().getSerializableExtra(
				"COLORSET");

		// settingtype에 따라서 setting을 바꾼다.

		if (graphType.equals("bar_scale"))
			chartType = 0;

		if (graphType.equals("bar_category"))
			chartType = 1;

		if (graphType.equals("line"))
			chartType = 2;

		if (graphType.equals("pie"))
			chartType = 3;

		// 돌려줘야 될 내용 : data내용들(scale이면 scale, category면 category들)

		Button setTitle = (Button) findViewById(R.id.settingTitleBT);
		Button setData = (Button) findViewById(R.id.settingDataBT);
		Button setColor = (Button) findViewById(R.id.changecolorBT);

		setTitle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// charttitle은 물론, 스케일 data면 x축, y축 제목도 바꿀수 있습니다.
				if (chartType % 2 == 1) // if categorical data
				{
					// AlertDialog를 이용해서 제목입력 받자.
					AlertDialog.Builder dataDialog = new AlertDialog.Builder(v
							.getContext());
					LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					dialogView = inflater.inflate(
							R.layout.setting_category_info, null);
					dataDialog.setTitle("Current chart Title : "
							+ chartTitleName);
					dataDialog.setView(dialogView);
					dataDialog.setPositiveButton("Confirm!",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									titleInput = (EditText) dialogView
											.findViewById(R.id.settingCateTitle);
									chartTitleName = titleInput.getText()
											.toString();
									Toast.makeText(getApplicationContext(),
											"Data Setting Completed!",
											Toast.LENGTH_SHORT).show();
								}
							});
					dataDialog.setNegativeButton("Don't Change",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									Toast.makeText(getApplicationContext(),
											"Changing title canceled!",
											Toast.LENGTH_SHORT).show();
								}
							});
					dataDialog.create().show();
				} else // if scale data
				{
					// AlertDialog를 이용해서 제목,양 축제목 입력받자.
					AlertDialog.Builder dataDialog = new AlertDialog.Builder(v
							.getContext());
					LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					dialogView = inflater.inflate(R.layout.setting_scale_info,
							null);
					dataDialog.setTitle("Current chart Title : "
							+ chartTitleName);
					dataDialog.setView(dialogView);
					dataDialog.setPositiveButton("Confirm!",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									titleInput = (EditText) dialogView
											.findViewById(R.id.settingScaleTitle);
									EditText Xinput = (EditText) dialogView
											.findViewById(R.id.settingScaleXaxis);
									EditText Yinput = (EditText) dialogView
											.findViewById(R.id.settingScaleYaxis);

									chartTitleName = titleInput.getText()
											.toString();
									xAxisName = Xinput.getText().toString();
									yAxisName = Yinput.getText().toString();
									Toast.makeText(getApplicationContext(),
											"Data Setting Completed!",
											Toast.LENGTH_SHORT).show();
								}
							});
					dataDialog.setNegativeButton("Don't change",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									Toast.makeText(getApplicationContext(),
											"Changing title canceled!",
											Toast.LENGTH_SHORT).show();
								}
							});
					dataDialog.create().show();
				}
			}
		});

		setData.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent setIntent = new Intent(SettingActivity.this,
						SettingDataActivity.class);
				setIntent.putExtra("DATA", data);
				setIntent.putExtra("TYPE", chartType % 2);
				startActivityForResult(setIntent, SETTING_REQUEST_DATA_RESULT);
			}
		});

		setColor.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// colorSet의 0번은 배경색, 1번부터 data색
				Intent setIntent = new Intent(SettingActivity.this,
						SettingColorActivity.class);
				setIntent.putExtra("TYPE", chartType % 2);
				setIntent.putExtra("DATA", data);
				setIntent.putExtra("COLORSET", colorSet);
				startActivityForResult(setIntent, SETTING_REQUEST_COLOR_RESULT);
			}
		});
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if ((requestCode == SETTING_REQUEST_DATA_RESULT)
				&& (resultCode == RESULT_OK)) {
			this.data = data.getSerializableExtra("DATA");
			result();
		}
		if ((requestCode == SETTING_REQUEST_COLOR_RESULT)
				&& (resultCode == RESULT_OK)) {
			this.colorSet = data.getIntegerArrayListExtra("COLORSET");
			result();
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			result();
			return false;
		default:
			return false;
		}
	}

	protected void result() {
		System.out.println("result()");
		Bundle B = new Bundle();
		B.putString("TITLE", chartTitleName);
		B.putString("XAXIS", xAxisName);
		B.putString("YAXIS", yAxisName);
		B.putSerializable("DATA", data);
		B.putSerializable("COLORSET", colorSet);

		Intent resultIntent = new Intent();
		resultIntent.putExtras(B);
		this.setResult(RESULT_OK, resultIntent);
		finish();
	}
}