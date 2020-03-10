package com.winplume.utils;

public class ByteOperationUtils {
    public static int func(byte height, byte low) {
        return height << 8 + low;
    }
}
