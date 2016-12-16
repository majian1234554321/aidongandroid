package com.leyuan.aidong.module.share;

import android.app.Activity;
import android.content.Intent;


public class MyShareUtils {
    public static final int SHARE_WEIXIN_CHAT = 1;
    public static final int SHARE_WEIXIN_FRIENDS = 2;
    public static final int SHARE_QQ = 3;
    public static final int SHARE_WEIBO = 4;

    private Activity context;
    private  MyQQShare myQQShare;
    private WeiBoShare weiBoShare;
    private WXShare wxShare;

    public  MyShareUtils(Activity context){
        this.context = context;
        myQQShare = new MyQQShare(context);
        weiBoShare = new WeiBoShare(context);
        wxShare = new WXShare(context);
    }

    public void share(int shareMode, String title, String content, String imageUrl, String webUrl) {
        switch (shareMode){
            case SHARE_WEIXIN_CHAT:
                wxShare.shareWeb(title,content,imageUrl,webUrl,false);
                break;
            case SHARE_WEIXIN_FRIENDS:
                wxShare.shareWeb(title,content,imageUrl,webUrl,true);
                break;
            case SHARE_QQ:
                myQQShare.share(title,content,imageUrl,webUrl);
                break;
            case SHARE_WEIBO:
                weiBoShare.share(title,content,imageUrl,webUrl);
                break;
        }
    }

    /**
     *call this in activity onActivityResult method
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        myQQShare.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * you must call this when activity is created and newIntent
     * @param intent
     */
    public  void onNewIntent(Intent intent){
        if(intent!=null)
        weiBoShare.onNewIntent(intent);
    }

    public void release() {
        context = null;
        myQQShare.release();
        wxShare.release();
        weiBoShare.release();
    }
}
