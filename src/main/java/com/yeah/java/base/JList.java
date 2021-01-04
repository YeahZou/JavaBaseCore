package com.yeah.java.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import net.sf.json.JSONObject;

public class JList {

	// list.retainAll(collection)方法只保留list中满足此条件的元素：该元素在collection中存在(交集)
	// 1、如果list为空，不管collection是什么，最终list都为空
	// 2、如果collection为空，最终list都为空
	public void retainAll(Collection<String> a, Collection<String> b) {
		List<String> stringList = new ArrayList<String>();
		
		stringList.add("a");
		stringList.add("b");
		System.out.println(String.format("\n%s retainAll %s: ", stringList, a));
		stringList.retainAll(a);
		System.out.println(stringList);
	}
	
	/** 并集-交集 */
	public void disjunction(Collection<String> a, Collection<String> b) {
		System.out.println(String.format("\n%s disjunction %s: ", a, b));
		System.out.println(CollectionUtils.disjunction(a, b));
	}
	
	/** 求交集 */
	public void intersection(Collection<String> a, Collection<String> b) {
		System.out.println(String.format("\n%s intersection %s: ", a, b));
		System.out.println(CollectionUtils.intersection(a, b));
	}
	
	/** a + b (求并集) */
	public void union(Collection<String> a, Collection<String> b) {
		System.out.println(String.format("\n%s union %s: ", a, b));
		System.out.println(CollectionUtils.union(a, b));
	}
	
	/** a - b (只在 a 中不在 b 中)*/
	public void subtract(Collection<String> a, Collection<String> b) {
		System.out.println(String.format("\n%s subtract %s: ", a, b));
		System.out.println(CollectionUtils.subtract(a, b));
	}
	
	public static void main(String[] argv) {
		List<String> a = new ArrayList<>(Arrays.asList("a", "b", "c"));
		List<String> b = new ArrayList<>(Arrays.asList("c", "d", "e"));
		JList jList = new JList();
		
		jList.retainAll(a, b);
		
		jList.disjunction(a, b);
		jList.intersection(a, b);
		jList.union(a, b);
		jList.subtract(a, b);
	}
}
