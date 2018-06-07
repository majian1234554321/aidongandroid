package com.example.aidong.ui.video.activity;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .adapter.video.WatchOfficeRelateCoursesAdapter;
import com.example.aidong .adapter.video.WatchOfficeRelateGoodAdapter;
import com.example.aidong .adapter.video.WatchOfficeRelateVideoAdapter;
import com.example.aidong .entity.GoodsBean;
import com.example.aidong .entity.video.VideoDetail;
import com.example.aidong .entity.video.VideoRelationResult;
import com.example.aidong .entity.video.WatchOfficeCourseBean;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .ui.home.activity.CourseListActivityNew;
import com.example.aidong .ui.home.activity.GoodsDetailActivity;
import com.example.aidong .ui.mvp.presenter.impl.VideoPresenterImpl;
import com.example.aidong .ui.mvp.view.VideoRelationView;
import com.example.aidong .utils.Logger;
import com.example.aidong .widget.SmartScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * 视界专题详情界面展开
 * Created by auth song on 2016/7/25
 */
public class VideoMoreActivity extends BaseActivity implements WatchOfficeRelateGoodAdapter.OnGoodsItemClickListener, WatchOfficeRelateVideoAdapter.OnVideoItemCLickListener, VideoRelationView, WatchOfficeRelateCoursesAdapter.OnRelateCourseClickListener {

    private WatchOfficeRelateVideoAdapter videoAdapter;
    private WatchOfficeRelateCoursesAdapter courseAdapter;
    private WatchOfficeRelateGoodAdapter goodAdapter;

    private String videoName;
    private int videoId;
    private int position;
    private ArrayList<VideoDetail> videos = new ArrayList<>();

    private int series_id;
    private int page = 1;

    private VideoPresenterImpl presenter;
    private View.OnTouchListener downTouchListener = new ViewGroup.OnTouchListener() {
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
                    if (downY > 0 && ((upY - downY) > 50 || (upY - downY > 20 && Math.abs(downX - upX) < 10))) {
                        finish();
                        overridePendingTransition(0, R.anim.slide_out_from_top);
                    } else {
                        return false;
                    }
                    break;
            }
            return true;
        }
    };

    public static void newInstance(Activity context, String videoName, int series_id, int position, ArrayList<VideoDetail> videos) {
        Intent intent = new Intent(context, VideoMoreActivity.class);
        intent.putExtra("videoName", videoName);
        intent.putExtra("series_id", series_id);
        intent.putExtra("position", position);
        intent.putParcelableArrayListExtra("videos", videos);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.slide_in_bottom, 0);
    }

    public static void newInstance(Activity context, int series_id, int position, VideoDetail video) {
        Intent intent = new Intent(context, VideoMoreActivity.class);
        intent.putExtra("videoName", video.getVideoName());
        intent.putExtra("series_id", series_id);
        intent.putExtra("position", position);
        intent.putExtra("video", video);
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
            series_id = intent.getIntExtra("series_id", 0);
            position = intent.getIntExtra("position", 0);
            VideoDetail video = intent.getParcelableExtra("video");
            videoId = video.getvId();
//            videos = intent.getParcelableArrayListExtra("videos");
//            if (videos != null && videos.size() > position) {
//                videos.remove(position);
//            }
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

        RecyclerView courseListView = (RecyclerView) findViewById(R.id.lv_relate_course);
        courseListView.setLayoutManager(new GridLayoutManager(this, 2));
        courseAdapter = new WatchOfficeRelateCoursesAdapter(this, this);
        courseListView.setAdapter(courseAdapter);

        RecyclerView goodRecyclerView = (RecyclerView) findViewById(R.id.rv_relate_good);
        goodRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        goodAdapter = new WatchOfficeRelateGoodAdapter(this, this);
        goodRecyclerView.setAdapter(goodAdapter);

//        scrollview.setOnTouchListener(downTouchListener);
//        videoRecyclerView.setOnTouchListener(downTouchListener);
//        courseListView.setOnTouchListener(downTouchListener);
//        goodRecyclerView.setOnTouchListener(downTouchListener);
    }

    private void getData() {
        presenter.getVideoRelation(String.valueOf(series_id), String.valueOf(page));
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
        GoodsDetailActivity.start(this, bean.getId(), bean.getType());
    }

    @Override
    public void onCourseClick(WatchOfficeCourseBean bean) {
        CourseListActivityNew.start(this, bean.getDictName());
    }

    @Override
    public void onVideoClick(VideoDetail bean) {

        VideoDetailActivity.start(this, series_id, bean.getPhase(), Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        Intent intent = new Intent(this, VideoDetailActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra("id", series_id);
//        intent.putExtra("phase", bean.getPhase());
//        startActivity(intent);
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
            for (VideoDetail videoDetail : videoList) {
                if (videoDetail.getvId() != videoId) {
                    videos.add(videoDetail);
                }
            }
            if (videos.isEmpty()) {
                findViewById(R.id.layout_relate_video).setVisibility(View.GONE);
            } else {
                videoAdapter.setData(videos);
            }
        } else {
//            findViewById(R.id.tv_relate_video).setVisibility(View.GONE);
//            findViewById(R.id.rv_relate_relate_video).setVisibility(View.GONE);

            findViewById(R.id.layout_relate_video).setVisibility(View.GONE);
        }

//        if (videos != null && !videos.isEmpty()) {
//            videoAdapter.setData(videos);
//        } else {
//            findViewById(R.id.tv_relate_video).setVisibility(View.GONE);
//            findViewById(R.id.rv_relate_relate_video).setVisibility(View.GONE);
//        }

        if (courseList != null && !courseList.isEmpty()) {
            courseAdapter.addList(courseList);
        } else {
//            findViewById(R.id.tv_relate_course).setVisibility(View.GONE);

            findViewById(R.id.layout_relate_course).setVisibility(View.GONE);
        }

        if (goodList != null && !goodList.isEmpty()) {
            goodAdapter.setData(goodList);
        } else {
//            findViewById(R.id.tv_relate_good).setVisibility(View.GONE);
//            findViewById(R.id.rv_relate_good).setVisibility(View.GONE);

            findViewById(R.id.layout_relate_goods).setVisibility(View.GONE);
        }
    }

}
