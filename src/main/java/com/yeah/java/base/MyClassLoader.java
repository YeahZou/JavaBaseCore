package com.yeah.java.base;

import static java.lang.System.out;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * 自定义类加载器
 * 类加载过程中的几个重要方法：
 * public Class<?> loadClass(String name, boolean resolve): 根据给定的全限定类名加载类
 * protected final Class<?> defineClass(String name, byte[] b, int off, int len): 将 byte array 转换为一个类实例 
 * protected Class<?> findClass(String name): 查找类
 * @ClassName:  MyClassLoader   
 * @Description:TODO
 * @author: zouye
 * @date:   2019年10月19日 下午3:48:09   
 * @TODO: ClassLoader vs ContextClassLoader
 *
 */
public class MyClassLoader extends ClassLoader {
	public Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] b = loadClassFromFile(name);
		
		// 将 byte array 转换为一个类实例 
		return defineClass(name, b, 0, b.length);
 	}
	
	// 模拟 loadClass 方法，这个方法是类加载器的入口
	private byte[] loadClassFromFile(String fileName) {
		InputStream in = getClass().getClassLoader().getResourceAsStream(
				fileName.replace('.', File.separatorChar) + ".class");
		byte[] buffer;
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		int next = 0;
		try {
			while((next = in.read()) != -1) {
				byteStream.write(next);
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		
		buffer = byteStream.toByteArray();
		return buffer;
	}
 	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		out.println("Classloader of this class is: " + MyClassLoader.class.getClassLoader());
		
		out.println("Classloader of Logger is: " + Logger.class.getClassLoader());
		
		// bootstrap class loader 使用 native code 编写，所以不能显示Java类信息，结果为null
		out.println("Classloader of ArrayList is: " + ArrayList.class.getClassLoader());
	}

}
