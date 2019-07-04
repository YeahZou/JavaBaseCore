package com.yeah.javabasic.util;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExp {
	public static void main(String[] args) {
		// 竖线在正则中有特殊含义，需转义
		String sep = "\\|";
		String envStr = "|SIT|PRD|";
		String[] envArr = envStr.split(sep);
		System.out.println(Arrays.asList(envArr));

		// 测试使用 Matcher 的 appendReplacement 方法，
		// $1~$9 有特殊含义
		String originStr = "repotype.develop=git\r\n" + 
				"repo.develop=http://192.168.0.22/deploy/branches/{{version}}\r\n" + 
				"gitbranch.develop=develop\r\n" + 
				"\r\n" + 
				"repotype=git\r\n" + 
				"repo=http://{{user}}:{{password}}@192.168.0.88/deploy/easydeploy.git\r\n" + 
				"git.masterBranch=master\r\n" + 
				"git.user=test\r\n" + 
				"git.password= test \r\n" + 
				"gitbranch=master\r\n" + 
				"\r\n" + 
				"agenttype=agentless\r\n" + 
				"ostype=unix\r\n" + 
				"osport=22\r\n" + 
				"osuser=ADMIN\r\n" + 
				"ospwd=aaaaaaa\r\n" + 
				"\r\n" + 
				"mailto=123456@techsure.com.cn,2222@sina.com\r\n" + 
				"mailcc=\r\n" + 
				"\r\n" + 
				"log.patterns=/app/logs/balantflow/*\r\n" + 
				"\r\n" + 
				"instance.looptype=outer\r\n" + 
				"instance.trigger.round=first\r\n" + 
				"\r\n" + 
				"";
		Pattern pattern = Pattern.compile("(ospwd|password\\s*=)(.+)");
		Matcher m = pattern.matcher(originStr);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			System.out.println(m.group());
			System.out.println(m.group(2));
			
			m.appendReplacement(sb, "$1" + m.group(2).toUpperCase());
		}
		m.appendTail(sb);

		System.out.println(sb.toString());
	}
}
