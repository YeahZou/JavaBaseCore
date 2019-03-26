package com.yeah.javabasic.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.json.JSONObject;

public class JList {

	// list.retainAll(collection)方法只保留list中满足此条件的元素：该元素在collection中存在
	// 1、如果list为空，不管collection是什么，最终list都为空
	// 2、如果collection为空，最终list都为空
	public void testRetainAll() {
		List<String> stringList = new ArrayList<String>();
		List<String> strList2 = Arrays.asList("a", "b", "c");
		List<String> strList3 = Arrays.asList("c", "d", "e");
		
		stringList.add("a");
		stringList.add("b");
		stringList.retainAll(strList2);
		System.out.println("retainAll strList2: " + stringList);
		stringList.retainAll(strList3);
		System.out.println("retainAll strList2: " + stringList);
	}
	
	public static void main(String[] argv) {
		JList jList = new JList();
		jList.testRetainAll();
		
		String obj = "{\"repo_git_conf\":[{\"repotype\":\"git\",\"repo\":\"http://{{user}}:{{password}}@192.168.0.82:7070/octopus/octopus-proxy.git\",\"gitbranch\":\"release\",\"git.masterBranch\":\"\",\"git.user\":\"\",\"git.password\":\"\"}],\"remote_connect\":[{\"agenttype\":\"agentless\",\"ostype\":\"unix\",\"osport\":\"22\",\"osuser\":\"root\",\"ospwd\":\"\"}]}";
		JSONObject json = JSONObject.fromObject(obj);
		System.out.println(json.toString(4));
	}
}
