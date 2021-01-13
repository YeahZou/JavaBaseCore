/**
 * 
 */
package javabasic;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.yeah.javabasic.util.file.Cache;

/**   
 * @ClassName   CacheTest   
 * @Description TODO
 * @author      zouye
 * @date        2021-01-12   
 *    
 */
public class CacheTest {

	private static String repoServiceUuid = "0000bbbf21002000";
	private static String repoUuid = "0000bbbf21002000";
	private static String srcBranch = "scr_branch";
	private static String targetBranch = "target_branch";
	private static String leftCommitId = "1234";
	private static String rightCommitId = "4321";
	private static String content = "[{\"a\":1}, {\"b\": 2}]";

	
	public static void main(String[] args) {
		try {
			//writeCommitCacheNotExist();
			// writeCommitCacheContentBig();
			writeSVNDiffToCache();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	/** 缓存目录不存在的情况下写入commit缓存 
	 * @throws IOException */
	public static void writeCommitCacheNotExist() throws IOException {
		Cache.writeSVNCommitsToCache(repoServiceUuid, repoUuid, srcBranch, targetBranch, leftCommitId, rightCommitId, content);
	}
	
	/** 缓存内容超过最新缓存文件的大小，需要生成新的缓存文件 */
	public static void writeCommitCacheContentBig() throws IOException {
		Cache.writeSVNCommitsToCache(repoServiceUuid, repoUuid, srcBranch, targetBranch, leftCommitId, rightCommitId, content);
	}
	
	/** 写diff缓存 
	 * @throws IOException */
	public static void writeSVNDiffToCache() throws IOException {
		Cache.writeSVNDiffToCache(repoServiceUuid, repoUuid, srcBranch, targetBranch, leftCommitId, rightCommitId, content);
	}
}
