package com.example.practical8c;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class MyAlarmService extends Service
{
	public void onCreate()
	{
		Toast.makeText(this,"MyAlarmService.onCreate()",Toast.LENGTH_LONG).show();
	}
	
	public IBinder onBind(Intent intent)
	{
		Toast.makeText(this,"MyAlarmService.onBind()",Toast.LENGTH_LONG).show();
		return null;
	}
	
	public void onDestroy()
	{
		super.onDestroy();
		Toast.makeText(this,"MyAlarmService.onDestroy()",Toast.LENGTH_LONG).show();
	}
	
	public void onStart(Intent intent,int startId)
	{
		super.onStart(intent, startId);
		Toast.makeText(this,"MyAlarmService.onStart()",Toast.LENGTH_LONG).show();
	}
	
	public boolean onUnbind(Intent intent)
	{
		Toast.makeText(this,"MyAlarmService.onUnbind()",Toast.LENGTH_LONG).show();
		return super.onUnbind(intent);
	}
}
