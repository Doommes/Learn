package com.doommes.learn.Thread_nine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ExcuteThread {

    List<String> list = new ArrayList<>();

    public synchronized void add(String string){
        list.add(string);
    }

    public  void send(){
        synchronized (list){
            System.out.println(Arrays.toString(list.toArray()));
        }
    }

    public static void main (String[] args){
        ExcuteThread excuteThread = new ExcuteThread();
        ExecutorService executorService= Executors.newFixedThreadPool(5);
        for (int i=0; i<5;i++){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    excuteThread.add("data");
                }
            });
        }
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                excuteThread.send();
            }
        });
    }
}
