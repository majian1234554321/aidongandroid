package com.leyuan.commonlibrary.util;

import android.annotation.TargetApi;
import android.os.Environment;
import android.os.StatFs;

/**
 * Created by hashbaz on 16/2/18.
 */
public class APICompat {
    private static final int VERSION=android.os.Build.VERSION.SDK_INT;


    public static long getTotalExternalStorageSize() {
        long size = 0;
        if(VERSION >= 18) {
            size = SDK18.getTotalExternalStorageSize();
        } else {
            if (Environment.getExternalStorageDirectory() != null) {
                StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
                size = (long)stat.getFreeBlocks()*(long)stat.getBlockSize();
            }
        }
        return size;
    }

    public static long getAvailableExternalStorageSize(){
        long size = 0;
        if (VERSION >= 18) {
            size = SDK18.getAvailableExternalStorageSize();
        } else {
            if (Environment.getExternalStorageDirectory() != null) {
                StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
                size = (long)stat.getAvailableBlocks()*(long)stat.getBlockSize();
            }
        }
        return size;
    }

    @TargetApi(18)
    private static class SDK18 {
        public static long getAvailableExternalStorageSize() {
            long size = 0;
            if (Environment.getExternalStorageDirectory() != null) {
                StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
                size = stat.getAvailableBlocksLong() * stat.getBlockSizeLong();
            }
            return size;
        }

        public static long getTotalExternalStorageSize() {
            long size = 0;
            if (Environment.getExternalStorageDirectory() != null) {
                StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
                size = stat.getFreeBlocksLong() * stat.getBlockSizeLong();
            }
            return size;
        }
    }
}
