package com.leyuan.aidong.utils;


import com.leyuan.aidong.entity.SystemBean;

public class Constant {
    //public static final String  BASE_URL = "http://192.168.0.223:5000/";
    public static final String  BASE_URL = "http://192.168.0.207:3000/";

    public static final int OK = 1;
    public static final int PAGE_FIRST = 1;
    public static final int CODE_OPEN_ALBUM = 11;
    public static final int ORDER_FROM_CART = 1;
    public static final int ORDER_BUY_NURTURE_IMMEDIATELY = 2;
    public static final int ORDER_BUY_EQUIPMENT_IMMEDIATELY = 3;
    public static final String PAY_ALI = "alipay";
    public static final String PAY_WEI_XIN = "wxpay";
    public static final String TYPE_NURTURE = "nutrition";
    public static final String TYPE_EQUIPMENT = "equipments";
    public static final String TYPE_FOODS = "foods";
    public static final String RECOMMEND_TYPE_CART = "cart";
    public static final String RECOMMEND_TYPE_EQUIPMENT = "equipment";
    public static final String RECOMMEND_TYPE_NUTRITION= "nutrition";
    public static final String EMPTY_STR = " ";
    public static final String DELIVERY_EXPRESS = "0";
    public static final String DELIVERY_SELF = "1";
    public static final int DEFAULT_MAX_UPLOAD_IMAGE_COUNT = 6;        //上传照片数量限制
    public static final String SEIRES_ID = "series_id";
    public static final String PHASE = "phase";

    public static SystemBean systemInfoBean;

    public static final String FILE_FOLDER = "Aidong";
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
}
