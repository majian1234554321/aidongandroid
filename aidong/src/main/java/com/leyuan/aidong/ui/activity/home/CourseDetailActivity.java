package com.leyuan.aidong.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.CourseDetailBean;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mvp.presenter.CoursePresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.CoursePresentImpl;
import com.leyuan.aidong.ui.mvp.view.CourseDetailActivityView;
import com.leyuan.aidong.widget.customview.SwitcherLayout;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * 课程详情
 * Created by song on 2016/11/15.
 */
public class CourseDetailActivity extends BaseActivity implements View.OnClickListener,CourseDetailActivityView{
    private static final String STATUS_END = "0";           //预约已结束
    private static final String STATUS_APPOINTED = "1";     //已预约
    private static final String STATUS_FULL = "2";          //预约人数已满
    private static final String STATUS_NOT_START = "3";     //即将开始预约
    private static final String STATUS_NOT_PAY = "4";       //待支付
    private static final String STATUS_NOT_NEED= "5";       //无需预约
    private static final String STATUS_APPOINT = "6";       //马上预约

    private ImageView ivBack;
    private TextView tvTitle;
    private ImageView ivShare;
    private SwitcherLayout switcherLayout;
    private LinearLayout contentLayout;
    private TextView tvStartTime;
    private BGABanner banner;
    private TextView tvHot;
    private SimpleDraweeView dvAvatar;
    private TextView tvCoachName;
    private ImageView ivFollow;
    private TextView tvTime;
    private TextView tvAddress;
    private TextView tvVenues;
    private TextView tvRoom;
    private TextView tvCount;
    private RecyclerView rvApplicant;
    private TextView tvDesc;
    private LinearLayout bottomLayout;
    private TextView tvPrice;
    private TextView tvState;

    private String code;
    private boolean isFollow;
    private CourseDetailBean detailBean;
    private CoursePresent coursePresent;

    public static void start(Context context,String code) {
        Intent starter = new Intent(context, CourseDetailActivity.class);
        starter.putExtra("code",code);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        pageSize = 20;
        coursePresent = new CoursePresentImpl(this,this);
        if(getIntent() != null){
            code = getIntent().getStringExtra("code");
        }

        initView();
        setListener();
        coursePresent.getCourseDetail(switcherLayout,code);
    }

