package com.yeah.java.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;

import static java.lang.System.out;

import java.io.File;
import java.nio.file.Paths;

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
	
	/** 替换模板文本中所有满足正则模式的内容 */
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
	
	/** windows 和 linux 的路径分割符 */
	public static void filePathSeparator() {
		List<String> fileList = Arrays.asList("src4", "src3/uddi-address.xml", "src5", "src5/uddi-address.xml", "uddi-address.xml");
		for (int i = 0; i < fileList.size(); i++) {
			System.out.println(fileList.get(i).split("[\\\\/]")[0]);
		}
	}
	
	/** 识别内部类的成员变量 */
	public static void getInnerClassMember() {
		Pattern pattern = Pattern.compile("^[\\w\\W]+?\\s+class\\s+\\w+\\s*\\{[^\\}]+class\\s+\\w+\\s*\\{[^\\;]+?\\s+(\\w+)\\s*\\;[\\w\\W]+\\}");
	    Matcher matcher = pattern.matcher("public class Test {\n" +
	            "\n" +
	            "    private String test;\n" +
	            "      class InnerClass{\n" +
	            "          private String test1;\n" +
	            "          private String test2;\n" +
	            "      }\n" +
	            "}");

	    if (matcher.find()) {
	        System.out.println(matcher.group(1));
	    }
	    
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
	}
	
	public static void issueNoGet() {
		String issueSeparator = ",，\\s";
		String issuePattern = "^\\s*(\\w+-\\d+)";
		String[] commitMessages = new String[] {
				"ISSUE-1,ISSUE-2,ISSUE-3 测试场景1，标准格式",
				"  ISSUE-1,ISSUE-2,ISSUE-3 测试场景2，开头有空格",
		        "  ISSUE-1,ISSUE-2,ISSUE-3   测试场景3，开头结尾有空格",
		        "  ISSUE-1,ISSUE-2,ISSUE-3测试场景4，开头有空格，结尾没有空格",
		        "  ISSUE-1,ISSUE-2,ISSUE-3测试场景5，开头有空格， ISSUE-10,ISSUE-20 结尾没有空格，中间有需求号",
		        "  ISSUE-1 ,ISSUE-2, ISSUE-3测试场景6，开头有空格， ISSUE-10,ISSUE-20 结尾没有空格，中间有需求号，需求之间逗号空格",
		        "  ISSUE-1 , ISSUE-2, ISSUE-3 ，测试场景7，开头有空格， ISSUE-10,ISSUE-20 结尾空格+中文逗号，中间有需求号，需求之间逗号空格",
		        "  ISSUE-1 ， ISSUE-2, ISSUE-3测试场景8，开头有空格， ISSUE-10,ISSUE-20 结尾没有空格，中间有需求号，需求之间中文逗号空格",
		        "  ISSUE-1测试场景9，一个需求",
		};
		
		String finalPattern = String.format("%s([%s]|)+", issuePattern, issueSeparator);
		Pattern pattern = Pattern.compile(finalPattern);
		for (String commit: commitMessages) {
			Matcher matcher = pattern.matcher(commit);
			List<String> issueList = new ArrayList<>();
			while(matcher.find()) {
				String issueNo = matcher.group(1);
				issueList.add(issueNo);
				
				matcher = pattern.matcher(StringUtils.substringAfter(commit, matcher.group(0)));
			}
			
			System.out.println(String.format("commit message: %s, issue list: %s", commit, issueList));
		}
	}
	
	public static void main(String[] args) {
		issueNoGet();
	}
}
