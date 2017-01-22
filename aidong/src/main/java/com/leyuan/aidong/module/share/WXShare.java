package com.leyuan.aidong.module.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.View;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.utils.Logger;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.open.utils.ThreadManager;

import java.io.ByteArrayOutputStream;

import static com.leyuan.aidong.ui.App.context;


/**
 * 分享或收藏的目标场景，通过修改scene场景值实现。
 * 发送到聊天界面——WXSceneSession
 * 发送到朋友圈——WXSceneTimeline
 * 添加到微信收藏——WXSceneFavorite
 * 要使你的程序启动后微信终端能响应你的程序，必须在代码中向微信终端注册你的id。
 * （如下图所示，可以在入口Activity的onCreate回调程序函数处，
 * 或其他合适的地方将你的应用id注册到微信。注册函数示例如下图所示。
 */

public class WXShare {

    public final static String WX_APP_ID = "wx365ab323b9269d30";
    private IWXAPI api;

    public WXShare(Context context) {
        api = WXAPIFactory.createWXAPI(context, WX_APP_ID);
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
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        // 调用api接口发送数据到微信
        api.sendReq(req);
    }

    public void shareWeb(final String title, final String desc, String imageUrl, final String url, final boolean isCircleOfFriends) {

        if (api == null) {
            api = WXAPIFactory.createWXAPI(context,  context.getString(R.string.weixingAppID), false);
        }
        if (!api.isWXAppInstalled()) {
            Toast.makeText(context, "没有安装微信", Toast.LENGTH_SHORT).show();
            return;
        }

//        ImageRequestBuilder.newBuilderWithSource(Uri.parse(imageUrl))
//                .setPostprocessor(new BasePostprocessor() {
//                    @Override
//                    public void process(Bitmap bitmap) {
//                        super.process(bitmap);
//                        //图片不能超过32kb,需要压缩
//                        Logger.i("share","bitmap getWidth = " + bitmap.getWidth());
//                        double targetwidth = Math.sqrt(32.00 * 1000);
//                        if (bitmap.getWidth() > targetwidth || bitmap.getHeight() > targetwidth) {
//                            // 创建操作图片用的matrix对象
//                            Matrix matrix = new Matrix();
//                            // 计算宽高缩放率
//                            double x = Math.max(targetwidth / bitmap.getWidth(), targetwidth
//                                    / bitmap.getHeight());
//                            // 缩放图片动作
//                            matrix.postScale((float) x, (float) x);
//                            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
//                                    bitmap.getHeight(), matrix, true);
//                        }
//
//                        shareWeb(title, desc, bitmap, url, isCircleOfFriends);
//
//                    }
//                }).build();
        ImageLoader.getInstance().loadImage(imageUrl, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap bitmap) {
                //                        //图片不能超过32kb,需要压缩
                        Logger.i("share","bitmap getWidth = " + bitmap.getWidth());
                        double targetwidth = Math.sqrt(22.00 * 1000);
                        if (bitmap.getWidth() > targetwidth || bitmap.getHeight() > targetwidth) {
                            // 创建操作图片用的matrix对象
                            Matrix matrix = new Matrix();
                            // 计算宽高缩放率
                            double x = Math.max(targetwidth / bitmap.getWidth(), targetwidth
                                    / bitmap.getHeight());
                            // 缩放图片动作
                            matrix.postScale((float) x, (float) x);
                            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                                    bitmap.getHeight(), matrix, true);
                        }
                final Bitmap finalBitmap = bitmap;
                ThreadManager.getMainHandler().post(new Runnable() {

                    @Override
                    public void run() {

                        shareWeb(title, desc, finalBitmap, url, isCircleOfFriends);
                    }
                });
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });


    }


    public void shareWeb(String title, String desc, Bitmap bitmap, String url, boolean isCircleOfFriends) {

        Logger.i("share","shareWeb  bitmap getWidth = " + bitmap.getWidth()+",  height = " +bitmap.getHeight());
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;

        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = desc;

        bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_launcher);
        msg.thumbData = bmpToByteArray(bitmap, true);


        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = isCircleOfFriends ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
       boolean result =  api.sendReq(req);

        Logger.i("share","api.sendReq(req) return value = "  + result);

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
