package com.yeah.java.base.jmh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.GroupThreads;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.profile.StackProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

@Measurement(iterations = 1)
@Warmup(iterations = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class RegExp {
	String[] commitMessages = new String[] {
			"ISSUE-1,ISSUE-2,ISSUE-3 测试场景1，标准格式",
			"  ISSUE-1,ISSUE-2,ISSUE-3 测试场景2，开头有空格",
	        "  ISSUE-1,ISSUE-2,ISSUE-3   测试场景3，开头结尾有空格",
	        "  ISSUE-1,ISSUE-2,ISSUE-3测试场景4，开头有空格，结尾没有空格",
	        "  ISSUE-1,ISSUE-2,ISSUE-3测试场景5，开头有空格， ISSUE-10,ISSUE-20 结尾没有空格，中间有需求号",
	        "  ISSUE-1 ,ISSUE-2, ISSUE-3测试场景6，开头有空格， ISSUE-10,ISSUE-20 结尾没有空格，中间有需求号，需求之间逗号空格",
	        "  ISSUE-1 , ,，ISSUE-2, ISSUE-3 ，测试场景7，开头有空格， ISSUE-10,ISSUE-20 结尾空格+中文逗号，中间有需求号，需求之间逗号空格",
	        "  ISSUE-1 ， ISSUE-2, ISSUE-3测试场景8，开头有空格， ISSUE-10,ISSUE-20 结尾没有空格，中间有需求号，需求之间中文逗号空格",
	        "  ISSUE-1测试场景9，一个需求",
	        "  测试场景10，没有需求",
	        "  ",
	        "  ISSUE-1，ISSUE-10,ISSUE-20,,ISSUE-2, ISSUE-3,ISSUE-2, ISSUE-3,ISSUE-2, ISSUE-3,ISSUE-2, ISSUE-3,ISSUE-2, ISSUE-3,ISSUE-2, ISSUE-3测试场景12,很多需求需求",
	};
	
	Pattern pattern1 = Pattern.compile("^\\s*(\\w+-\\d+)([,，\\s]|)+");
	Pattern pattern2 = Pattern.compile("^\\s*(([a-zA-Z]+-\\d+)([,，\\s]|)+)+");
	
	// 通过不断截掉已匹配前缀的方式获取 message 中的所有需求号
	@Benchmark
	public void issueNoGetIterator() {
		for (String commit: commitMessages) {
			Matcher matcher = pattern1.matcher(commit);
			List<String> issueList = new ArrayList<>();
			while(matcher.find()) {
				String issueNo = matcher.group(1);
				issueList.add(issueNo);
				
				commit = StringUtils.substringAfter(commit, matcher.group(0));
				matcher = pattern1.matcher(commit);
			}
		}
	}
	
	// 一次获取所有需求
	@Benchmark
	public void issueNoGetOnce() {
		for (String commit: commitMessages) {
			Matcher matcher = pattern2.matcher(commit);
			List<String> issueList = new ArrayList<>();
			
			if (matcher.find() && matcher.groupCount() > 0) {
				String issueNos = matcher.group(0).trim();
				
				String[] issueArr = issueNos.split("[,，\\s]+");
				if (issueArr != null && issueArr.length > 0) {
					for (int i = 0; i < issueArr.length; i++) {
						if ("".equals(issueArr[i])) {
							continue;
						}
						
						issueList.add(issueArr[i]);
					}
				}
			}
		}
	}

	public static void main(String[] args) throws RunnerException {
		Options opts = new OptionsBuilder()
				.include(RegExp.class.getSimpleName())
				.forks(1)
				.timeout(TimeValue.seconds(100))
				.addProfiler(StackProfiler.class)
				.build();
		new Runner(opts).run();
	}
}
