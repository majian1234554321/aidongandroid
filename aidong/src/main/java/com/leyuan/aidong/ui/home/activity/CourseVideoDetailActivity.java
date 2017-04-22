package com.leyuan.aidong.ui.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.widget.media.TextViewPrintly;

/**
 * 课程视频详情
 * Created by song on 2017/4/21.
 */
public class CourseVideoDetailActivity extends BaseActivity implements View.OnClickListener {
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
    

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_video_detail);
        initView();
        setListener();
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
        ivBack = (Button) findViewById(R.id.iv_back);
        btAppoint = (Button) findViewById(R.id.bt_appoint);
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
                startActivity(new Intent(this,RelatedVideoActivity.class));
                break;
            default:
                break;
        }
    }
}
