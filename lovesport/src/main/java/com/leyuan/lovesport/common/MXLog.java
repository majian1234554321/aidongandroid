package com.leyuan.lovesport.common;

import android.util.Log;

public class MXLog {
	public static final boolean isDebug = true;
	private static final String TAG ="loveSport";
	
	public static void d(String tag, String msg){
		if(isDebug){
			Log.d(tag, msg);
		}
	}
	
	public static void d(String msg){
		d(TAG, msg);
	}
	
	public static void e(String tag, String msg){
		if(isDebug){
			Log.e(tag, msg);
		}
	}
	
	public static void e(String msg){
		e(TAG, msg);
	}
	
	public static void w(String tag, String msg){
		if(isDebug){
			Log.w(tag, msg);
		}
	}
	
	public static void w(String msg){
		w(TAG, msg);
	}
	
	public static void i(String tag, String msg){
		if(isDebug){
			Log.i(tag, msg);
		}
	}
	
	public static void i(String msg){
		i(TAG, msg);
	}
	
	public static void v(String tag, String msg){
		if(isDebug){
			Log.v(tag, msg);
		}
	}
	
	public static void v(String msg){
		v(TAG, msg);
	}
	
	public static void out(String msg){
		if(isDebug){
			System.out.println(msg);
		}
	}

}
