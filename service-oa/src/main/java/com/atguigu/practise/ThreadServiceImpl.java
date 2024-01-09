package com.atguigu.practise;

import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Service
public class ThreadServiceImpl  {



    public void threadMessage(Long processId, Long userId, String taskId) {
        Thread T1 = new Thread(() -> {
            // 线程 T1 的任务
        });
        Thread T2 = new Thread(() -> {
            try {
                T1.join(); // 等待 T1 执行完成
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 线程 T2 的任务
        });

        Thread T3 = new Thread(() -> {
            try {
                T2.join(); // 等待 T2 执行完成
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 线程 T3 的任务
        });
        T1.start();
        T2.start();
        T3.start();
        CountDownLatch latch1 = new CountDownLatch(1);
        CountDownLatch latch2 = new CountDownLatch(1);

        Thread t1 = new Thread(() -> {
            System.out.println("T1 running.");
            latch1.countDown(); // T1 执行完后释放 latch1
        });

        Thread t2 = new Thread(() -> {
            try {
                latch1.await(); // 等待 latch1 的释放
                System.out.println("T2 running.");
                latch2.countDown(); // T2 执行完后释放 latch2
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t3 = new Thread(() -> {
            try {
                latch2.await(); // 等待 latch2 的释放
                System.out.println("T3 running.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
        t3.start();

    }

}
