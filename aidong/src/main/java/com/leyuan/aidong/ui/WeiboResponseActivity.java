package com.leyuan.aidong.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.leyuan.aidong.R;
import com.leyuan.aidong.module.weibo.AccessTokenKeeper;
import com.leyuan.aidong.module.weibo.WeiBoConstants;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.ToastUtil;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.utils.Utility;

import static com.leyuan.aidong.ui.App.context;

/**
 * Created by user on 2017/3/15.
 */
public class WeiboResponseActivity extends Activity implements IWeiboHandler.Response {

    private IWeiboShareAPI mWeiboShareAPI;

    private String title;
    private String content;
    private String imageUrl;
    private String webUrl;

    public static void start(Context context, String title, String content, String imageUrl, String webUrl) {
        Intent intent = new Intent(context, WeiboResponseActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        intent.putExtra("imageUrl", imageUrl);
        intent.putExtra("webUrl", webUrl);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        imageUrl = intent.getStringExtra("imageUrl");
        webUrl = intent.getStringExtra("webUrl");

        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(context, WeiBoConstants.APP_KEY);

        mWeiboShareAPI.registerApp();
        if (savedInstanceState != null) {
            mWeiboShareAPI.handleWeiboResponse(getIntent(), this);
        }
        Logger.i("share  WeiboResponseActivity onCreate");

        share(title, content, imageUrl, webUrl);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Logger.i("share  WeiboResponseActivity onNewIntent");
        mWeiboShareAPI.handleWeiboResponse(intent, this);
    }

    @Override
    public void onResponse(BaseResponse baseResponse) {
        Logger.i("share  WeiboResponseActivity ", "weibo share baseResponse.errCode = ");
        if (baseResponse != null) {

            switch (baseResponse.errCode) {
                case WBConstants.ErrorCode.ERR_OK:
                    ToastUtil.showConsecutiveShort(R.string.share_ok);
                    break;
                case WBConstants.ErrorCode.ERR_CANCEL:
                    ToastUtil.showConsecutiveShort(R.string.share_cancel);
                    break;
                case WBConstants.ErrorCode.ERR_FAIL:
                    ToastUtil.showConsecutiveShort(R.string.share_fail);
                    break;
            }
        }
        finish();
    }


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

        AuthInfo authInfo = new AuthInfo(this, WeiBoConstants.APP_KEY, WeiBoConstants.REDIRECT_URL, WeiBoConstants.SCOPE);
        Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(this);
        String token = "";
        if (accessToken != null) {
            token = accessToken.getToken();
        }
        mWeiboShareAPI.sendRequest(this, request, authInfo, token, new WeiboAuthListener() {

            @Override
            public void onWeiboException(WeiboException arg0) {
                Logger.i("share", "  mWeiboShareAPI onWeiboException");
            }

            @Override
            public void onComplete(Bundle bundle) {

                Logger.i("share", "  mWeiboShareAPI onComplete");
                Oauth2AccessToken newToken = Oauth2AccessToken.parseAccessToken(bundle);
                AccessTokenKeeper.writeAccessToken(WeiboResponseActivity.this, newToken);
//                Toast.makeText(context, "onAuthorizeComplete token = " + newToken.getToken(), 0).show();
            }

            @Override
            public void onCancel() {

                Logger.i("share", "  mWeiboShareAPI onCancel");
            }
        });


        Logger.i("share", "  mWeiboShareAPI.sendRequest(context, request);");

    }

    public void share(final String title, final String content, String imageUrl, final String webUrl) {
        Glide.with(this).load(imageUrl).asBitmap()
                .into(new SimpleTarget<Bitmap>(100, 100) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        sendMessage(title, content, resource, webUrl);
                    }
                });
    }
}
