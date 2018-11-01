package com.example.jun.antiphone;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;
import util.DateTimeUtils;

public class HoldingActivity extends AppCompatActivity {

    private static final String TAG = "Taggg " + HoldingActivity.class.toString();

    //20 minutes is 1200 seconds
    private static final int maxTime = 1200;
    private static final double stepTime = 5;
    CircularProgressIndicator circularProgress;
    BroadcastReceiver screenOnOffReceiver;
    double currentProgress;
    boolean allowRun;
    boolean clockIsRunning = false;
    TextView txtTime;
    TextView txtPoint;
    int point;

    boolean shouldKeepRunning = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_holding);
        registerBroadcastReceiver();

//        isStartAgain = false;
        resetClock();

    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
//        registerBroadcastReceiver();
        if (shouldKeepRunning) {
            Log.d(TAG, "onStart:  keep running, start the clock");
            startClock();
        } else {
            // should not keep running, stop the app
            Log.d(TAG, "onStart:  should not keep running, stop the app");
            resetClock();
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }

//        isStop = false;
//        if (isStartAgain) {
        //user pressed system menu
//            finish();
//            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//        }
//        isStartAgain = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        Log.d(TAG, "onStop");
        shouldKeepRunning = false;
        disableClock();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: unregisterReceiver");
        getApplicationContext().unregisterReceiver(screenOnOffReceiver);
    }

    private void startClock() {
        if (!clockIsRunning) {
            clockIsRunning = true;
            enableClock();
            circularProgress = findViewById(R.id.circular_progress);
            circularProgress.setMaxProgress(maxTime);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        if (allowRun) {
                            currentProgress += stepTime;
                            if (currentProgress >= 0) {
                                EventBus.getDefault().post(new ChangeUIEvent(currentProgress, point));
                            }
                            try {
                                Thread.sleep(100);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            break;
                        }
                    }
                }
            }).start();
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeUI(ChangeUIEvent event) {
        double percent = event.getPercent();
        circularProgress.setCurrentProgress(percent % maxTime);
        txtTime = findViewById(R.id.txtTime);
        txtPoint = findViewById(R.id.txtPoint);
        txtTime.setText(DateTimeUtils.generateTime(percent));
        int point = ((int) percent / maxTime) * 20;
        txtPoint.setText(Integer.toString(point));
    }

    public void stopHolding(View view) {
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public class ChangeUIEvent {
        private double percent;
        private int point;

        public ChangeUIEvent(double percent, int point) {
            this.percent = percent;
            this.point = point;
        }

        public int getPoint() {
            return point;
        }

        public double getPercent() {
            return percent;
        }
    }

    private void registerBroadcastReceiver() {
        Log.d(TAG, "registerBroadcastReceiver: start receiver");
        IntentFilter theFilter = new IntentFilter();
        /** System Defined Broadcast */
        theFilter.addAction(Intent.ACTION_SCREEN_ON);
        theFilter.addAction(Intent.ACTION_SCREEN_OFF);
        theFilter.addAction(Intent.ACTION_MEDIA_BUTTON);

        screenOnOffReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String strAction = intent.getAction();
                if (strAction.equals(Intent.ACTION_SCREEN_OFF)) {
                    ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
// The first in the list of RunningTasks is always the foreground task.
                    ActivityManager.RunningTaskInfo foregroundTaskInfo = am.getRunningTasks(1).get(0);

                    if (foregroundTaskInfo.topActivity.getClassName().equals(HoldingActivity.class.getName())) {
                        // foreground task is this application
                        //continue the clock
                        Log.d(TAG, String.format("onReceive: foreground task is this application, start the clock, foregroundtaskinfo=%s", foregroundTaskInfo.topActivity.getClassName()));
                        shouldKeepRunning = true;
                        startClock();
                    } else {
                        // user is not opening our application
                        // stop the clock
                        Log.d(TAG, String.format("onReceive:user is not opening our application,stop the clock, foregroundtaskinfo=%s", foregroundTaskInfo.topActivity.getClassName()));
                        shouldKeepRunning = false;
                    }


//                Log.d(TAG, "onReceive: isStop = " + isStop);

                    //todo hoa's code

//                    if (isStop) return;
//                String strAction = intent.getAction();
//                if (strAction.equals(Intent.ACTION_SCREEN_OFF)) {
//                    startClock();
////                    Log.d(TAG, "onReceive: Screen off");
//                    isStartAgain = false;
//                }
                }

            }
        };
        getApplicationContext().registerReceiver(screenOnOffReceiver, theFilter);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            Log.d(TAG, "onKeyDown: MENU BUTTON");
        }
        return super.onKeyDown(keyCode, event);
    }

    private void resetClock() {
        currentProgress = -1;
    }

    private void enableClock() {
        allowRun = true;
    }

    private void disableClock() {
        allowRun = false;
        clockIsRunning = false;
    }
}
