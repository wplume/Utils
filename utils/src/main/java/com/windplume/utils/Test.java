package com.windplume.utils;

import java.io.File;

public class Test {
    public static void main(String[] args) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler(CrashHandler.getInstance());
        File file = new File("D:/test/");
        file.mkdir();
        CrashHandler.getInstance().setLogPath("D:/test/log");
        throw new Exception("test");
    }
}
