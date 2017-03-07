package com.leyuan.aidong.module.share;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.leyuan.aidong.R;
import com.leyuan.aidong.module.weibo.WeiBoConstants;
import com.leyuan.aidong.utils.Logger;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.constant.WBConstants;

/**
 * Created by user on 2016/12/6.
 */

public class WeiBoShare implements IWeiboHandler.Response {
    
    IWeiboShareAPI mWeiboShareAPI;
    private Activity context;

    public  WeiBoShare(Activity context){
         this.context = context;
        // 创建微博分享接口实例
         mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(context, WeiBoConstants.APP_KEY);
        // 注册第三方应用到微博客户端中，注册成功后该应用将显示在微博的应用列表中。
        // 但该附件栏集成分享权限需要合作申请，详情请查看 Demo 提示
        // NOTE：请务必提前注册，即界面初始化的时候或是应用程序初始化时，进行注册
        mWeiboShareAPI.registerApp();
    }

     public  void onNewIntent(Intent intent){
         mWeiboShareAPI.handleWeiboResponse(intent, this);
     }

    @Override
    public void onResponse(BaseResponse baseResponse) {
        if(baseResponse!= null){
            Logger.i("share"," baseResponse.errCode = " + baseResponse.errCode);
            switch (baseResponse.errCode) {


                case WBConstants.ErrorCode.ERR_OK:

                    break;
                case WBConstants.ErrorCode.ERR_CANCEL:

                    break;
                case WBConstants.ErrorCode.ERR_FAIL:

                    break;
            }
        }
    }



    public  void sendMessage(String title,String content, Bitmap bitmap, String webUrl) {
        Logger.i("share"," sendMessage = " );
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();

        TextObject textObject = new TextObject();
        textObject.text = title+"-"+content;
        weiboMessage.textObject = textObject;

        //图片不能超过32kb
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.back);
        Logger.i("share"," bitmap size  = " +bitmap.getByteCount());
        ImageObject imageObject = new ImageObject();
        imageObject.setImageObject(bitmap);
        weiboMessage.imageObject = imageObject;


        WebpageObject mediaObject = new WebpageObject();
        mediaObject.actionUrl = webUrl;
        //导入的是shareSdk的微博 需要删掉shareSdk
        weiboMessage.mediaObject = mediaObject;

        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;
        mWeiboShareAPI.sendRequest(context, request);

        Logger.i("share","  mWeiboShareAPI.sendRequest(context, request);" );

    }

    public  void share(final String title, final String content, String imageUrl, final String webUrl) {

        sendMessage(title, content, null, webUrl);

        /*ImageLoader.getInstance().loadImage(imageUrl, new ImageLoadingListener() {
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
                double targetwidth = Math.sqrt(32.00 * 1000);
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
                        sendMessage(title, content, finalBitmap, webUrl);
                    }
                });

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });*/


    }

    public void release() {
        
    }
}
