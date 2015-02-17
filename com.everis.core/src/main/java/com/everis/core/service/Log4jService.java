package com.everis.core.service;

import java.io.PrintWriter;

public interface Log4jService {

    String ROOT_CATEGORY = "rootCategory";
    String FILE = "file";
    String MAX_FILE_SIZE = "maxFileSize";
    String MAX_BACKUP_INDEX = "maxBackupIndex";

    String obtener(String key);

    void test(PrintWriter out);
}
