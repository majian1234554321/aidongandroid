package com.leyuan.aidong.ui.video.activity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.exoplayer.util.Util;
import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.CommonViewPagerAdapter;
import com.leyuan.aidong.config.ConstantUrl;
import com.leyuan.aidong.entity.video.VideoDetail;
import com.leyuan.aidong.module.share.SharePopupWindow;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.activity.account.LoginActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.VideoPresenterImpl;
import com.leyuan.aidong.ui.mvp.view.VideoDetailView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.FastBlur;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.ToastGlobal;
import com.leyuan.aidong.widget.media.TextViewPrintly;

import java.util.ArrayList;

public class VideoDetailActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener, VideoDetailView {

    private static final int BITMAP_BLUR_OK = 3;
    private ViewPager viewPager;
    private ImageView iv_back, iv_up_arrow, iv_share, iv_reply, iv_like, img_blur;
    private RelativeLayout layout_under, layout_page_tag;
    private TextView tv_reply_count, tv_like_count, txt_page_tag;

    private TextViewPrintly tv_course_desc, tv_course_name, tv_auth_and_time;
    private int series_id, phase, currentPostion;
    private ArrayList<View> mViews = new ArrayList<>();
    private ArrayList<Bitmap> mBitmaps = new ArrayList<>();
    public static Bitmap blurBitmaps[];

    private CommonViewPagerAdapter pagerAdapter;

    private int itemPrased;
    private boolean isJustInto = true;

    private int screen_width;
    private VideoPresenterImpl presenter;


    private ArrayList<VideoDetail> videos = new ArrayList<>();
    private SharePopupWindow sharePopupWindow;
    private String parseList;

