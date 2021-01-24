package com.yeah.java.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang.StringUtils;
import org.bouncycastle.util.Strings;

import static java.lang.System.out;

public class MyFile {
	public static void main(String[] args) throws Exception {
		LineNumberReader br = null;
		FileInputStream in = new FileInputStream("D:\\myworkspace\\test2021\\test");

		try {
			
			File file = new File("D:\\aaaa\\bbbb");
			System.out.println(StringUtils.join(file.list(), ","));
			System.out.println(file.createNewFile());
			/*br = new LineNumberReader(new InputStreamReader(in));

			// 统计行数完成，流也就结束了，如果文件比buffer大，无法再回到文件开头
			System.out.println("File line count is: " + br.lines().count());
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}*/
		} catch(Exception e) {
		} finally {
			if (in != null) {
				in.close();
			}
			
			if (br != null) {
				br.close();
			}
		}
	}

}
