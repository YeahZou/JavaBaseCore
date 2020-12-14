package com.yeah.java.base;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 验证main方法能否不加 static 修饰符
 * @ClassName:  TestMain   
 * @Description:TODO
 * @author: zouye
 * @date:   2019年7月11日 下午6:24:59   
 *
 */
public class TestMain {
	static {
		System.out.println("静态块方法");
	}
	
	static Integer i;
	public static void main(String[] args) {
		System.out.println("没有 static 修饰符");
		
		List<String> list = Arrays.asList("1", "2");
		list.add("3");
		System.out.println(list);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		System.out.println(new Date(System.currentTimeMillis() - 60 * 60 * 1000));
		main(3);
	}
	
	// 重载
	public static void main(int a) {
		System.out.println("重载main方法,，输入参数：" + a);
		System.out.println(i);
	}
	
}
