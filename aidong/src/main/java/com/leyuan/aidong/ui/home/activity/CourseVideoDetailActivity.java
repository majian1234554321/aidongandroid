package com.leyuan.aidong.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.exoplayer.util.Util;
import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.CourseRecommendVideoAdapter;
import com.leyuan.aidong.entity.CourseVideoBean;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.activity.account.LoginActivity;
import com.leyuan.aidong.ui.mvp.presenter.CoursePresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.CoursePresentImpl;
import com.leyuan.aidong.ui.mvp.view.CourseVideoDetailActivityView;
import com.leyuan.aidong.ui.video.activity.PlayerActivity;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.leyuan.aidong.widget.media.TextViewPrintly;

import java.util.List;

/**
 * 课程视频详情
 * Created by song on 2017/4/21.
 */
public class CourseVideoDetailActivity extends BaseActivity implements CourseVideoDetailActivityView,View.OnClickListener,
        CourseRecommendVideoAdapter.ItemClickListener {
    private ImageView ivBlur;
    private ImageView ivCover;
    private ImageView ivStart;
    private TextViewPrintly tvCourseName;
    private TextViewPrintly tvAuthAndTime;
    private ImageView ivUpArrow;
    private TextViewPrintly tvCourseDesc;
    private RelativeLayout layoutMoreVideo;
    private RecyclerView recyclerView;
    private Button ivBack;
    private Button btAppoint;
    private SwitcherLayout switcherLayout;
    private LinearLayout contentLayout;

    private CourseRecommendVideoAdapter adapter;
    private String courseId;
    private String videoId;
    private CourseVideoBean courseVideoBean;

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
        CoursePresent coursePresent = new CoursePresentImpl(this,this);
        if(getIntent() != null){
            courseId = getIntent().getStringExtra("courseId");
            videoId = getIntent().getStringExtra("videoId");
        }
        initView();
        setListener();
        coursePresent.getRelateCourseVideo(courseId,videoId);
    }

    private void initView(){
        ivBlur = (ImageView) findViewById(R.id.iv_blur);
        ivCover = (ImageView) findViewById(R.id.img_cover);
        ivStart = (ImageView) findViewById(R.id.iv_start);
        tvCourseName = (TextViewPrintly) findViewById(R.id.tv_course_name);
        tvAuthAndTime = (TextViewPrintly) findViewById(R.id.tv_auth_and_time);
        ivUpArrow = (ImageView) findViewById(R.id.iv_up_arrow);
        tvCourseDesc = (TextViewPrintly) findViewById(R.id.tv_course_desc);
        contentLayout = (LinearLayout) findViewById(R.id.ll_content);
        switcherLayout = new SwitcherLayout(this,contentLayout);
        layoutMoreVideo = (RelativeLayout) findViewById(R.id.layout_more_video);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new CourseRecommendVideoAdapter(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(adapter);
        ivBack = (Button) findViewById(R.id.iv_back);
        btAppoint = (Button) findViewById(R.id.bt_appoint);
        btAppoint.setText(App.mInstance.isLogin() ? Constant.activity : getString(R.string.tip_new_user));
    }

    private void setListener(){
        ivBack.setOnClickListener(this);
        btAppoint.setOnClickListener(this);
        layoutMoreVideo.setOnClickListener(this);
        ivStart.setOnClickListener(this);
        adapter.setListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_appoint:
                if(App.mInstance.isLogin()){
                    CourseActivity.start(this,courseVideoBean.getTypeName());
                }else {
                    startActivityForResult(new Intent(this, LoginActivity.class),Constant.REQUEST_LOGIN);
                }
                break;
            case R.id.layout_more_video:
                RelatedVideoActivity.start(this,courseId);
                break;
            case R.id.iv_start:
                Intent intent = new Intent(this, PlayerActivity.class)
                        .setData(Uri.parse(courseVideoBean.getFile()))
                        .putExtra(PlayerActivity.CONTENT_TYPE_EXTRA, Util.TYPE_HLS);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void updateRelateVideo(List<CourseVideoBean> videoBeanList) {
        btAppoint.setVisibility(View.VISIBLE);
        if(videoBeanList != null&& !videoBeanList.isEmpty()) {
            courseVideoBean = videoBeanList.get(0);
            GlideLoader.getInstance().displayImage(courseVideoBean.getCover(), ivCover);
            GlideLoader.getInstance().displayImageWithBlur(courseVideoBean.getCover(),ivBlur);
            tvCourseName.setText(courseVideoBean.getName());
            tvAuthAndTime.setText(String.format(getString(R.string.course_type_and_during),
                    courseVideoBean.getTypeName(),courseVideoBean.getDuring()));
            tvCourseDesc.setText(courseVideoBean.getIntroduce());
            adapter.setData(videoBeanList);
        }
    }

    @Override
    public void onItemClick(String videoId) {
        CourseVideoDetailActivity.start(this,courseId,videoId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        btAppoint.setText(Constant.activity);
    }

    @Override
    public void showLoadingView() {
        switcherLayout.showLoadingLayout();
    }

    @Override
    public void showErrorView() {
        switcherLayout.showExceptionLayout();
    }

    @Override
    public void showContentView() {
        switcherLayout.showContentLayout();
    }

}