    private int tag_width;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (isJustInto) {
                        fillingViewDataNoAnimation(msg.arg1);
                    } else {
                        fillingViewData(msg.arg1);
                    }
                    isJustInto = false;
                    break;
                case BITMAP_BLUR_OK:
                    img_blur.setImageBitmap(blurBitmaps[currentPostion]);
                    break;
            }
        }
    };


    public static void start(Context context, int series_id, int phase, int flag) {

        Intent intent = new Intent(context, VideoDetailActivity.class);
        if (flag > 0)
            intent.setFlags(flag);
        intent.putExtra("series_id", series_id);
        intent.putExtra("phase", phase);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screen_width = getResources().getDisplayMetrics().widthPixels;
        series_id = getIntent().getIntExtra("series_id", 0);
        phase = getIntent().getIntExtra("phase", -1);
        presenter = new VideoPresenterImpl(this);
        presenter.setVideoDetailView(this);
        setContentView(R.layout.activity_watch_office_detail);

        initView();
        initData();
        getDataFromInter();

        sharePopupWindow = new SharePopupWindow(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_up_arrow = (ImageView) findViewById(R.id.iv_up_arrow);
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

        layout_under.setOnTouchListener(new ViewGroup.OnTouchListener() {
            float downY;
            float downX;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downY = event.getY();
                        downX = event.getX();
                        Logger.i("video downY = " + downY);
                        break;
                    case MotionEvent.ACTION_UP:
                        float upY = event.getY();
                        float upX = event.getX();
                        Logger.i("video upY = " + upY);
                        if ((downY - upY) > 50 || (downY - upY > 20 && Math.abs(downX - upX) < 10)) {
                            //                            ToastTools.makeShortText("跳到更多界面");
                            int position = viewPager.getCurrentItem();
                            if (videos != null && videos.size() > 0) {
                                VideoDetail detail = videos.get(position);
                                VideoMoreActivity.newInstance(VideoDetailActivity.this, series_id, position, detail);
                            }

                        }
                        break;
                }
                return true;
            }
        });
        iv_back.setOnClickListener(this);
        iv_up_arrow.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        iv_reply.setOnClickListener(this);
        iv_like.setOnClickListener(this);
        tv_course_desc.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    public void getDataFromInter() {
        presenter.getVideoDetail(String.valueOf(series_id));
    }

    private void inValidViewData() {
        tv_course_name.setText("");
        tv_auth_and_time.setText("");
        tv_course_desc.setText("");
        layout_under.setVisibility(View.INVISIBLE);

    }

    private void fillingViewData(int index) {
        VideoDetail videoDetail = videos.get(index);
        layout_under.setVisibility(View.VISIBLE);
        iv_like.setImageResource(videoDetail.isParsed() ? R.drawable.details_like : R.drawable.video_details_praise_no);
        tv_reply_count.setText("" + videoDetail.getCommentsCount());
        tv_like_count.setText("" + videoDetail.getLikesCount());
        tv_course_name.printString("" + videoDetail.getVideoName());
        tv_auth_and_time.printString(videoDetail.getAuthor() + " • " + videoDetail.getDuring());
        tv_course_desc.printString(videoDetail.getIntroduce());
        txt_page_tag.setText(videoDetail.getPhase()+ "-" + videos.size());
    }

    private void fillingViewDataNoAnimation(int index) {
        VideoDetail videoDetail = videos.get(index);
        layout_under.setVisibility(View.VISIBLE);
        iv_like.setImageResource(videoDetail.isParsed() ? R.drawable.details_like : R.drawable.video_details_praise_no);
        tv_reply_count.setText("" + videoDetail.getCommentsCount());
        tv_like_count.setText("" + videoDetail.getLikesCount());
        tv_course_name.setText("" + videoDetail.getVideoName());
        tv_auth_and_time.printString(videoDetail.getAuthor() + " • " + videoDetail.getDuring());
        tv_course_desc.setText(videoDetail.getIntroduce());
//        txt_page_tag.setText((index + 1) + "-" + videos.size());
        txt_page_tag.setText(videoDetail.getPhase()+ "-" + videos.size());
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout_page_tag.getLayoutParams();
        params.leftMargin = (i * tag_width) + (int) (v * tag_width);
        layout_page_tag.setLayoutParams(params);
    }

    @Override
    public void onPageSelected(int i) {
        if (blurBitmaps != null && blurBitmaps.length > 0) {
            img_blur.setImageBitmap(blurBitmaps[i]);
        }
        inValidViewData();
        mHandler.removeMessages(0);
        mHandler.removeCallbacksAndMessages(null);
        Message message = Message.obtain();
        message.what = 0;
        message.arg1 = i;
        mHandler.sendMessageDelayed(message, 200);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_up_arrow:
                int position = viewPager.getCurrentItem();
                if (videos != null && videos.size() > 0) {
                    VideoDetail detail = videos.get(position);
                    VideoMoreActivity.newInstance(VideoDetailActivity.this,
                            series_id, position, detail);
                }
                break;
            case R.id.iv_share:
//                if (!App.mInstance.isLogin()) {
//                    startActivity(new Intent(this, LoginActivity.class));
//                } else

                if (videos != null && videos.size() > 0) {
                    VideoDetail videoDetail = videos.get(viewPager.getCurrentItem());
                    share(videoDetail);
                } else {
                    ToastGlobal.showShortConsecutive("无可分享视频");
                }
                break;
            case R.id.iv_reply:
                if (videos != null && videos.size() > 0) {
                    VideoDetail detail_commont = videos.get(viewPager.getCurrentItem());
                    VideoCommentActivity.newInstance(this, series_id, detail_commont.getPhase(),
                            detail_commont.getVideoName(), Constant.REQUEST_VIDEO_COMMENT);
                }

                //评论
                break;
            case R.id.iv_like:
                //赞
                if (videos.isEmpty())
                    return;
                if (!App.getInstance().isLogin()) {
                    startActivity(new Intent(this, LoginActivity.class));
                } else {
                    int currentItem = viewPager.getCurrentItem();
                    Logger.i("video", "currentItem = " + currentItem);
                    VideoDetail videoDetail = videos.get(currentItem);

                    if (videoDetail.isParsed()) {
                        presenter.deleteLikeVideo(String.valueOf(series_id), String.valueOf(videoDetail.getPhase()), currentItem);
                    } else {
                        presenter.likeVideo(String.valueOf(series_id), String.valueOf(videoDetail.getPhase()), currentItem);
                    }

                }

                break;
            default:
                break;

        }
    }

    private void parseVideo(int phase, int currentItem) {
        presenter.likeVideo(String.valueOf(series_id), String.valueOf(phase), currentItem);
    }

    private void share(final VideoDetail video) {
        final String url = ConstantUrl.URL_SHARE_VIDEO + video.getvId();

        sharePopupWindow.showAtBottom(video.getVideoName() + Constant.I_DONG_FITNESS, video.getIntroduce(), video.getCover(), url);
    }

    @Override
    public void onGetVideoDetailList(ArrayList<VideoDetail> vs) {

        if (vs == null || vs.isEmpty()) return;
        videos = vs;
        for (int i = 0; i < videos.size(); i++) {
            if (videos.get(i).getPhase() == phase) {
                currentPostion = i;
                break;
            }
        }

        initViewPager();
        initBottomTag();

        if (currentPostion == 0) {
            fillingViewDataNoAnimation(0);
            isJustInto = false;
        }
        viewPager.setCurrentItem(currentPostion, false);

    }

    private void initBottomTag() {
        tag_width = screen_width / videos.size();
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout_page_tag.getLayoutParams();
        params.width = tag_width;
        layout_page_tag.setLayoutParams(params);
    }

    private void initViewPager() {
        parseList = App.getInstance().getParseString();
        blurBitmaps = new Bitmap[videos.size()];
        for (int i = 0; i < videos.size(); i++) {
            final VideoDetail videoDetail = videos.get(i);
            if (parseList != null && parseList.contains(videoDetail.getvId() + "")) {
                videoDetail.setIsParsed(true);
            }

            View view = View.inflate(VideoDetailActivity.this, R.layout.item_video_detail_viewpager, null);
            final ImageView iv_cover = (ImageView) view.findViewById(R.id.iv_cover);
            ImageView iv_start = (ImageView) view.findViewById(R.id.iv_start);
            final int finalI = i;
            Glide.with(this).load(videoDetail.getCover()).asBitmap()
                    .into(new SimpleTarget<Bitmap>(750, 750) {
                        @Override
                        public void onResourceReady(final Bitmap loadedImage, GlideAnimation<? super Bitmap> glideAnimation) {
                            if (loadedImage != null) {
                                iv_cover.setImageBitmap(loadedImage);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (blurBitmaps != null)
                                            blurBitmaps[finalI] = FastBlur.doBlur(Bitmap.createScaledBitmap(loadedImage, 250,
                                                    250, false), 60, true);
                                        if (finalI == currentPostion) {
                                            mHandler.sendEmptyMessage(BITMAP_BLUR_OK);
                                        }
                                    }
                                }).start();

                            }
                        }
                    });

            iv_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(VideoDetailActivity.this, PlayerActivity.class)
                            .setData(Uri.parse(videoDetail.getVideo()))
                            // .setNurtureList(Uri.parse("http://pili-live-hls.ps.qiniucdn.com/NIU7PS/57b12c4b75b6253fb20003e4.m3u8"))
                            .putExtra(PlayerActivity.CONTENT_TYPE_EXTRA, Util.TYPE_HLS)
                            // .putExtra(PlayerActivity.CONTENT_TYPE_EXTRA, Util.TYPE_OTHER)
                            //.putExtra(PlayerActivity.CONTENT_ID_EXTRA, "")
                             //.putExtra(PlayerActivity.CONTENT_ID_EXTRA, "")
                            .putExtra(PlayerActivity.VIDEO_ID, String.valueOf(videoDetail.getvId()));

                    Logger.i("playerActivity ", " from videoId =  " + videoDetail.getContentId());
                    startActivity(intent);
                }
            });
            mViews.add(view);
        }

        pagerAdapter = new CommonViewPagerAdapter(mViews);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(this);
    }


    @Override
    public void onLikesResult(boolean success, int currentItem) {
        if (success) {
            VideoDetail videoDetail = videos.get(currentItem);
            videoDetail.setLikesCount(videoDetail.getLikesCount() + 1);
            videoDetail.setIsParsed(true);
            tv_like_count.setText("" + videoDetail.getLikesCount());
            iv_like.setImageResource(R.drawable.details_like);
            App.getInstance().addParseId(videoDetail.getvId() + "");
        }
    }

    @Override
    public void onDeleteLikesResult(boolean success, int currentItem) {
        if (success) {
            VideoDetail videoDetail = videos.get(currentItem);
            videoDetail.setLikesCount(videoDetail.getLikesCount() - 1);
            videoDetail.setIsParsed(false);
            tv_like_count.setText("" + videoDetail.getLikesCount());
            iv_like.setImageResource(R.drawable.video_details_praise_no);

            App.getInstance().deleteParseId(videoDetail.getvId() + "");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.REQUEST_VIDEO_COMMENT:
                    int publishCommentNumber = data.getIntExtra(Constant.PUBLISH_COMMENT_NUMBER, 0);

                    Logger.i(" publishCommentNumber onActivityResult  " + publishCommentNumber);
                    VideoDetail detail_current = videos.get(viewPager.getCurrentItem());
                    try {
                        int oldCommentNum = Integer.parseInt(detail_current.getCommentsCount());
                        detail_current.setCommentsCount(String.valueOf(oldCommentNum + publishCommentNumber));
                        tv_reply_count.setText(detail_current.getCommentsCount());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }


                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        if (blurBitmaps != null) {
            for (int i = 0; i < blurBitmaps.length; i++) {
                if (blurBitmaps[i] != null) {
                    blurBitmaps[i].recycle();
                }
            }
            blurBitmaps = null;
        }
    }

}
