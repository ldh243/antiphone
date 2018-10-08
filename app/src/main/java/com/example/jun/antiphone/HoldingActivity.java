package com.example.jun.antiphone;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;

public class HoldingActivity extends AppCompatActivity {

    private static final String TAG = "Taggg " + HoldingActivity.class.toString();
    CircularProgressIndicator circularProgress;
    BroadcastReceiver screenOnOffReceiver;
    double currentProgress;
    boolean isRunning;
    boolean allowRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holding);
        registerBroadcastReceiver();
        Log.d(TAG, "onCreate");
    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        Log.d(TAG, "onStart");
        if (!allowRun) {
            Log.d(TAG, "onStart + start clock");
            resetClock();
            startClock();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        Log.d(TAG, "onStop");
        disableClock();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getApplicationContext().unregisterReceiver(screenOnOffReceiver);
    }

    private void startClock() {
        enableClock();
        if (isRunning) return;
        circularProgress = findViewById(R.id.circular_progress);
        circularProgress.setMaxProgress(1200);
        circularProgress.setProgressTextAdapter(TIME_TEXT_ADAPTER);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: clock start " + allowRun);
                while (true) {
                    if (allowRun) {
                        isRunning = true;
                        currentProgress += 0.1;
                        currentProgress %= 1200;
                        EventBus.getDefault().post(new ChangeUIEvent(currentProgress));
                        try {
                            Thread.sleep(100);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        isRunning = false;
                        break;
                    }
                }
                Log.d(TAG, "run: clock end");
            }
        }).start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeUI(ChangeUIEvent event) {
        double percent = event.getPercent();
        circularProgress.setCurrentProgress(percent);
    }

    private static final CircularProgressIndicator.ProgressTextAdapter TIME_TEXT_ADAPTER = new CircularProgressIndicator.ProgressTextAdapter() {
        @Override
        public String formatText(double time) {
            int hours = (int) (time / 3600);
            time %= 3600;
            int minutes = (int) (time / 60);
            int seconds = (int) (time % 60);
            StringBuilder sb = new StringBuilder();
            if (hours < 10) {
                sb.append(0);
            }
            sb.append(hours).append(":");
            if (minutes < 10) {
                sb.append(0);
            }
            sb.append(minutes).append(":");
            if (seconds < 10) {
                sb.append(0);
            }
            sb.append(seconds);
            return sb.toString();
        }
    };

    public void stopHolding(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public class ChangeUIEvent {
        private double percent;

        public ChangeUIEvent(double percent) {
            this.percent = percent;
        }

        public double getPercent() {
            return percent;
        }
    }

    private void registerBroadcastReceiver() {

        Log.d(TAG, "registerBroadcastReceiver");

        IntentFilter theFilter = new IntentFilter();
        /** System Defined Broadcast */
        theFilter.addAction(Intent.ACTION_SCREEN_ON);
        theFilter.addAction(Intent.ACTION_SCREEN_OFF);
        theFilter.addAction(Intent.ACTION_USER_PRESENT);

        screenOnOffReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String strAction = intent.getAction();

                KeyguardManager myKM = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);

                Log.d(TAG, "onReceive action: " + strAction);
//                Log.d(TAG, "onReceive myKM: " + myKM.inKeyguardRestrictedInputMode());

                if(strAction.equals(Intent.ACTION_USER_PRESENT) || strAction.equals(Intent.ACTION_SCREEN_OFF) || strAction.equals(Intent.ACTION_SCREEN_ON)  ) {
                    if (!allowRun) startClock();
                }
            }
        };
        getApplicationContext().registerReceiver(screenOnOffReceiver, theFilter);
    }

    private void resetClock() {
        currentProgress = 0;
    }

    private void enableClock() {
        allowRun = true;
    }

    private void disableClock() {
        allowRun = false;
    }

}
