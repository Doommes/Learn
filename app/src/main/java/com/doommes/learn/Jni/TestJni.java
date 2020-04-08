package com.doommes.learn.Jni;

public class TestJni {

    static {
        System.loadLibrary("JniTest");
    }
    public native static String sayHello();

    public static native int sum(int a, int b);

}
