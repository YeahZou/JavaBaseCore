package com.yeah.java.base.thread;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池有两个作用：
 * <p>1、避免线程被频繁创建和销毁带来的时间消耗
 * <p>2、避免并发度过高带来的机器资源消耗
 * <p>第一个问题主要通过两个参数 corePoolSize 和 maximumPoolSize 控制，如果不设置超时机制，线程池中的线程不会被销毁
 * <p>第二个问题则使用阻塞队列控制，当要执行的任务数量小于等于 corePoolSize时，新建线程执行任务；
 * 超过 corePoolSize 时，任务会被放到阻塞队列；
 * 队列满了还有任务，且线程池里面线程的数量小于 maximumPoolSize，则新建线程执行任务；
 * 队列满了，线程池也满了，则执行拒绝策略。
 *
 */
public class MyThreadPool {

    public static void main(String[] args) throws InterruptedException, IOException {
        int corePoolSize = 1;
        int maximumPoolSize = 3;
        long keepAliveTime = 10;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(5);
        ThreadFactory threadFactory = new NameTreadFactory();
        RejectedExecutionHandler handler = new MyIgnorePolicy();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        //executor.prestartAllCoreThreads(); // 预启动所有核心线程
        
        for (int i = 1; i <= 10; i++) {
            MyTask task = new MyTask(String.valueOf(i));
            executor.execute(task);
        }

        //System.in.read(); //阻塞主线程
        executor.awaitTermination(40000, unit);
    }

    static class NameTreadFactory implements ThreadFactory {

        private final AtomicInteger mThreadNum = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "my-thread-" + mThreadNum.getAndIncrement());
            System.out.println(t.getName() + " has been created");
            return t;
        }
    }

    public static class MyIgnorePolicy implements RejectedExecutionHandler {

        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            doLog(r, e);
        }

        private void doLog(Runnable r, ThreadPoolExecutor e) {
            // 可做日志记录等
            System.err.println( r.toString() + " rejected");
//          System.out.println("completedTaskCount: " + e.getCompletedTaskCount());
        }
    }

    static class MyTask implements Runnable {
        private String name;

        public MyTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
            	System.out.println(this.toString() + " is start...!");
                Thread.sleep(30000); //让任务执行慢点
                System.out.println(this.toString() + " is finished!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "MyTask [name=" + name + "]";
        }
    }
}