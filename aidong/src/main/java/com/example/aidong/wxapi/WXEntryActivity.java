package com.example.aidong.wxapi;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.Logger;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;

    private static final String APP_ID = "wx365ab323b9269d30";
    private static final java.lang.String TAG = "WXEntryActivity";
    private IWXAPI api;
    private String code_code;
    private String title;
    private String content;
    private String imageUrl;
    private String webUrl;
    private boolean isCircle;

    public static void start(Context context, String title, String content, String imageUrl, String webUrl, boolean b) {
        Intent intent = new Intent(context, WXEntryActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        intent.putExtra("imageUrl", imageUrl);
        intent.putExtra("webUrl", webUrl);
        intent.putExtra("isCircle", b);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Logger.i(TAG, "onCreate share ");
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        imageUrl = intent.getStringExtra("imageUrl");
        webUrl = intent.getStringExtra("webUrl");
        isCircle = intent.getBooleanExtra("isCircle", false);


        api = WXAPIFactory.createWXAPI(this, APP_ID, false);
        api.registerApp(APP_ID);
        api.handleIntent(getIntent(), this);

        if (title != null) {
            share(title, content, imageUrl, webUrl, isCircle);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Logger.i("share", "  public void onReq(BaseReq baseReq) {");
    }

    @Override
    public void onResp(BaseResp resp) {
        Logger.i("share", "resp.errCode " + resp.errCode + " type = " + resp.getType());
        if (RETURN_MSG_TYPE_LOGIN == resp.getType()) {
            Intent intent = new Intent();
            intent.setAction(Constant.WX_LOGIN_SUCCESS_ACTION);
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    code_code = ((SendAuth.Resp) resp).code;
                    intent.putExtra(Constant.STATE, 1);
                    intent.putExtra(Constant.WX_LOGIN_CODE, code_code);
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    intent.putExtra(Constant.STATE, 2);
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    intent.putExtra(Constant.STATE, 3);
                    break;
                default:
                    intent.putExtra(Constant.STATE, 4);
            }
            intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            sendBroadcast(intent);
            Logger.i("login ", " sendBroadcast");
        } else {
            String result = null;
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    result = "发送成功";
                    Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    result = "发送取消";
                    Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    result = "发送被拒绝";
                    Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                    break;
                default:
                    result = "发送返回";
                    Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                    break;
            }
        }

        Logger.i(TAG, "onResp finish  share ");
        finish();
    }


    public void share(final String title, final String desc, String imageUrl, final String url, final boolean isCircleOfFriends) {

        if (api == null) {
            api = WXAPIFactory.createWXAPI(this, APP_ID, false);
        }
        if (!api.isWXAppInstalled()) {
            Toast.makeText(this, "没有安装微信", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Glide.with(this).load(imageUrl).asBitmap()
                .into(new SimpleTarget<Bitmap>(50, 50) {
                    @Override
                    public void onResourceReady(final Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        shareWeb(title, desc, resource, url, isCircleOfFriends);
                    }
                });

    }

    public void shareWeb(String title, String desc, Bitmap bitmap, String url, boolean isCircleOfFriends) {

        Logger.i("share", "shareWeb  bitmap getWidth = " + bitmap.getWidth() + ",  height = " + bitmap.getHeight());
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;

        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = desc;
        msg.thumbData = bmpToByteArray(bitmap, false);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = isCircleOfFriends ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        boolean result = api.sendReq(req);
        finish();

        Logger.i("share", "api.sendReq(req) return value = " + result);

    }


    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.i(TAG, "onDestroy share ");
    }
}
