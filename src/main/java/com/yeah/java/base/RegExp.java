package com.yeah.java.base;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import static java.lang.System.out;

public class RegExp {
	public static void parseDBexecIndexFilter(String content, String opt) {
		String filter = "";
		String index  = "";
		
		if (StringUtils.isBlank(opt)) {
			return;
		}
		
		if (content != null && !content.trim().equals("")) {
			String[] lines = content.split("(\n|\r\n)");
			Pattern p = Pattern.compile("^dbexec.+?-" + opt + "\\s+([^-\\s].*)$");
			for (String line: lines) {
				line = line.trim();
				if (line.equals("") || line.startsWith("#")) {
					continue;
				}
				
				Matcher m = p.matcher(line);
				if (m.find() && m.groupCount() == 1) {
					String valuePattern = m.group(1).startsWith("\"") ? "^\"([^\"]+)\"" : m.group(1).startsWith("\'") ? "^\'([^\']+)\'" : "^(\\S+)";
					p = Pattern.compile(valuePattern);
					m = p.matcher(m.group(1));
					filter = m.find() && m.groupCount() == 1 ? m.group(1) : ""; 
				}
			}
		}
		
		System.out.println("filter is " + filter);
		System.out.println("index is " + index);
	}
	
	public static void appendReplacement() {
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
	
	public static void main(String[] args) {
	    /*Pattern pattern = Pattern.compile("^[\\w\\W]+?\\s+class\\s+\\w+\\s*\\{[^\\}]+class\\s+\\w+\\s*\\{[^\\;]+?\\s+(\\w+)\\s*\\;[\\w\\W]+\\}");
	    Matcher matcher = pattern.matcher("public class Test {\n" +
	            "\n" +
	            "    private String test;\n" +
	            "      class InnerClass{\n" +
	            "          private String test1;\n" +
	            "          private String test2;\n" +
	            "      }\n" +
	            "}");

	    if(matcher.find()){
	        System.out.println(matcher.group(1));
	    }
	    
	    System.out.println("xxx is: " + "".split("(\n|\r\n)")[0]);*/
		List<String> fileList = Arrays.asList("src4", "src3/uddi-address.xml", "src5", "src5/uddi-address.xml", "uddi-address.xml");
		for (int i = 0; i < fileList.size(); i++) {
			System.out.println(fileList.get(i).split("[\\\\/]")[0]);
		}
	}
	
	/*public static void main(String[] args) {
		//out.println("src/123/234/345/xxx.java".replaceAll("^.*", ""));
		//out.println("src/123/234/345/xxx.java".replaceAll("^(.+)/[^/]+$", "$1"));
		
		Pattern p = Pattern.compile("^[\\w\\W]+?\\s+class\\s+\\w+\\s*\\{[^\\}]+class\\s+\\w+\\s*\\{[^\\;]+?\\s+(\\w+)\\s*\\;.*\\}");
		String code = "public class Test {\\r\\nprivate String test;\\r\\n\\tclass InnerClass{\\r\\nprivate  String   test1 ; private  String   test2  ;  private String test3;\\r\\n\\t}}";
		Matcher m = p.matcher(code);
		
		if (m.find()) {
			String vars = m.group(1);
			String[] varArr = vars.split(";");
			for (int i = 0; i < varArr.length; i++) {
				System.out.println(varArr[i].trim());
			}
			System.out.println(vars);
		}
	}*/
}
