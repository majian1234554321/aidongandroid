package com.leyuan.aidong.config;


import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.leyuan.aidong.entity.ConfigUrlBean;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.utils.SharePrefUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by user on 2015/5/20
 */
public class UrlConfig {
    public static boolean debug = false;

    private static String urlHtml;
    private static String urlHost;

//    static {
//        if (debug) {
//            urlHtml = "http://share.51aidong.com/";
//            urlHost = "http://app.51aidong.com/";
//        } else {
//            urlHtml = "http://share.aidong.me/";
//            urlHost = "http://a.aidong.me/";
//        }
//    }

    public static String BASE_URL = urlHost;
    static String BASE_URL_HTML = urlHtml;

    public static void setDebug(boolean release) {
        debug = !release;
        if (debug) {
            BASE_URL_HTML = "http://share.51aidong.com/";
            BASE_URL = "http://app.51aidong.com/";
        } else {
            BASE_URL_HTML = "http://share.aidong.me/";
            BASE_URL = "http://a.aidong.me/";
        }

    }


    public static void get(final Callback callback) {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String response = get("http://share.51aidong.com/app_switch");

                Gson gson = new Gson();
                try {

                    ConfigUrlBean bean = gson.fromJson(response, ConfigUrlBean.class);
                    Log.i("ConfigUrlBean = ", bean.toString());
                    if (bean.getData() != null) {
                        setDebug(bean.getData().isRelease());
                        RetrofitHelper.setSingleton(null);
                        SharePrefUtils.putReleaseConfig(bean.getData().isRelease());
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onResponse(response);
                        }
                    });

                    Log.i("ConfigUrlBean = ", " urlHost = " + urlHost);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onResponse(response);
                        }
                    });
                }

            }
        }).start();
    }

    public static String get(String url) {
        HttpURLConnection conn = null;
        try {
            // 利用string url构建URL对象
            URL mURL = new URL(url);
            conn = (HttpURLConnection) mURL.openConnection();

            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(10000);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {

                InputStream is = conn.getInputStream();
                return getStringFromInputStream(is);
            } else {
//                throw new NetworkErrorException("response status is " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return null;
    }

    private static String getStringFromInputStream(InputStream is)
            throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // 模板代码 必须熟练
        byte[] buffer = new byte[1024];
        int len = -1;
        try {
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String state = os.toString();
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 把流中的数据转换成字符串,采用的编码是utf-8(模拟器默认编码)

        return state;
    }

    public interface Callback {
        void onResponse(String response);
    }
}

