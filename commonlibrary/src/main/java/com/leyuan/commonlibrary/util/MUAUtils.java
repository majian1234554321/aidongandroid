package com.leyuan.commonlibrary.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by hashbaz on 16/2/18.
 *
 */
public class MUAUtils {
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "0.0";
        }
    }

    public static int px2dp(Context context, int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,
                context.getResources().getDisplayMetrics());
    }

    public static int dp2px(Context context, int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,
                context.getResources().getDisplayMetrics());
    }

    public static Map<String, String> getValueMap(Object obj) {
        Map<String, String> map = new HashMap<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            String varName = field.getName();
            try {
                boolean accessFlag = field.isAccessible();
                field.setAccessible(true);
                Object o = field.get(obj);
                if (o != null)
                    map.put(varName, o.toString());
                field.setAccessible(accessFlag);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }
        return map;
    }

    public static void showIme(@NonNull View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService
                (Context.INPUT_METHOD_SERVICE);
        // the public methods don't seem to work for me, so… reflection.
        try {
            Method showSoftInputUnchecked = InputMethodManager.class.getMethod(
                    "showSoftInputUnchecked", int.class, ResultReceiver.class);
            showSoftInputUnchecked.setAccessible(true);
            showSoftInputUnchecked.invoke(imm, 0, null);
        } catch (Exception e) {
            // ho hum
        }
    }

    public static void hideIme(@NonNull View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context
                .INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static boolean isSdcardExist() {
        return false;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static JSONArray loadJsonArray(Context context, String path) throws IOException, JSONException {
	    InputStream inputStream = context.getApplicationContext().getAssets().open(path, AssetManager.ACCESS_BUFFER);
	    BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
	    StringBuilder responseStrBuilder = new StringBuilder();

	    String content;
	    while ((content = streamReader.readLine()) != null)
		    responseStrBuilder.append(content);
	    return new JSONArray(responseStrBuilder.toString());
    }

	public static JSONObject loadJsonObject(Context context, String path) throws IOException, JSONException {
		InputStream inputStream = context.getApplicationContext().getAssets().open(path, AssetManager.ACCESS_BUFFER);
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		StringBuilder responseStrBuilder = new StringBuilder();

		String content;
		while ((content = streamReader.readLine()) != null)
			responseStrBuilder.append(content);
		return new JSONObject(responseStrBuilder.toString());
	}
}