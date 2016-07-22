package com.example.practical6c;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DFragment extends DialogFragment
{
	View view;
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{	
		LayoutInflater inflater=getActivity().getLayoutInflater();
		AlertDialog.Builder aBuilder=new AlertDialog.Builder(getActivity());
		view=inflater.inflate(R.layout.dialog_fragment,null);
		aBuilder.setView(view);
		
		aBuilder.setPositiveButton("Confirm",new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				EditText mText=(EditText)view.findViewById(R.id.input);
				String tmp=mText.getText().toString();
				Toast.makeText(getActivity(),"Hello, "+tmp,Toast.LENGTH_SHORT).show();
			}
		});
		
		aBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			}
		});
		
		return aBuilder.create();
	}
}
