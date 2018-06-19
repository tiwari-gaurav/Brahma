package com.brahma.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;

import com.brahma.R;
import com.brahma.utils.TypeWriter;


/**
 * Created by gaurav on 15/9/17.
 */

public class SplashActivity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 5000;
    Animation animFadein;
    private TypeWriter mAppText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mAppText=(TypeWriter) findViewById(R.id.app_text);



        //TypeWriter

        //Add a character every 150ms
        mAppText.setCharacterDelay(150);
        mAppText.animateText("See Brahma in 360");

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                // load the animation

                Intent i = new Intent(SplashActivity.this, SignUpActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
