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

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Parcelable;
import android.text.InputType;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class DataInputLayout extends LinearLayout {

	private TextView textview_x;
	private TextView textview_y;
	private EditText edittext_x;
	private EditText edittext_y;
	private ScaleInput scaledata;
	private CategoryInput categorydata;
	private int TYPE = 0;

	public DataInputLayout(Context context, int TYPE) {
		super(context);
		// TODO Auto-generated constructor stub
		this.TYPE = TYPE;

		setOrientation(0);
		setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.FILL_PARENT,
				8f));
		setGravity(Gravity.CENTER_HORIZONTAL);
		scaledata = new ScaleInput();
		categorydata = new CategoryInput();

		textview_x = new TextView(context);
		textview_y = new TextView(context);
		edittext_x = new EditText(context);
		edittext_y = new EditText(context);
		textview_x.setTextColor(Color.WHITE);
		textview_y.setTextColor(Color.WHITE);
		edittext_x.setBackgroundResource(R.drawable.border_data);
		edittext_y.setBackgroundResource(R.drawable.border_data);
		edittext_x.setTextColor(Color.WHITE);
		edittext_y.setTextColor(Color.WHITE);
		edittext_x.setGravity(Gravity.CENTER);
		edittext_y.setGravity(Gravity.CENTER);

		textview_x.setLayoutParams(new LayoutParams(0,
				ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
		textview_y.setLayoutParams(new LayoutParams(0,
				ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
		edittext_x.setLayoutParams(new LayoutParams(0,
				ViewGroup.LayoutParams.WRAP_CONTENT, 1.5f));
		edittext_y.setLayoutParams(new LayoutParams(0,
				ViewGroup.LayoutParams.WRAP_CONTENT, 1.5f));
		setWeightSum(5f);

		if (this.TYPE == 0) {
			textview_x.setText("X Value");
			textview_y.setText("Y Value");

			edittext_x.setInputType(InputType.TYPE_CLASS_NUMBER);
			edittext_y.setInputType(InputType.TYPE_CLASS_NUMBER);
		} else if (this.TYPE == 1) {
			textview_x.setText("Name");
			textview_y.setText("Value");

			edittext_y.setInputType(InputType.TYPE_CLASS_NUMBER);
		}

		addView(textview_x);
		addView(edittext_x);
		addView(textview_y);
		addView(edittext_y);

	}

	private void setScaleData() {
		try {
			scaledata.setX(Float.parseFloat(edittext_x.getText().toString()));
		} catch (Exception e) {
		}
		try {
			scaledata.setY(Float.parseFloat(edittext_y.getText().toString()));
		} catch (Exception e) {
		}
	}

	private void setCategoryData() {
		try {
			categorydata.name = edittext_x.getText().toString();
		} catch (Exception e) {
		}
		try {
			categorydata.frequency = Integer.parseInt(edittext_y.getText()
					.toString());
		} catch (Exception e) {
		}
	}

	public void settingSetScaleData(ScaleInput dat) {
		String inputX = Float.toString(dat.getX());
		String inputY = Float.toString(dat.getY());
		edittext_x.setText(inputX);
		edittext_y.setText(inputY);
	}

	public void settingSetCategoryData(CategoryInput dat) {
		String freq = Integer.toString(dat.getFreq());
		edittext_x.setText(dat.getName());
		edittext_y.setText(freq);
	}

	public ScaleInput getScaleData() {
		return scaledata;
	}

	public CategoryInput getCategoryData() {
		return categorydata;
	}

	public void setData() {
		switch (TYPE) {
		case 0:
			setScaleData();
			break;
		case 1:
			setCategoryData();
			break;
		}
	}

}
