package com.leyuan.aidong.ui.video.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.leyuan.aidong.R;
import com.leyuan.aidong.config.ConstantUrl;
import com.leyuan.aidong.entity.video.LiveVideoInfo;
import com.leyuan.aidong.module.share.SharePopupWindow;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.activity.account.LoginActivity;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.FastBlur;
import com.leyuan.aidong.utils.LiveDateFilterUtil;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.widget.media.TextViewPrintly;


public class LiveDetailActivity extends BaseActivity implements View.OnClickListener {


    private static final int LIVE_ENDED = 0;
    private static final int LIVE_BEGIN = 1;

    private LiveState liveState = LiveState.NO_BEGIN;
    private ImageView iv_back, iv_share, iv_reply, iv_like, img_blur, img_bg, img_live_begin_or_end;
    private RelativeLayout layout_under, layout_page_tag;
    private TextView tv_reply_count, tv_like_count, txt_page_tag;

    private TextViewPrintly tv_course_desc, tv_course_name, tv_auth_and_time;
    public static Bitmap blurBitmaps;
    private boolean isPrased;
    private String idongId;
    private LiveVideoInfo info;

    private SharePopupWindow sharePopupWindow;


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == LIVE_ENDED) {
                liveState = LiveState.ENDED;
                img_live_begin_or_end.setImageResource(R.drawable.live_end);
            } else if (msg.what == LIVE_BEGIN) {
                liveState = LiveState.BEGINED;
                img_live_begin_or_end.setImageResource(R.drawable.live_detail_living);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        info = (LiveVideoInfo) getIntent().getSerializableExtra(Constant.LIVE_INFO);
        setContentView(R.layout.activity_live_detail);
        sharePopupWindow = new SharePopupWindow(this);

        initView();
        initData();
        getDataFromInter();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        img_bg = (ImageView) findViewById(R.id.img_bg);
        img_live_begin_or_end = (ImageView) findViewById(R.id.img_live_begin_or_end);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        iv_reply = (ImageView) findViewById(R.id.iv_reply);
        iv_like = (ImageView) findViewById(R.id.iv_like);
        img_blur = (ImageView) findViewById(R.id.img_blur);

        layout_under = (RelativeLayout) findViewById(R.id.layout_under);
        layout_page_tag = (RelativeLayout) findViewById(R.id.layout_page_tag);

        tv_course_name = (TextViewPrintly) findViewById(R.id.tv_course_name);
        tv_auth_and_time = (TextViewPrintly) findViewById(R.id.tv_auth_and_time);
        tv_course_desc = (TextViewPrintly) findViewById(R.id.tv_course_desc);
        tv_reply_count = (TextView) findViewById(R.id.tv_reply_count);
        tv_like_count = (TextView) findViewById(R.id.tv_like_count);
        txt_page_tag = (TextView) findViewById(R.id.txt_page_tag);
    }

    private void initData() {

        iv_back.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        iv_reply.setOnClickListener(this);
        iv_like.setOnClickListener(this);
        img_live_begin_or_end.setOnClickListener(this);
    }

    public void getDataFromInter() {
        if (info == null) return;
        Glide.with(this).load(info.getLiveCover()).asBitmap()
                .into(new SimpleTarget<Bitmap>(750, 750) {
                    @Override
                    public void onResourceReady(Bitmap loadedImage, GlideAnimation<? super Bitmap> glideAnimation) {
                        if (loadedImage != null) {
                            img_bg.setImageBitmap(loadedImage);
                            blurBitmaps = FastBlur.doBlur(Bitmap.createScaledBitmap(loadedImage,
                                    250, 250, false), 60, true);
                            img_blur.setImageBitmap(blurBitmaps);

                        }
                    }
                });

        tv_course_name.setText("" + info.getLiveName());
//        tv_auth_and_time.setText(info.getLiveBeginTime() + " / " + info.getLiveAuthor());
        tv_auth_and_time.setText( info.getLiveAuthor() +" • " + info.getLiveBeginTime());
        tv_course_desc.setText("" + info.getLiveContent());
        tv_reply_count.setText("" + info.getCommentsCou());
        tv_like_count.setText("" + info.getPraiseCou());

        long startTime = LiveDateFilterUtil.compareLongTime(info.getLiveBeginTime());
        long endTime = LiveDateFilterUtil.compareLongTime(info.getLiveEndTime());
        Logger.i("time", "startTime = " + startTime + ", endTime = " + endTime);

        if (startTime > 0) {
            img_live_begin_or_end.setImageResource(R.drawable.live_not_start);
            liveState = LiveState.NO_BEGIN;
        } else if (endTime > 0) {
            img_live_begin_or_end.setImageResource(R.drawable.live_detail_living);
            liveState = LiveState.BEGINED;
        } else {
            img_live_begin_or_end.setImageResource(R.drawable.live_end);
            liveState = LiveState.ENDED;
        }


        if (startTime > 0) {
            mHandler.sendEmptyMessageDelayed(LIVE_BEGIN, startTime);
        } else if (endTime > 0) {
            mHandler.sendEmptyMessageDelayed(LIVE_ENDED, endTime);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (blurBitmaps != null) {
            blurBitmaps.recycle();
            blurBitmaps = null;
        }
        mHandler.removeCallbacksAndMessages(null);
        sharePopupWindow.release();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:
                //分享
                if (info != null) {
                    String url = ConstantUrl.LIVE_SHARE + info.getLiveId();
                    sharePopupWindow.showAtBottom(info.getLiveName() + Constant.I_DONG_FITNESS, info.getLiveContent(), info.getLiveCover(), url);
                }

                break;
            case R.id.iv_reply:

                Intent intent = new Intent(this, LiveCommentActivity.class);
                intent.putExtra(Constant.VIDEO_ID, info.getLiveId());
                intent.putExtra(Constant.VIDEO_NAME, info.getLiveName());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_bottom, 0);

                //评论
                break;
            case R.id.iv_like:
                //赞
                if (TextUtils.isEmpty(idongId)) {
                    startActivity(new Intent(this, LoginActivity.class));
                } else if (isPrased) {
                    //                    ToastTools.show("已点赞", this);
                    //                    iv_like.setImageResource(R.drawable.video_details_praise_no);
                    //                    isPrased = false;
                    Toast.makeText(this, "已点赞", Toast.LENGTH_SHORT).show();
                } else {
                    parseLive();
                    iv_like.setImageResource(R.drawable.details_like);
                    tv_like_count.setText("" + (info.getPraiseCou() + 1));
                    isPrased = true;
                }

                break;
            case R.id.img_live_begin_or_end:
                if (liveState == LiveState.BEGINED) {
                    Intent intent_live = new Intent(this, LivingVideoActivity.class);
                    if (info != null)
                        intent_live.putExtra(Constant.LIVE_INFO, info);
                    startActivity(intent_live);
                }
                break;
            default:
                break;

        }
    }

    private void parseLive() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        sharePopupWindow.onActivityResult(requestCode, resultCode, data);
    }

    enum LiveState {
        NO_BEGIN, BEGINED, ENDED
    }

}
