package com.winplume.utils;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        }  finally {
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
}
