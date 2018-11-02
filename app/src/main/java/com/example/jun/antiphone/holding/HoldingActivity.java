package com.example.jun.antiphone.holding;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.jun.antiphone.R;
import com.example.jun.antiphone.singleton.RestfulAPIManager;
import com.google.firebase.auth.FirebaseAuth;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;
import entity.ActivitiesLogDTO;
import util.DateTimeUtils;
import util.PointUtils;

public class HoldingActivity extends AppCompatActivity {

    private static final String TAG = "Taggg " + HoldingActivity.class.toString();

    //20 minutes is 1200 seconds
    private static final int MAX_TIME = 1200;
    private static final int STEP_TIME = 5;
    CircularProgressIndicator circularProgress;
    BroadcastReceiver screenOnOffReceiver;
    long currentProgress;
    boolean allowRun;
    boolean clockIsRunning = false;
    TextView txtTime;
    TextView txtPoint;
    long point;

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
            stopAndSave();

            //maybe dont need to reset because the activity finish here
            resetClock();
//            finish();
//            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
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
            circularProgress.setMaxProgress(MAX_TIME);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        if (allowRun) {
                            currentProgress += STEP_TIME;
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
        long seconds = event.getSeconds();
        circularProgress.setCurrentProgress(seconds % MAX_TIME);
        txtTime = findViewById(R.id.txtTime);
        txtPoint = findViewById(R.id.txtPoint);
        txtTime.setText(DateTimeUtils.generateTime(seconds));
        long point = PointUtils.calculatePoint(seconds);
        txtPoint.setText(Long.toString(point));
    }

    public void stopHolding(View view) {
        stopAndSave();
    }

    public class ChangeUIEvent {
        private long seconds;
        private long point;

        public ChangeUIEvent(long percent, long point) {
            this.seconds = percent;
            this.point = point;
        }

        public long getPoint() {
            return point;
        }

        public long getSeconds() {
            return seconds;
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
                }

            }
        };
        getApplicationContext().registerReceiver(screenOnOffReceiver, theFilter);
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

    private void stopAndSave() {
        disableClock();
        Log.d(TAG, String.format("stopAndSave: currentProgress=%s", currentProgress));

        long point = PointUtils.calculatePoint(currentProgress);

        // post current point to api

        ActivitiesLogDTO activitiesLogDTO = new ActivitiesLogDTO();
        activitiesLogDTO.setActivitiesLogDate(DateTimeUtils.getDateString());
        activitiesLogDTO.setActivitiesLogAchievedTime(currentProgress);
        Log.d(TAG, String.format("stopAndSave: pointReceived=%s", point));
        activitiesLogDTO.setActivitiesLogPointReceived(point);
        activitiesLogDTO.setUsername(FirebaseAuth.getInstance().getCurrentUser().getUid());


        RestfulAPIManager.getInstancẹ̣̣̣().postActivityLog(this,activitiesLogDTO);


        //show dialogFragment
        //todo set custom text here
        DialogFragment dialogFragment = HoldingPointDialogFragment.create("Ouch! You can do better !", currentProgress, point);
        dialogFragment.show(this.getSupportFragmentManager(), TAG);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//        finish();
    }
}
