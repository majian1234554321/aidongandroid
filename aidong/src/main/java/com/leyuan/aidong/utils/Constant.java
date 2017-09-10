package com.leyuan.aidong.utils;


import android.os.Environment;

import com.leyuan.aidong.entity.SystemBean;
import com.leyuan.aidong.entity.VenuesBean;
import com.leyuan.aidong.entity.data.FollowData;

import java.io.File;
import java.util.List;

public class Constant {
//    public static final String BASE_URL = "http://192.168.100.119:3000/";
//    public static final String BASE_URL = "http://app.51aidong.com/";
//    public static final String CAPTCHA_IMAGE = BASE_URL + "captcha_image/";
//    public static final String URL_USER_AGREEMENT = BASE_URL + "homepage";
//    public static final String URL_SHARE_DYNAMIC = "http://www.ostagram.ru";
//    public static final String URL_AFTER_SALES_SERVICE = "http://www.ostagram.ru";

    // 默认存放图片的路径
    public final static String DEFAULT_SAVE_IMAGE_PATH = Environment
            .getExternalStorageDirectory()
            + File.separator
            + "IDong"
            + File.separator + "idong_img" + File.separator;

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
    public static final int REQUEST_PUBLISH_DYNAMIC = 9;
    public static final int REQUEST_NEXT_ACTIVITY = 10;
    public static final int REQUEST_PHONE_BINGDING = 22;
    public static final int REQUEST_ADD_ADDRESS = 100;
    public static final int REQUEST_SELECT_ADDRESS = 101;
    public static final int REQUEST_UPDATE_DELIVERY = 102;
    public static final int REQUEST_SETTLEMENT_CART = 103;
    public static final int REQUEST_REFRESH_DYNAMIC = 104;
    public static final int REQUEST_VIDEO_COMMENT = 105;
    public static final int REQUEST_USER_INFO = 106;

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

    public static final int BINDING_PHONE_OK = 21;
    public static final String BINDING_PHONE = "binding_phone";
    public static final String DEFAULT_CITY = "上海";
    public static final String OS = "android";
    public static final String OS_TYPE = "andriod";
    public static final String BROADCAST_ACTION_SELECTED_CITY = "com.leyuan.chat.selected_city";
    public static final CharSequence NEGATIVE_ONE = "-1";
    public static final String TOTAL_GOODS_PRICE = "total_goods_price";
    public static final String COUPON_LIST = "coupon_list";
    public static final String CAMPAIGN = "campaign";
    public static final String ORDER = "order";
    public static final String COURSE = "course";
    public static final String PUSH_CAMPAIGN = "push_campaign";
    public static final String PUBLISH_COMMENT_NUMBER = "publishCommentNumber";
    public static final String BROADCAST_ACTION_REGISTER_SUCCESS = "com.leyuan.register.success";
    public static final String I_DONG_FITNESS = " | 爱动健身";
    public static final String SHARE_GOODS_TITLE = "爱动精选-推荐给您一个宝贝";
    public static final String VIDEO = "video";
    public static final String LIVE = "live";
    public static final String CATEGORY = "category";
    public static final String BROADCAST_ACTION_CLEAR_CMD_MESSAGE = "broadcast_action_clear_cmd_message";
    public static final String DYNAMIC_ID = "dynamic_id";
    public static final int GOODS_FOODS_START_INDEX = 10000;
    public static final String BROADCAST_ACTION_PUBLISH_DYNAMIC_SUCCESS = "broadcast_action_publish_dynamic_success";
    public static final String BROADCAST_ACTION_EXCHANGE_COUPON_SUCCESS = "broadcast_action_exchange_coupon_success";

    public static List<VenuesBean> gyms;
    public static SystemBean systemInfoBean;
    public static String activityOnLogin; //课程视频底部提示文字(登录状态下)
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

    //支付类型
    public static final String PAY_ALI = "alipay";
    public static final String PAY_WEIXIN = "wxpay";

    //商品类型
    public static final String GOODS_EQUIPMENT = "equipment";
    public static final String GOODS_FOODS = "food";
    public static final String GOODS_NUTRITION = "nutrition";

    //动态类型
    public static final int DYNAMIC_NONE = -1;
    public static final int DYNAMIC_VIDEO = 0;
    public static final int DYNAMIC_ONE_IMAGE = 1;
    public static final int DYNAMIC_TWO_IMAGE = 2;
    public static final int DYNAMIC_THREE_IMAGE = 3;
    public static final int DYNAMIC_FOUR_IMAGE = 4;
    public static final int DYNAMIC_FIVE_IMAGE = 5;
    public static final int DYNAMIC_SIX_IMAGE = 6;
    public static final int DYNAMIC_MULTI_IMAGE = 7;

    //首页item类型
    public static final int HOME_IMAGE_AND_HORIZONTAL_LIST = 0;
    public static final int HOME_TITLE_AND_VERTICAL_LIST = 1;
    public static final int HOME_ITEM_DEFAULT = 1;

    //确认订单结算类型
    public static final String SETTLEMENT_CART = "cart";
    public static final String SETTLEMENT_NURTURE_IMMEDIATELY = "nutrition";
    public static final String SETTLEMENT_FOOD_IMMEDIATELY = "food";
    public static final String SETTLEMENT_EQUIPMENT_IMMEDIATELY = "equipment";

    //获取优惠券的位置
    public static final String COUPON_EQUIPMENT = "equipment";
    public static final String COUPON_NUTRITION = "nutrition";
    public static final String COUPON_COURSE = "course";
    public static final String COUPON_CAMPAIGN = "campaign";
    public static final String COUPON_CART = "cart";

    //推荐商品位置
    public static final String RECOMMEND_EQUIPMENT = "equipment";
    public static final String RECOMMEND_NUTRITION = "nutrition";
    public static final String RECOMMEND_FOOD = "food";
    public static final String RECOMMEND_CART = "cart";
    public static final String RECOMMEND_ORDER = "iorder";

    //交货方式
    public static final String DELIVERY_EXPRESS = "0";  //快递
    public static final String DELIVERY_SELF = "1";     //自提

    //透传消息字段定义
    public static String KDNUSERNAME = "kDNUserName";
    public static final String CIRCLE = "circle";
    public static final String KCMDMSGTYPE = "kCMDMsgType";
    public static final String KDNPRAISEAVATAR = "kDNPraiseAvatar";
    public static final String KDNID = "kDNID";
    public static final String KDNOCCURTIME = "kDNOccurTime";
    public static final String KDNCONTENT = "kDNContent";
    public static final String KDNCONTENTURL = "kDNContentUrl";
    public static final String KDNCOMMENTTYPE = "kDNCommentType";
    public static final String KDNMSGID = "kDNMsgID";
    public static final String KDNCONTENTTYPE = "kDNContentType";
    public static final String KDNREPLYSITENICKNAME = "kDNreplySiteNickName";
    public static final String BROADCAST_ACTION_RECEIVER_CMD_MESSAGE = "broadcast_action_receiver_cmd_message";


    public static class Chat {
        public static final String SYSYTEM_ID = "admin";
    }
}
