package com.example.practical4c;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	EditText mEdit;
	TextView mText;
	String bef;
	Button mData[];	
	String output;
	Button temp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mData=new Button[16];
		output="";
		bef="";
		
		mText=(TextView)findViewById(R.id.output);
		for (int i=0;i<16;i++)
		{
			mData[i]=(Button)findViewById(R.id.button1+i);
		}
		
		mData[0].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				output+="1";
				mText.setText(bef+"1");
				bef+="1";
			}
		});
		
		mData[1].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				output+="2";
				mText.setText(bef+"2");
				bef+="2";
			}
		});

		mData[2].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				output+="3";
				mText.setText(bef+"3");
				bef+="3";
			}
		});
		
		mData[3].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				output+="+";
				mText.setText("");
				bef="";
			}
		});
		
		mData[4].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				output+="4";
				mText.setText(bef+"4");
				bef+="4";
			}
		});
		
		mData[5].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				output+="5";
				mText.setText(bef+"5");
				bef+="5";
			}
		});
		
		mData[6].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				output+="6";
				mText.setText(bef+"6");
				bef+="6";
			}
		});
		
		mData[7].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				output+="-";
				mText.setText("");
				bef="";
			}
		});
		
		mData[8].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				output+="7";
				mText.setText(bef+"7");
				bef+="7";
			}
		});
		
		mData[9].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				output+="8";
				mText.setText(bef+"8");
				bef+="8";
			}
		});
		
		mData[10].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				output+="9";
				mText.setText(bef+"9");
				bef+="9";
			}
		});
		
		mData[11].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				output+="*";
				mText.setText("");
				bef="";
			}
		});
		
		mData[12].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				output+="0";
				mText.setText(bef+"0");
				bef+="0";
			}
		});
		
		mData[13].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				output="";
				mText.setText("");
				bef="";
			}
		});
		
		mData[14].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				double tmp=calculate();
				
				mText.setText(Double.toString(tmp));
			}
		});
		
		mData[15].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				output+="/";
			}
		});
	}
	
	public double calculate()
	{
		int i;
		if (output.equals(""))
			return 0.0;
		
		for (i=0;i<output.length();i++)
		{
			if (!('0'<=output.charAt(i) && output.charAt(i)<='9'))
				break;
		}
		
		String oper1=output.substring(0,i);
		String oper2=output.substring(i+1);
		
		System.out.println("oper1 : "+oper1+", oper2 : "+oper2);
		
		char operate=output.charAt(i);
		
		if (operate=='+')
		{
			double tmp=Double.parseDouble(oper1)+Double.parseDouble(oper2);
			return tmp;
		}
		if (operate=='-')
		{
			double tmp=Double.parseDouble(oper1)-Double.parseDouble(oper2);
			return tmp;
		}
		if (operate=='*')
		{
			double tmp=Double.parseDouble(oper1)*Double.parseDouble(oper2);
			return tmp;
		}
		if (operate=='/')
		{
			double tmp=Double.parseDouble(oper1)/Double.parseDouble(oper2);
			return tmp;
		}
		
		return Double.parseDouble(output);
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
