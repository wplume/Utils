package com.windplume.utils;

public class ByteOperationUtils {
    public static int func(byte height, byte low) {
        return ((height & 0xff) << 8) + (low & 0xff);
    }


    public static String hexByteArrayToHexString(byte[] bytes) {
        StringBuilder buf = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            //以十六进制长度为2的形式输出
            buf.append(String.format("%02x", b & 0xff)).append(" ");
        }
        return buf.toString();
    }
}
