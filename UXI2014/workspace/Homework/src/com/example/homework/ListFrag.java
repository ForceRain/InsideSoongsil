package com.example.homework;

import java.util.ArrayList;
import java.util.Arrays;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListFrag extends ListFragment
{	
	private String[] name=new String[]{"H-Dragon","JunHo","H-Cat","JongHyeon","WonKyun","person6","person7","person8","person9","person10"};

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view=inflater.inflate(R.layout.list_fragment,container,false);
		ArrayList<String> tmp=new ArrayList<String>();
		tmp.addAll(Arrays.asList(name));
		ArrayAdapter<String> data=new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1,tmp);
		
		ListView listv=new ListView(this.getActivity(),null);
		listv.setAdapter(data);
		
		return view;
	}
	
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l,v,position,id);
		ShowFrag frag=(ShowFrag)getFragmentManager().findFragmentById(R.id.show_fragment);
		String input=(String)getListAdapter().getItem(position);
		
		frag.change(input);
		
	}
}
