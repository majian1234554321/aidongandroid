package com.example.aidong.config;

import static com.example.aidong .config.UrlConfig.BASE_URL;
import static com.example.aidong .config.UrlConfig.BASE_URL_HTML;
import static com.example.aidong .config.UrlConfig.BASE_URL_MEMBER_CARD;

/**
 * Created by user on 2017/1/12.
 */
public class ConstantUrl {
    public static final String CAPTCHA_IMAGE = BASE_URL + "captcha_image/";

    public final static String URL_SHARE_GYM = BASE_URL_HTML + "share_gym?gid=";
    public final static String URL_SHARE_PRODUCT = BASE_URL_HTML + "share_product?pid=";
    public final static String URL_SHARE_CAMPAIGN = BASE_URL_HTML + "share_campaign?cid=";
    public final static String URL_SHARE_COURSE_CIRCLE = BASE_URL_HTML+"share_course?cid="; //http://opentest.aidong.me/share/课程编号/course
//    public final static String URL_SHARE_COURSE = BASE_URL_HTML + "share_timetable?tid=";
    public final static String URL_SHARE_COURSE = BASE_URL_MEMBER_CARD+"share/"; //http://opentest.aidong.me/share/课程编号/course

    public final static String URL_SHARE_DYNAMIC = BASE_URL_HTML + "share_dynamic?yid=";
    public final static String URL_SHARE_NEWS = BASE_URL_HTML + "share_info?nid=";
    public final static String URL_SHARE_VIDEO = BASE_URL_HTML + "share_videos?vid=";
    public final static String URL_SHARE_COUPON = BASE_URL_HTML + "share_coupon?timeno=";
    public final static String URL_RETURN_SERVICE = BASE_URL_HTML + "return_service?rid=";
    public final static String LIVE_SHARE = BASE_URL_HTML + "share_live?lid=";
    public static final String URL_USER_AGREEMENT = BASE_URL_HTML + "agreement";
    public static final String URL_ABOUT_US = BASE_URL_HTML + "aboutus";
    public final static String URL_FEEDBACK = BASE_URL_HTML + "feedback?iuser=";


    public final static String WX_APP_ID = "wx365ab323b9269d30";
}
