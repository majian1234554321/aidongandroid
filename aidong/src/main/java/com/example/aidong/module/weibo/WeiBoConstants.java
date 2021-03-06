package com.example.aidong.module.weibo;

/**
 * Created by user on 2016/12/6.
 */

public interface  WeiBoConstants {
    /** 当前 DEMO 应用的 APP_KEY，第三方应用应该使用自己的 APP_KEY 替换该 APP_KEY */
//    public static final String APP_KEY      = "2029019793";
//    public static final String App_Secret= "4bb1a98ba671b19c71614f58fbc1e9bd";

    public static final String APP_KEY      = "2668022259";
    public static final String App_Secret= "6d2047e118a3c12909225c21d9d7f2ad";
    /**
     * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
     * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
     */
    public static final String REDIRECT_URL = "http://www.umeng.com/social";

    /**
     * WeiboSDKDemo 应用对应的权限，第三方开发者一般不需要这么多，可直接设置成空即可。
     * 详情请查看 Demo 中对应的注释。
     */
    public static final String SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog";
}
