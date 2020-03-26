package com.winplume.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileUtils {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String readFileText26(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    public static String readFileText(String path) throws IOException {
        String res;
        InputStream is = null;
        try {
            is = new FileInputStream(new File(path));
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            res = sb.toString();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return res;
    }

    public static void writeToFile(String s, String path) throws IOException {
        try (FileOutputStream os = new FileOutputStream(new File(path))) {
            os.write(s.getBytes());
            os.flush();
        }
    }

    public static void fileAppend(String append, String path) throws IOException {

        try {
            final File file = new File(path);
            FileOutputStream fos = new FileOutputStream(file, true);
            fos.write(append.getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void fileAppend26(String append, String Path) throws IOException {
        Files.write(Paths.get(Path), append.getBytes(), StandardOpenOption.APPEND);
    }
}
