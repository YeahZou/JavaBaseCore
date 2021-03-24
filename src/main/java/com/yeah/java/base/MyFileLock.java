package com.yeah.java.base;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.System.out;

public class MyFileLock {
	public class MyThread extends Thread {
	    public void run(){
	    	try {
				invokeLock();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	  }
	
	private static void invokeLock() throws Exception {
		String lockPath = "D:\\doc\\ezdeploy发布平台";
		String rootDir = "D:\\doc\\ezdeploy发布平台\\POC";
		Path rootPath = Paths.get(rootDir).toAbsolutePath();
		rootDir = rootPath.toString();
		String lockFilePath = rootDir.replaceAll("\\" + File.separator, "_").replaceAll("\\.$", "") + ".lock";
		File lockFile = new File(lockFilePath);
		@SuppressWarnings("resource")
		FileChannel lockChannel = new RandomAccessFile(lockFile, "rw").getChannel();
		FileLock buildLock = lockChannel.lock(0, 4096, true);

		int bufLen = 1024;
		byte[] buf = new byte[bufLen];
		try {
			Thread.sleep(1000);
			ProcessBuilder builder = new ProcessBuilder("echo", "a.txt");
			builder.directory(new File(rootDir));
			Process proc = builder.start();

			InputStream in = proc.getInputStream();
			InputStream err = proc.getErrorStream();
			int len;
			while ((len = in.read(buf, 0, bufLen)) >= 0) {
				out.write(buf, 0, len);
			}
			proc.waitFor();
			int exitValue = proc.exitValue();
			if (exitValue != 0) {
				String errMsg = "";
				try {
					len = err.read(buf, 0, bufLen);
					errMsg = new String(buf, 0, bufLen);
				} catch (Exception ex) {
				}
				throw new IOException("zip dir failed:" + rootDir + ":" + errMsg);
			}
		} catch (InterruptedException e) {
			throw new IOException("zip dir failed:" + rootDir);
		} finally {
			if (buildLock != null) {
				buildLock.release();
			}
			if (lockChannel != null) {
				lockChannel.close();
			}
			if (lockFile != null) {
				//lockFile.delete();
			}
			
		}
	}

	public static void main(String[] args) throws Exception {
		MyFileLock fileLock = new MyFileLock();
		for (int i = 0; i < 5; i++) {
			Thread t = fileLock.new MyThread();
			t.start();
		}
	}

}
