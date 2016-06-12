package com.leyuan.lovesport.utils;

import java.io.File;

import android.os.Environment;

public class SDUtil {
	// 判断sd卡是否存�?
		public static boolean isExsitSDCard(){
			boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
			return sdCardExist;
		}

		// 获取目录
		public static File getSDPath(String dirName) {
			File sdDir = new File(Environment.getExternalStorageDirectory() + "/" + dirName);
			return sdDir;
		}
}
