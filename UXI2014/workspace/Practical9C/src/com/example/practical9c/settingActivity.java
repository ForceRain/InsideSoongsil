package com.example.practical9c;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class settingActivity extends PreferenceActivity
{
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.setting);
	}
}
