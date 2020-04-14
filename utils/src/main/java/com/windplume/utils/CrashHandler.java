package com.windplume.utils;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";
    private static CrashHandler crashHandler;
    private String logPath;

    private CrashHandler() {

    }

    public static CrashHandler getInstance() {
        if (crashHandler == null) {
            crashHandler = new CrashHandler();
        }
        return crashHandler;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
        StringBuilder sb = new StringBuilder();
        sb.append(e.getMessage());

        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss", Locale.CHINA);
        String time = format.format(new Date(System.currentTimeMillis()));
        String s = time + '\n' + stringWriter.toString() + '\n';

        try {
            FileUtils.fileAppend(s, logPath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
