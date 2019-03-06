package com.doommes.learn.Thread_nine;

import android.util.Log;

import java.util.concurrent.CountDownLatch;

public class VideoConference implements Runnable {
    private static final String TAG = "VideoConference";
    private final CountDownLatch mCount;

    public VideoConference(int number) {
        mCount = new CountDownLatch(number);
    }

    public void arrive (String name){
        Log.d(TAG, "now arrive is: " + name);
        mCount.countDown();
        //Log.d(TAG, "arrive: wait" + mCount.getCount());
    }

    @Override
    public void run() {
        Log.d(TAG, "run: controller: " + mCount.getCount());

        try {
            mCount.await();
            Log.d(TAG, "run: All the participants have come.\n");
            Log.d(TAG, "run: Let`s start...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
