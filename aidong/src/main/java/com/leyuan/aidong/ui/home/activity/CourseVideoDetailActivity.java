package com.leyuan.aidong.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.CourseRecommendVideoAdapter;
import com.leyuan.aidong.entity.CourseVideoBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mvp.presenter.CoursePresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.CoursePresentImpl;
import com.leyuan.aidong.ui.mvp.view.CourseVideoDetailActivityView;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.widget.media.TextViewPrintly;

import java.util.List;

/**
 * 课程视频详情
 * Created by song on 2017/4/21.
 */
public class CourseVideoDetailActivity extends BaseActivity implements CourseVideoDetailActivityView,View.OnClickListener, CourseRecommendVideoAdapter.ItemClickListener {
    private static final String RELATE = "relation";
    private ImageView ivCover;
    private ImageView ivStart;
    private RelativeLayout rlInfo;
    private TextViewPrintly tvCourseName;
    private TextViewPrintly tvAuthAndTime;
    private ImageView ivUpArrow;
    private TextViewPrintly tvCourseDesc;
    private LinearLayout layoutVideoList;
    private RelativeLayout layoutMoreVideo;
    private RecyclerView recyclerView;
    private Button ivBack;
    private Button btAppoint;

    private CourseRecommendVideoAdapter adapter;
    private CoursePresent coursePresent;
    private String courseId;
    private String videoId;

    public static void start(Context context, String courseId) {
        Intent starter = new Intent(context, CourseVideoDetailActivity.class);
        starter.putExtra("courseId",courseId);
        context.startActivity(starter);
    }

    public static void start(Context context, String courseId,String videoId) {
        Intent starter = new Intent(context, CourseVideoDetailActivity.class);
        starter.putExtra("courseId",courseId);
        starter.putExtra("videoId",videoId);
        context.startActivity(starter);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_video_detail);
        if(getIntent() != null){
            courseId = getIntent().getStringExtra("courseId");
            videoId = getIntent().getStringExtra("videoId");
        }
        coursePresent = new CoursePresentImpl(this,this);
        initView();
        setListener();
        coursePresent.getRelateCourseVideo(courseId,videoId);
    }

    private void initView(){
        ivCover = (ImageView) findViewById(R.id.img_cover);
        ivStart = (ImageView) findViewById(R.id.iv_start);
        rlInfo = (RelativeLayout) findViewById(R.id.rl_info);
        tvCourseName = (TextViewPrintly) findViewById(R.id.tv_course_name);
        tvAuthAndTime = (TextViewPrintly) findViewById(R.id.tv_auth_and_time);
        ivUpArrow = (ImageView) findViewById(R.id.iv_up_arrow);
        tvCourseDesc = (TextViewPrintly) findViewById(R.id.tv_course_desc);
        layoutVideoList = (LinearLayout) findViewById(R.id.layout_video_list);
        layoutMoreVideo = (RelativeLayout) findViewById(R.id.layout_more_video);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new CourseRecommendVideoAdapter(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(adapter);
        ivBack = (Button) findViewById(R.id.iv_back);
        btAppoint = (Button) findViewById(R.id.bt_appoint);
    }

    private void setListener(){
        ivBack.setOnClickListener(this);
        btAppoint.setOnClickListener(this);
        layoutMoreVideo.setOnClickListener(this);
        adapter.setListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_appoint:
                break;
            case R.id.layout_more_video:
                RelatedVideoActivity.start(this,courseId);
                break;
            default:
                break;
        }
    }

    @Override
    public void updateRelateVideo(List<CourseVideoBean> videoBeanList) {
        if(videoBeanList != null&& !videoBeanList.isEmpty()) {
            GlideLoader.getInstance().displayImage(videoBeanList.get(0).getCover(), ivCover);
            tvCourseName.setText(videoBeanList.get(0).getName());
            tvAuthAndTime.setText(videoBeanList.get(0).getDuring());
            tvCourseDesc.setText(videoBeanList.get(0).getIntroduce());
            adapter.setData(videoBeanList);
        }
    }

    @Override
    public void onItemClick(String videoId) {
        CourseVideoDetailActivity.start(this,courseId,videoId);
    }
}
