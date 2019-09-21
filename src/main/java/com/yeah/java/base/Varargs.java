package com.yeah.java.base;

/**
 * 变参方法可接收一个数组参数
 * @ClassName:  Varargs   
 * @Description:TODO
 * @author: zouye
 * @date:   2019年7月4日 上午10:57:08   
 *
 */
public class Varargs {
	public  static void main(String[] args) {
		int[] arr = {1,2,3,4,5,6};
		System.out.println("给变长参数方法传入数组：");
		test(arr);
		System.out.println("给变长参数方法传入不定长参数：");
		test(9, 6, 7, 8);
	}
	
	// 定义变长参数的方法
	 private static void test(int... params) {
		 StringBuffer sb = new StringBuffer();
		 for (int i = 0; i < params.length; i++) {
			 sb.append(params[i]);
			 sb.append(", ");
		 }
		 
		System.out.println(sb.toString());
	}
}
