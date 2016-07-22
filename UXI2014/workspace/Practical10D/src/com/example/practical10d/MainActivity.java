package com.example.practical10d;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Browser;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	private static final String DEBUG_TAG="BOOKMARK HISTORY";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		String [] requestedColumns=
			{
				Browser.BookmarkColumns.TITLE,Browser.BookmarkColumns.VISITS,
				Browser.BookmarkColumns.BOOKMARK
			};
		Cursor cur=managedQuery(Browser.BOOKMARKS_URI,requestedColumns,null,null,null);
		Log.d(DEBUG_TAG,"Bookmarks count : "+cur.getCount());
		int titleIdx=cur.getColumnIndex(Browser.BookmarkColumns.TITLE);
		int visitsIdx=cur.getColumnIndex(Browser.BookmarkColumns.VISITS);
		int bmIdx=cur.getColumnIndex(Browser.BookmarkColumns.BOOKMARK);
		cur.moveToFirst();
		int count=0;
		while (!cur.isAfterLast())
		{
			Log.d("SimpleBookmarks",cur.getString(titleIdx)+" visited "+cur.getInt(visitsIdx)+" times :"+(cur.getInt(bmIdx)!=0?"true":"false"));
			cur.moveToNext();
			count++;
		}
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
