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

public class CategoryInput implements Parcelable {
	// category type input structure
	String name = "";
	int frequency;

	public CategoryInput() {
	}

	public CategoryInput(String name, int frequency) {
		this.name = name;
		this.frequency = frequency;
	}

	public CategoryInput(Parcel in) {
		readFromParcel(in);
	}

	public int getFreq() {
		return this.frequency;
	}

	public void setFreq(int freq) {
		this.frequency = freq;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(this.name);
		dest.writeInt(this.frequency);
	}

	public void readFromParcel(Parcel in) {
		this.name = in.readString();
		this.frequency = in.readInt();
	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		@Override
		public Object createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new CategoryInput(source);
		}

		@Override
		public Object[] newArray(int size) {
			// TODO Auto-generated method stub
			return new CategoryInput[size];
		}

	};
}
