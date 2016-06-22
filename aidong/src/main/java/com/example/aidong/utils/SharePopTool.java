package com.example.aidong.utils;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.aidong.R;
import com.leyuan.commonlibrary.util.ToastUtil;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

/**
 * Created by user on 2015/10/30.
 */
public class SharePopTool {
    private Context context;
    private PopupWindow mPopup;
    private View view;
    // 首先在您的Activity中添加如下成员变量
    private UMSocialService mController;
    private String wxappId = "wx365ab323b9269d30";
    private String wxappSecret = "4bdcdc546ee89c4301b52a97509b055a";
    private String url;
    private String imageUrl;
    private String content;
    private String title;
    private boolean isDetailInfo = false;

    public SharePopTool(Context context, View view, String url, UMSocialService mController, String imageUrl, String content, String title) {
        this.context = context;
        this.view = view;
        this.url = url;
        this.mController = mController;
        this.imageUrl = imageUrl;
        this.content = content;
        this.title = title;
        isDetailInfo = true;
    }

    public SharePopTool(Context context, View view, String url, UMSocialService mController) {
        this.context = context;
        this.view = view;
        this.url = url;
        this.mController = mController;
        isDetailInfo = false;
    }

    public void showChoseBox() {
        if (mPopup != null) {
            mPopup = null;
        }
        if (mPopup == null) {
            initWindow();
        }
        if (mPopup.isShowing()) {
            mPopup.dismiss();
        } else {
            mPopup.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        }
    }


    private void initWindow() {
        LayoutInflater li = (LayoutInflater) context
                .getSystemService("layout_inflater");
        View _view = li.inflate(R.layout.layout_sport_share_pop, null);
        TextView txt_share_pop_null = (TextView) _view.findViewById(R.id.txt_share_pop_null);
        LinearLayout layout_share_pop_weixin = (LinearLayout) _view.findViewById(R.id.layout_share_pop_weixin);
        LinearLayout layout_share_pop_weixin_rings = (LinearLayout) _view.findViewById(R.id.layout_share_pop_weixin_rings);
        LinearLayout layout_share_pop_xinlang = (LinearLayout) _view.findViewById(R.id.layout_share_pop_xinlang);
        TextView txt_share_pop_cancel = (TextView) _view.findViewById(R.id.txt_share_pop_cancel);

        LinearLayout layout = (LinearLayout) _view.findViewById(R.id.layout_share_pop);
        layout.getBackground().setAlpha(150);
        LinearLayout content = (LinearLayout) _view.findViewById(R.id.layout_share_content);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.popup_anim_in);
        content.startAnimation(animation);
        txt_share_pop_null.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                mPopup.dismiss();
            }
        });
        layout_share_pop_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weixin();
                Share(SHARE_MEDIA.WEIXIN);
                mPopup.dismiss();
            }
        });
        layout_share_pop_weixin_rings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weixinRings();
                Share(SHARE_MEDIA.WEIXIN_CIRCLE);
                mPopup.dismiss();
            }
        });
        layout_share_pop_xinlang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sinlang();
                Share(SHARE_MEDIA.SINA);
                mPopup.dismiss();
            }
        });
        txt_share_pop_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopup.dismiss();
            }
        });

        mPopup = new PopupWindow(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        mPopup.setFocusable(true);
        mPopup.setContentView(_view);
//        mPopup.setAnimationStyle(R.style.popup_style);
        mPopup.setBackgroundDrawable(new BitmapDrawable());
    }

    public void weixin() {
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(context, wxappId, wxappSecret);
        wxHandler.addToSocialSDK();

        //设置微信好友分享内容
        WeiXinShareContent weixinContent = new WeiXinShareContent();

        //点击跳转url
        weixinContent.setTargetUrl(url);
        if (isDetailInfo) {
            weixinContent.setShareMedia(new UMImage(context,
                    imageUrl));
            //设置分享文字
            weixinContent.setShareContent(content);
            //设置title
            weixinContent.setTitle(title);
        } else {
            weixinContent.setShareImage(new UMImage(context, R.drawable.ic_launcher));
            //设置分享文字
            weixinContent.setShareContent("爱动健身的分享");
            //设置title
            weixinContent.setTitle("爱动分享");
        }
        mController.setShareMedia(weixinContent);
    }

    public void weixinRings() {
//         添加微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(context, wxappId, wxappSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
        //设置微信朋友圈分享内容
        CircleShareContent circleMedia = new CircleShareContent();


        if (isDetailInfo) {
            circleMedia.setShareMedia(new UMImage(context,
                    imageUrl));
            circleMedia.setShareContent(content);
//      设置朋友圈title
            circleMedia.setTitle(title);

        } else {
            circleMedia.setShareContent("爱动健身的分享");
//      设置朋友圈title
            circleMedia.setTitle("爱动分享");
            circleMedia.setShareImage(new UMImage(context, R.drawable.ic_launcher));
        }


        circleMedia.setTargetUrl(url);
        mController.setShareMedia(circleMedia);
    }

    public void sinlang() {
        SinaShareContent sinaShareContent=new SinaShareContent();
        //点击跳转url
        sinaShareContent.setTargetUrl(url);
        if (isDetailInfo) {
            sinaShareContent.setShareMedia(new UMImage(context,
                    imageUrl));
            //设置分享文字
            sinaShareContent.setShareContent(content+url);
            //设置title
            sinaShareContent.setTitle(title);
        } else {
            sinaShareContent.setShareImage(new UMImage(context, R.drawable.ic_launcher));
            //设置分享文字
            sinaShareContent.setShareContent("爱动健身的分享");
            //设置title
            sinaShareContent.setTitle("爱动分享");
        }
        mController.setShareMedia(sinaShareContent);
    }

    public void Share(SHARE_MEDIA platform) {
        mController.postShare(context, platform,
                new SocializeListeners.SnsPostListener() {
                    @Override
                    public void onStart() {
                        ToastUtil.show( "开始分享",context);
                    }

                    @Override
                    public void onComplete(SHARE_MEDIA platform, int eCode,
                                           SocializeEntity entity) {
                        if (eCode == 200) {
                            ToastUtil.show( "分享成功",context);
                        } else {
                            ToastUtil.show( "分享失败",context);
                        }
                    }
                });

    }
}
