package com.yeah.javabasic.util.file;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileLock;

/** 测试临时文件操作，本质上就是一个普通文件，只不过是在 JVM 退出时会调用一下删除 */
public class TempFile {

	public static void main(String[] args) {
		// 在分支目录的同级，创建锁文件，名称：<branch-name>.lock
		File tmpFile = null;
		FileLock fileLock = null;
		try {
			tmpFile = File.createTempFile("branch", ".lock", new File("D:\\data\\workingcopy\\0000bbbf21002000\\Repository99\\关联交易管理信息系统\\03源码类"));
			tmpFile.deleteOnExit();
			
		} catch (IOException e) {
			
		} finally {
			if (tmpFile != null) {
				tmpFile.delete();
			}
		}

	}

}
