package com.example.tiagoeira.quizgame;

import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class SettingsActivity extends PreferenceActivity {

    public static final String USERNAME_KEY = "USERNAME_KEY";
    public static final String INTERVAL_KEY = "INTERVAL_KEY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        addPreferencesFromResource(R.xml.prefs);
    }


    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String username = prefs.getString(USERNAME_KEY, null);
        String interval = prefs.getString(INTERVAL_KEY, null);

        //set application to save data

    }
}
