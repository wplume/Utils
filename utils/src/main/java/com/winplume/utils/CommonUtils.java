package com.winplume.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.YuvImage;

import java.io.ByteArrayOutputStream;

public class CommonUtils {
    public static Bitmap nv212Bitmap(byte[] nv21, int width, int height) {
        ByteArrayOutputStream nv21Stream = new ByteArrayOutputStream();
        YuvImage yuvImage = new YuvImage(nv21, ImageFormat.NV21, width, height, null);
        yuvImage.compressToJpeg(new android.graphics.Rect(0, 0, width, height), 100, nv21Stream);
        return BitmapFactory.decodeByteArray(nv21Stream.toByteArray(), 0, nv21Stream.size());
    }

    public static byte[] getNV21(int inputWidth, int inputHeight, Bitmap scaled) {

        int[] argb = new int[inputWidth * inputHeight];

        scaled.getPixels(argb, 0, inputWidth, 0, 0, inputWidth, inputHeight);

//        byte[] yuv = new byte[inputWidth * inputHeight * 3 / 2];
        byte[] yuv = new byte[inputHeight * inputWidth + 2 * (int) Math.ceil(inputHeight / 2.0) * (int) Math.ceil(inputWidth / 2.0)];
        encodeYUV420SP(yuv, argb, inputWidth, inputHeight);

//        scaled.recycle();

        return yuv;
    }

    public static void encodeYUV420SP(byte[] yuv420sp, int[] argb, int width, int height) {
        final int frameSize = width * height;

        int yIndex = 0;
        int uvIndex = frameSize;

        int a, R, G, B, Y, U, V;
        int index = 0;
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {

                a = (argb[index] & 0xff000000) >> 24; // a is not used obviously
                R = (argb[index] & 0xff0000) >> 16;
                G = (argb[index] & 0xff00) >> 8;
                B = (argb[index] & 0xff) >> 0;

                // well known RGB to YUV algorithm
                Y = ((66 * R + 129 * G + 25 * B + 128) >> 8) + 16;
                U = ((-38 * R - 74 * G + 112 * B + 128) >> 8) + 128;
                V = ((112 * R - 94 * G - 18 * B + 128) >> 8) + 128;

                // NV21 has a plane of Y and interleaved planes of VU each sampled by a factor of 2
                //    meaning for every 4 Y pixels there are 1 V and 1 U.  Note the sampling is every other
                //    pixel AND every other scanline.
                yuv420sp[yIndex++] = (byte) ((Y < 0) ? 0 : ((Y > 255) ? 255 : Y));
                if (j % 2 == 0 && index % 2 == 0) {
                    yuv420sp[uvIndex++] = (byte) ((V < 0) ? 0 : ((V > 255) ? 255 : V));
                    yuv420sp[uvIndex++] = (byte) ((U < 0) ? 0 : ((U > 255) ? 255 : U));
                }

                index++;
            }
        }
    }

    public static byte[] rotateNV21(byte[] nv21, int width, int height) {
        int hangs = width;// 行 —
        int lies = height;// 列 ｜
        int size = lies * hangs;
        byte[] newNv21 = new byte[nv21.length];
        for (int hang = 0; hang < hangs; hang++) {
            for (int lie = 0; lie < lies; lie++) {
                newNv21[hang * hangs + lie] = nv21[(lies - lie - 1) * width + hang];
            }
        }
        return newNv21;
    }

    public static byte[] arrayRotateCouple90(byte[] array, int width, int height) {
        int hangs = width / 2;// 总行数 —
        int lies = height * 2;// 总列数 ｜ 肯定是2的倍数
        byte[] newArray = new byte[array.length];
        for (int hang = 0; hang < hangs; hang++) {
            for (int lie = 0; lie < lies / 2; lie++) {
                newArray[hang * lies + lie * 2] = array[(height - (lie + 1)) * width + hang * 2];
                newArray[hang * lies + lie * 2 + 1] = array[(height - (lie + 1)) * width + hang * 2 + 1];

//                StringBuilder sb = new StringBuilder();
//                for (int j = 0; j < newArray.length; j++) {
//                    sb.append(newArray[j]);
//                    if ((j + 1) % lies == 0) {
//                        sb.append("\n");
//                    } else {
//                        sb.append(" ");
//                    }
//                }
//                System.out.println(sb.toString());
            }
        }
        return newArray;
    }

    public static byte[] arrayRotate90(byte[] array, int width, int height) {
        int hangs = width;// 总行数 —
        int lies = height;// 总列数 ｜
        int size = lies * hangs;
        byte[] newArray = new byte[array.length];
        for (int hang = 0; hang < hangs; hang++) {
            for (int lie = 0; lie < lies; lie++) {
                int i = hang * lies + lie;
                int i1 = (lies - lie - 1) * width + hang;
                newArray[i] = array[i1];
//                System.out.printf("(%d, %d) %d (%d, %d)\n", hang, lie, array[i1], i, i1);

//                StringBuilder sb = new StringBuilder();
//                for (int j = 0; j < newArray.length; j++) {
//                    sb.append(newArray[j]);
//                    if ((j + 1) % height == 0) {
//                        sb.append("\n");
//                    } else {
//                        sb.append(" ");
//                    }
//                }
//                System.out.println(sb.toString());
            }
        }
        return newArray;
    }
}
