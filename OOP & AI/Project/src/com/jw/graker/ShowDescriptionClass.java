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

import android.os.Parcelable;

public class ShowDescriptionClass {
	private int graphType;
	private ArrayList<ScaleInput> scaleData;
	private ArrayList<CategoryInput> categoryData;
	private float averX;
	private float averY;
	private float varX;
	private float varY;
	private float maxX;
	private float maxY;
	private float minX;
	private float minY;
	private int categoryTotal;

	public ShowDescriptionClass(int graphType) // graphtype=0 : scale, 1 :
												// category
	{
		categoryTotal = 0;
		averX = 0;
		averY = 0;
		varX = 0;
		varY = 0;
		this.graphType = graphType;
	}

	public void setScaleData(ArrayList<ScaleInput> data) {
		scaleData = data;
	}

	public void setCategoryData(ArrayList<CategoryInput> data) {
		categoryData = data;
	}

	public String getXScaleMax() {
		ScaleInput obj = new ScaleInput();
		obj.setX(0);
		obj.setY(0);
		for (int i = 0; i < scaleData.size(); i++) {
			if (obj.getX() < scaleData.get(i).getX()) {
				obj.setX(scaleData.get(i).getX());
				obj.setY(scaleData.get(i).getY());
			}
		}
		maxX = obj.getX();
		String output = " Maximum X : " + obj.getX() + "( Y : " + obj.getY()
				+ ")";
		return output;
	}

	public String getYScaleMax() {
		ScaleInput obj = new ScaleInput();
		obj.setX(0);
		obj.setY(0);
		for (int i = 0; i < scaleData.size(); i++) {
			if (obj.getY() < scaleData.get(i).getY()) {
				obj.setX(scaleData.get(i).getX());
				obj.setY(scaleData.get(i).getY());
			}
		}
		maxY = obj.getY();
		String output = " Maximum Y : " + obj.getY() + "( X : " + obj.getX()
				+ ")";
		return output;
	}

	public String getXScaleMin() {
		ScaleInput obj = new ScaleInput();
		obj.setX(scaleData.get(scaleData.size() - 1).getX());
		obj.setY(scaleData.get(scaleData.size() - 1).getY());
		for (int i = 0; i < scaleData.size(); i++) {
			if (obj.getX() > scaleData.get(i).getX()) {
				obj.setX(scaleData.get(i).getX());
				obj.setY(scaleData.get(i).getY());
			}
		}
		minX = obj.getX();
		String output = " Minimum X : " + obj.getX() + "( Y : " + obj.getY()
				+ ")";
		return output;
	}

	public String getYScaleMin() {
		ScaleInput obj = new ScaleInput();
		obj.setX(scaleData.get(scaleData.size() - 1).getX());
		obj.setY(scaleData.get(scaleData.size() - 1).getY());
		for (int i = 0; i < scaleData.size(); i++) {
			if (obj.getY() > scaleData.get(i).getY()) {
				obj.setX(scaleData.get(i).getX());
				obj.setY(scaleData.get(i).getY());
			}
		}
		minY = obj.getY();
		String output = " Minimum Y : " + obj.getY() + "( X : " + obj.getX()
				+ ")";
		return output;
	}

	public String getXYAverage() {
		float Xsum = 0;
		float Ysum = 0;

		for (int i = 0; i < scaleData.size(); i++) {
			Xsum += scaleData.get(i).getX();
			Ysum += scaleData.get(i).getY();
		}
		averX = Xsum / scaleData.size();
		averY = Ysum / scaleData.size();
		String output = " X axis : " + Float.toString(averX) + "\n Y axis : "
				+ Float.toString(averY);
		return output;
	}

	public String getXYVar() {
		for (int i = 0; i < scaleData.size(); i++) {
			varX += (scaleData.get(i).getX() - averX)
					* (scaleData.get(i).getX() - averX);
			varY += (scaleData.get(i).getY() - averY)
					* (scaleData.get(i).getY() - averY);
		}

		String output;
		if (scaleData.size() > 1) {
			varX = varX / (scaleData.size() - 1);
			varY = varY / (scaleData.size() - 1);
			output = " X axis : " + Float.toString(varX) + "\n Y axis : "
					+ Float.toString(varY);
		} else
			output = " Cannot calculate.";

		return output;
	}

	public String getXYStdev() {
		String output;
		if (scaleData.size() > 1) {
			double stdX = Math.sqrt(varX);
			double stdY = Math.sqrt(varY);
			output = " X axis : " + Double.toString(stdX) + "\n Y axis : "
					+ Double.toString(stdY);
		} else {
			output = " Cannot calculate.";
		}

		return output;
	}

	public String getXYMedian() {
		int eo = scaleData.size() % 2;
		float medX;
		float medY;
		if (eo == 1) {
			medX = scaleData.get(scaleData.size() / 2).getX();
			medY = scaleData.get(scaleData.size() / 2).getY();
		} else {
			medX = (scaleData.get(scaleData.size() / 2 - 1).getX() + scaleData
					.get(scaleData.size() / 2).getX()) / 2;
			medY = (scaleData.get(scaleData.size() / 2 - 1).getY() + scaleData
					.get(scaleData.size() / 2).getY()) / 2;
		}
		String output = " X axis : " + Float.toString(medX) + "\n Y axis : "
				+ Float.toString(medY);
		return output;
	}

	public String getXYRange() {
		String output = " X axis : " + Float.toString(maxX - minX)
				+ "\n Y axis : " + Float.toString(maxY - minY);
		return output;
	}

	// end of scale

	public String getMaxCategory() {
		CategoryInput obj = new CategoryInput();
		obj.setName("");
		obj.setFreq(0);
		for (int i = 0; i < categoryData.size(); i++) {
			categoryTotal += categoryData.get(i).getFreq();
			if (obj.getFreq() < categoryData.get(i).getFreq()) {
				obj = categoryData.get(i);
			}
		}

		String output = " Category Name : " + obj.getName() + "\n Frequency : "
				+ obj.getFreq();
		return output;
	}

	public String getMinCategory() {
		CategoryInput obj = new CategoryInput();
		obj.setName(categoryData.get(categoryData.size() - 1).getName());
		obj.setFreq(categoryData.get(categoryData.size() - 1).getFreq());
		for (int i = 0; i < categoryData.size(); i++) {
			if (obj.getFreq() > categoryData.get(i).getFreq())
				obj = categoryData.get(i);
		}

		String output = " Category Name : " + obj.getName() + "\n Frequency : "
				+ obj.getFreq();
		return output;
	}

	public String getCategoryPercentage() {
		String output = " Percentage : \n";
		for (int i = 0; i < categoryData.size(); i++) {
			output += categoryData.get(i).getName()
					+ " : "
					+ Float.toString((categoryData.get(i).getFreq() / (categoryTotal * 1.0f)) * 100)
					+ "%\n";
		}
		return output;
	}
}
