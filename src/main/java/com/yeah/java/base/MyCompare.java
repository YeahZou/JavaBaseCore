package com.yeah.java.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MyCompare {

	public static void main(String[] args) {
		//testCollections();
		
		newComparatorTest();
	}
	
	@SuppressWarnings("unchecked")
	public static void newComparatorTest() {
		JSONArray issueList = new JSONArray();
		for (int i = 0; i < 12; i++) {
			JSONObject obj = new JSONObject();
			JSONArray commitList = new JSONArray();

			if (i == 7) {
				obj.put("issueNo", 7);
				issueList.add(obj);
				
				continue;
			}
			
			for (int j = 0; j < 10; j++) {
				JSONObject commit = new JSONObject();
				JSONObject committerDate = new JSONObject();
				committerDate.put("time", j % 2 == 0 ? j : 10 - j);
				
				commit.put("committerDate", committerDate);
				commit.put("commitId", 10 - j);
				
				commitList.add(commit);
			}
			
			
			
			obj.put("issueNo", i);
			obj.put("commitList", commitList);
			issueList.add(obj);
		}
		
		Collections.sort(issueList, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
            	JSONArray commitList1 = o1.optJSONArray("commitList");
            	JSONArray commitList2 = o2.optJSONArray("commitList");
            	if (CollectionUtils.isEmpty(commitList1) && CollectionUtils.isEmpty(commitList2)) {
            		return 0;
            	} else if (CollectionUtils.isEmpty(commitList1)) {
					return 1;
				} else if (CollectionUtils.isEmpty(commitList2)) {
            		return -1;
            	} else {
            		JSONObject date1 = commitList1.getJSONObject(commitList1.size() - 1).optJSONObject("committerDate");
            		JSONObject date2 = commitList2.getJSONObject(commitList2.size() - 1).optJSONObject("committerDate");
            		
            		if (date1 == null && date2 == null) {
            			return 0;
            		} else if (date1 == null) {
            			return 1;
            		} else if (date2 == null) {
            			return -1;
            		} else {
            			return (int) (date1.getLong("time") - date2.getLong("time"));
            		}
            	}
            }
        });
		
		System.out.println(issueList);
	}
	
	
	@SuppressWarnings("unchecked")
	public static void testCollections() {
		JSONArray arr = new JSONArray();
		JSONObject obj1 = new JSONObject();
		obj1.put("commitId", 2);
		
		JSONObject obj2 = new JSONObject();
		obj2.put("commitId", 1);
		
		JSONObject obj3 = new JSONObject();
		obj3.put("commitId", 3);
		
		arr.add(obj1);
		arr.add(obj2);
		arr.add(obj3);
		Collections.sort(arr, new Comparator<JSONObject>() {
			@Override
			public int compare(JSONObject o1, JSONObject o2) {
				return ((Long)o2.getLong("commitId")).compareTo((Long)o1.getLong("commitId"));
			}
		});
		
		System.out.println("调用 Collections 的 sort 对 jsonarray 排序：" + arr);
	}
	
	public static void testComparable() {
		Random random = new Random(100);
		List<TVo1> list = new ArrayList<>();
		MyCompare c = new MyCompare();
		for (int i = 0; i < 10; i++) {
			MyCompare.TVo1 tv1 = c.new TVo1();
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
	public class TVo1 implements Comparable<TVo1> {

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
}
