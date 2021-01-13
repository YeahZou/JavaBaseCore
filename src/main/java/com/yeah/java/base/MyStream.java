/**
 * 
 */
package com.yeah.java.base;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONReader;

/**   
 * @ClassName   MyStream   
 * @Description 验证 fastjson 的流接口，必须所有的都读，不能跳过
 * @author      zouye
 * @date        2021-01-10   
 *    
 */

public class MyStream {
	public static void main(String[] args) throws FileNotFoundException {
		  JSONReader reader = new JSONReader(new FileReader("D:\\study\\json.json"));
		  reader.startObject();
		  
		  JSONArray diffList = null;
		  while(reader.hasNext()) {
		      String key = reader.readString();
		      if (StringUtils.equals("100..99", key)) {
		    	  diffList = reader.readObject(JSONArray.class);
		      } else {
		    	  reader.readObject();
		      }
		  }
		  reader.endObject();
		  reader.close();
		  
		  System.out.println(diffList);
	}
}
