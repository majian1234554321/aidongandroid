package com.leyuan.aidong.module.share;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.leyuan.aidong.module.weibo.AccessTokenKeeper;
import com.leyuan.aidong.module.weibo.WeiBoConstants;
import com.leyuan.aidong.utils.Logger;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.utils.Utility;

/**
 * Created by user on 2016/12/6.
 */

public class WeiBoShare {

    IWeiboShareAPI mWeiboShareAPI;
    private Activity context;

    public WeiBoShare(Activity context, Bundle savedInstanceState) {
        this.context = context;
        // 创建微博分享接口实例
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(context, WeiBoConstants.APP_KEY);

        // 获取微博客户端相关信息，如是否安装、支持 SDK 的版本
        boolean isInstalledWeibo = mWeiboShareAPI.isWeiboAppInstalled();
        int supportApiLevel = mWeiboShareAPI.getWeiboAppSupportAPI();

        // 注册第三方应用到微博客户端中，注册成功后该应用将显示在微博的应用列表中。
        // 但该附件栏集成分享权限需要合作申请，详情请查看 Demo 提示
        // NOTE：请务必提前注册，即界面初始化的时候或是应用程序初始化时，进行注册
        boolean registerResult = mWeiboShareAPI.registerApp();
        Logger.i("share weibo", "registerResult = " + registerResult);

        // 当 Activity 被重新初始化时（该 Activity 处于后台时，可能会由于内存不足被杀掉了），
        // 需要调用 {@link IWeiboShareAPI#handleWeiboResponse} 来接收微博客户端返回的数据。
        // 执行成功，返回 true，并调用 {@link IWeiboHandler.Response#onResponse}；
        // 失败返回 false，不调用上述回调
//        if (savedInstanceState != null) {
//            mWeiboShareAPI.handleWeiboResponse(context.getIntent(), this);
//        }
    }

    public void onNewIntent(Intent intent, IWeiboHandler.Response response) {
        // 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
        // 来接收微博客户端返回的数据；执行成功，返回 true，并调用
        // {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
        boolean callWeiboRespense = mWeiboShareAPI.handleWeiboResponse(intent, response);
        Logger.i("share", "weibo onNewIntent callWeiboRespense = " + callWeiboRespense);
    }

//    @Override
//    public void onResponse(BaseResponse baseResponse) {
//        if (baseResponse != null) {
//            Logger.i("share", "weibo share baseResponse.errCode = " + baseResponse.errCode);
//            switch (baseResponse.errCode) {
//                case WBConstants.ErrorCode.ERR_OK:
//                    ToastUtil.showConsecutiveShort(R.string.share_ok);
//                    break;
//                case WBConstants.ErrorCode.ERR_CANCEL:
//                    ToastUtil.showConsecutiveShort(R.string.share_cancel);
//                    break;
//                case WBConstants.ErrorCode.ERR_FAIL:
//                    ToastUtil.showConsecutiveShort(R.string.share_fail);
//                    break;
//            }
//        }
//    }


    public void sendMessage(String title, String content, Bitmap bitmap, String webUrl) {
        Logger.i("share", " sendMessage = ");
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();

        TextObject textObject = new TextObject();
        textObject.text = title + "-" + content;
        weiboMessage.textObject = textObject;

        //图片不能超过32kb
        Logger.i("share", " bitmap size  = " + bitmap.getByteCount());
        ImageObject imageObject = new ImageObject();
        imageObject.setImageObject(bitmap);
        weiboMessage.imageObject = imageObject;

        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = title;
        mediaObject.description = content;
        mediaObject.setThumbImage(bitmap);
        mediaObject.actionUrl = webUrl;
        mediaObject.defaultText = "Webpage 默认文案";
        //导入的是shareSdk的微博 需要删掉shareSdk
        weiboMessage.mediaObject = mediaObject;

        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;
//        mWeiboShareAPI.sendRequest(context, request);

        AuthInfo authInfo = new AuthInfo(context, WeiBoConstants.APP_KEY, WeiBoConstants.REDIRECT_URL, WeiBoConstants.SCOPE);
        Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(context);
        String token = "";
        if (accessToken != null) {
            token = accessToken.getToken();
        }
        mWeiboShareAPI.sendRequest(context, request, authInfo, token, new WeiboAuthListener() {

            @Override
            public void onWeiboException(WeiboException arg0) {
                Logger.i("share", "  mWeiboShareAPI onWeiboException");
            }

            @Override
            public void onComplete(Bundle bundle) {

                Logger.i("share", "  mWeiboShareAPI onComplete");
                Oauth2AccessToken newToken = Oauth2AccessToken.parseAccessToken(bundle);
                AccessTokenKeeper.writeAccessToken(context, newToken);
//                Toast.makeText(context, "onAuthorizeComplete token = " + newToken.getToken(), 0).show();
            }

            @Override
            public void onCancel() {

                Logger.i("share", "  mWeiboShareAPI onCancelJoin");
            }
        });


        Logger.i("share", "  mWeiboShareAPI.sendRequest(context, request);");

    }

    public void share(final String title, final String content, String imageUrl, final String webUrl) {
        Glide.with(context).load(imageUrl).asBitmap()
                .into(new SimpleTarget<Bitmap>(100, 100) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        sendMessage(title, content, resource, webUrl);
                    }
                });
    }

    public void release() {
        mWeiboShareAPI = null;
    }

    public void setShareListener(ShareCallback listener) {

    }
}
