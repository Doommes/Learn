package com.doommes.learn.Thread_nine;

class Singleton {
    private static final Singleton ourInstance = new Singleton();

    static Singleton getInstance() {
        return ourInstance;
    }

    private Singleton() {
    }
}
