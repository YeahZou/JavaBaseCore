package com.yeah.java.base;

/** 对象引用，将非空值赋值给一个 null 对象，不会报错<br>
 * 
 **/
public class MyObjectRefer {

	public static void main(String[] args) throws Exception {
		String strNull = null;
		String strConst = "";
		String strObject = new String();
		StringBuffer sbNull = null;
		StringBuffer sbObject = new StringBuffer();
		
		argsReferIsNull(strNull);
		System.out.println("参数是字符串引用为null：" + strNull);
		
		argsReferIsNull(strConst);
		System.out.println("参数是字符串常量：" + strConst);
		
		argsReferIsNull(strObject);
		System.out.println("参数是字符串对象：" + strObject);
		
		try {
			argsReferIsNull(sbNull);
			System.out.println("参数是StringBuffer null：" + sbNull);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		argsReferIsNull(sbObject);
		System.out.println("参数是StringBuffer object：" + sbObject);
	}
	
	/** 字符串对象引用 */
	public static void argsReferIsNull(String res) {
		res = "argsFromFunction";
		System.out.println(res);
	}
	
	
	/** StringBuffer 对象引用 */
	public static void argsReferIsNull(StringBuffer res) {
		res.append("argsFromFunction");
		System.out.println(res);
	}

}
