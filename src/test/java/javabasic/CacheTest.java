/**
 * 
 */
package javabasic;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.yeah.javabasic.util.file.Cache;

import net.sf.json.JSONArray;

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
	private static JSONArray content = JSONArray.fromObject("[{\r\n" + 
			"	\"authorEmail\": \"\",\r\n" + 
			"	\"authorDate\": {\r\n" + 
			"		\"date\": 25,\r\n" + 
			"		\"hours\": 16,\r\n" + 
			"		\"seconds\": 27,\r\n" + 
			"		\"month\": 11,\r\n" + 
			"		\"timezoneOffset\": -480,\r\n" + 
			"		\"year\": 120,\r\n" + 
			"		\"minutes\": 13,\r\n" + 
			"		\"time\": 1608884007533,\r\n" + 
			"		\"day\": 5,\r\n" + 
			"		\"timeInMicros\": 1608884007533820\r\n" + 
			"	},\r\n" + 
			"	\"author\": \"admin\",\r\n" + 
			"	\"commitId\": \"1000\",\r\n" + 
			"	\"issueNo\": \"ZC-888\",\r\n" + 
			"	\"message\": \"ZC-888 合并前源分支提交commit2\",\r\n" + 
			"	\"authorDateTimestamp\": 1608884007533,\r\n" + 
			"	\"branch\": \"\",\r\n" + 
			"	\"committerEmail\": \"\",\r\n" + 
			"	\"committerDateTimestamp\": 1608884007533,\r\n" + 
			"	\"committer\": \"admin\",\r\n" + 
			"	\"comment\": \"ZC-109 合并前源分支提交commit2\",\r\n" + 
			"	\"diffInfoList\": [],\r\n" + 
			"	\"bugfixList\": [],\r\n" + 
			"	\"id\": \"971\",\r\n" + 
			"	\"committerDate\": 1608884007533,\r\n" + 
			"	\"mergeStatus\": \"merged\"\r\n" + 
			"}]");

	
	public static void main(String[] args) {
		try {
			writeCommitCacheNotExist();
			//updateSVNCommitsToCache();
			getSVNCommitsFromCache();
			// writeCommitCacheContentBig();
			//writeSVNDiffToCache();
			//writeSVNCommitsToCacheUseIndex();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	/** 缓存目录不存在的情况下写入commit缓存 
	 * @throws IOException */
	public static void writeCommitCacheNotExist() throws IOException {
		JSONArray commitArr = JSONArray.fromObject("[{\r\n" + 
				"	\"authorEmail\": \"\",\r\n" + 
				"	\"authorDate\": {\r\n" + 
				"		\"date\": 25,\r\n" + 
				"		\"hours\": 16,\r\n" + 
				"		\"seconds\": 27,\r\n" + 
				"		\"month\": 11,\r\n" + 
				"		\"timezoneOffset\": -480,\r\n" + 
				"		\"year\": 120,\r\n" + 
				"		\"minutes\": 13,\r\n" + 
				"		\"time\": 1608884007533,\r\n" + 
				"		\"day\": 5,\r\n" + 
				"		\"timeInMicros\": 1608884007533820\r\n" + 
				"	},\r\n" + 
				"	\"author\": \"admin\",\r\n" + 
				"	\"commitId\": \"971\",\r\n" + 
				"	\"issueNo\": \"ZC-109\",\r\n" + 
				"	\"message\": \"ZC-109 合并前源分支提交commit2\",\r\n" + 
				"	\"authorDateTimestamp\": 1608884007533,\r\n" + 
				"	\"branch\": \"\",\r\n" + 
				"	\"committerEmail\": \"\",\r\n" + 
				"	\"committerDateTimestamp\": 1608884007533,\r\n" + 
				"	\"committer\": \"admin\",\r\n" + 
				"	\"comment\": \"ZC-109 合并前源分支提交commit2\",\r\n" + 
				"	\"diffInfoList\": [],\r\n" + 
				"	\"bugfixList\": [],\r\n" + 
				"	\"id\": \"971\",\r\n" + 
				"	\"committerDate\": 1608884007533,\r\n" + 
				"	\"mergeStatus\": \"merged\"\r\n" + 
				"},\r\n" + 
				"{\r\n" + 
				"	\"authorEmail\": \"\",\r\n" + 
				"	\"authorDate\": {\r\n" + 
				"		\"date\": 25,\r\n" + 
				"		\"hours\": 16,\r\n" + 
				"		\"seconds\": 5,\r\n" + 
				"		\"month\": 11,\r\n" + 
				"		\"timezoneOffset\": -480,\r\n" + 
				"		\"year\": 120,\r\n" + 
				"		\"minutes\": 11,\r\n" + 
				"		\"time\": 1608883865330,\r\n" + 
				"		\"day\": 5,\r\n" + 
				"		\"timeInMicros\": 1608883865330395\r\n" + 
				"	},\r\n" + 
				"	\"author\": \"admin\",\r\n" + 
				"	\"commitId\": \"969\",\r\n" + 
				"	\"issueNo\": \"ZC-109\",\r\n" + 
				"	\"message\": \"ZC-109 合并前源分支提交commit\",\r\n" + 
				"	\"authorDateTimestamp\": 1608883865330,\r\n" + 
				"	\"branch\": \"\",\r\n" + 
				"	\"committerEmail\": \"\",\r\n" + 
				"	\"committerDateTimestamp\": 1608883865330,\r\n" + 
				"	\"committer\": \"admin\",\r\n" + 
				"	\"comment\": \"ZC-109 合并前源分支提交commit\",\r\n" + 
				"	\"diffInfoList\": [],\r\n" + 
				"	\"bugfixList\": [],\r\n" + 
				"	\"id\": \"969\",\r\n" + 
				"	\"committerDate\": 1608883865330,\r\n" + 
				"	\"mergeStatus\": \"merged\"\r\n" + 
				"}]");
		
		//Cache.writeSVNCommitsToCache(repoServiceUuid, repoUuid, srcBranch, targetBranch, leftCommitId, rightCommitId, commitArr);
	}
	
	public static void updateSVNCommitsToCache() throws Exception {
		//Cache.updateSVNCommitsToCache(repoServiceUuid, repoUuid, srcBranch, targetBranch, "9999", rightCommitId, content);
	}
	
	/** 缓存内容超过最新缓存文件的大小，需要生成新的缓存文件 */
	public static void writeCommitCacheContentBig() throws IOException {
		//Cache.writeSVNCommitsToCache(repoServiceUuid, repoUuid, srcBranch, targetBranch, leftCommitId, rightCommitId, content);
	}
	
	/** 写diff缓存 
	 * @throws IOException */
	public static void writeSVNDiffToCache() throws IOException {
		//Cache.writeSVNDiffToCache(repoServiceUuid, repoUuid, srcBranch, targetBranch, leftCommitId, rightCommitId, content);
	}
	
	/** 使用index文件的情况，写commit到缓存 */
	public static void writeSVNCommitsToCacheUseIndex() throws IOException {
		//Cache.writeSVNCommitsToCacheUseIndex(repoServiceUuid, repoUuid, srcBranch, targetBranch, leftCommitId, rightCommitId, content);
	}
	
	/** 读 commit 缓存 */
	public static void getSVNCommitsFromCache() throws IOException {
		//JSONArray caches = Cache.getSVNCommitsFromCache(repoServiceUuid, repoUuid, srcBranch, targetBranch, leftCommitId, 1);
		//System.out.println(caches);
	}
}
