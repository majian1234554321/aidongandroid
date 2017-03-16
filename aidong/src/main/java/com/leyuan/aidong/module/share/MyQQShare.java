package com.leyuan.aidong.module.share;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.leyuan.aidong.R;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.ToastUtil;
import com.tencent.connect.common.Constants;
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
    private Activity context;
    private IUiListener qqShareListener = new IUiListener() {

        @Override
        public void onCancel() {
            Logger.i("QQSHARE", "qqShareListener onCancel");
            ToastUtil.show("分享取消", context);
//                shareCallback.onCancel();
        }

        @Override
        public void onComplete(Object response) {
            Logger.i("QQSHARE", "qqShareListener onComplete");
            ToastUtil.show("分享成功", context);
//                shareCallback.onComplete(response);
        }

        @Override
        public void onError(UiError e) {
            Logger.i("QQSHARE", "qqShareListener onError");
            ToastUtil.show("分享失败", context);
//                shareCallback.onError();
        }
    };

    public MyQQShare(Activity context) {
        mTencent = Tencent.createInstance(context.getString(R.string.qq_id), context.getApplicationContext());
        this.context = context;
    }

    public void share(String title, String content, String imageUrl, String webUrl) {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, content);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, webUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "爱动健身");
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_QQ_SHARE) {
            Tencent.onActivityResultData(requestCode, resultCode, data, qqShareListener);
        }
    }

    public void release() {
        mTencent.releaseResource();
        mTencent = null;
        context = null;
        qqShareListener = null;
    }

    public void setShareListener(ShareCallback listener) {

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
