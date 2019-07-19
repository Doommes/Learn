package com.doommes.learn.Thread_nine;

import android.util.Log;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintQueue {
    private static final String TAG = "PrintQueue";
    private final ReentrantLock queueLock = new ReentrantLock();


    public void printJob(Object document){
        boolean isLock = true;
        try {
//            isLock = queueLock.tryLock();
            queueLock.lock();
            long duration = (long) (Math.random() * 10000);
            System.out.println(Thread.currentThread().getName() +
                    " PrintQueue: print a job during " + (duration/1000) + " seconds");
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if (isLock) {
                queueLock.unlock();
                System.out.printf("%s: The document has been printed.\n", Thread.currentThread().getName());
            }else {
                System.out.printf("%s: The document has not been printed.\n", Thread.currentThread().getName());
            }
        }

    }

    public static class Job implements Runnable{
        private PrintQueue mPrintQueue;

        public Job(PrintQueue printQueue) {
            mPrintQueue = printQueue;
        }

        @Override
        public void run() {
            System.out.printf("%s: Going to print a document.\n", Thread.currentThread().getName());
            mPrintQueue.printJob(new Object());
            //System.out.printf("%s: The document has been printed.\n", Thread.currentThread().getName());
        }
    }

    public static void main(String[] args){
        PrintQueue printQueue = new PrintQueue();
        Job job = new Job(printQueue);

        for (int i = 1; i <= 10; i++) {
            Thread thread = new Thread(job);
            thread.start();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
