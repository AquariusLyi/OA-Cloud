package com.atguigu.practise;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {
    private static Thread t1;
    private static Thread t2;
    private static Thread t3;
    public static void main(String[] args) {

        t1 = new Thread(() -> {
            System.out.println("T1 is running.");
            LockSupport.unpark(t2); // 唤醒线程T2
        });

        t2 = new Thread(() -> {
            LockSupport.park(); // 阻塞线程T2
            System.out.println("T2 is running.");
            LockSupport.unpark(t3); // 唤醒线程T3
        });

        t3 = new Thread(() -> {
            LockSupport.park(); // 阻塞线程T3
            System.out.println("T3 is running.");
        });


        t1.start();
        t2.start();
        t3.start();
    }
}