package com.leyuan.aidong.module.share;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;

import com.leyuan.aidong.R;
import com.tencent.connect.share.QQShare;
import com.tencent.open.utils.ThreadManager;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * Created by user on 2016/12/3.
 */

public class MyQQShare {
    private Tencent mTencent;
    private  Activity context ;

    public MyQQShare(Activity context){
        mTencent = Tencent.createInstance(context.getString(R.string.qq_id), context.getApplicationContext());
        this.context = context;
    }

    public  void share(String title, String content, Bitmap bitmap, String webUrl){
        final Bundle params = new Bundle();
        //本地地址一定要传sdcard路径，不要什么getCacheDir()或getFilesDir()
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, Environment.getExternalStorageDirectory().getAbsolutePath().concat("/a.png"));
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "爱动健身");
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN); //打开这句话，可以实现分享纯图到QQ空间
        doShareToQQ(params);
    }


    public  void share(String title, String content, String imageUrl, String webUrl){
        final Bundle params = new Bundle();
        //本地地址一定要传sdcard路径，不要什么getCacheDir()或getFilesDir()
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl);
        params.putString(QQShare.SHARE_TO_QQ_TITLE,title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,content);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,webUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "爱动健身");
        doShareToQQ(params);
    }
    private void doShareToQQ(final Bundle params) {
        // QQ分享要在主线程做
        ThreadManager.getMainHandler().post(new Runnable() {

            @Override
            public void run() {
                if (null != mTencent) {
                    mTencent.shareToQQ(context, params, qqShareListener);
                }
            }
        });
    }

    IUiListener qqShareListener = new IUiListener() {
        @Override
        public void onCancel() {
        }

        @Override
        public void onComplete(Object response) {
        }

        @Override
        public void onError(UiError e) {
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, qqShareListener);
    }

    public void release() {

    }

    /**
     *  由于QQ分享并不需要提前登录，因此，可以直接调用Tencent.shareToQQ的接口；接口调用很简单，代码如下：
     public void share(View view)
     {
     Bundle bundle = new Bundle();
     //这条分享消息被好友点击后的跳转URL。
     bundle.putString(Constants.PARAM_TARGET_URL, "http://connect.qq.com/");
     //分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_SUMMARY不能全为空，最少必须有一个是有值的。
     bundle.putString(Constants.PARAM_TITLE, "我在测试");
     //分享的图片URL
     bundle.putString(Constants.PARAM_IMAGE_URL, "http://img3.cache.netease.com/photo/0005/2013-03-07/8PBKS8G400BV0005.jpg");
     //分享的消息摘要，最长50个字
     bundle.putString(Constants.PARAM_SUMMARY, "测试");
     //手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
     bundle.putString(Constants.PARAM_APPNAME, "??我在测试");
     //标识该消息的来源应用，值为应用名称+AppId。
     bundle.putString(Constants.PARAM_APP_SOURCE, "星期几" + AppId);

     mTencent.shareToQQ(this, bundle , listener);

     }
     特别注意：一定要添加以下代码，才可以从回调listener中获取到消息

     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     if (null != mTencent)
     mTencent.onActivityResult(requestCode, resultCode, data);
     }
     */
}
