


import com.example.project.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class BarChartPreference extends PreferenceActivity
{
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.addPreferencesFromResource(R.xml.barchartpreference);
	}
}
