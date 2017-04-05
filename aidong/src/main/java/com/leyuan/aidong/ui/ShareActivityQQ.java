package com.leyuan.aidong.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.leyuan.aidong.R;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.ToastGlobal;
import com.tencent.connect.share.QQShare;
import com.tencent.open.utils.ThreadManager;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * Created by user on 2017/4/5.
 */
public class ShareActivityQQ extends Activity {

    private String title;
    private String content;
    private String imageUrl;
    private String webUrl;

    public static void start(Context context, String title, String content, String imageUrl, String webUrl) {
        Intent intent = new Intent(context, ShareActivityQQ.class);
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

        mTencent = Tencent.createInstance(getString(R.string.qq_id), getApplicationContext());

        share(title, content, imageUrl, webUrl);
    }


    private Tencent mTencent;
    private IUiListener qqShareListener = new IUiListener() {

        @Override
        public void onCancel() {
            Logger.i("QQSHARE", "qqShareListener onCancel");
            ToastGlobal.showShortConsecutive("分享取消");
            finish();
        }

        @Override
        public void onComplete(Object response) {
            Logger.i("QQSHARE", "qqShareListener onComplete");
            ToastGlobal.showShortConsecutive("分享成功");
            finish();
        }

        @Override
        public void onError(UiError e) {
            Logger.i("QQSHARE", "qqShareListener onError");
            ToastGlobal.showShortConsecutive("分享失败");
            finish();
        }
    };

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
                    mTencent.shareToQQ(ShareActivityQQ.this, params, qqShareListener);
                }else{
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data, qqShareListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTencent.releaseResource();
        mTencent = null;
    }
}
