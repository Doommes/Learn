package com.doommes.learn.Thread_nine;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.doommes.learn.R;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ThreadActivity";
    private ThreadLocal<Boolean> mThreadLocal;
    private final Object syn = 0;
    /**
     * Status
     */
    private Button mStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        initView();
        //testThreadPool();
        //testSync();
        //testCountDownLatch();
        //testPriorityANDStatus();
        mThreadLocal = new ThreadLocal<>();
        mThreadLocal.set(false);

    }

    private void testPriorityANDStatus() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(new PriorityANDStatus(i));
            if (i % 2 == 0) {
                threads[i].setPriority(Thread.MAX_PRIORITY);
            } else {
                threads[i].setPriority(Thread.MIN_PRIORITY);
            }
            threads[i].setName("Thread-" + i);
        }

        for (int i = 0; i < 10; i++) {
            //threads[i].start();
            executorService.execute(threads[i]);
        }

        Thread.State[] states = new Thread.State[10];
        for (int i = 0; i < 10; i++) {
            Log.d(TAG, "Main: Status of the Thread " + i + " is " + threads[i].getState());
            states[i] = threads[i].getState();
        }
        boolean finish = false;
        while (!finish) {
            for (int i = 0; i < 10; i++) {
                if (threads[i].getState() != states[i]) {
                    writeThreadInfo(threads[i], states[i]);
                    states[i] = threads[i].getState();
                }
            }
            finish = true;
            for (int i = 0; i < 10; i++) {
                finish = finish && threads[i].getState() == Thread.State.TERMINATED;
            }
        }
    }

    private void writeThreadInfo(Thread thread, Thread.State state) {
        Log.d(TAG, "Main: state change " + thread.getName() + " old= " + state + " now = " + thread.getState());
    }

    private void initView() {
        mStart = (Button) findViewById(R.id.start);
        mStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.start:
                WaitThread waitThread = new WaitThread();
                NotifyThread notifyThread = new NotifyThread();

                Thread wait = new Thread(waitThread);
                Thread notify = new Thread(notifyThread);

                wait.setName("Thread-0");
                notify.setName("Thread-1");

                wait.start();
                notify.start();

                while (wait.getState() != Thread.State.TERMINATED ||
                        notify.getState() != Thread.State.TERMINATED){
                    Log.d(TAG, "Main " + wait.getState() + " : " + notify.getState());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private class PriorityANDStatus implements Runnable {

        private int name;

        public PriorityANDStatus(int name) {
            this.name = name;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                long duration = (long) (Math.random() * 1000);
                Log.d(TAG, "Thread-" + name + " i=" + i + " res= " + i * name);
                try {
                    Thread.sleep(duration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void testCountDownLatch() {
        ExecutorService executorService = Executors.newCachedThreadPool();

        VideoConference videoConference = new VideoConference(10);
        Thread thread = new Thread(videoConference);
        executorService.execute(thread);

        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(new Participant("P-" + i, videoConference));
        }
        for (int i = 0; i < 10; i++) {
            //threads[i].start();
            executorService.execute(threads[i]);
        }
    }

    private void testSync() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        for (int i = 0; i < 3; i++) {
            myThread thread = new myThread();

            thread.start();
            //executorService.execute(thread);
        }
    }

    private void testThreadPool() {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 100; i++) {
            final int finalI = i;
            Thread thread = new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        Thread.sleep(1000);
                        Log.d("Thread", "run: " + finalI);
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

            synchronized (this) {
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


    class myThread extends Thread {
        @Override
        public void run() {
            Sync sync = new Sync();
            Log.d(TAG, "run: " + sync.hashCode());
            sync.test();
        }
    }

    public class WaitThread implements Runnable {

        @Override
        public void run() {
            Log.d(TAG, "开始执行 : " + Thread.currentThread().getName() + " Status : " + Thread.currentThread().getState());
            synchronized (syn) {
                try {
                    Log.d(TAG, "成功锁定同步块 ：" + Thread.currentThread().getName() + " Status : " + Thread.currentThread().getState());
                    Thread.sleep(1000);
                    Log.d(TAG, "被挂起， 等待唤醒 : " + Thread.currentThread().getName() + " Status : " + Thread.currentThread().getState());
                    syn.wait(2000);
                    Log.d(TAG, "被唤醒， 继续执行 : " + Thread.currentThread().getName() + " Status : " + Thread.currentThread().getState());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Log.d(TAG, "执行完毕 : " + Thread.currentThread().getName() + " Status : " + Thread.currentThread().getState());
        }
    }

    public class NotifyThread implements Runnable {
        @Override
        public void run() {
            Log.d(TAG, "开始执行 : " + Thread.currentThread().getName() + " Status : " + Thread.currentThread().getState());
            synchronized (syn) {
                try {
                    Log.d(TAG, "成功锁定同步块 ：" + Thread.currentThread().getName() + " Status : " + Thread.currentThread().getState());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "唤醒其他线程 : " + Thread.currentThread().getName() + " Status : " + Thread.currentThread().getState());
                syn.notify();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            Log.d(TAG, "执行完毕 : " + Thread.currentThread().getName() + " Status : " + Thread.currentThread().getState());
        }
    }
}
