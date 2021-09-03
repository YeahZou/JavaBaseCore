/**
 * 
 */
package com.yeah.java.svn;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**   
 * @ClassName   ParseLog   
 * @Description 解析 svn log 命令的输出，将其转为 vo 格式
 * @author      zouye
 * @date        2021-06-22   
 *    
 */
public class ParseLog {

	// svn log http://xxxx.com:8080/svn/projects/branches/branch-feature1@60 --search=ADMIN  --limit=10 -r60:81
	// 不能用换行符切割
	public static List<String> logs = Arrays.asList(
		// 常规 log
		"------------------------------------------------------------------------\r\n" + 
		"r81 | WB20200909003 | 2021-05-13 17:06:55 +0800 (周四, 13 5月 2021) | 1 line\r\n" + 
		"\r\n" + 
		"TAPD-100799502 SVN需求合并报错 [Revert commits (80,75) from 'branch-feature1' by ADMIN]\r\n" + 
		"------------------------------------------------------------------------\r\n" + 
		"r79 | WB20200909003 | 2021-05-13 16:19:11 +0800 (周四, 13 5月 2021) | 1 line\r\n" + 
		"\r\n" + 
		"TAPD-100799504 【代码中心】SVN需求合并报错 [Merge commits (73) from 'branch-feature4' to 'branch-feature1' by ADMIN]\r\n" + 
		"------------------------------------------------------------------------\r\n"
		,
		
		// 无结果的 log
		"------------------------------------------------------------------------\r\n"
		,
		
		// message 中有换行符的 log
		"------------------------------------------------------------------------\r\n" + 
		"r97 | WB20200909003 | 2021-06-22 19:09:55 +0800 (周二, 22 6月 2021) | 4 lines\r\n" + 
		"\r\n" + 
		"测试 message 中有 换行符\\r\\n我前面是换行符\r\n" + 
		"\r\n" + 
		"我上面有个空行，后面也有一个空行\r\n" + 
		"\r\n" + 
		"------------------------------------------------------------------------\r\n"
		,
		
		// message 中有 log 分割线
		"------------------------------------------------------------------------\r\n" + 
		"r98 | WB20200909003 | 2021-06-22 19:14:33 +0800 (周二, 22 6月 2021) | 5 lines\r\n" + 
		"\r\n" + 
		"------------------------------------------------------------------------\r\n" + 
		"测试 message 中有 log 分割线\r\n" + 
		"------------------------------------------------------------------------\r\n" + 
		"\r\n" + 
		"------------------------------------------------------------------------\r\n" + 
		"------------------------------------------------------------------------\r\n"
		,
		
		// 有文件的 log,  svn log . --limit 1 -v -r10:19
		"------------------------------------------------------------------------\r\n" + 
		"r16 | techsure | 2020-10-18 19:09:16 +0800 (周日, 18 10月 2020) | 1 line\r\n" + 
		"Changed paths:\r\n" + 
		"   A /branches/project1\r\n" + 
		"   A /branches/project1/file1.txt\r\n" + 
		"   A /branches/project1/file2.txt\r\n" + 
		"   A /branches/project1/file3.txt\r\n" + 
		"   A /branches/project2\r\n" + 
		"   A /branches/project2/file1.txt\r\n" + 
		"   A /branches/project2/file2.txt\r\n" + 
		"   A /branches/project2/file3.txt\r\n" + 
		"   A /branches/project3\r\n" + 
		"   A /branches/project3/file1.txt\r\n" + 
		"   A /branches/project3/file2.txt\r\n" + 
		"   A /branches/project3/file3.txt\r\n" + 
		"\r\n" + 
		"init\r\n" + 
		"------------------------------------------------------------------------\r\n"
	);
	
	public static void main(String[] args) throws IOException {
		SvnChangeLogParser parser = new SvnChangeLogParser();
		
		for (String log: logs) {
			System.out.println(parser.parse(log));
		}
		
		//System.out.println(parser.parse(logs.get(2)));
	}

}
