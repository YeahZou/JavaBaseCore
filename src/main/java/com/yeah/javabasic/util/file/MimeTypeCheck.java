/**
 * 
 */
package com.yeah.javabasic.util.file;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;

import javax.activation.MimetypesFileTypeMap;

import org.apache.tika.Tika;

/**   
 * @ClassName   MimeTypeCheck   
 * @Description TODO
 * @author      zouye
 * @date        2021-01-27   
 *    
 */
public class MimeTypeCheck {
	public static void main(String[] argv) throws IOException {
		System.setProperty("content.types.user.table","D:\\workspace\\javabasic\\content-types.properties");
		
		File file = new File(".project");
		// 可能为null
	    FileNameMap fileNameMap = URLConnection.getFileNameMap();
	    String mimeType = fileNameMap.getContentTypeFor(file.getName());
	    System.out.println(mimeType);
	    
	    // 检测失败默认 application/octet-stream
	    MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap("D:\\workspace\\javabasic\\mime-types.properties");
	    mimeType = fileTypeMap.getContentType(file.getName());
	    System.out.println(mimeType);
	    
	    // 通过内容判断，准确度高。文件必须实际存在，否则报错
	    Tika tika = new Tika();
	    mimeType = tika.detect(file);
	    System.out.println(mimeType);
	}
}
