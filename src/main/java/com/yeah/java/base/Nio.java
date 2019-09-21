package com.yeah.java.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static java.lang.System.out;
public class Nio {
	// 解析兄弟目录
	private static void resolveSibling() throws IOException {
		String root = "D:\\app\\";
		Path rootPath = Paths.get(root);
		Path sibling = rootPath.resolveSibling("\\test\\abcd\\");
		if (sibling != null) {
			Files.createDirectories(Paths.get(root + "\\app"));
			Files.createDirectories(Paths.get(root + "\\exec"));
		}
	}
	
	private static void tempPath() throws IOException {
		//Path tmpPath = Files.createTempDirectory("test", "xxx", "wr");
		
		String tmp = System.getProperty("java.io.tmpdir");
		String projectPath = tmp + "balantflow-module-deploy";
		Path tmpPath = Paths.get(projectPath + "/src/java/com/techsure/deploy");
		Path myTmpPath = Files.createDirectories(tmpPath);
		
		FileOutputStream os = new FileOutputStream(projectPath + "/src/java/com/techsure/deploy/test.txt");
		os.write("abcd".getBytes());
		os.close();
		
		FileOutputStream fos = new FileOutputStream(tmp + "/balantflow-module-deploy.zip");
		ZipOutputStream zipOut = new ZipOutputStream(fos);
		File fileToZip = new File(projectPath);
		
		zipFile(fileToZip, fileToZip.getName(), zipOut);
		zipOut.close();
		fos.close();
		//out.println(myTmpPath);
		//out.println(tmpPath);
	}
	
	public static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
		if (fileToZip.isHidden()) {
			return;
		}
		
		if (fileToZip.isDirectory()) {
			if (fileName.endsWith("/")) {
				zipOut.putNextEntry(new ZipEntry(fileName));
				zipOut.closeEntry();
			} else {
				zipOut.putNextEntry(new ZipEntry(fileName + "/"));
				zipOut.closeEntry();
			}
			
			File[] children = fileToZip.listFiles();
			for (File childFile: children) {
				zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
			}
			return;
		}
		
		FileInputStream fis = new FileInputStream(fileToZip);
		ZipEntry zipEntry = new ZipEntry(fileName);
		zipOut.putNextEntry(zipEntry);
		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zipOut.write(bytes, 0, length);
		}
		zipOut.closeEntry();
		fis.close();
	}
	
	public static void main(String[] args) throws IOException {
		// resolveSibling();
		tempPath();
	}
}
