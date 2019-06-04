package com.yeah.javabasic.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Nio {
	public static void main(String[] args) throws IOException {
		String root = "D:\\app\\";
		Path rootPath = Paths.get(root);
		Path sibling = rootPath.resolveSibling("\\test\\abcd\\");
		if (sibling != null) {
			Files.createDirectories(Paths.get(root + "\\app"));
			Files.createDirectories(Paths.get(root + "\\exec"));
		}
	}
}
