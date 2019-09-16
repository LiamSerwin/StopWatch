package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String KEY_CHRONOMETER_BASE = "chronometer base";
    public static final String KEY_CHORONOMEETERISCLICKED = "chronometer is clicked";
    public static final String KEY_CHRONOMETER_PAUSE = "chronometer pause";

    //Look up the Log class for Android
    //list all the levels of logginh and when they're used
    //lowest to highest priority:
    //log.v(Verbose)
    //log.d(debug)
    //log.i(info)
    //log.w(warning)
    //log.e(error)
    // launced app --> onCreate, onStart, onResume
    //rotate --> onPause, onStop, onDestroy, onCreate, onStart, onResume
    //hit the square button --> onStop
    //click back on the app from the square button --> onPause, onStop
    //hit the circle button --> onPause, onStop
    //relaunch the app from the phone navigation (not play button) --> onStart, onResume
    //hit the back button --> onStop, onDestroy
    private Button buttonStart;
    private Button buttonReset;
    private Chronometer chronometer;
    private boolean isClicked;
    private long baseClock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");
        isClicked = false;
        wireWidgets();
        setListeners();
        baseClock = 0;

        // if the savedInstanceState isn't null
            // pull out the value of the base that we saved from the Bundle
            //set the chronometer's base to that value
            //start the chronometer

        //next functionality would be to akso store in the bundle
        //whether if was running or stopped to decide if you should
        //start it or not in onCreate
        if(savedInstanceState != null)
        {
            isClicked = savedInstanceState.getBoolean(KEY_CHORONOMEETERISCLICKED);
            chronometer.setBase(savedInstanceState.getLong(KEY_CHRONOMETER_BASE));
            baseClock = savedInstanceState.getLong(KEY_CHRONOMETER_PAUSE);
            chronometer.start();
        }
    }

    private void setListeners() {
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isClicked = !isClicked;

                if (isClicked) {
                    if (baseClock == 0) {
                        baseClock = SystemClock.elapsedRealtime();
                    }
                    chronometer.start();
                    buttonStart.setText(getString(R.string.main_stop));
                    chronometer.setBase(chronometer.getBase() + SystemClock.elapsedRealtime() - baseClock);

                } else {
                    chronometer.stop();
                    baseClock = SystemClock.elapsedRealtime();
                    buttonStart.setText(getString(R.string.main_start));

                }

            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.stop();
                buttonStart.setText(getString(R.string.main_start));
                baseClock = SystemClock.elapsedRealtime();

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    //activity is now running

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    // this activity is completely hidden
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    //activity is finished
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_CHRONOMETER_BASE, chronometer.getBase());
        outState.putBoolean(KEY_CHORONOMEETERISCLICKED, isClicked);
        outState.putLong(KEY_CHRONOMETER_PAUSE, baseClock);

    }

    private void wireWidgets() {
        buttonReset = findViewById(R.id.button_main_reset);
        buttonStart = findViewById(R.id.button_main_start);
        chronometer = findViewById(R.id.chronometer_main_time);

    }
}

