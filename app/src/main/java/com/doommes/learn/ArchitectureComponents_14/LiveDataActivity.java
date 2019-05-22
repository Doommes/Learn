package com.doommes.learn.ArchitectureComponents_14;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.doommes.learn.R;

import java.util.Timer;
import java.util.TimerTask;

public class LiveDataActivity extends AppCompatActivity implements View.OnClickListener {
    private testViewModel mViewModel;
    private ShareDataViewModel mShareDataViewModel;
    private TextView mTset;
    private static final int ONE_SECOND = 1000;

    private int mCount = 0;
    private long mInitialTime;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mTset.setText(String.valueOf(msg.obj));
        }
    };
    private Button mClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_data);
        initView();

        mViewModel = ViewModelProviders.of(this).get(testViewModel.class);

        mShareDataViewModel = ViewModelProviders.of(this).get(ShareDataViewModel.class);
        ShareObserver();
        //sub();
        //subTo();
    }

    private void ShareObserver() {
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                mClick.setText(String.valueOf(integer));
                mCount = integer;
            }
        };
        mShareDataViewModel.getMutableLiveData().observe(this, observer);
    }

    private void subTo() {
        mInitialTime = SystemClock.elapsedRealtime();
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                final long newValue = (SystemClock.elapsedRealtime() - mInitialTime) / 1000;
                Message message = new Message();
                message.obj = newValue;
                mHandler.sendMessage(message);
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
        mClick = (Button) findViewById(R.id.click);
        mClick.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.click:
                mShareDataViewModel.getMutableLiveData().postValue(++mCount);
                break;
        }
    }
}
