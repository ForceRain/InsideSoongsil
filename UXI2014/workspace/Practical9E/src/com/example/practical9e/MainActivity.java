package com.example.practical9e;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final int READ_BLOCK_SIZE=100;
	EditText input;
	TextView show;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		input=(EditText)findViewById(R.id.input);
		
		Button write=(Button)findViewById(R.id.write);
		Button read=(Button)findViewById(R.id.read);
		
		show=(TextView)findViewById(R.id.show);
		write.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				show.setText("myfile.txt saved to External Storage");
				writeBtn(v);
			}
		});
		
		read.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				show.setText("myfile.txt data retrieved from External Storage");
				readBtn(v);
			}
		});
	}
	private void writeBtn(View v)
	{
		try
		{
			//Environment obj=new Environment();
			FileOutputStream fileWriter=new FileOutputStream("/sdcard/myfile.txt");
			OutputStreamWriter outputWriter=new OutputStreamWriter(fileWriter);
			outputWriter.write(input.getText().toString());
			outputWriter.close();
			
			Toast.makeText(getBaseContext(),"File saved Successfully!",Toast.LENGTH_SHORT).show();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void readBtn(View v)
	{
		try
		{
			//Environment obj1=new Environment();
			FileInputStream fileReader=new FileInputStream("/sdcard/myfile.txt");
			InputStreamReader inputReader=new InputStreamReader(fileReader);
			char []inputBuffer=new char[READ_BLOCK_SIZE];
			String s="";
			int charRead;
			
			while ((charRead=inputReader.read(inputBuffer))>0)
			{
				String readString=String.copyValueOf(inputBuffer,0,charRead);
				s+=readString;
			}
			inputReader.close();
			input.setText(s);
		}
		catch(Exception e)
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
