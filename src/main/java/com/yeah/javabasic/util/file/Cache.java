/**
 * 
 */
package com.yeah.javabasic.util.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;

/**   
 * @ClassName   CacheUtils   
 * @Description TODO
 * @author      zouyewriteSVNCommitsToCache
 * @date        2021-01-11   
 *    
 */
public class Cache {
	public static String INVALID_CHARS = "\\/:*?\"<>|.";
	public static String WORKING_COPY_PATH = "D:\\data\\workingcopy";
	
	/** 缓存文件大小 512kb */
	public static Long CACHE_FILE_MAX_SIZE = 512 * 1024L;
	
	public static String readCache(boolean forceFlush, String repoServiceUuid, String repoUuid, String srcBranch, String targetBranch) {
		if (forceFlush) {
			return null;
		} else {
			String cachePath = getCachePath(repoServiceUuid, repoUuid, srcBranch, targetBranch);			
			File cacheFilePath = new File(cachePath);
			if (cacheFilePath.exists()) {
				File headFile = new File(cachePath + "/HEAD");
				if (!headFile.exists()) {
					return null;
				}
				
				
			}
			
			return null;
		}
	}
	
	public static void add(String fileName, String str) {
	    try {
	        if (str != null && str.length() > 0) {
	          writeToFile(new File(fileName), str);
	        }
	      } catch(IOException e) {
	        throw new RuntimeException(e);
	      }
	}
	
	private static long writeToFile(File file, String str) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
	    FileChannel channel = raf.getChannel();
		ByteBuffer bytes = ByteBuffer.wrap(str.getBytes(StandardCharsets.UTF_8));
	    // 字符串的长度
		ByteBuffer length = ByteBuffer.allocate(4).putInt(bytes.array().length);

		long pos = raf.length();
		channel.position(pos);
		length.flip();
		channel.write(length);
		channel.write(bytes);

		channel.close();
		raf.close();
		
