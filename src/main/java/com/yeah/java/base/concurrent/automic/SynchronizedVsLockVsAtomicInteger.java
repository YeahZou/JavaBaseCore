/**
 * 使用 JVH 做 synchronized，lock、atomic 的性能对比
 */
package com.yeah.java.base.concurrent.automic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.GroupThreads;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.profile.StackProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

/**   
 * @author      zouye
 * @date        2021-07-10   
 *    
 */

@Measurement(iterations = 10)
@Warmup(iterations = 10)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class SynchronizedVsLockVsAtomicInteger {

	// 将 IntMonitor 设置为线程组共享，也就是允许多个线程并发执行
	@State(Scope.Group)
	public static class IntMonitor {
		private int x;
		private AtomicInteger ax = new AtomicInteger();
		
		private final Lock lock = new ReentrantLock();	
		
		// 使用显式锁进行同步
		public void lockInc() {
			lock.lock();
			try {
				x++;
			} finally {
				lock.unlock();
			}
		}
		
		// 使用 synchronized 关键字进行同步
		public void syncInc() {
			synchronized(this) {
				x++;
			}
		}
		
		// 使用 AtomicInteger
		public void atomicInc() {
			ax.incrementAndGet();
		}
	}
	
	// 在线程组 “synchronized” 中，有 10 个线程将会对 IntMonitor 类实例的 syncInc 方法进行调用
	@GroupThreads(10)
	@Group("synchronized")
	@Benchmark
	public void syncInc(IntMonitor monitor) {
		monitor.syncInc();
	}
	
	@GroupThreads(10)
	@Group("lock")
	@Benchmark
	public void lockInc(IntMonitor monitor) {
		monitor.lockInc();
	}
	
	@GroupThreads(10)
	@Group("atomic")
	@Benchmark
	public void atomicInc(IntMonitor monitor) {
		monitor.atomicInc();
	}
	
	public static void main(String[] args) throws RunnerException {
		Options opts = new OptionsBuilder()
				.include(SynchronizedVsLockVsAtomicInteger.class.getSimpleName())
				.forks(1)
				.timeout(TimeValue.seconds(10))
				.addProfiler(StackProfiler.class)
				.build();
		new Runner(opts).run();
	}

}
