package com.example.tiagoeira.quizgame;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isConnected()){
            //start service to get questions:
            //add statement to check internet connection interval of minutes:
            //startService(new Intent(getApplicationContext(), QuestionIntentService.class));
            Toast.makeText(getApplicationContext(), "Connected...getting questions!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Theres's no Connection...", Toast.LENGTH_LONG).show();
        }


        /* Start New Game Button */
        Button btnNewGame = (Button) findViewById(R.id.btnNewGame);
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), GamePlayActivity.class));
            }
        });

        Button btnSettings = (Button) findViewById(R.id.btnSettings);
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            }
        });

        /* Exit App */
        Button btnExit = (Button) findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String JSON_SERVICE_LOG = "JSON_SERVICE_LOG";

                Log.i(JSON_SERVICE_LOG,"test_main_activity");

                moveTaskToBack(true);
                MainActivity.this.finish();
            }
        });
    }

    //Check if there is internet connection
    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            return true;
        }
        else {
            return false;
        }
    }
}
