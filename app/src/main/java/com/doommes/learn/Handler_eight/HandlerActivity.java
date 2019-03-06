package com.doommes.learn.Handler_eight;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.doommes.learn.R;

import java.lang.ref.WeakReference;

public class HandlerActivity extends AppCompatActivity {
    private static final String TAG = "HandlerActivity";

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        mBooleanThreadLocal.set(true);
        Log.d(TAG, "run: main "+mBooleanThreadLocal.get());

        new Thread("Thread#1"){
            @Override
            public void run() {
                super.run();
                mBooleanThreadLocal.set(false);
                Log.d(TAG, "run: 1 " + mBooleanThreadLocal.get());
            }
        }.start();

        new Thread("Thread#2"){
            @Override
            public void run() {
                super.run();
                Log.d(TAG, "run: 2 "+mBooleanThreadLocal.get());
            }
        }.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(HandlerActivity.this, "不会崩溃啦！", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        });

    }


    class LooperThead extends Thread {
        Handler mHandler;

        @SuppressLint("HandlerLeak")
        @Override
        public void run() {
            super.run();
            Looper.prepare();
            mHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                }
            };
            Looper.loop();
        }
    }

    private static class SafeHandler extends Handler {
        private WeakReference<Activity> ref;

        public SafeHandler(Activity activity) {
            this.ref = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Activity activity = ref.get();
            if (activity != null){

            }
        }
    }

    private ThreadLocal<Boolean> mBooleanThreadLocal = new ThreadLocal<>();

}
