package com.ut3.ehg.turismotepic;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


public class SplashScreen2 extends Activity {
    // Set the duration of the splash screen
    private static final long SPLASH_SCREEN_DELAY = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen2);
        TextView tx = (TextView)findViewById(R.id.text1);





        Typeface beba = Typeface.createFromAsset(getAssets(), "fonts/BebasKai-Regular.otf");
        tx.setTypeface(beba);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                // Start the next activity
                Intent mainIntent = new Intent().setClass(SplashScreen2.this, Login.class);
                startActivity(mainIntent);

                // Close the activity so the User won't able to go back this
                finish();

            }
        };

        // Simulate a long loading process on application startup.
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }
}
