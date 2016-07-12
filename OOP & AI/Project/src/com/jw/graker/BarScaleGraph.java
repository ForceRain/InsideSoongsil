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

public class BarScaleGraph {
	private String xAxis, yAxis;
	private ArrayList<Integer> dataX, dataY;
	public String getXAxis(){
		return this.xAxis;
	}
	
	public String getYAxis(){
		return this.yAxis;
	}
	public ArrayList<Integer> getXData(){
		return this.dataX;
	}
	public ArrayList<Integer> getYData(){
		return this.dataY;
	}
}
