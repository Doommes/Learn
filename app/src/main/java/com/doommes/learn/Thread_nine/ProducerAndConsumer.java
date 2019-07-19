package com.doommes.learn.Thread_nine;

import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerAndConsumer {

    public static void main(String[] args){
        DataBuffer buffer = new DataBuffer(10);

        new Thread(new ProducerTask(buffer), " ProducerTask").start();
        new Thread(new ConsumerTask(buffer),  "ConsumerTask").start();
    }

    public static class DataBuffer{
        private int maxSize;
        private LinkedList<Date> buffer;

        private ReentrantLock lock;
        private Condition producer;
        private Condition consumer;

        public DataBuffer(int maxSize) {
            this.maxSize = maxSize;
            buffer = new LinkedList<>();
            lock = new ReentrantLock();

            producer = lock.newCondition();
            consumer = lock.newCondition();
        }

        public void put() throws InterruptedException {
            lock.lock();
            while (buffer.size() == this.maxSize){
                producer.await();
                System.out.printf("%s: 缓冲区为10， 生产者挂起\n", Thread.currentThread().getName());
            }
            buffer.add(new Date());

            System.out.printf("%s: Add one %s. Buffer size is %d.\n",
                    Thread.currentThread().getName(),
                    buffer.peekLast(),
                    buffer.size());
            consumer.signalAll();
            lock.unlock();
        }

        public void get() throws InterruptedException {
            lock.lock();
            while (buffer.size() == 0){
                consumer.await();
                System.out.printf("%s: 缓冲区为0， 消费者挂起\n", Thread.currentThread().getName());
            }

            System.out.printf("%s: Get one %s. Buffer size is %d.\n",
                    Thread.currentThread().getName(),
                    buffer.pollFirst(),
                    buffer.size());
            producer.signalAll();
            lock.unlock();
        }
    }

    public static class Producer implements Runnable{
        private DataBuffer mDataBuffer;

        public Producer(DataBuffer dataBuffer) {
            mDataBuffer = dataBuffer;
        }

        @Override
        public void run() {
            try {
                while (true){
                    mDataBuffer.put();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Consumer implements Runnable {
        private DataBuffer buffer;

        public Consumer(DataBuffer buffer) {
            this.buffer = buffer;
        }

        @Override
        public void run() {
            try {
                while (true){
                    buffer.get();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class ProducerTask implements Runnable {
        private DataBuffer mDataBuffer;

        public ProducerTask(DataBuffer dataBuffer) {
            mDataBuffer = dataBuffer;
        }

        @Override
        public void run() {
            Thread thread = new Thread(new Producer(mDataBuffer), "Producer " );
            thread.start();

        }
    }

    public static class ConsumerTask implements Runnable {
        private DataBuffer buffer;

        public ConsumerTask(DataBuffer buffer) {
            this.buffer = buffer;
        }

        @Override
        public void run() {
            Thread thread = new Thread(new Consumer(buffer), "Consumer " );
            thread.start();
            for (int i = 0; i < 1; i++) {

            }
        }
    }
}
