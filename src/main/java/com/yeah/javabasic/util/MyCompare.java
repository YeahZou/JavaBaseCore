package com.yeah.javabasic.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MyCompare {

	public static void main(String[] args) {
		
		/**
		 * 实现Comparable接口的对象可以使用List的sort方法排序，该方法实现升序（自然顺序）排序
		 * 通过compareTo 方法的返回值来决定要不要交换两个元素的顺序
		 * 返回值小于等于0，不交换元素顺序，因为此时已经是升序了
		 * 大于0，交换顺序
		 * 
		 * @author: zouye
		 * @date:   2019年6月4日 下午6:21:31   
		 *
		 */
		class TVo1 implements Comparable<TVo1> {

			private String name;
			
			private Integer age;
			
			public void setName(String name) {
				this.name = name;
			}
			
			public String getName() {
				return this.name;
			}
			
			public void setAge(Integer age) {
				this.age = age;
			} 
			
			public Integer getAge() {
				return this.age;
			}
			
			public int compareTo(TVo1 o) {
				int v = this.age - o.age;
				System.out.println(this.toString() + o.toString());
				System.out.println(v);
				return v;
			}
			
			public String toString() {
				return "name:" + this.name + ", age: " + this.age + "\n";
			}
			
		}
		
		Random random = new Random(100);
		List<TVo1> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			TVo1 tv1 = new TVo1();
			tv1.setAge(random.nextInt(100) + 1);
			tv1.setName(10 - i + "Name");
			
			list.add(tv1);
		}
		
		System.out.println("Before sort:");
		System.out.println(list);
		
		// 实际上调用的是 List的sort方法
		Collections.sort(list);
		System.out.println("After sort:");
		System.out.println(list);
	}
	
	
}
