package com.example.aidong.module.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.aidong .utils.Logger;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;


/**
 * 分享或收藏的目标场景，通过修改scene场景值实现。
 * 发送到聊天界面——WXSceneSession
 * 发送到朋友圈——WXSceneTimeline
 * 添加到微信收藏——WXSceneFavorite
 * 要使你的程序启动后微信终端能响应你的程序，必须在代码中向微信终端注册你的id。
 * （如下图所示，可以在入口Activity的onCreate回调程序函数处，
 * 或其他合适的地方将你的应用id注册到微信。注册函数示例如下图所示。
 */

@Deprecated
public class WXShare {

    public final static String WX_APP_ID = "wx365ab323b9269d30";
    private IWXAPI api;
    private Context context;

    public WXShare(Context context) {
        this.context = context;
        api = WXAPIFactory.createWXAPI(context, WX_APP_ID, true);
        api.registerApp(WX_APP_ID);
    }

    // 文本分享
    private void shareText() {
        // 初始化一个WXTextObject对象
        WXTextObject textObj = new WXTextObject();
        textObj.text = "微信文本分享测试";
        // 用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;   // 发送文本类型的消息时，title字段不起作用
        // msg.title = "Will be ignored";
        msg.description = "微信文本分享测试";   // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
        req.message = msg;   // 分享或收藏的目标场景，通过修改scene场景值实现。
        // 发送到聊天界面 —— WXSceneSession
        // 发送到朋友圈 —— WXSceneTimeline
        // 添加到微信收藏 —— WXSceneFavorite
        req.scene = SendMessageToWX.Req.WXSceneSession;
        // 调用api接口发送数据到微信
        api.sendReq(req);
    }

    public void share(final String title, final String desc, String imageUrl, final String url, final boolean isCircleOfFriends) {

        if (api == null) {
            api = WXAPIFactory.createWXAPI(context, WX_APP_ID, false);
        }
        if (!api.isWXAppInstalled()) {
            Toast.makeText(context, "没有安装微信", Toast.LENGTH_SHORT).show();
            return;
        }

        Glide.with(context).load(imageUrl).asBitmap()
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
        msg.thumbData = bmpToByteArray(bitmap, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = isCircleOfFriends ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        boolean result = api.sendReq(req);

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

    public void release() {

    }
}
