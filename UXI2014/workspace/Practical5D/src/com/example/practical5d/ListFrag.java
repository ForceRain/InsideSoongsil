package com.example.practical5d;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.util.Log;

public class ListFrag extends ListFragment{
	Integer[] imageIds = {R.drawable.hdragon, R.drawable.junho, R.drawable.mrk, R.drawable.hcat,R.drawable.wonkyun,R.drawable.family,R.drawable.osung,R.drawable.police,R.drawable.taehoon,R.drawable.mimi,R.drawable.ceyap};
	String[] values = new String[] { "H-Dragon", "Junho", "Mr.K", "H-Cat", "WonKyun","Family","O-Sung","Police","TaeHoon","Mi-Mi","CEYAP"};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		String item = (String) getListAdapter().getItem(position);
		
		ShowFrag frag = (ShowFrag) getFragmentManager().findFragmentById(R.id.frag_capt);
		//if (frag != null && frag.isInLayout()) {
			frag.setText(values[position]);
			frag.setImage(this.imageIds[position]);
		//}
	}
	
}

