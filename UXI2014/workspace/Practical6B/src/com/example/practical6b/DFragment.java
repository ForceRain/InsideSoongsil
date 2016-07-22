package com.example.practical6b;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class DFragment extends DialogFragment
{
	Button yes;
	Button no;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView=inflater.inflate(R.layout.dialog_fragment,container,false);
		getDialog().setTitle("What is Android?");
				
		yes=(Button)rootView.findViewById(R.id.yesbtn);
		no=(Button)rootView.findViewById(R.id.nobtn);
		
		yes.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				Toast.makeText(v.getContext(),"Android is EASY?",Toast.LENGTH_SHORT).show();
			}
		});
		
		no.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				Toast.makeText(v.getContext(),"Android is HARD!",Toast.LENGTH_SHORT).show();
			}
		});
		
		return rootView;
	}
}
