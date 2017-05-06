package com.leyuan.aidong.utils;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.leyuan.aidong.entity.VenuesBean;
import com.leyuan.aidong.entity.model.UserCoach;
import com.leyuan.aidong.entity.user.MineInfoBean;

import java.util.List;

public class SharePrefUtils {
    private static final String SHARE_PREFS_NAME = "dataconfig";
    private static final java.lang.String TAG = "SharePrefUtils";
    private static SharedPreferences mSharedPreferences;

    public synchronized static void setUser(Context ctx, UserCoach user) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                    Context.MODE_PRIVATE);
        }
        Gson gson = new Gson();
        String json = gson.toJson(user);
        mSharedPreferences.edit().putString("user", json).commit();

        Logger.i(TAG, " mSharedPreferences.edit().putString(\"user\", json).commit();");
    }

    public static UserCoach getUser(Context ctx) {
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

    public static void saveSportsHistory(Context ctx, MineInfoBean mineInfo) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                    Context.MODE_PRIVATE);
        }
        Gson gson = new Gson();
        String json = gson.toJson(mineInfo);
        mSharedPreferences.edit().putString("mineInfo", json).apply();

        Logger.i(TAG, " mSharedPreferences.edit().putString(\"sportHistory\", json).commit();");
    }

    public static MineInfoBean getMineInfo(Context ctx) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                    Context.MODE_PRIVATE);
        }
        MineInfoBean info = null;
        String json = mSharedPreferences.getString("mineInfo", null);
        try {
            Gson gson = new Gson();
            info = gson.fromJson(json, MineInfoBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();

        }
        return info;
    }

    public static List<VenuesBean> getSportHistory(Context ctx) {
        MineInfoBean infoBean = getMineInfo(ctx);
        return infoBean == null ? null : infoBean.getGyms();
    }


//	public static void setLogin(Context ctx, boolean value){
//		if (mSharedPreferences == null) {
//			mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
//					Context.MODE_PRIVATE);
//		}
//		mSharedPreferences.edit().putBoolean("islogin", value).commit();
//	}

    public static void putInt(Context ctx, String key, int value) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                    Context.MODE_PRIVATE);
        }
        mSharedPreferences.edit().putInt(key, value).commit();
    }

    public static int getInt(Context ctx, String key, int defaultValue) {
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


    public static String getToken(Context context) {
        return getString(context, "token", null);
    }

    public static void setToken(Context context, String token) {
        putString(context, "token", token);
    }
}
