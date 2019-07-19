package com.doommes.learn.Thread_nine;

import java.util.Date;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {

    public static void main(String[] args){
        PriceInfo priceInfo = new PriceInfo();

        for (int i = 0; i < 3; i++) {
            Thread threadWriter = new Thread(new Writer(priceInfo));
            threadWriter.start();
        }

        while (true) {
            new Thread(new Reader(priceInfo)).start();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class PriceInfo{
        private double price1;
        private double price2;
        private ReadWriteLock mLock;

        public PriceInfo() {
            this.price1 = 1.0;
            this.price2 = 2.0;
            mLock = new ReentrantReadWriteLock();
        }

        public double getPrice1() {
            mLock.readLock().lock();
            double value = price1;
            mLock.readLock().unlock();
            return price1;

        }

        public void setPrice(double price1, double price2) {
            mLock.writeLock().lock();
            System.out.printf("Writer: Attemp to modify the price! Price1:%f Price2:%f\n", price1, price2);
            this.price1 = price1;
            this.price2 = price2;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("Writer: Price has been modified: Price1:%f Price2:%f\n", price1, price2);
            mLock.writeLock().unlock();
        }

        public double getPrice2() {
            mLock.readLock().lock();
            double value = price2;
            mLock.readLock().unlock();
            return price2;

        }

    }

    public static class Reader implements Runnable{
        private PriceInfo mPriceInfo;

        public Reader(PriceInfo priceInfo) {
            mPriceInfo = priceInfo;
        }

        @Override
        public void run() {
            Date date = new Date();
            System.out.printf("%s: Price1 %f .%s\n",
                    Thread.currentThread().getName(), mPriceInfo.getPrice1(), date);
            System.out.printf("%s: Price2 %f .%s\n",
                    Thread.currentThread().getName(), mPriceInfo.getPrice2(), date);
        }
    }

    public static class Writer implements Runnable{
        private PriceInfo mPriceInfo;

        public Writer(PriceInfo priceInfo) {
            mPriceInfo = priceInfo;
        }

        @Override
        public void run() {
            double price1 = Math.random() * 10;
            double price2 = Math.random() * 8;
            try {
                Thread.sleep((long) (Math.random()*10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mPriceInfo.setPrice(price1, price2);
        }
    }
}
