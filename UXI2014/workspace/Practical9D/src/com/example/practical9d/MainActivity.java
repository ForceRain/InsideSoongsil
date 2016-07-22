package com.example.practical9d;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	EditText textmsg;
	private static final int READ_BLOCK_SIZE=100;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		textmsg=(EditText)findViewById(R.id.input);
		
		Button write=(Button)findViewById(R.id.write);
		write.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				writeBtn(v);
			}
		});
		
		Button read=(Button)findViewById(R.id.read);
		read.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				readBtn(v);
			}
		});
	}
	
	public void writeBtn(View v)
	{
		try
		{
			FileOutputStream fileout=openFileOutput("mytextfile.txt",MODE_PRIVATE);
			OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
			outputWriter.write(textmsg.getText().toString());
			outputWriter.close();
			Toast.makeText(getBaseContext(),"File saved successfully!",Toast.LENGTH_SHORT).show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void readBtn(View v)
	{
		try
		{
			FileInputStream fileIn=openFileInput("mytextfile.txt");
			InputStreamReader InputRead=new InputStreamReader(fileIn);
			char[] inputBuffer=new char[READ_BLOCK_SIZE];
			String s="";
			int charRead;
			
			while ((charRead=InputRead.read(inputBuffer))>0)
			{
				String readString=String.copyValueOf(inputBuffer,0,charRead);
				s+=readString;
			}
			InputRead.close();
			Toast.makeText(getBaseContext(),s,Toast.LENGTH_SHORT).show();
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
