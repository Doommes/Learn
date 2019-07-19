package com.doommes.learn.Thread_nine;

import android.util.Log;

public class ThreadStatusAndTran {
    private static final Object syn = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new WaitThread());
        Thread thread2 = new Thread(new NotifyThread());

        System.out.printf("Main: %s:%s , %s:%s\n", thread1.getName(), thread1.getState(), thread2.getName(), thread2.getState());

        thread1.start();
        thread2.start();

        while (thread1.getState() != Thread.State.TERMINATED
                || thread2.getState() != Thread.State.TERMINATED) {
            System.out.printf("Main: %s:%s , %s:%s\n", thread1.getName(), thread1.getState(), thread2.getName(), thread2.getState());
            Thread.sleep(1000);
        }
    }


    public static class WaitThread implements Runnable {

        @Override
        public void run() {
            System.out.printf("%s：开始执行，准备锁定object。\n", Thread.currentThread().getName());
            synchronized (syn) {
                try {
                    System.out.printf("%s：成功锁定object，执行同步模块。\n", Thread.currentThread().getName());
                    Thread.sleep(1000);
                    System.out.printf("%s：被挂起，等待其他线程唤醒自己。\n", Thread.currentThread().getName());
                    syn.wait(2000);
                    System.out.printf("%s: 被唤起，继续开始执行。\n", Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.printf("%s: 执行结束。", Thread.currentThread().getName());
        }
    }

    public static class NotifyThread implements Runnable {
        @Override
        public void run() {
            System.out.printf("%s：开始执行，准备锁定object。\n", Thread.currentThread().getName());
            synchronized (syn) {
                try {
                    System.out.printf("%s：成功锁定object，执行同步模块。\n", Thread.currentThread().getName());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("%s: 唤醒其他线程。\n", Thread.currentThread().getName());
                syn.notify();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            System.out.printf("%s: 执行结束。\n", Thread.currentThread().getName());
        }
    }
}
