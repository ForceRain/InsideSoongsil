

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//여기서부터는 그냥 시험용 으로 Bar-Scale적용
		ArrayList<Float> x=new ArrayList<Float>();
		ArrayList<Float> y=new ArrayList<Float>();
		
		x.add(12f);
		x.add(13f);
		x.add(15f);
		x.add(18f);
		x.add(21f);
		
		y.add(10f);
		y.add(6f);
		y.add(20f);
		y.add(24.3f);
		y.add(30f);
		
		Intent goIntent=new Intent(MainActivity.this,com.example.project.bargraph.BarChartScaleBuilder.class);
		goIntent.putExtra("x",x);
		goIntent.putExtra("y",y);
		startActivity(goIntent);
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
