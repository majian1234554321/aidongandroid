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
import com.leyuan.aidong.entity.course.CourseBeanNew;
import com.leyuan.aidong.module.share.SharePopupWindow;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.CourseDetailPresentImpl;
import com.leyuan.aidong.ui.mvp.view.CourseDetailViewNew;
import com.leyuan.aidong.utils.GlideLoader;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by user on 2017/10/30.
 */

public class CourseDetailNewActivity extends BaseActivity implements View.OnClickListener, CourseDetailViewNew {

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
    private CourseDetailPresentImpl coursePresent;
    private ApplicantAdapter applicantAdapter;
    private SharePopupWindow sharePopupWindow;
    private CourseBeanNew course;

    public static void start(Context context, String code) {
        Intent starter = new Intent(context, CourseDetailNewActivity.class);
        starter.putExtra("code", code);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail_new);
        coursePresent = new CourseDetailPresentImpl(this, this);
        if (getIntent() != null) {
            code = getIntent().getStringExtra("code");
        }

        initView();
        setListener();
        coursePresent.getCourseDetail(code);

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
        ivBack.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        llApply.setOnClickListener(this);
        banner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                GlideLoader.getInstance().displayImage((String) model, (ImageView) view);
            }
        });
    }

    @Override
    public void onClick(View v) {
           switch (v.getId()){
               case R.id.iv_back:
                   finish();
                   break;
               case R.id.iv_share:

                   break;
               case R.id.ll_apply:
                   switch (course.getStatus()) {

                       case CourseBeanNew.NORMAL:

                           break;
                       case CourseBeanNew.APPOINTED:

                           break;
                       case CourseBeanNew.APPOINTED_NO_PAY:
                           break;
                       case CourseBeanNew.QUEUED:
                           break;
                       case CourseBeanNew.FEW:
                           break;
                       case CourseBeanNew.QUEUEABLE:
                           break;
                       case CourseBeanNew.FULL:
                           break;
                       case CourseBeanNew.END:
                           break;
                       default:
                           break;
                   }


                   break;
           }
    }

    @Override
    public void onGetCourseDetail(CourseBeanNew course) {
        if (course == null) return;
        this.course = course;
        banner.setData(course.getImage(), null);
        txtCourseName.setText(course.getName());
        txtCoachName.setText(course.getCoach().getName());
        txtCoachDesc.setText(course.getCoach().getIntroduce());
        GlideLoader.getInstance().displayImage(course.getCoach().getAvatar(),imgCoachAvatar);
        txtNormalPrice.setText("￥ " + course.getPrice());
        txtMemberPrice.setText("会员价: ￥" + course.getMember_price());
        txtCourseTime.setText(course.getClass_time());
        txtCourseRoom.setText(course.getStore().getName());
        txtCourseLocation.setText(course.getStore().getAddress());
        tvDesc.setText(course.getIntroduce());


        switch (course.getStatus()) {

            case CourseBeanNew.NORMAL:
                tvPrice.setText("￥ " + course.getPrice());
                tvState.setText(R.string.appointment_immediately);
                tvPrice.setTextColor(getResources().getColor(R.color.white));
                tvState.setTextColor(getResources().getColor(R.color.white));
                llApply.setBackgroundColor(getResources().getColor(R.color.main_red));
                break;
            case CourseBeanNew.APPOINTED:

                tvPrice.setText("");
                tvState.setText(R.string.appointmented_look_detail);
                tvPrice.setTextColor(getResources().getColor(R.color.white));
                tvState.setTextColor(getResources().getColor(R.color.white));
                llApply.setBackgroundColor(getResources().getColor(R.color.main_red));

                break;
            case CourseBeanNew.APPOINTED_NO_PAY:
                tvPrice.setText("");
                tvState.setText(R.string.no_pay_appointmented);
                tvPrice.setTextColor(getResources().getColor(R.color.white));
                tvState.setTextColor(getResources().getColor(R.color.white));
                llApply.setBackgroundColor(getResources().getColor(R.color.main_red));
                break;
            case CourseBeanNew.QUEUED:
                tvPrice.setText("");
                tvState.setText(R.string.queueing_look_detail);
                tvPrice.setTextColor(getResources().getColor(R.color.white));
                tvState.setTextColor(getResources().getColor(R.color.white));
                llApply.setBackgroundColor(getResources().getColor(R.color.main_red));
                break;
            case CourseBeanNew.FEW:
                tvPrice.setText("");
                tvState.setText(R.string.appoint_full_queue_immedialtely);
                tvPrice.setTextColor(getResources().getColor(R.color.white));
                tvState.setTextColor(getResources().getColor(R.color.white));
                llApply.setBackgroundColor(getResources().getColor(R.color.main_red));
                break;
            case CourseBeanNew.QUEUEABLE:
                tvPrice.setText("");
                tvState.setText(R.string.appoint_full_queue_immedialtely);
                tvPrice.setTextColor(getResources().getColor(R.color.white));
                tvState.setTextColor(getResources().getColor(R.color.white));
                llApply.setBackgroundColor(getResources().getColor(R.color.main_red));
                break;
            case CourseBeanNew.FULL:
                tvPrice.setText("");
                tvState.setText(R.string.fulled);
                tvPrice.setTextColor(getResources().getColor(R.color.white));
                tvState.setTextColor(getResources().getColor(R.color.white));
                llApply.setBackgroundColor(getResources().getColor(R.color.list_line_color));
                break;
            case CourseBeanNew.END:
                tvPrice.setText("");
                tvState.setText(R.string.ended);
                tvPrice.setTextColor(getResources().getColor(R.color.white));
                tvState.setTextColor(getResources().getColor(R.color.white));
                llApply.setBackgroundColor(getResources().getColor(R.color.list_line_color));
                break;
            default:
                break;
        }

        if(course.getReservable()==0){
            tvPrice.setText("");
            tvState.setText(R.string.do_not_nedd_appointment);
            tvState.setTextColor(getResources().getColor(R.color.white));
            llApply.setBackgroundColor(getResources().getColor(R.color.list_line_color));
        }

    }
}
