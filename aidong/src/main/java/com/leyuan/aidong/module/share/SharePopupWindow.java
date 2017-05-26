package com.leyuan.aidong.module.share;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.example.aidong.wxapi.WXEntryActivity;
import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.ShareActivityQQ;
import com.leyuan.aidong.ui.WeiboResponseActivity;
import com.leyuan.aidong.utils.HtmlToStringUtils;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.ToastUtil;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.constant.WBConstants;


/**
 * Created by huyouni on 2016/12/7.
 */

public class SharePopupWindow extends PopupWindow implements View.OnClickListener {

    private Activity context;
    private ImageView imgWeichat;
    private ImageView imgWeiFriend;
    private ImageView imgQq;
    private ImageView imgWeibo;
    //    private MyShareUtils myShareUtils;
    private String title;
    private String content;
    private String imageUrl;
    private String webUrl;

    public SharePopupWindow(Activity context) {
        super(context);
        this.context = context;
//        myShareUtils = new MyShareUtils(context);

        initView();
        initData();
    }

    private void initView() {
        View contentView = View.inflate(context, R.layout.popup_window_share, null);
        this.setContentView(contentView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setTouchable(true);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.popupStyle);

        ColorDrawable background = new ColorDrawable(0x4f000000);
        this.setBackgroundDrawable(background);

        imgWeichat = (ImageView) contentView.findViewById(R.id.img_weichat);
        imgWeiFriend = (ImageView) contentView.findViewById(R.id.img_wei_friend);
        imgQq = (ImageView) contentView.findViewById(R.id.img_qq);
        imgWeibo = (ImageView) contentView.findViewById(R.id.img_weibo);
        imgWeichat.setOnClickListener(this);
        imgWeiFriend.setOnClickListener(this);
        imgQq.setOnClickListener(this);
        imgWeibo.setOnClickListener(this);
    }

    private void initData() {

    }

    public void showAtBottom(String title, String content, String imageUrl, String webUrl) {

        this.showAtLocation(((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0), Gravity.BOTTOM, 0, 0);
        this.title = title;
        String html = HtmlToStringUtils.delHTMLTag(content);
        this.content =  html.substring(0,html.length() > 30 ? 30 : html.length());
        this.imageUrl = imageUrl;
        this.webUrl = webUrl;
        Logger.i("share", "title = " + title + ",  content = " + this.content + "， image url = " + imageUrl + ", webUrl = " + webUrl);
    }

    @Deprecated
    public void setShareListener(ShareCallback listener) {
//        myShareUtils.setShareListener(listener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_weichat:
                WXEntryActivity.start(context, title, content, imageUrl, webUrl, false);
                dismiss();
                break;
            case R.id.img_wei_friend:
                WXEntryActivity.start(context, title, content, imageUrl, webUrl, true);
                dismiss();
                break;
            case R.id.img_qq:
//                myShareUtils.share(MyShareUtils.SHARE_QQ, title, content, imageUrl, webUrl);
                ShareActivityQQ.start(context, title, content, imageUrl, webUrl);
                dismiss();
                break;
            case R.id.img_weibo:
                WeiboResponseActivity.start(context, title, content, imageUrl, webUrl);
                dismiss();
                break;
        }
    }

    /**
     * call this in activity onActivityResult method
     */
    @Deprecated
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        myShareUtils.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * you must call this when activity onNewIntent
     *
     * @param intent
     * @param response
     */
    @Deprecated
    private void onNewIntent(Intent intent, IWeiboHandler.Response response) {
//        myShareUtils.onNewIntent(intent, response);
    }

    @Deprecated
    public void release() {
        context = null;
//        myShareUtils.release();
//        myShareUtils = null;
    }

    @Deprecated
    private void onResponse(BaseResponse baseResponse) {
        if (baseResponse != null) {
            Logger.i("share", "weibo share baseResponse.errCode = " + baseResponse.errCode);
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
    }
}
