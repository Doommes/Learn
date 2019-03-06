package com.doommes.learn.Thread_nine;

import android.util.Log;

import java.util.concurrent.TimeUnit;

public class Participant implements Runnable {
    private static final String TAG = "Participant";
    private String name;
    private VideoConference mVideoConference;

    public Participant(String name, VideoConference videoConference) {
        this.name = name;
        mVideoConference = videoConference;
    }

    @Override
    public void run() {
        long duration = (long) (Math.random() * 10);
        Log.d(TAG, "run: "+ name + " " + duration);
        try {
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mVideoConference.arrive(name);
    }
}
