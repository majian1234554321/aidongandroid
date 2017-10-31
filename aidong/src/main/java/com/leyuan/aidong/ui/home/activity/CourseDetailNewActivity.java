package com.leyuan.aidong.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.ApplicantAdapter;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.CourseDetailBean;
import com.leyuan.aidong.module.share.SharePopupWindow;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mvp.presenter.CoursePresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.CoursePresentImpl;
import com.leyuan.aidong.ui.mvp.view.CourseDetailActivityView;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by user on 2017/10/30.
 */

public class CourseDetailNewActivity extends BaseActivity implements View.OnClickListener, CourseDetailActivityView {

    private ScrollView scrollView;
    private BGABanner banner;
    private TextView txtCourseName;
    private TextView txtCoachName;
    private ImageView imgCoachIdentify;
    private TextView txtCoachDesc;
    private ImageView imgCoachAvatar;
    private TextView txtNormalPrice;
    private TextView txtMemberPrice;
    private TextView txtCourseTime;
    private TextView txtCourseRoom;
    private TextView txtCourseLocation;
    private TextView tvDesc;
    private ImageView ivBack;
    private TextView tvTitle;
    private ImageView ivShare;
    private LinearLayout llApply;
    private TextView tvPrice;
    private TextView tvState;

    private String code;
    private boolean isFollow;
    private CourseDetailBean bean;
    private CoursePresent coursePresent;
    private ApplicantAdapter applicantAdapter;
    private SharePopupWindow sharePopupWindow;

    public static void start(Context context, String code) {
        Intent starter = new Intent(context, CourseDetailActivity.class);
        starter.putExtra("code", code);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail_new);
        coursePresent = new CoursePresentImpl(this, this);
        if (getIntent() != null) {
            code = getIntent().getStringExtra("code");
        }

        initView();
        setListener();
        sharePopupWindow = new SharePopupWindow(this);
    }

    private void initView() {
        scrollView = (ScrollView) findViewById(R.id.scroll_view);
        banner = (BGABanner) findViewById(R.id.banner);
        txtCourseName = (TextView) findViewById(R.id.txt_course_name);
        txtCoachName = (TextView) findViewById(R.id.txt_coach_name);
        imgCoachIdentify = (ImageView) findViewById(R.id.img_coach_identify);
        txtCoachDesc = (TextView) findViewById(R.id.txt_coach_desc);
        imgCoachAvatar = (ImageView) findViewById(R.id.img_coach_avatar);
        txtNormalPrice = (TextView) findViewById(R.id.txt_normal_price);
        txtMemberPrice = (TextView) findViewById(R.id.txt_member_price);
        txtCourseTime = (TextView) findViewById(R.id.txt_course_time);
        txtCourseRoom = (TextView) findViewById(R.id.txt_course_room);
        txtCourseLocation = (TextView) findViewById(R.id.txt_course_location);
        tvDesc = (TextView) findViewById(R.id.tv_desc);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivShare = (ImageView) findViewById(R.id.iv_share);
        llApply = (LinearLayout) findViewById(R.id.ll_apply);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvState = (TextView) findViewById(R.id.tv_state);
    }

    private void setListener() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void setCourseDetail(CourseDetailBean courseDetailBean) {

    }

    @Override
    public void addFollowResult(BaseBean baseBean) {

    }

    @Override
    public void cancelFollowResult(BaseBean baseBean) {

    }
}
