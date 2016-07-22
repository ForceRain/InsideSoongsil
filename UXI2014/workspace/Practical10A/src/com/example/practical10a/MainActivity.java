package com.example.practical10a;

import android.support.v7.app.ActionBarActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TextView output=(TextView)findViewById(R.id.output);
		String emailStr=refreshData();
		output.setText(emailStr);
	}
	
	private String refreshData()
	{
		String emailData="";
		try
		{
			ContentResolver cr=getBaseContext().getContentResolver();
			Cursor cur=cr.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
			if (cur.getCount()>0)
			{
				Log.i("Content Provider","Reading contact emails");
				while (cur.moveToNext())
				{
					String contactId=cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
					Cursor emails=cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,null,ContactsContract.CommonDataKinds.Email.CONTACT_ID+"="+contactId,null,null);
					while (emails.moveToNext())
					{
						String emailAddress=emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
						emailData+=" "+emailAddress;	
					}
					emails.close();
				}
			}
			else
			{
				emailData+="Data not found.";
			}
			cur.close();
		}
		catch (Exception e)
		{
			emailData+="Exception : "+e+"";
		}
		return emailData;
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
