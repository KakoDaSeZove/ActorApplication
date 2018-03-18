package com.example.tijana.actorapplication.activities;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.example.tijana.actorapplication.R;

public class PreferenceSettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
