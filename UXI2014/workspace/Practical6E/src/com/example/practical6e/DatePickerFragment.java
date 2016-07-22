package com.example.practical6e;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment
{
	Handler mHandler;
	int mDate;
	int mMonth;
	int mYear;
	
	public DatePickerFragment(Handler h)
	{
		mHandler=h;
	}
	
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		Bundle b=getArguments();
		
		mDate=b.getInt("set_date");
		mMonth=b.getInt("set_month");
		mYear=b.getInt("set_year");
		
		DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener()
		{
				@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) 
			{
				// TODO Auto-generated method stub
				mDate=dayOfMonth;
				mMonth=monthOfYear;
				mYear=year;
				
				Bundle b=new Bundle();
				
				b.putInt("set_date",mDate);
				b.putInt("set_month",mMonth);
				b.putInt("set_year",mYear);
				
				b.putString("date","Set Date :"+Integer.toString(mYear)+". "+Integer.toString(mMonth+1)+". "+Integer.toString(mDate));
				
				Message m=new Message();
				
				m.setData(b);
				mHandler.sendMessage(m);
			}
		};
		return new DatePickerDialog(getActivity(),listener,mYear,mMonth,mDate);
	}
}
