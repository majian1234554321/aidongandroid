package com.leyuan.aidong.config;


/**
 * Created by user on 2015/5/20
 */
public class UrlConfig {
    public static final boolean debug = true;

    private static String urlHtml;
    private static String urlHost;

    static {
        if (debug) {
            urlHtml = "http://share.51aidong.com/";
            urlHost = "http://app.51aidong.com/";
        } else {
            urlHtml = "http://share.51aidong.com/";
            urlHost = "http://app.51aidong.com/";
        }
    }

    public static final String BASE_URL = urlHost;
    static final String BASE_URL_HTML = urlHtml;
}

