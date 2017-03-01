package com.leyuan.aidong.module.share;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.leyuan.aidong.R;


/**
 * Created by huyouni on 2016/12/7.
 */

public class SharePopupWindow extends PopupWindow implements View.OnClickListener {

    private Activity context;
    private ImageView imgWeichat;
    private ImageView imgWeiFriend;
    private ImageView imgQq;
    private ImageView imgWeibo;
    private MyShareUtils myShareUtils;
    private String title;
    private String content;
    private String imageUrl;
    private String webUrl;

    public SharePopupWindow(Activity context) {
        super(context);
        this.context = context;
        myShareUtils = new MyShareUtils(context);
        myShareUtils.onNewIntent(context.getIntent());
        initView();
        initData();
    }

    private void initView() {
        View contentView = View.inflate(context, R.layout.popup_window_share, null);
        this.setContentView(contentView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
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
        this.showAtLocation(((ViewGroup)context.findViewById(android.R.id.content)).getChildAt(0), Gravity.BOTTOM, 0, 0);
        this.title =title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.webUrl = webUrl;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_weichat:
                 myShareUtils.share(MyShareUtils.SHARE_WEIXIN_CHAT,title,content,imageUrl,webUrl);
                break;
            case R.id.img_wei_friend:
                myShareUtils.share(MyShareUtils.SHARE_WEIXIN_FRIENDS,title,content,imageUrl,webUrl);
                break;
            case R.id.img_qq:
                myShareUtils.share(MyShareUtils.SHARE_QQ,title,content,imageUrl,webUrl);
                break;
            case R.id.img_weibo:
                myShareUtils.share(MyShareUtils.SHARE_WEIBO,title,content,imageUrl,webUrl);
                break;
        }
    }

    /**
     *call this in activity onActivityResult method
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        myShareUtils.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * you must call this when activity onNewIntent
     * @param intent
     */
    public  void onNewIntent(Intent intent){
        myShareUtils.onNewIntent(intent);
    }

    public void release(){
        context = null;
        myShareUtils.release();
        myShareUtils = null;
    }
}
