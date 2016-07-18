package com.example.aidong.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * 统一管理log类
 */
public class LogUtils {
	
	/**
	 * 打包发布时设为false
	 */
	private static final boolean LOGGER = true;
	
	private static final String TAG = "LogUtils";
	
	public static void v(String tag, String msg) {
		if (LOGGER && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
			Log.v(TAG, tag + "----->" + msg);
		}
	}

	public static void d(String tag, String msg) {
		if (LOGGER && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
			Log.d(TAG, tag + "----->" + msg);
		}
	}

	public static void i(String tag, String msg) {
		if (LOGGER && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
			Log.i(TAG, tag + "----->" + msg);
		}
	}

	public static void w(String tag, String msg) {
		if (LOGGER && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
			Log.v(TAG, tag + "----->" + msg);
		}
	}

	public static void e(String tag, String msg) {
		if (LOGGER && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
			Log.e(TAG, tag + "----->" + msg);
		}
	}

	public static void e(String tag, String msg, Throwable tr) {
		if (LOGGER && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
			Log.e(TAG, tag + "----->" + msg);
		}
	}
}
