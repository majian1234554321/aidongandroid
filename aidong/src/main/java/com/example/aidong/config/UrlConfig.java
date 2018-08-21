package com.example.aidong.config;


/**
 * Created by user on 2015/5/20
 */
public class UrlConfig {
    public static final boolean debug = true;
    public static final boolean isMi = true;

    public static final boolean logo = true;

    private static String urlHtml;
    private static String urlHost;
    private static String urlHostUpdata;
    private static String urlMemberCard;

    static {
        if (debug) {
            urlHtml = "http://share.51aidong.com/";
            urlHostUpdata = "http://m.aidong.me:9020/v1/app/check/";
            urlHost = "http://app.51aidong.com/";//测试服
            urlMemberCard = "http://opentest.aidong.me/";
//            urlMemberCard = "http://stage.aidong.me/";
//            urlHost = "http://app-stage.51aidong.com/";//预发布
        } else {
            urlHtml = "http://share.aidong.me/";
            urlHost = "http://a.aidong.me/";
            urlHostUpdata = "http://me.51aidong.com:9021/v1/app/check/";
            urlMemberCard = "http://open.aidong.me/";
        }
    }

    public static final String BASE_URL = urlHost;
    static final String BASE_URL_HTML = urlHtml;
    public static final String BASE_URL_VERSION = urlHostUpdata;
    public static final String BASE_URL_MEMBER_CARD = urlMemberCard;
}

