package com.doommes.learn.jvm;
/**
 * VM agrs: -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails
 * -XX:SurvivorRatio=8
 */

public class MinorGCTest {

        private static final int _10MB = 10 *1024 *1024;

        public static void testAllocation(){
            byte[] a1, a2, a3, a4;
            a1 = new byte[2 *_10MB];
            a2 = new byte[2 *_10MB];
            a3 = new byte[2 *_10MB];
            a4 = new byte[1 *_10MB];
        }

        public static void main(String[] args) {
            testAllocation();
        }

}
