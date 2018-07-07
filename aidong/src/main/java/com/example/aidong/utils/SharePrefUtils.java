package com.example.aidong.utils;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.example.aidong .entity.CircleDynamicBean;
import com.example.aidong .entity.VenuesBean;
import com.example.aidong .entity.course.CourseFilterBean;
import com.example.aidong .entity.data.CouponData;
import com.example.aidong .entity.model.UserCoach;
import com.example.aidong .entity.user.MineInfoBean;
import com.example.aidong .ui.App;

import java.util.ArrayList;
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

    public static ArrayList<CircleDynamicBean> getCmdMessage(Context ctx,String userId) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(userId,
                    Context.MODE_PRIVATE);
        }
        ArrayList<CircleDynamicBean> beans = null;
        String json = mSharedPreferences.getString("cmdMessage", null);
        try {
            Gson gson = new Gson();
            beans = gson.fromJson(json, new TypeToken<List<CircleDynamicBean>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();

        }

        Logger.i(TAG, " mSharedPreferences.edit().putString(\"getCmdMessage\", json).commit();");
        return beans;
    }

    public static void addCmdMessage(Context ctx, CircleDynamicBean message,String userId) {
        ArrayList<CircleDynamicBean> beans = getCmdMessage(ctx,userId);
        if (beans == null) beans = new ArrayList<>();
        beans.add(message);
        saveCmdMessage(ctx, beans,userId);

        Logger.i(TAG, " mSharedPreferences.edit().putString(\"addCmdMessage\", json).commit();");
    }

    public static void saveCmdMessage(Context ctx, ArrayList<CircleDynamicBean> beans,String userId) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(userId,
                    Context.MODE_PRIVATE);
        }
        Gson gson = new Gson();
        String json = gson.toJson(beans);
        mSharedPreferences.edit().putString("cmdMessage", json).apply();

        Logger.i(TAG, " mSharedPreferences.edit().putString(\"saveCmdMessage\", json).commit();");
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



    public static void removeString(Context ctx, String key) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                    Context.MODE_PRIVATE);
        }

        mSharedPreferences.edit().remove(key).commit();
    }



    public static String getString(Context ctx, String key, String defaultValue) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                    Context.MODE_PRIVATE);
        }

        return mSharedPreferences.getString(key, defaultValue);
    }

    public static void putLong(Context ctx, String key, long value) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                    Context.MODE_PRIVATE);
        }
        mSharedPreferences.edit().putLong(key, value).commit();
    }

    public static long getLong(Context ctx, String key, long defaultValue) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                    Context.MODE_PRIVATE);
        }
        return mSharedPreferences.getLong(key, defaultValue);
    }


    public static String getToken(Context context) {
        return getString(context, "token", null);
    }

    public static void setToken(Context context, String token) {
        putString(context, "token", token);
    }

    public static void putCheckAutoStartTime(Context ctx) {
        putLong(ctx, "checkAutoStartTime", System.currentTimeMillis());
    }

    public static long getLastCheckAutoStartTime(Context ctx) {
        return getLong(ctx, "checkAutoStartTime", 0);
    }

    public static void putReleaseConfig(boolean release) {
        putBoolean(App.context, "release", release);
    }

    public static boolean getReleaseConfig(boolean defaul) {
        return getBoolean(App.context, "release", defaul);
    }

    public static void putNewUserCoupon(Context context, CouponData couponData) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(SHARE_PREFS_NAME,
                    Context.MODE_PRIVATE);
        }
        Gson gson = new Gson();
        String json = gson.toJson(couponData);
        mSharedPreferences.edit().putString("newUserCoupon", json).commit();

        Logger.i(TAG, " mSharedPreferences.edit().putString(\"newUserCoupon\", json).commit();");
    }

    public static CouponData getNewUserCoupon(Context context) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(SHARE_PREFS_NAME,
                    Context.MODE_PRIVATE);
        }
        CouponData couponData = null;
        String json = mSharedPreferences.getString("newUserCoupon", null);
        try {
            Gson gson = new Gson();
            couponData = gson.fromJson(json, CouponData.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();

        }
        return couponData;
    }

    public static void putCourseFilterConfig(Context context, CourseFilterBean courseFilterData) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(SHARE_PREFS_NAME,
                    Context.MODE_PRIVATE);
        }
        Logger.i("Course", courseFilterData.toString());
        Gson gson = new Gson();
        String json = gson.toJson(courseFilterData);
        mSharedPreferences.edit().putString("courseFilterData", json).commit();

        Logger.i(TAG, " mSharedPreferences.edit().putCourseFilterConfig.commit();");
    }

    public static CourseFilterBean getCourseFilterConfig(Context context) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(SHARE_PREFS_NAME,
                    Context.MODE_PRIVATE);
        }
        CourseFilterBean courseFilterData = null;
        String json = mSharedPreferences.getString("courseFilterData", null);
        try {
            Gson gson = new Gson();
            courseFilterData = gson.fromJson(json, CourseFilterBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();

        }
        return courseFilterData;
    }
}
