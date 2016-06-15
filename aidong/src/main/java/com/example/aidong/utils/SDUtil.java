package com.example.aidong.utils;

import android.os.Environment;

import java.io.File;

public class SDUtil {
		public static boolean isExsitSDCard(){
			boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
			return sdCardExist;
		}

		public static File getSDPath(String dirName) {
			File sdDir = new File(Environment.getExternalStorageDirectory() + "/" + dirName);
			return sdDir;
		}
}
