package com.leyuan.lovesport.utils;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.leyuan.lovesport.model.UserCoach;

public class SharePrefUtils {
	private static final String SHARE_PREFS_NAME = "dataconfig";
	private static SharedPreferences mSharedPreferences;
	
	public synchronized static void setUser(Context ctx,UserCoach user){
		if (mSharedPreferences == null) {
			mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
					Context.MODE_PRIVATE);
		}
		Gson gson = new Gson();
		String json = gson.toJson(user);
		mSharedPreferences.edit().putString("user", json).commit();
	}
	
	public static UserCoach getUser(Context ctx){
		if (mSharedPreferences == null) {
			mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
					Context.MODE_PRIVATE);
		}
		UserCoach user = null;
		String json = mSharedPreferences.getString("user", null);
		try {
			Gson gson = new Gson();
			user = gson.fromJson(json, UserCoach.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			
		}
		return user;
	}
	public static void setLogin(Context ctx, boolean value){
		if (mSharedPreferences == null) {
			mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
					Context.MODE_PRIVATE);
		}
		mSharedPreferences.edit().putBoolean("islogin", value).commit();
	}
	
	public static void putInt(Context ctx, String key, int value){
		if (mSharedPreferences == null) {
			mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
					Context.MODE_PRIVATE);
		}
		mSharedPreferences.edit().putInt(key, value).commit();
	}
	public static int getInt(Context ctx, String key, int defaultValue){
		if (mSharedPreferences == null) {
			mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
					Context.MODE_PRIVATE);
		}
		return mSharedPreferences.getInt(key, defaultValue);
	}
	
	
	public static void putBoolean(Context ctx, String key, boolean value) {
		if (mSharedPreferences == null) {
			mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
					Context.MODE_PRIVATE);
		}

		mSharedPreferences.edit().putBoolean(key, value).commit();
	}

	public static boolean getBoolean(Context ctx, String key,
			boolean defaultValue) {
		if (mSharedPreferences == null) {
			mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
					Context.MODE_PRIVATE);
		}

		return mSharedPreferences.getBoolean(key, defaultValue);
	}

	public static void putString(Context ctx, String key, String value) {
		if (mSharedPreferences == null) {
			mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
					Context.MODE_PRIVATE);
		}

		mSharedPreferences.edit().putString(key, value).commit();
	}

	public static String getString(Context ctx, String key, String defaultValue) {
		if (mSharedPreferences == null) {
			mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
					Context.MODE_PRIVATE);
		}

		return mSharedPreferences.getString(key, defaultValue);
	}
	
	


}
