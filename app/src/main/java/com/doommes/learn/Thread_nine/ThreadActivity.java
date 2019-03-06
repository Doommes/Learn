package com.doommes.learn.Thread_nine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.doommes.learn.R;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadActivity extends AppCompatActivity {
    private static final String TAG = "ThreadActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        //testThreadPool();
        //testSync();
        testCountDownLatch();
    }

    private void testCountDownLatch() {
        ExecutorService executorService = Executors.newCachedThreadPool();

        VideoConference videoConference = new VideoConference(10);
        Thread thread = new Thread(videoConference);
        executorService.execute(thread);

        Thread[] threads = new Thread[10];
        for (int i = 0; i<10 ; i++){
            threads[i] = new Thread(new Participant("P-"+i, videoConference));
        }
        for (int i = 0; i<10 ; i++){
            //threads[i].start();
            executorService.execute(threads[i]);
        }
    }

    private void testSync() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        for(int i = 0 ; i<3 ; i++){
            myThread thread = new myThread();

            thread.start();
            //executorService.execute(thread);
        }
    }

    private void testThreadPool() {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i=0; i<100; i++){
            final int finalI = i;
            Thread thread = new Thread(){
                @Override
                public void run() {
                    super.run();
                    try {
                        Thread.sleep(1000);
                        Log.d("Thread", "run: "+ finalI);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            executorService.execute(thread);
        }
    }


    public class Sync {

        public void test() {

            synchronized(this){
                Log.d(TAG, "test: start");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "test: end");
            }

        }
    }



    class  myThread extends Thread {
        @Override
        public void run() {
            Sync sync = new Sync();
            Log.d(TAG, "run: "+sync.hashCode());
            sync.test();
        }
    }
}
