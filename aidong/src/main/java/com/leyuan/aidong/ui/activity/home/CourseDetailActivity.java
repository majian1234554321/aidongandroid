package com.leyuan.aidong.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.BannerBean;
import com.leyuan.aidong.entity.CourseDetailBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mvp.presenter.CoursePresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.CoursePresentImpl;
import com.leyuan.aidong.ui.mvp.view.CourseDetailActivityView;
import com.nostra13.universalimageloader.core.ImageLoader;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * 课程详情
 * Created by song on 2016/11/15.
 */
public class CourseDetailActivity extends BaseActivity implements View.OnClickListener,CourseDetailActivityView {
    private ImageView ivBack;
    private TextView tvTitle;
    private ImageView ivShare;
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
    private LinearLayout applyLayout;
    private TextView tvPrice;
    private TextView tvState;

    private String id;
    private int pageSize;
    private CoursePresent coursePresent;

    public static void start(Context context,String id) {
        Intent starter = new Intent(context, CourseDetailActivity.class);
        starter.putExtra("id",id);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        pageSize = 20;
        coursePresent = new CoursePresentImpl(this,this);
        if(getIntent() != null){
            id = getIntent().getStringExtra("id");
        }

        initView();
        setListener();
        coursePresent.getCourseDetail(id);
    }

    private void initView(){
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivShare = (ImageView) findViewById(R.id.iv_share);
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
        applyLayout = (LinearLayout) findViewById(R.id.ll_apply);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvState = (TextView) findViewById(R.id.tv_state);
        banner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                ImageLoader.getInstance().displayImage(((BannerBean)model).getImage(),(ImageView)view);
            }
        });
    }

    private void setListener(){
        ivBack.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        dvAvatar.setOnClickListener(this);
        ivFollow.setOnClickListener(this);
        applyLayout.setOnClickListener(this);

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
                break;
            case R.id.ll_apply:
                break;
            default:
                break;

        }
    }

    @Override
    public void setCourseDetail(CourseDetailBean bean) {
        tvTitle.setText(bean.getName());
        dvAvatar.setImageURI(bean.getCover());
        tvCoachName.setText(bean.getName());

    }

    @Override
    public void showErrorView() {

    }
}
