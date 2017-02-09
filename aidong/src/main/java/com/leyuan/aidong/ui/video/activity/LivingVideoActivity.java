package com.leyuan.aidong.ui.video.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.video.LiveVideoInfo;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.utils.LiveDateFilterUtil;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.common.Common;
import com.leyuan.aidong.utils.common.Urls;
import com.leyuan.aidong.widget.media.IjkVideoView;
import com.lidroid.xutils.http.RequestParams;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVideoPath = getIntent().getStringExtra(Common.VIDEO_PATH);
        mLiveVideoInfo = (LiveVideoInfo) getIntent().getSerializableExtra(Common.LIVE_INFO);

        setContentView(R.layout.living_video_activity);
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        initView();
        initData();
        addLivingMsg();
    }

    private void addLivingMsg() {
        RequestParams params = new RequestParams();
        if (mLiveVideoInfo != null)
            params.addBodyParameter("liveId", String.valueOf(mLiveVideoInfo.getLiveId()));
//        MyHttpUtils http = new MyHttpUtils();
//        http.send(HttpRequest.HttpMethod.POST, Urls.BASE_URL_TEXT + "/addLivingMsg.action", params, null);
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
        //                mVideoView.setVideoPath("rtmp://pili-live-rtmp.aidong.me/aidong-tv/guanfang_jhq");
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
        mVideoView.stopPlayback();
        mVideoView.release(true);
        mVideoView.stopBackgroundPlay();
        IjkMediaPlayer.native_profileEnd();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.img_share:
                if (mLiveVideoInfo != null) {
                    String url = Urls.LIVE_SHARE + mLiveVideoInfo.getLiveId();
//                    SharePopToolVideo sharePopTool = new SharePopToolVideo(LivingVideoActivity.this, ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0),
//                            url, mController, mLiveVideoInfo.getLiveCover(), mLiveVideoInfo.getLiveContent(), mLiveVideoInfo.getLiveName());
//                    sharePopTool.showChoseBox();
                }
                break;
            case R.id.img_special:
                Intent intent = new Intent(this, WatchOfficeActivity.class);
                intent.putExtra(Common.VIDEO_TYPE, 0);
                startActivity(intent);
                finish();
                break;
            case R.id.img_deep_into:
                Intent intentDeep = new Intent(this, WatchOfficeActivity.class);
                intentDeep.putExtra(Common.VIDEO_TYPE, 1);
                startActivity(intentDeep);
                finish();
                break;
            case R.id.img_celebrity:
                Intent intentCele = new Intent(this, WatchOfficeActivity.class);
                intentCele.putExtra(Common.VIDEO_TYPE, 2);
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
