package com.everis.core.service;

import java.io.PrintWriter;

public interface Log4jService {

	String rootCategory = "rootCategory";
	String file = "file";
	String maxFileSize = "maxFileSize";
	String maxBackupIndex = "maxBackupIndex";
	String obtener(String key);
	void test(PrintWriter out);
}
