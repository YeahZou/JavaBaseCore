package com.yeah.java.base.concurrent;

import java.util.concurrent.*;
import java.util.stream.IntStream;
public class MyVolatile {

	private volatile int count = 0;
    
	// ++ 是非原子操作，多线程条件下使用volatile并不能关键字并不能得到正确值
    public void increamentCount() {      
        count++;
    }
    public int getCount() {
        return count;
    }
    
	public static void main(String[] args) throws InterruptedException {
		MyVolatile myMain = new MyVolatile();
		ExecutorService service = Executors.newFixedThreadPool(3);
		IntStream.range(0, 1000).forEach(count -> service.submit(myMain::increamentCount));
		service.awaitTermination(1000, TimeUnit.MILLISECONDS);
		service.shutdown();
		System.out.println(myMain.getCount());
	}
}
