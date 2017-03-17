package com.leyuan.aidong.utils;


import com.leyuan.aidong.entity.SystemBean;
import com.leyuan.aidong.entity.data.FollowData;

public class Constant {
//    public static final String BASE_URL = "http://192.168.100.119:3000/";

    public static final String BASE_URL = "http://app.51aidong.com/";
    public static final String CAPTCHA_IMAGE = BASE_URL + "captcha_image/";
    public static final String URL_USER_AGREEMENT = BASE_URL + "homepage";


    public static final int OK = 1;
    public static final int NOT_LOGIN = 101;
    public static final int PAGE_FIRST = 1;
    public static final int CODE_OPEN_ALBUM = 11;
    public static final int REQUEST_LOGIN = 0;
    public static final int REQUEST_SELECT_PHOTO = 1;
    public static final int REQUEST_SELECT_VIDEO = 2;
    public static final int REQUEST_SELECT_COUPON = 3;
    public static final int REQUEST_CONFIRM = 4;
    public static final int REQUEST_ADD_CART = 5;
    public static final int REQUEST_BUY_IMMEDIATELY = 6;
    public static final int REQUEST_TO_CART = 7;
    public static final int REQUEST_TO_DYNAMIC = 8;
    public static final int DEFAULT_MAX_UPLOAD_IMAGE_COUNT = 6;        //上传照片数量限制
    public static final String FILE_FOLDER = "Aidong";
    public static final String EMPTY_STR = " ";

    public static final String SEIRES_ID = "series_id";
    public static final String PHASE = "phase";
    public static final String WX_LOGIN_CODE = "wx_login_code";
    public static final String WX_LOGIN_SUCCESS_ACTION = "com.leyuan.login.wx";
    public static final String VIDEO_LIST_TYPE = "video_list_type";
    public static final String BROADCAST_ACTION_NEW_MESSAGE = "com.leyuan.chat.message";
    public static final String NICK_NAME = "nickname";
    public static final String AVATAR = "avatar";
    public static final String SUCCESS = "success";
    public static final String STATE = "state";
    public static final int REQUEST_NEXT_ACTIVITY = 10;


    public static SystemBean systemInfoBean;
    public static FollowData followData;

    public static final byte ID_OFFICAL = 1;
    public static final byte ID_COACH = 2;
    public static final String LIVE_ID = "live_id";
    public static final String VIDEO_ID = "video_id";
    public static final String VIDEO_NAME = "video_name";
    public static final String LIVE_INFO = "live_info";
    public static final String VIDEO_TYPE = "1";
    public static final String VIDEO_PATH = "/aidong/1";
    public static final String COPY_IMAGE = "copy_image";
    public static final String POSITION = "position";

    public static class Chat {
        public static final String SYSYTEM_ID = "admin";
    }
}
