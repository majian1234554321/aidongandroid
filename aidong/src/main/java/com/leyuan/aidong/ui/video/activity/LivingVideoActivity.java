package com.leyuan.aidong.ui.video.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aidong.R;
import com.leyuan.aidong.config.ConstantUrl;
import com.leyuan.aidong.entity.video.LiveVideoInfo;
import com.leyuan.aidong.module.share.SharePopupWindow;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.LivePresenterImpl;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.LiveDateFilterUtil;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.widget.media.IjkVideoView;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;


public class LivingVideoActivity extends BaseActivity implements View.OnClickListener {

    private IjkVideoView mVideoView;
    private String mVideoPath;
    private LiveVideoInfo mLiveVideoInfo;
    private TextView txt_author;
    private ImageView img_close, img_share, img_special, img_deep_into, img_celebrity;
    LinearLayout layout_live_end, layout_refresh;
    private boolean mBackPressed;
    private SharePopupWindow sharePopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVideoPath = getIntent().getStringExtra(Constant.VIDEO_PATH);
        mLiveVideoInfo = (LiveVideoInfo) getIntent().getSerializableExtra(Constant.LIVE_INFO);

        setContentView(R.layout.living_video_activity);
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        initView();
        initData();
        addLivingMsg();
    }

    private void addLivingMsg() {
        if (mLiveVideoInfo != null) {
            new LivePresenterImpl(this, null).livePlayStatistics(String.valueOf(mLiveVideoInfo.getLiveId()));
        }
    }

    private void initView() {
        mVideoView = (IjkVideoView) findViewById(R.id.video_view);
        txt_author = (TextView) findViewById(R.id.txt_author);
        img_close = (ImageView) findViewById(R.id.img_close);
        img_share = (ImageView) findViewById(R.id.img_share);
        layout_live_end = (LinearLayout) findViewById(R.id.layout_live_end);
        layout_refresh = (LinearLayout) findViewById(R.id.layout_refresh);
        img_special = (ImageView) findViewById(R.id.img_special);
        img_deep_into = (ImageView) findViewById(R.id.img_deep_into);
        img_celebrity = (ImageView) findViewById(R.id.img_celebrity);
    }

    private void initData() {

        if (mLiveVideoInfo != null)
            mVideoView.setVideoPath(mLiveVideoInfo.getLivePath());
        //                mVideoView.setVideoPath("rtmp://pili-live-rtmp.aidong.me/aidong-tv/test");
        mVideoView.start();
        mVideoView.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer mp) {
                layout_live_end.setVisibility(View.VISIBLE);
            }
        });
        mVideoView.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(IMediaPlayer mp, int what, int extra) {
                Logger.i("liveVideo", "------------Error: what = " + what + ",  extra  = " + extra);
                //                Toast.makeText(LivingVideoActivity.this, "获取直播失败，请检查网络或重试", Toast.LENGTH_SHORT).show();
                //                finish();
                if (mLiveVideoInfo != null) {
                    if (LiveDateFilterUtil.compareTime(mLiveVideoInfo.getLiveEndTime()) <= 10) {
                        layout_live_end.setVisibility(View.VISIBLE);
                        layout_refresh.setVisibility(View.GONE);
                        txt_author.setVisibility(View.GONE);
                    } else {
                        //刷新
                        mVideoView.release(false);
                        layout_live_end.setVisibility(View.GONE);
                        layout_refresh.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(LivingVideoActivity.this, "获取直播失败，请检查网络或重试", Toast.LENGTH_SHORT).show();
                    finish();
                }


                return true;
            }
        });

        if (mLiveVideoInfo != null)
            txt_author.setText(mLiveVideoInfo.getLiveAuthor() + "");

        img_close.setOnClickListener(this);
        img_share.setOnClickListener(this);
        img_special.setOnClickListener(this);
        img_deep_into.setOnClickListener(this);
        img_celebrity.setOnClickListener(this);
        layout_refresh.setOnClickListener(this);


    }

    @Override
    public void onBackPressed() {
        mBackPressed = true;
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //        if (mBackPressed || !mVideoView.isBackgroundPlayEnabled()) {
        //            mVideoView.stopPlayback();
        //            mVideoView.release(true);
        //            mVideoView.stopBackgroundPlay();
        //        } else {
        //            mVideoView.enterBackground();
        //        }
        //        IjkMediaPlayer.native_profileEnd();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.i("onDestroy onDestroy ", " time --------- " + System.currentTimeMillis());

        new Thread(new Runnable() {
            @Override
            public void run() {
                mVideoView.stopPlayback();
                Logger.i("onDestroy stopPlayback ", " time --------- " + System.currentTimeMillis());
                mVideoView.release(true);
                Logger.i("onDestroy release ", " time --------- " + System.currentTimeMillis());
                mVideoView.stopBackgroundPlay();
                Logger.i("onDestroy stopBackgroundPlay ", " time --------- " + System.currentTimeMillis());
                IjkMediaPlayer.native_profileEnd();
                Logger.i("onDestroy native_profileEnd ", " time --------- " + System.currentTimeMillis());
            }
        }).start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.img_share:
                if (mLiveVideoInfo != null) {
                    String url = ConstantUrl.LIVE_SHARE + mLiveVideoInfo.getLiveId();
                    if (sharePopupWindow == null) {
                        sharePopupWindow = new SharePopupWindow(this);
                    }
                    sharePopupWindow.showAtBottom(mLiveVideoInfo.getLiveName() + Constant.I_DONG_FITNESS, mLiveVideoInfo.getLiveContent()
                            , mLiveVideoInfo.getLiveCover(), url);
                }
                break;
            case R.id.img_special:
                Intent intent = new Intent(this, WatchOfficeActivity.class);
                intent.putExtra(Constant.VIDEO_TYPE, 0);
                startActivity(intent);
                finish();
                break;
            case R.id.img_deep_into:
                Intent intentDeep = new Intent(this, WatchOfficeActivity.class);
                intentDeep.putExtra(Constant.VIDEO_TYPE, 1);
                startActivity(intentDeep);
                finish();
                break;
            case R.id.img_celebrity:
                Intent intentCele = new Intent(this, WatchOfficeActivity.class);
                intentCele.putExtra(Constant.VIDEO_TYPE, 2);
                startActivity(intentCele);
                finish();
                break;
            case R.id.layout_refresh:

                mVideoView.resume();
                mVideoView.start();
                layout_refresh.setVisibility(View.GONE);
                break;
        }
    }

}
