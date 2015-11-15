package com.smartplace.polar.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.smartplace.polar.R;
import com.smartplace.polar.helpers.MemoryServices;


public class LauncherActivity extends AppCompatActivity {

    // used to know if the back button was pressed in the splash screen activity and avoid opening the next activity
    private boolean mIsBackButtonPressed;
    private static final int SPLASH_DURATION = 1000; // 500 miliseconds
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

    }
    @Override
    public void onResume()
    {
        super.onResume();
        Handler handler = new Handler();

        // run a thread after n seconds to start the home screen
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                // make sure we close the splash screen so the user won't come back when it presses back key


                if (!mIsBackButtonPressed) {

                    // start the home screen if the back button wasn't pressed already

                    if(MemoryServices.getPublicKey(getBaseContext())==null){

                        // Go to login activity
                        Intent intent = new Intent(LauncherActivity.this, LoginActivity.class);

                        //delete activity from back stack
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        LauncherActivity.this.startActivity(intent);
                    }else {

                        // Go to main activity
                        Intent intent = new Intent(LauncherActivity.this, MainActivity.class);

                        //delete activity from back stack
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        LauncherActivity.this.startActivity(intent);

                    }

                }

                finish();

            }

        }, SPLASH_DURATION); // time in milliseconds (1 second = 1000 milliseconds) until the run() method will be called

    }

    @Override
    public void onPause()
    {
        super.onPause();

    }

    @Override
    public void onBackPressed() {

        // set the flag to true so the next activity won't start up
        mIsBackButtonPressed = true;
        super.onBackPressed();

    }

}