		return pos;
	}

	public static String getAt(String fileName, int pointer) {
		try {
			String val = readFromFile(new File(fileName), pointer);
		    return val;
		} catch(IOException e) {
		    throw new RuntimeException(e);
		}
	}

	private static String readFromFile(File file, long pointer) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
	    FileChannel channel = raf.getChannel();
	    FileChannel fc = channel.position(pointer);

	    //get length of entry
	    ByteBuffer buffer = ByteBuffer.wrap(new byte[4]);
	    fc.read(buffer);
	    buffer.flip();
	    int length = buffer.getInt();

	    //read entry
	    buffer = ByteBuffer.wrap(new byte[length]);
	    fc.read(buffer);
	    buffer.flip();

	    channel.close();
	    raf.close();
	    return new String(buffer.array(), StandardCharsets.UTF_8);
	}
	
	/** 将 svn commit 写入缓存，commit历史不会被修改，如果 */
	public static void writeSVNCommitsToCache(String repoServiceUuid, String repoUuid,
			String srcBranch, String targetBranch, String leftCommitId, String rightCommitId, String content) throws IOException {
		
		String pathStr = getCachePath(repoServiceUuid, repoUuid, srcBranch, targetBranch) + "/commit/";
		File path = new File(pathStr);
		
		if (!path.exists()) {
			if (!path.mkdirs()) {
				System.err.println(String.format("create cache path '%s' failed", pathStr));
				return;
			}
		}
		
		String[] fileNames = path.list();
		if (fileNames == null || fileNames.length == 0) { // 文件不存在
			File file = new File(pathStr + leftCommitId);
			try {
				if (!file.createNewFile()) {
					System.err.println(String.format("create commit cache '%s' failed.", file.getAbsolutePath()));
					return;
				}
				
				FileUtils.writeStringToFile(file, content, StandardCharsets.UTF_8);
			} catch (IOException e) {
				System.err.println(String.format("write commit cache to '%s' failed, %s", file.getAbsolutePath(), e.getMessage()));
				return;
			}
		} else {
			List<Long> commitIdList = new ArrayList<>();
			for (String commitId: fileNames) {
				commitIdList.add(Long.parseLong(commitId));
			}
			
			Collections.sort(commitIdList);
			
			String fileName = String.valueOf(commitIdList.get(commitIdList.size() - 1));
			File oldFile = new File(pathStr + fileName);
			File newFile = new File(pathStr + leftCommitId);
			
			// 文件已超过大小，创建新文件
			if (oldFile.length() >= CACHE_FILE_MAX_SIZE) {
				try {
					if (!newFile.createNewFile()) {
						System.err.println(String.format("create commit cache '%s' failed.", newFile.getAbsolutePath()));
						return;
					}
					
					FileUtils.writeStringToFile(newFile, content, StandardCharsets.UTF_8);
				} catch (IOException e) {
					System.err.println(String.format("write commit cache to '%s' failed, %s", newFile.getAbsolutePath(), e.getMessage()));
					return;
				}
			} else {
				// TODO 这里用追加不行
				FileUtils.writeStringToFile(oldFile, content, StandardCharsets.UTF_8, true);
				oldFile.renameTo(newFile);
			}
		}
	}
	
	/** 将 svn diff 内容写入缓存，diff内容不存在更新的问题，都是新增 */
	public static void writeSVNDiffToCache(String repoServiceUuid, String repoUuid,
			String srcBranch, String targetBranch, String leftCommitId, String rightCommitId, String content) throws IOException {
		
		String pathStr = getCachePath(repoServiceUuid, repoUuid, srcBranch, targetBranch) + "/diff/";
		File path = new File(pathStr);
		
		if (!path.exists()) {
			if (!path.mkdirs()) {
				System.err.println(String.format("create cache path '%s' failed", pathStr));
				return;
			}
		}
		
		File indexFile = new File(pathStr + "index");
		if (!indexFile.createNewFile()) {
			System.err.println(String.format("create cache file '%s' failed.", indexFile.getAbsolutePath()));
			return;
		}
		
		String diffRange = String.format("%s..%s", leftCommitId, rightCommitId);
		String[] fileNames = path.list();
		int latestFileNum = 1;
		
		// 找到最新的缓存 diff 文件
		for (String fileName: fileNames) {
			if (fileName.equals("index")) {
				continue;
			}
			
			int curr = Integer.parseInt(fileName);
			if (latestFileNum < curr) {
				latestFileNum = curr;
			}
		}
		
		File lastestCacheFile = new File(pathStr + latestFileNum);
		if (!lastestCacheFile.exists() && !lastestCacheFile.createNewFile()) {
			System.err.println(String.format("create cache file '%s' failed.", lastestCacheFile.getAbsolutePath()));
			return;
		}
		
		if (lastestCacheFile.length() >= CACHE_FILE_MAX_SIZE) { // 超过最大缓存限制，创建新文件
			latestFileNum = latestFileNum + 1;
			lastestCacheFile = new File(pathStr + latestFileNum);
			if (!lastestCacheFile.createNewFile()) {
				System.err.println(String.format("create cache file '%s' failed.", lastestCacheFile.getAbsolutePath()));
				return;
			}
		}
		
		long pos = writeToFile(lastestCacheFile, content);
		
		// 将缓存位置信息追加到索引文件中
		FileUtils.writeStringToFile(indexFile, String.format("%s %s %s\n", diffRange, latestFileNum, pos), StandardCharsets.UTF_8, true);
	}
	
	/** 从缓存中读取 diff 内容 */
	public static JSONArray getSVNDiffFromCache(String repoServiceUuid, String repoUuid,
			String srcBranch, String targetBranch, String leftCommitId, String rightCommitId) {
	
		String pathStr = getCachePath(repoServiceUuid, repoUuid, srcBranch, targetBranch) + "/diff/";
		File path = new File(pathStr);
		
		if (!path.exists()) {
			return null;
		}
		
		File indexFile = new File(pathStr + "index");
		if (!indexFile.exists()) {
			return null;
		}
		
		String diffRange = String.format("%s..%s", leftCommitId, rightCommitId);
		String diffFileName = null;
		int pointer = 0;
		
		String line = null;
		
		InputStreamReader isr = null;
		BufferedReader br = null;
		InputStream in = null;
		try {
			in = new FileInputStream(indexFile);
			isr = new InputStreamReader(in);
			br = new BufferedReader(isr);
			while((line = br.readLine()) != null) {
				String[] location = line.split(" ");
				if (StringUtils.equals(diffRange, location[0])) {
					diffFileName = location[1];
					pointer = Integer.parseInt(location[2]);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				
				if (isr != null) {
					isr.close();
				}
				
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
			}
		}
		
		if (StringUtils.isBlank(diffFileName)) {
			return null;
		}
		
		String content = getAt(pathStr + diffFileName, pointer);
		if (StringUtils.isNotBlank(content)) {
			return JSONArray.parseArray(content);
		}

		return null;
	}
	
	/** 从缓存读取 commits */
	public static JSONArray getSVNCommitsFromCache(String repoServiceUuid, String repoUuid,
			String srcBranch, String targetBranch, String srcCommitId, int size) throws IOException {
		
		String pathStr = getCachePath(repoServiceUuid, repoUuid, srcBranch, targetBranch) + "/commit/";
		File path = new File(pathStr);
		
		if (!path.exists()) {
			return null;
		}
		
		String[] fileNames = path.list();
		if (fileNames == null || fileNames.length == 0) {
			return null;
		}
		
		Long srcCommitIdNum = Long.parseLong(srcCommitId);
		List<Long> commitIdList = new ArrayList<>();
		for (String commitId: fileNames) {
			commitIdList.add(Long.parseLong(commitId));
		}
		
		Collections.sort(commitIdList);
		
		JSONArray commits = new JSONArray();
		int idx = commitIdList.size() - 1;
		
		// 缓存数量大于1
		if (commitIdList.size() > 1) {
			for (int i = commitIdList.size() - 2; i >= 0; i--) {
				// 传入的 commitId 比次新还新，那就取最新的缓存
				if (srcCommitIdNum > commitIdList.get(i)) {
					idx = i + 1;
					break;
				}
			}
		}
		
		while(idx >= 0 && commits.size() < size) {
			String content = FileUtils.readFileToString(new File(pathStr + commitIdList.get(idx)));
			if (StringUtils.isNotBlank(content)) {
				commits = JSONArray.parseArray(content);
			}
			
			idx--;
		}
		
		return commits;
	}
	
	public static String getCachePath(String repoServiceUuid, String repoUuid, String srcBranch, String targetBranch) {
		return String.format("%s/%s/.cache/%s/%s.%s",
			WORKING_COPY_PATH,
			repoServiceUuid,
			repoUuid,
			StringUtils.replaceChars(srcBranch, INVALID_CHARS, StringUtils.repeat("_", INVALID_CHARS.length())),
			StringUtils.replaceChars(targetBranch, INVALID_CHARS, StringUtils.repeat("_", INVALID_CHARS.length())));
	}

}
