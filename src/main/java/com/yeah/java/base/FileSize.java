/**
 * 
 */
package com.yeah.java.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**   
 * @ClassName   FileSize   
 * @Description TODO
 * @author      zouye
 * @date        2021-01-19   
 *    
 */
public class FileSize {

	public static void main(String[] args) throws Exception {
		File file = new File("D:\\data\\workingcopy\\test.txt");
		OutputStream os = new FileOutputStream(file);
		
		for (int i = 0; i < 100000; i++) {
			os.write(String.format("12345678..87654321 %s %s\n", i, i).getBytes());
		}
		
		InputStream in = new FileInputStream(file);
		InputStreamReader reader = new InputStreamReader(in);
		BufferedReader r = new BufferedReader(reader);
		
		r.readLine();
		os.close();
	}
}
