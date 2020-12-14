package com.yeah.java.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
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
		//tempPath();
		/*String[] p1 = new String[]{"/Projects/Branches/*", "/Projects/Branches/**", "/Projects/Branches/v\\[", "/Projects/Branches/v?.?.?", "/Projects/Branches/{v1.2.3, v1.2.4, v1.2.5}/", "/Projects/Branches/v[0-9.]*"};
		String[] path = new String[] {"/Projects/Branches/中文", "/Projects/Branches/v[/", "/Projects/Branches/v1.2.3.4/", "/Projects/Branches/v1.2.3/", "/Projects/Branches/v1.2.3/Java/", "/Projects/Branches/v1.2.3/Java/Spring/"};
		
		for (int i = 0; i < p1.length; i++) {
			for (int j = 0; j < path.length; j++) {
				PathMatcher matcher = FileSystems.getDefault().getPathMatcher( "glob:" + p1[i]);
				if (matcher.matches(Paths.get(path[j]))) {
					System.out.println(p1[i] + " matches " + path[j]);
				}
			}
		}*/
		
		// 调用 new File 不会创建文件/目录
		System.out.println(new File("/data/xxx/ddd").getCanonicalPath());
		
		
	}
}