    private void initView(){
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivShare = (ImageView) findViewById(R.id.iv_share);
        contentLayout = (LinearLayout) findViewById(R.id.ll_content);
        switcherLayout = new SwitcherLayout(this,contentLayout);
        tvStartTime = (TextView) findViewById(R.id.tv_start_time);
        banner = (BGABanner) findViewById(R.id.banner);
        tvHot = (TextView) findViewById(R.id.tv_hot);
        dvAvatar = (SimpleDraweeView) findViewById(R.id.dv_avatar);
        tvCoachName = (TextView) findViewById(R.id.tv_coach_name);
        ivFollow = (ImageView) findViewById(R.id.iv_follow);
        tvTime = (TextView) findViewById(R.id.tv_time);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        tvVenues = (TextView) findViewById(R.id.tv_venues);
        tvRoom = (TextView) findViewById(R.id.tv_room);
        tvCount = (TextView) findViewById(R.id.tv_count);
        rvApplicant = (RecyclerView) findViewById(R.id.rv_applicant);
        tvDesc = (TextView) findViewById(R.id.tv_desc);
        bottomLayout = (LinearLayout) findViewById(R.id.ll_apply);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvState = (TextView) findViewById(R.id.tv_state);
        banner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                ImageLoader.getInstance().displayImage((String) model,(ImageView)view);
            }
        });
    }

    private void setListener(){
        ivBack.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        dvAvatar.setOnClickListener(this);
        ivFollow.setOnClickListener(this);
        bottomLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:
                break;
            case R.id.dv_avatar:
                break;
            case R.id.iv_follow:
                if(isFollow){
                    coursePresent.cancelFollow(detailBean.getCoach().getCoachId());
                }else {
                    coursePresent.addFollow(detailBean.getCoach().getCoachId());
                }
                break;
            case R.id.ll_apply:
                bottomToTargetActivity();
                break;
            default:
                break;
        }
    }

    @Override
    public void setCourseDetail(CourseDetailBean bean) {
        ivShare.setVisibility(View.VISIBLE);
        detailBean = bean;
        tvTitle.setText(bean.getName());
        List<String> cover = new ArrayList<>();
        cover.add(bean.getCover());
        banner.setData(cover,null);
        dvAvatar.setImageURI(bean.getCover());
        tvCoachName.setText(bean.getName());
        tvTime.setText(bean.getClass_date());
        tvAddress.setText(bean.getGym().getAddress());
        tvVenues.setText(bean.getGym().getName());
        tvRoom.setText(bean.getClassroom());
        tvCount.setText(String.format(getString(R.string.course_applicant_count),
                bean.getApplied_count(),bean.getStock()));
        tvDesc.setText(Html.fromHtml(bean.getIntroduce()));
        tvPrice.setText(String.format(getString(R.string.rmb_price),bean.getPrice()));
        tvStartTime.setText(String.format(getString(R.string.appoint_time),bean.getClassTime()));
        setBottomStatus();
    }

    @Override
    public void addFollow(BaseBean baseBean) {
        if(baseBean.getStatus() == 1){
            isFollow = true;
            ivFollow.setBackgroundResource(R.drawable.icon_following);
            Toast.makeText(this,R.string.follow_success,Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,R.string.follow_fail,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void cancelFollow(BaseBean baseBean) {
        if(baseBean.getStatus() == 1){
            isFollow = false;
            ivFollow.setBackgroundResource(R.drawable.icon_follow);
            Toast.makeText(this,R.string.cancel_follow_success,Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,R.string.cancel_follow_fail,Toast.LENGTH_LONG).show();
        }
    }

    private void setBottomStatus(){
        bottomLayout.setVisibility(View.VISIBLE);
        switch (detailBean.getStatus()){
            case STATUS_APPOINT:   //跳预约
                tvStartTime.setVisibility(View.GONE);
                tvPrice.setVisibility(View.VISIBLE);
                tvState.setText(R.string.status_appoint);
                bottomLayout.setBackgroundColor(Color.parseColor("#000000"));
                break;
            case STATUS_END:
                tvStartTime.setVisibility(View.GONE);
                tvPrice.setVisibility(View.GONE);
                tvState.setText(R.string.status_end);
                bottomLayout.setBackgroundColor(Color.parseColor("#666667"));
                break;
            case STATUS_APPOINTED:
                tvStartTime.setVisibility(View.GONE);
                tvPrice.setVisibility(View.GONE);
                tvState.setText(R.string.status_appointed);
                bottomLayout.setBackgroundColor(Color.parseColor("#666667"));
                break;

            case STATUS_FULL:
                tvStartTime.setVisibility(View.GONE);
                tvPrice.setVisibility(View.GONE);
                tvState.setText(R.string.status_full);
                bottomLayout.setBackgroundColor(Color.parseColor("#666667"));
                break;
            case STATUS_NOT_START:
                tvStartTime.setVisibility(View.VISIBLE);
                tvPrice.setVisibility(View.GONE);
                tvState.setText(R.string.status_not_start);
                bottomLayout.setBackgroundColor(Color.parseColor("#666667"));
                break;
            case STATUS_NOT_PAY:    //跳订单详情
                tvStartTime.setVisibility(View.GONE);
                tvPrice.setVisibility(View.VISIBLE);
                tvState.setText(R.string.status_not_pay);
                bottomLayout.setBackgroundColor(Color.parseColor("#000000"));
                break;
            case STATUS_NOT_NEED:
                tvStartTime.setVisibility(View.GONE);
                tvPrice.setVisibility(View.GONE);
                tvState.setText(R.string.status_not_need);
                bottomLayout.setBackgroundColor(Color.parseColor("#666667"));
                break;
            default:
                break;
        }
    }

    private void bottomToTargetActivity(){
        if(STATUS_APPOINT.equals(detailBean.getStatus())){     //预约
            if(App.mInstance.isLogin()){
                //todo 判断同一时间是否已有预约
                AppointInfoActivity.start(this, AppointInfoActivity.TYPE_COURSE,detailBean);
            }else {
                //todo  登录 登录完成之后重新刷接口
            }
        }else if(STATUS_NOT_PAY.equals(detailBean.getStatus())){
            AppointCourseActivity.start(this,detailBean);
        }else {
            AppointCourseActivity.start(this,detailBean);
        }
    }
}
