package com.leyuan.aidong.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.ApplicantAdapter;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.CourseDetailBean;
import com.leyuan.aidong.module.share.SharePopupWindow;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.activity.AppointCourseDetailActivity;
import com.leyuan.aidong.ui.mine.activity.UserInfoActivity;
import com.leyuan.aidong.ui.mine.activity.account.LoginActivity;
import com.leyuan.aidong.ui.mvp.presenter.CoursePresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.CoursePresentImpl;
import com.leyuan.aidong.ui.mvp.view.CourseDetailActivityView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.SystemInfoUtils;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.zzhoujay.richtext.RichText;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * 课程详情
 * Created by song on 2016/11/15.
 */
public class CourseDetailActivity extends BaseActivity implements View.OnClickListener, CourseDetailActivityView {
    private static final String STATUS_APPOINT = "1";       //马上预约
    private static final String STATUS_NOT_START = "2";     //即将开始预约
    private static final String STATUS_APPOINT_END = "3";   //预约已结束
    private static final String STATUS_APPOINTED = "4";     //已预约
    private static final String STATUS_NOT_PAY = "5";       //待支付
    private static final String STATUS_FULL = "6";          //预约人数已满
    private static final String STATUS_COURSE_END = "7";    //课程结束
    private static final String STATUS_NOT_NEED = "8";       //无需预约

    private ImageView ivBack;
    private TextView tvTitle;
    private ImageView ivShare;
    private SwitcherLayout switcherLayout;
    private ScrollView scrollView;
    private TextView tvStartTime;
    private BGABanner banner;
    private TextView tvBannerPrice;
    private ImageView ivAvatar;
    private TextView tvCoachName;
    private ImageView ivFollow;
    private TextView tvTime;
    private LinearLayout addressLayout;
    private TextView tvAddress;
    private TextView tvVenues;
    private TextView tvRoom;
    private TextView tvCount;
    private RecyclerView rvApplicant;
    private TextView tvDesc;
    private LinearLayout bottomLayout;
    private TextView tvBottomPrice;
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
        setContentView(R.layout.activity_course_detail);
        coursePresent = new CoursePresentImpl(this, this);
        if (getIntent() != null) {
            code = getIntent().getStringExtra("code");
        }

