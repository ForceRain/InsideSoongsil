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
import android.os.Parcel;
import android.os.Parcelable;

public class ScaleInput implements Parcelable {
	private float x;
	private float y;

	public ScaleInput() {
	}

	public ScaleInput(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public ScaleInput(Parcel in) {
		readFromParcel(in);
	}

	public float getX() {
		return x;
	}

	public void setX(float X) {
		this.x = X;
	}

	public float getY() {
		return y;
	}

	public void setY(float Y) {
		this.y = Y;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeFloat(this.x);
		dest.writeFloat(this.y);
	}

	public void readFromParcel(Parcel in) {
		this.x = in.readFloat();
		this.y = in.readFloat();
	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

		@Override
		public Object createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new ScaleInput(source);
		}

		@Override
		public Object[] newArray(int size) {
			// TODO Auto-generated method stub
			return new ScaleInput[size];
		}

	};
}
