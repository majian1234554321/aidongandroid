package com.leyuan.commonlibrary.share;

import android.app.Activity;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

public class ShareUtils {

    public ShareUtils(Activity context){
        ShareSDK.initSDK(context);
    }
    public void shareWechatMoments(String title, String desc, String imageUrl, String url, PlatformActionListener listener) {
        Platform.ShareParams spWechatMoments = new Platform.ShareParams();
        spWechatMoments.setShareType(Platform.SHARE_WEBPAGE);
        spWechatMoments.setTitle(title);
        spWechatMoments.setText(desc);
        spWechatMoments.setImageUrl(imageUrl);
        spWechatMoments.setUrl(url);

        Platform wechatMoments = ShareSDK.getPlatform(WechatMoments.NAME);
        wechatMoments.setPlatformActionListener(listener);
        wechatMoments.share(spWechatMoments);
    }

    public void shareWechat(String title, String desc, String imageUrl, String url, PlatformActionListener listener) {
        Platform.ShareParams spWechat = new Platform.ShareParams();
        spWechat.setShareType(Platform.SHARE_WEBPAGE);
        spWechat.setTitle(title);
        spWechat.setText(desc);
        spWechat.setImageUrl(imageUrl);
        spWechat.setUrl(url);

        spWechat.setShareType(Platform.SHARE_WEBPAGE);
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        wechat.setPlatformActionListener(listener);
        wechat.share(spWechat);
    }

    public void shareWeibo(String title, String desc, String imageUrl, String url, PlatformActionListener listener) {
        Platform.ShareParams spWeibo = new Platform.ShareParams();
        spWeibo.setText(title + desc + url);
        spWeibo.setImageUrl(imageUrl);
        Platform sinaWeibo = ShareSDK.getPlatform(SinaWeibo.NAME);
        sinaWeibo.setPlatformActionListener(listener);
        sinaWeibo.share(spWeibo);
    }

    public void shareQQ(String title, String desc, String imageUrl, String url, PlatformActionListener listener) {
        Platform.ShareParams spQQ = new Platform.ShareParams();
        spQQ.setShareType(Platform.SHARE_WEBPAGE);
        spQQ.setTitle(title);
        spQQ.setText(desc);
        spQQ.setImageUrl(imageUrl);
        spQQ.setTitleUrl(url);

        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.setPlatformActionListener(listener);
        qq.share(spQQ);
    }

    public void shareQQzone(String title, String desc, String imageUrl, String url, PlatformActionListener listener) {
        Platform.ShareParams spQzone = new Platform.ShareParams();
        spQzone.setTitle(title);
        spQzone.setTitleUrl(url);
        spQzone.setText(desc);
        spQzone.setImageUrl(imageUrl);
        spQzone.setSite("爱动健身");
        spQzone.setSiteUrl(url);

        Platform qzone = ShareSDK.getPlatform(QZone.NAME);
        qzone.setPlatformActionListener(listener);
        qzone.share(spQzone);
    }
}
