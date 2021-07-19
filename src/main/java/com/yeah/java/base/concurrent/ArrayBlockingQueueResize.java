package com.yeah.java.base.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/** ArrayBlockingQueueResize 不支持自动扩容 */
public class ArrayBlockingQueueResize {

	public static BlockingQueue<Integer> quene = new ArrayBlockingQueue<>(2);
	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			ArrayBlockingQueueResize.quene.add(1);
		}
		
		System.out.println(ArrayBlockingQueueResize.quene);
	}

}