        initView();
        setListener();
        coursePresent.getCourseDetail(switcherLayout, code);
        sharePopupWindow = new SharePopupWindow(this);
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivShare = (ImageView) findViewById(R.id.iv_share);
        scrollView = (ScrollView) findViewById(R.id.scroll_view);
        switcherLayout = new SwitcherLayout(this, scrollView);
        tvStartTime = (TextView) findViewById(R.id.tv_start_time);
        banner = (BGABanner) findViewById(R.id.banner);
        tvBannerPrice = (TextView) findViewById(R.id.tv_hot);
        ivAvatar = (ImageView) findViewById(R.id.dv_avatar);
        tvCoachName = (TextView) findViewById(R.id.tv_coach_name);
        ivFollow = (ImageView) findViewById(R.id.iv_follow);
        tvTime = (TextView) findViewById(R.id.tv_time);
        addressLayout = (LinearLayout) findViewById(R.id.ll_address);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        tvVenues = (TextView) findViewById(R.id.tv_venues);
        tvRoom = (TextView) findViewById(R.id.tv_room);
        tvCount = (TextView) findViewById(R.id.tv_count);
        rvApplicant = (RecyclerView) findViewById(R.id.rv_applicant);
        tvDesc = (TextView) findViewById(R.id.tv_desc);
        bottomLayout = (LinearLayout) findViewById(R.id.ll_apply);
        tvBottomPrice = (TextView) findViewById(R.id.tv_price);
        tvState = (TextView) findViewById(R.id.tv_state);
        banner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                GlideLoader.getInstance().displayImage((String) model, (ImageView) view);
            }
        });

        applicantAdapter = new ApplicantAdapter(this);
        rvApplicant.setAdapter(applicantAdapter);
        rvApplicant.setLayoutManager(new LinearLayoutManager
                (this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        ivAvatar.setOnClickListener(this);
        ivFollow.setOnClickListener(this);
        bottomLayout.setOnClickListener(this);
        addressLayout.setOnClickListener(this);
        tvCount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:
                sharePopupWindow.showAtBottom(bean.getName(), bean.getIntroduce(), bean.getCover().get(0),
                        "http://www.baidu.com");
                break;
            case R.id.ll_address:
                MapActivity.start(this, "地址详情", bean.getGym().getName(), bean.getAddress(),
                        bean.getGym().getCoordinate().getLat(), bean.getGym().getCoordinate().getLng());
                break;
            case R.id.dv_avatar:
                UserInfoActivity.start(this, bean.getCoach().getId());
                break;
            case R.id.iv_follow:
                if (App.mInstance.isLogin()) {
                    if (isFollow) {
                        coursePresent.cancelFollow(bean.getCoach().getId());
                    } else {
                        coursePresent.addFollow(bean.getCoach().getId());
                    }
                } else {
                    startActivityForResult(new Intent(this, LoginActivity.class), Constant.REQUEST_LOGIN);
                }
                break;
            case R.id.tv_count:
                AppointmentUserActivity.start(this, bean.getApplied());
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
        this.bean = bean;
        tvTitle.setText(bean.getName());
        banner.setData(bean.getCover(), null);
        tvBannerPrice.setText(String.format(getString(R.string.rmb_price_double),
                FormatUtil.parseDouble(bean.getPrice())));
        GlideLoader.getInstance().displayCircleImage(bean.getCoach().getAvatar(), ivAvatar);
        tvCoachName.setText(bean.getCoach().getName());
        tvTime.setText(String.format(getString(R.string.detail_time),
                bean.getClassDate(), bean.getClassTime(), bean.getBreakTime()));
        tvAddress.setText(bean.getGym().getAddress());
        tvVenues.setText(bean.getGym().getName());
        tvRoom.setText(bean.getClassroom());
        tvCount.setText(String.format(getString(R.string.course_applicant_count),
                bean.getAppliedCount(), bean.getPlace()));
        applicantAdapter.setData(bean.getApplied());
        if (!TextUtils.isEmpty(bean.getIntroduce())) {
            RichText.from(bean.getIntroduce()).placeHolder(R.drawable.place_holder_logo)
                    .error(R.drawable.place_holder_logo).into(tvDesc);
        }
        tvBottomPrice.setText(FormatUtil.parseDouble(bean.getPrice()) == 0f
                ? "免费" : String.format(getString(R.string.rmb_price), bean.getPrice()));
        tvStartTime.setText(String.format(getString(R.string.appoint_time),
                bean.getEntryStartTime()));

        isFollow = SystemInfoUtils.isFollow(this, bean.getCoach());
        ivFollow.setBackgroundResource(isFollow ? R.drawable.icon_following
                : R.drawable.icon_follow);
        setBottomStatus();
    }

    private void setBottomStatus() {
        bottomLayout.setVisibility(View.VISIBLE);
        switch (bean.getStatus()) {
            case STATUS_APPOINT:   //跳预约
                tvStartTime.setVisibility(View.GONE);
                tvBottomPrice.setVisibility(View.VISIBLE);
                tvState.setText(R.string.status_appoint);
                bottomLayout.setBackgroundColor(Color.parseColor("#000000"));
                break;
            case STATUS_APPOINT_END:
                tvStartTime.setVisibility(View.GONE);
                tvBottomPrice.setVisibility(View.GONE);
                tvState.setText(R.string.status_appoint_end);
                bottomLayout.setBackgroundColor(Color.parseColor("#666667"));
                break;
            case STATUS_COURSE_END:
                tvStartTime.setVisibility(View.GONE);
                tvBottomPrice.setVisibility(View.GONE);
                tvState.setText(R.string.status_course_end);
                bottomLayout.setBackgroundColor(Color.parseColor("#666667"));
                break;
            case STATUS_APPOINTED:
                tvStartTime.setVisibility(View.GONE);
                tvBottomPrice.setVisibility(View.VISIBLE);
                tvState.setText(R.string.status_appointed);
                bottomLayout.setBackgroundColor(Color.parseColor("#000000"));
                break;
            case STATUS_FULL:
                tvStartTime.setVisibility(View.GONE);
                tvBottomPrice.setVisibility(View.GONE);
                tvState.setText(R.string.status_full);
                bottomLayout.setBackgroundColor(Color.parseColor("#666667"));
                break;
            case STATUS_NOT_START:
                tvStartTime.setVisibility(View.VISIBLE);
                tvBottomPrice.setVisibility(View.GONE);
                tvState.setText(R.string.status_not_start);
                bottomLayout.setBackgroundColor(Color.parseColor("#666667"));
                break;
            case STATUS_NOT_PAY:    //跳订单详情
                tvStartTime.setVisibility(View.GONE);
                tvBottomPrice.setVisibility(View.VISIBLE);
                tvState.setText(R.string.status_not_pay);
                bottomLayout.setBackgroundColor(Color.parseColor("#000000"));
                break;
            case STATUS_NOT_NEED:
                tvStartTime.setVisibility(View.GONE);
                tvBottomPrice.setVisibility(View.GONE);
                tvState.setText(R.string.status_not_need);
                bottomLayout.setBackgroundColor(Color.parseColor("#666667"));
                break;
            default:
                break;
        }
    }

    private void bottomToTargetActivity() {
        if (STATUS_APPOINT.equals(bean.getStatus())) {        //预约
            if (App.mInstance.isLogin()) {
                AppointCourseActivity.start(this, bean);
            } else {
                startActivityForResult(new Intent(this, LoginActivity.class), Constant.REQUEST_LOGIN);
            }
        }else if(STATUS_NOT_PAY.equals(bean.getStatus())){    //待支付
            AppointCourseDetailActivity.start(this, bean.getCode(),true);
        }else if(STATUS_APPOINTED.equals(bean.getStatus())){  //已预约
            AppointCourseDetailActivity.start(this, bean.getCode(),true);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        sharePopupWindow.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constant.REQUEST_LOGIN) {
                coursePresent.getCourseDetail(switcherLayout, code);
            }
        }
    }

    @Override
    public void addFollowResult(BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
            isFollow = true;
            SystemInfoUtils.addFollow(bean.getCoach());
            ivFollow.setBackgroundResource(R.drawable.icon_following);
            Toast.makeText(this, R.string.follow_success, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.follow_fail + baseBean.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void cancelFollowResult(BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
            isFollow = false;
            SystemInfoUtils.removeFollow(bean.getCoach());
            ivFollow.setBackgroundResource(R.drawable.icon_follow);
            Toast.makeText(this, R.string.cancel_follow_success, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.cancel_follow_fail + baseBean.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharePopupWindow.release();
    }
}
