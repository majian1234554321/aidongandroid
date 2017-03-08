package com.leyuan.aidong.ui.video.activity;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.video.WatchOfficeRelateCourseAdapter;
import com.leyuan.aidong.adapter.video.WatchOfficeRelateGoodAdapter;
import com.leyuan.aidong.adapter.video.WatchOfficeRelateVideoAdapter;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.entity.video.VideoDetail;
import com.leyuan.aidong.entity.video.VideoRelationResult;
import com.leyuan.aidong.entity.video.WatchOfficeCourseBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.VideoPresenterImpl;
import com.leyuan.aidong.ui.mvp.view.VideoRelationView;
import com.leyuan.aidong.widget.MyListView;
import com.leyuan.aidong.widget.SmartScrollView;

import java.util.List;

/**
 * 视界专题详情界面展开
 * Created by auth song on 2016/7/25
 */
public class VideoMoreActivity extends BaseActivity implements WatchOfficeRelateGoodAdapter.OnGoodsItemClickListener, WatchOfficeRelateVideoAdapter.OnVideoItemCLickListener, VideoRelationView {

    private WatchOfficeRelateVideoAdapter videoAdapter;
    private WatchOfficeRelateCourseAdapter courseAdapter;
    private WatchOfficeRelateGoodAdapter goodAdapter;

    private String videoName;
    private String videoId;
    private String phase;
    private int position;

    private String series_id;
    private int page = 1;

    private VideoPresenterImpl presenter;

    /**
     * 提供给其他界面调用传入所需的参数
     *
     * @param context  来源界面
     * @param videoId  系列编号
     * @param phase    期数
     * @param position
     */
    public static void newInstance(Activity context, String videoName, String videoId, String phase, int position) {
        Intent intent = new Intent(context, VideoMoreActivity.class);
        intent.putExtra("videoName", videoName);
        intent.putExtra("videoId", videoId);
        intent.putExtra("phase", phase);
        intent.putExtra("position", position);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.slide_in_bottom, 0);
    }

    public static void newInstance(Activity context, String videoName, String series_id, int position) {
        Intent intent = new Intent(context, VideoMoreActivity.class);
        intent.putExtra("videoName", videoName);
        intent.putExtra("series_id", series_id);
        intent.putExtra("position", position);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.slide_in_bottom, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_more);
        Intent intent = getIntent();
        if (intent != null) {
            videoName = intent.getStringExtra("videoName");
            series_id = intent.getStringExtra("series_id");
            phase = intent.getStringExtra("phase");
            position = intent.getIntExtra("position", 0);

        }

        presenter = new VideoPresenterImpl(this);
        presenter.setVideoRelationView(this);
        initView();
        getData();
    }

    private void initView() {
//        ImageView img_bg = (ImageView) findViewById(R.id.img_bg);

        if (VideoDetailActivity.blurBitmaps != null && VideoDetailActivity.blurBitmaps[position] != null) {
            findViewById(R.id.rootView).setBackground(new BitmapDrawable(VideoDetailActivity.blurBitmaps[position]));
        }

        TextView tvVideoName = (TextView) findViewById(R.id.tv_course_name);
        tvVideoName.setText(videoName);

        SmartScrollView scrollview = (SmartScrollView) findViewById(R.id.scrollview);
        scrollview.setScrollTopListener(new SmartScrollView.OnScrollToTopListener() {
            @Override
            public void onScrollTop() {
                finish();
                overridePendingTransition(0, R.anim.slide_out_from_top);
            }
        });

        ImageView ivBack = (ImageView) findViewById(R.id.iv_down_arrow);
        ivBack.setOnClickListener(backListener);
        RecyclerView videoRecyclerView = (RecyclerView) findViewById(R.id.rv_relate_relate_video);
        videoRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        videoAdapter = new WatchOfficeRelateVideoAdapter(this, this);
        videoRecyclerView.setAdapter(videoAdapter);
        MyListView courseListView = (MyListView) findViewById(R.id.lv_relate_course);
        courseAdapter = new WatchOfficeRelateCourseAdapter(this);
        courseListView.setAdapter(courseAdapter);
        RecyclerView goodRecyclerView = (RecyclerView) findViewById(R.id.rv_relate_good);
        goodRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        goodAdapter = new WatchOfficeRelateGoodAdapter(this, this);
        goodRecyclerView.setAdapter(goodAdapter);
    }

    private void getData() {
        presenter.getVideoRelation(series_id, String.valueOf(page));
//        presenter.getVideoRelation("5", String.valueOf(page));
    }


    private View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
            overridePendingTransition(0, R.anim.slide_out_from_top);
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0, R.anim.slide_out_from_top);

    }

    @Override
    public void onGoodsClick(GoodsBean bean) {
    }

    @Override
    public void onVideoClick(VideoDetail bean) {
        Intent intent = new Intent(this, VideoDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("id", bean.getvId());
        intent.putExtra("phase", bean.getPhase() - 1);
        startActivity(intent);
        overridePendingTransition(0, R.anim.slide_out_from_top);
    }

    @Override
    public void onGetRelation(VideoRelationResult.VideoRelation relateBean) {
        if (relateBean == null)
            return;
        List<VideoDetail> videoList = relateBean.getVideo();
        List<WatchOfficeCourseBean> courseList = relateBean.getCourse();
        List<GoodsBean> goodList = relateBean.getGood();
        if (videoList != null && !videoList.isEmpty()) {
//            videoList.addAll(videoList);
//            videoList.addAll(videoList);
            videoAdapter.setData(videoList);
        } else {
            findViewById(R.id.tv_relate_video).setVisibility(View.GONE);
            findViewById(R.id.rv_relate_relate_video).setVisibility(View.GONE);
        }

        if (courseList != null && !courseList.isEmpty()) {
            courseAdapter.addList(courseList);
        } else {
            findViewById(R.id.tv_relate_course).setVisibility(View.GONE);
        }

        if (goodList != null && !goodList.isEmpty()) {
//            goodList.addAll(goodList);
//            goodList.addAll(goodList);
            goodAdapter.setData(goodList);
        } else {
            findViewById(R.id.tv_relate_good).setVisibility(View.GONE);
            findViewById(R.id.rv_relate_good).setVisibility(View.GONE);
        }

    }
}
