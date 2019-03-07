package com.doommes.learn.ArchitectureComponents_14;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.doommes.learn.R;

import java.util.Timer;
import java.util.TimerTask;

public class LiveDataActivity extends AppCompatActivity {
    private testViewModel mViewModel;
    private TextView mTset;
    private static final int ONE_SECOND = 1000;

    private long mInitialTime;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //mTset.setText(String.valueOf());
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_data);
        initView();

        mViewModel = ViewModelProviders.of(this).get(testViewModel.class);

        //sub();

        //subTo();
    }

    private void subTo() {
        mInitialTime = SystemClock.elapsedRealtime();
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                final long newValue = (SystemClock.elapsedRealtime() - mInitialTime) / 1000;
                mTset.setText(String.valueOf(newValue));
            }
        }, ONE_SECOND, ONE_SECOND);
    }

    private void sub() {
        Observer<Long> observer = new Observer<Long>() {
            @Override
            public void onChanged(@Nullable Long aLong) {
                mTset.setText(String.valueOf(aLong));
            }
        };
        mViewModel.getData().observe(this, observer);
    }

    private void initView() {
        mTset = (TextView) findViewById(R.id.tset);
    }
}
