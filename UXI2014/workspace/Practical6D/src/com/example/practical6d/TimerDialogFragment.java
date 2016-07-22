package com.example.practical6d;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TimePicker;

public class TimerDialogFragment extends DialogFragment
{
	Handler mHandler;
	int mHour;
	int mMinute;
	
	public TimerDialogFragment(Handler h)//mainActivity¿¡¼­ handler º¸³»ÁÜ
	{
		mHandler=h;
	}
	
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		Bundle b=getArguments();//bundle ¿¡¼­ ¹º°¡¸¦ °¡Á®¿È
		mHour=b.getInt("set_hour");//set_hour¿¡¼­ ¹º°¡¸¦ »©¿À³ªº½
		mMinute=b.getInt("set_minute");//set_minute¿¡¼­ ¶Ç ¹º°¡¸¦ »©¿À³ªº½
		
		TimePickerDialog.OnTimeSetListener listener=new TimePickerDialog.OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				// TODO Auto-generated method stub
				mHour=hourOfDay;
				mMinute=minute;
				
				Bundle b=new Bundle();
				b.putInt("set_hour",mHour);
				b.putInt("set_minute",mMinute);
				b.putString("set_time","Set Time :"+Integer.toString(mHour)+" : "+Integer.toString(mMinute));
				
				Message m=new Message();
				m.setData(b);
				
				mHandler.sendMessage(m);
			}
		};
		
		return new TimePickerDialog(getActivity(),listener,mHour,mMinute,false);
	}
}
