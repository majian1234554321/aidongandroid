package com.leyuan.aidong.config;


/**
 * Created by user on 2015/5/20
 */
public class UrlConfig {
    public static final boolean debug = true;
    public static final boolean isMi = false;

    private static String urlHtml;
    private static String urlHost;

    static {
        if (debug) {
            urlHtml = "http://share.51aidong.com/";
//          urlHost = "http://app.51aidong.com/";//测试服
            urlHost = "http://app-stage.51aidong.com/";//预发布
        } else {
            urlHtml = "http://share.aidong.me/";
            urlHost = "http://a.aidong.me/";
        }
    }

    public static final String BASE_URL = urlHost;
    static final String BASE_URL_HTML = urlHtml;
}

