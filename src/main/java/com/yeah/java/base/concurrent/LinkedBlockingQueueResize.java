package com.yeah.java.base.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueResize {

	public static BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			queue.add(i);
		}
		
		System.out.println(queue);
		for (int i = 0; i < 10; i++) {
			System.out.println(queue.poll());
		}
	}
}
