package com.leyuan.aidong.utils.common;


/**
 * Created by user on 2015/5/20
 */
public class Urls {
    public static boolean debug = false;

    //    public final static String BASE_URL_TEXT = "http://192.168.50.127:8080/aidongApp";
    //    public final static String BASE_URL_TEXT = "http://192.168.50.146:8080/app";
    //        public final static String BASE_URL_TEXT = "http://m1.aidong.me/aidongPre";

    //        public final static String BASE_URL_TEXT = "http://m1.aidong.me/aidong9";
    //    public final static String BASE_ME_ULR = "http://m1.aidong.me";


    public final static String BASE_URL_TEXT = "http://m.aidong.me/aidong10";
    public final static String BASE_ME_ULR = "http://m.aidong.me";

    public final static String PAY_URL = BASE_URL_TEXT;
    public final static String SHARE_URL = BASE_ME_ULR + "/share";
    public final static String CUSTOMER_SERVICE_URL = BASE_ME_ULR + "/customer_service/index.html?aidongid=";

    public final static String CHECK_VERSION = BASE_ME_ULR + "/checkVersion";

    public final static String VIDEO_SHARE = BASE_ME_ULR + "/eyeshot/?";
    public final static String LIVE_SHARE = BASE_ME_ULR + "/live/index.html?lid=";

    public final static String HOME_URL = BASE_URL_TEXT + "/getHomeNew";
    public final static String HOME_MORE_GOOD_URL = BASE_URL_TEXT + "/getHomeFoodDetail";

    public final static String TALENT_LIST_URL = BASE_URL_TEXT + "/carnival/talents.json";
    public final static String TALENT_DETAIL_URL = BASE_URL_TEXT + "/carnival/talents/";
    public final static String MINE_TALENT = BASE_URL_TEXT + "/mine/apply.json";
}

