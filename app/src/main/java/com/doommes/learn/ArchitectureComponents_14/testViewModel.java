package com.doommes.learn.ArchitectureComponents_14;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.SystemClock;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class testViewModel extends ViewModel{
    private static final int ONE_SECOND = 1000;

    private long mInitialTime;
    private MutableLiveData<Long> mData = new MutableLiveData<>();

    public testViewModel() {

        mInitialTime = SystemClock.elapsedRealtime();
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                final long newValue = (SystemClock.elapsedRealtime() - mInitialTime) / 1000;
                mData.postValue(newValue);
            }
        }, ONE_SECOND, ONE_SECOND);
    }

    public LiveData<Long> getData() {
        return mData;
    }
}
