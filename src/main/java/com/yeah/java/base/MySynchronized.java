package com.yeah.java.base;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class MySynchronized {

	public static int staticSum = 0;
	
	public static void sumStaticCount() {
		staticSum ++;
	}
	
	// 对 MySynchronized 类的class对象加锁
	public static void synchronizedSumStaticCount() {
		synchronized(MySynchronized.class) {
			staticSum++;
		}
	}
	
	public class MySynchronizedMethods {
		 
	    private int sum = 0;
	 
	    // 未同步的方法
	    public void calculate() {
	        setSum(getSum() + 1);
	    }
	    
	    // 加了synchronized 关键字的方法
	    public synchronized void synchronizedCalculate() {
	    	setSum(getSum() + 1);
	    }
	    
	    // 另一种实现方式
	    public void synchronizedBlock() {
	    	synchronized(this) {
	    		setSum(getSum() + 1);
	    	}
	    }

		public int getSum() {
			return sum;
		}

		public void setSum(int sum) {
			this.sum = sum;
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService service = Executors.newFixedThreadPool(3);
		MySynchronized mySynch = new MySynchronized();
		MySynchronizedMethods sumObj = mySynch.new MySynchronizedMethods();
		
		// 如果接口时 FunctionalInterface，则可以在lambda表达式中使用
		// IntStream.range(0, 1000).forEach(count -> service.submit(() -> sumObj.calculate()));
		IntStream.range(0, 1000).forEach(count -> service.submit(sumObj::calculate));
		service.awaitTermination(1000, TimeUnit.MILLISECONDS);
		
		// 由于存在竞争，执行1000次累加的结果可能小于1000，
		// 线程1 get了 sum，还没有执行累加操作，线程2也get了sum，那么他们拿到的sum值就是一样的，
		// 这两个线程此次执行的累加操作只相当于执行一次的效果
		System.out.println("No thread synchronized calculate result: " + sumObj.getSum());
		
		MySynchronizedMethods syncObj = mySynch.new MySynchronizedMethods();
		IntStream.range(0, 1000).forEach(count -> service.submit(syncObj::synchronizedCalculate));
		service.awaitTermination(1000, TimeUnit.MILLISECONDS);
		System.out.println("synchronized method calculate result: " + syncObj.getSum());
		
		MySynchronizedMethods syncObj2 = mySynch.new MySynchronizedMethods();
		IntStream.range(0, 1000).forEach(count -> service.submit(syncObj2::synchronizedBlock));
		service.awaitTermination(1000, TimeUnit.MILLISECONDS);
		System.out.println("synchronized block calculate result: " + syncObj2.getSum());
		
		IntStream.range(0, 1000).forEach(count -> service.submit(MySynchronized::sumStaticCount));
		service.awaitTermination(1000, TimeUnit.MILLISECONDS);
		System.out.println("No synchronized static calculate result: " + MySynchronized.staticSum);
		
	}

}
 