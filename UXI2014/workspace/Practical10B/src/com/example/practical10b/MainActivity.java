package com.example.practical10b;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TextView output=(TextView)findViewById(R.id.output);
		String totalStr=refreshData();
		output.setText(totalStr);
	}
	
	public String refreshData()
	{
		String data="";
		try
		{
			ContentResolver cr=getBaseContext().getContentResolver();
			Cursor cur=cr.query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null);
			if (cur.getCount()>0)
			{
				Log.i("ContentProvider","Reading contact Emails");
	
				int i=0;
				while (cur.moveToNext())
				{
					String contactId=cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
					Cursor names=cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+contactId,null,null);
					data+="First Name :";
					while (names.moveToNext())
					{
						String name=names.getString(names.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
						data+=name+"\n";
					}
					Cursor phones=cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+contactId,null,null);
					data+="Phone Number : ";
					while (phones.moveToNext())
					{
						String phone=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA));
						data+=phone+"\n";
					}
					phones.close();
					data+="Email : ";
					Cursor emails=cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,null,ContactsContract.CommonDataKinds.Email.CONTACT_ID+"="+contactId,null,null);
					while (emails.moveToNext())
					{
						String email=emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
						data+=email+"\n";
					}
					data+="\n";
					emails.close();
				}
			}
			else
			{
				data+="Data not found.";
			}
			cur.close();
		}
		catch (Exception e)
		{
			data+="Exception :"+e+".";
		}
		return data;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
