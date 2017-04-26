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
import com.leyuan.aidong.entity.CategoryBean;
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
public class CourseVideoDetailActivity extends BaseActivity implements CourseVideoDetailActivityView,View.OnClickListener {
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
    private CategoryBean courseVideo;

    public static void start(Context context, CategoryBean courseVideo) {
        Intent starter = new Intent(context, CourseVideoDetailActivity.class);
        starter.putExtra("courseVideo",courseVideo);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_video_detail);
        if(getIntent() != null){
            courseVideo = (CategoryBean) getIntent().getSerializableExtra("courseVideo");
        }
        coursePresent = new CoursePresentImpl(this,this);
        initView();
        setListener();
        coursePresent.getRelateCourseVideo(courseVideo.getId());
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

        GlideLoader.getInstance().displayImage(courseVideo.getCover(),ivCover);
        tvCourseName.setText(courseVideo.getName());
        tvAuthAndTime.setText(courseVideo.getDuring());
        tvCourseDesc.setText(courseVideo.getVideo_desc());

    }

    private void setListener(){
        ivBack.setOnClickListener(this);
        btAppoint.setOnClickListener(this);
        layoutMoreVideo.setOnClickListener(this);
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
                RelatedVideoActivity.start(this,courseVideo.getId());
                break;
            default:
                break;
        }
    }

    @Override
    public void updateRelateVideo(List<CourseVideoBean> videoBeanList) {
        adapter.setData(videoBeanList);
    }
}
