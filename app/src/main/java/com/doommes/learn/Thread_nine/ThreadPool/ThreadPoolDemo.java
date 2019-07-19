package com.doommes.learn.Thread_nine.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolDemo {
        private ExecutorService mExecutor = Executors.newCachedThreadPool();
        public void test(){
            mExecutor.shutdown();

        }
}
