package com.yeah.java.base;

/**
 * 验证main方法能否不加 static 修饰符
 * @ClassName:  TestMain   
 * @Description:TODO
 * @author: zouye
 * @date:   2019年7月11日 下午6:24:59   
 *
 */
public class TestMain {

	public static void main(String[] args) {
		System.out.println("没有 static 修饰符");
		main(3);
	}
	
	// 重载
	public static void main(int a) {
		System.out.println("重载main方法,，输入参数：" + a);
	}
	
}
