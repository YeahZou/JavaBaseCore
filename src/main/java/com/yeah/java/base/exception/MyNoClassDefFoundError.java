package com.yeah.java.base.exception;

public class MyNoClassDefFoundError {

	// 疑问：两次都是调用 new ClassWithInitError()，为什么两次抛出的异常不同
	public static void main(String[] args) {
		ClassWithInitError error;
		
		try {
			error = new ClassWithInitError();
		} catch (Throwable t) {
			// java.lang.ExceptionInInitializerError
			System.out.println(t);
		}
		
		// java.lang.NoClassDefFoundError: Could not initialize class com.yeah.java.base.exception.ClassWithInitError
		error = new ClassWithInitError();
	}
}
