package com.yeah.javabasic.util;

import java.util.Arrays;

public class RegExp {
	public static void main(String[] args) {
		String sep = "\\|";
		String envStr = "|SIT|PRD|";
		String[] envArr = envStr.split(sep);
		System.out.println(Arrays.asList(envArr));
	}
}
