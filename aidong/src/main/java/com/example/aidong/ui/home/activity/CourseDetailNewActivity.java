package com.example.aidong.ui.home.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;

import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .adapter.home.ApplicantAdapter;
import com.example.aidong .config.ConstantUrl;
import com.example.aidong .entity.course.CourseBeanNew;
import com.example.aidong .entity.course.CourseCouponPack;
import com.example.aidong .entity.course.CourseDetailDataNew;
import com.example.aidong .entity.course.CourseStore;
import com.example.aidong .module.share.SharePopupWindow;
import com.example.aidong .ui.App;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .ui.mine.activity.AppointDetailCourseNewActivity;
import com.example.aidong .ui.mine.activity.UserInfoActivity;
import com.example.aidong .ui.mine.activity.account.LoginActivity;
import com.example.aidong .ui.mvp.presenter.impl.CourseDetailPresentImpl;
import com.example.aidong .ui.mvp.view.CourseDetailViewNew;
import com.example.aidong .utils.Constant;
import com.example.aidong .utils.DateUtils;
import com.example.aidong .utils.DialogUtils;
import com.example.aidong .utils.GlideLoader;
import com.example.aidong .utils.Logger;
import com.example.aidong .utils.ToastGlobal;
import com.example.aidong .widget.richtext.RichWebView;
import com.zzhoujay.richtext.RichText;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by user on 2017/10/30.
 */

public class CourseDetailNewActivity extends BaseActivity implements View.OnClickListener, CourseDetailViewNew {

    private static final java.lang.String TAG = "CourseDetailNewActivity";
    private ScrollView scrollView;
    private BGABanner banner;
    private TextView txtCourseName;
    private TextView txtCoachName;
    private ImageView imgCoachIdentify;
    private TextView txtCoachDesc, tv_UnMember, tv_price2, tv_price;
    private ImageView imgCoachAvatar;
    private TextView txtNormalPrice;
    private TextView txtMemberPrice;
    private TextView txtCourseTime;
    private TextView txtCourseRoom;
    private TextView txtCourseLocation;

    private RelativeLayout layoutCoursePack;
    private TextView txtCoursePackInfo;
    private TextView txtCoursePackPrice,tv_tips;

    //    private TextView tvDesc;
    private RelativeLayout layout_course_coach, rl_Member, rl_UnMember;
    private ImageView ivBack;
    private TextView tvTitle;
    private ImageView ivShare;
    private LinearLayout llApply;

    private TextView tvState;
    private LinearLayout layout_course_location;

    private RichWebView webView;

    private String code;
    private boolean isFollow;
    private CourseDetailPresentImpl coursePresent;
    private ApplicantAdapter applicantAdapter;
    private SharePopupWindow sharePopupWindow;
    private CourseBeanNew course;
    private Handler handler = new Handler();

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) return;

            switch (action) {

                case Constant.BROADCAST_ACTION_LOGIN_SUCCESS:
                    coursePresent.getCourseDetail(code);
                    break;
                case Constant.BROADCAST_ACTION_COURSE_PAY_SUCCESS:
                    finish();
                    break;
                case Constant.BROADCAST_ACTION_COURSE_PAY_SFAIL:
                    finish();
                    break;
                case Constant.BROADCAST_ACTION_COURSE_QUEUE_SUCCESS:
                    finish();
                    break;
                case Constant.BROADCAST_ACTION_GOODS_PAY_FAIL:
                    finish();
                    break;
                case Constant.BROADCAST_ACTION_GOODS_PAY_SUCCESS:
                    finish();
                    break;

                case Constant.BROADCAST_ACTION_COURSE_APPOINT_CANCEL:
                    coursePresent.getCourseDetail(code);
                    break;
                case Constant.BROADCAST_ACTION_COURSE_APPOINT_DELETE:
                    coursePresent.getCourseDetail(code);
                    break;
                case Constant.BROADCAST_ACTION_COURSE_QUEUE_CANCELED:
                    coursePresent.getCourseDetail(code);
                    break;
                case Constant.BROADCAST_ACTION_COURSE_QUEUE_DELETED:
                    coursePresent.getCourseDetail(code);
                    break;
            }
        }
    };
    private CourseCouponPack coupon_pack;

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
        DialogUtils.showDialog(this, "加载中...", false);
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
        tv_UnMember = (TextView) findViewById(R.id.tv_UnMember);


        layoutCoursePack = (RelativeLayout) findViewById(R.id.layout_course_pack);
        txtCoursePackInfo = (TextView) findViewById(R.id.txt_course_pack_info);
        txtCoursePackPrice = (TextView) findViewById(R.id.txt_course_pack_price);

        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivShare = (ImageView) findViewById(R.id.iv_share);
        llApply = findViewById(R.id.ll_apply);
        rl_UnMember = findViewById(R.id.rl_UnMember);

        rl_Member = findViewById(R.id.rl_Member);

        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_price2 = (TextView) findViewById(R.id.tv_price2);

        tvState = findViewById(R.id.tv_state);

        layout_course_coach = (RelativeLayout) findViewById(R.id.layout_course_coach);
        webView = (RichWebView) findViewById(R.id.web_view);
        tv_tips = findViewById(R.id.tv_tips);


        String value = "抢购套券享受特惠价,热门课程低至 59元/每节";
        SpannableString ss = new SpannableString(value);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this,R.color.main_red)), value.length()-6, value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_tips.setText(ss);
        tv_tips.setVisibility(View.GONE);
    }

    private void setListener() {

        IntentFilter filter = new IntentFilter();

        filter.addAction(Constant.BROADCAST_ACTION_LOGIN_SUCCESS);
        filter.addAction(Constant.BROADCAST_ACTION_COURSE_PAY_SUCCESS);
        filter.addAction(Constant.BROADCAST_ACTION_COURSE_PAY_SFAIL);

        filter.addAction(Constant.BROADCAST_ACTION_COURSE_APPOINT_CANCEL);
        filter.addAction(Constant.BROADCAST_ACTION_COURSE_APPOINT_DELETE);

        filter.addAction(Constant.BROADCAST_ACTION_COURSE_QUEUE_SUCCESS);
        filter.addAction(Constant.BROADCAST_ACTION_COURSE_QUEUE_CANCELED);
        filter.addAction(Constant.BROADCAST_ACTION_COURSE_QUEUE_DELETED);

        filter.addAction(Constant.BROADCAST_ACTION_GOODS_PAY_FAIL);
        filter.addAction(Constant.BROADCAST_ACTION_GOODS_PAY_SUCCESS);

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);

        layoutCoursePack.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        llApply.setOnClickListener(this);
        layout_course_coach.setOnClickListener(this);
        txtCourseLocation.setOnClickListener(this);
        banner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                ImageView imageView = (ImageView) view;
                imageView.setScaleType(ImageView.ScaleType.CENTER);

                GlideLoader.getInstance().displayImage2((String) model, imageView);
            }
        });
    }



    

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_course_coach:
                if (course.getCoach() != null)
                    UserInfoActivity.start(this, course.getCoach().getContact());
                else
                    ToastGlobal.showShortConsecutive("教练信息有误");

                break;
            case R.id.iv_back:
                finish();
                break;


            case R.id.layout_course_pack:
                GoodsVirtualListActivity.start(this, coupon_pack.getItemProduct());
                break;
            case R.id.iv_share:
                String image = "";
                if (course.getImage() != null && course.getImage().size() > 0) {
                    image = course.getImage().get(0);
                }

                sharePopupWindow.showAtBottom(course.getName(), course.getIntroduce(),
                        image, ConstantUrl.URL_SHARE_COURSE + course.getId() + "/course");

                break;
            case R.id.txt_course_location:
                CourseStore store = course.getStore();
                if (store != null) {
                    if (store.getCoordinate() != null && store.getCoordinate().length >= 2) {
                        MapActivity.start(this, store.getName(), store.getName(), store.getAddress(),
                                store.getCoordinate()[0] + "", store.getCoordinate()[1] + "");
                    } else {
                        MapActivity.start(this, store.getName(), store.getName(), store.getAddress(),
                                "31.00", "108.00");
                    }
                }

                break;
            case R.id.ll_apply:
                if (!App.getInstance().isLogin()) {
                    startActivity(new Intent(this, LoginActivity.class));
                    return;
                }

                if (course!=null&&course.isMember_only() && !course.isMember()) {
                    ToastGlobal.showShortConsecutive("该课程只有会员才能预约");
                    return;
                }

                switch (course.getStatus()) {

                    case CourseBeanNew.NORMAL:
                    case CourseBeanNew.FEW:
                        if (course.getSeat() != null && course.getSeat().isNeed()) {
                            CourseChooseSeat.start(this, course);
                        } else {
                            ConfirmOrderCourseActivity.start(this, course);
                        }

                        break;
                    case CourseBeanNew.APPOINTED:
                        //跳查看预约
                        AppointDetailCourseNewActivity.courseStart(this, course.getId());

                        break;
                    case CourseBeanNew.APPOINTED_NO_PAY:
                        ///跳查看预约
                        AppointDetailCourseNewActivity.courseStart(this, course.getId());

                        break;
                    case CourseBeanNew.QUEUED:

                        //跳查看排队
                        CourseQueueDetailActivity.startFromCourse(this, course.getId());

                        break;

                    case CourseBeanNew.QUEUEABLE:
                        ///跳确认排队
                        CourseQueueConfirmActivity.start(this, course);
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
    public void onGetCourseDetail(CourseDetailDataNew courseData) {
        DialogUtils.dismissDialog();
        if (courseData == null || courseData.getTimetable() == null) return;
        this.course = courseData.getTimetable();
        this.coupon_pack = courseData.getCoupon_pack();
        tvTitle.setText(courseData.getTimetable().getName());

        if (TextUtils.isEmpty(coupon_pack.getItemProduct())) {
            layoutCoursePack.setVisibility(View.GONE);
        } else {
            layoutCoursePack.setVisibility(View.VISIBLE);
            txtCoursePackInfo.setText(coupon_pack.getTitle());
            txtCoursePackPrice.setText(coupon_pack.getPrice() + "元/节!");
        }


        banner.setData(course.getImage(), null);
        txtCourseName.setText(course.getName());
        if (course.getCoach() != null) {
            txtCoachName.setText(course.getCoach().getName());
            if (!TextUtils.isEmpty(course.getCoach().getIntroduce())) {

                // txtCoachDesc.setText(course.getCoach().getIntroduce());

                RichText.from(course.getCoach().getIntroduce()).placeHolder(R.drawable.place_holder_logo)
                        .error(R.drawable.place_holder_logo).into(txtCoachDesc);
            }
            GlideLoader.getInstance().displayCircleImage(course.getCoach().getAvatar(), imgCoachAvatar);
        }


        txtNormalPrice.setText(String.format(getString(R.string.rmb_price_double), course.getPrice()));
        txtMemberPrice.setText("会员: " + String.format(getString(R.string.rmb_price_double), course.getMember_price()));
        txtCourseTime.setText(course.getClass_time());
        txtCourseRoom.setText(course.getStore().getName() + "-" + course.getStore().getClassroom());
        txtCourseLocation.setText(course.getStore().getAddress());
        webView.setRichText(course.getIntroduce());


        switch (course.getStatus()) {

            case CourseBeanNew.NORMAL:
            case CourseBeanNew.FEW:

                tv_price.setTextColor(getResources().getColor(R.color.white));

                llApply.setBackgroundColor(getResources().getColor(R.color.main_red));
                llApply.setClickable(true);

                if (course.getSeat() != null && course.getSeat().isNeed()) {
                    tvState.setText(R.string.appointment_choose_seat);
                }


                if (course.member) { //会员
                    rl_Member.setVisibility(View.VISIBLE);
                    rl_UnMember.setVisibility(View.GONE);

                            tvState.setText("立即预约");
                    tvState.setTextColor(getResources().getColor(R.color.main_red));
                    tv_price.setText("会员: " + String.format(getString(R.string.rmb_price_double), course.getMember_price()));
                    tv_price2.setText(String.format(getString(R.string.rmb_price_double), course.getPrice()));
                    tv_price2.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );

                } else {
                    rl_Member.setVisibility(View.GONE);
                    rl_UnMember.setVisibility(View.VISIBLE);



                    tv_UnMember.setText(String.format(getString(R.string.rmb_price_double), course.getPrice())+" 立即预约");
                    tv_UnMember.setTextColor(getResources().getColor(R.color.white));

                }


                break;
            case CourseBeanNew.APPOINTED:

                rl_Member.setVisibility(View.GONE);
                rl_UnMember.setVisibility(View.VISIBLE);

                tv_UnMember.setText(R.string.appointmented_look_detail);
                tv_UnMember.setTextColor(getResources().getColor(R.color.white));

                llApply.setBackgroundColor(getResources().getColor(R.color.main_red));
                llApply.setClickable(true);


                break;
            case CourseBeanNew.APPOINTED_NO_PAY:

                rl_Member.setVisibility(View.GONE);
                rl_UnMember.setVisibility(View.VISIBLE);

                tv_UnMember.setText(R.string.wait_pay_appointed);
                tv_UnMember.setTextColor(getResources().getColor(R.color.white));

                llApply.setBackgroundColor(getResources().getColor(R.color.main_red));
                llApply.setClickable(true);
                break;
            case CourseBeanNew.QUEUED:

                rl_Member.setVisibility(View.GONE);
                rl_UnMember.setVisibility(View.VISIBLE);

                tv_UnMember.setText(R.string.queueing_look_detail);
                tv_UnMember.setTextColor(getResources().getColor(R.color.white));

                llApply.setBackgroundColor(getResources().getColor(R.color.main_red));
                llApply.setClickable(true);
                break;

            case CourseBeanNew.QUEUEABLE:

                rl_Member.setVisibility(View.GONE);
                rl_UnMember.setVisibility(View.VISIBLE);

                tv_UnMember.setText(R.string.appoint_full_queue_immedialtely);
                tv_UnMember.setTextColor(getResources().getColor(R.color.white));

                llApply.setBackgroundColor(getResources().getColor(R.color.main_red));
                llApply.setClickable(true);
                break;
            case CourseBeanNew.FULL:

                rl_Member.setVisibility(View.GONE);
                rl_UnMember.setVisibility(View.VISIBLE);

                tv_UnMember.setText("已满员");
                tv_UnMember.setTextColor(getResources().getColor(R.color.white));

                llApply.setBackgroundColor(getResources().getColor(R.color.list_line_color));
                llApply.setClickable(false);
                break;
            case CourseBeanNew.END:

                rl_Member.setVisibility(View.GONE);
                rl_UnMember.setVisibility(View.VISIBLE);

                tv_UnMember.setText(R.string.ended);
                tv_UnMember.setTextColor(getResources().getColor(R.color.white));

                llApply.setBackgroundColor(getResources().getColor(R.color.list_line_color));
                llApply.setClickable(false);
                break;
            default:
                break;
        }


        if (DateUtils.compareLongTime(course.getReserve_time()) > 0) {

//            tvState.setText("预约开始时间:" + course.getReserve_time());
//            tvState.setTextColor(getResources().getColor(R.color.white));
//            llApply.setBackgroundColor(getResources().getColor(R.color.black));
//            llApply.setClickable(false);

            Logger.i(TAG, "DateUtils.compareLongTime(course.getReserve_time()) = " + DateUtils.compareLongTime(course.getReserve_time()));
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    coursePresent.getCourseDetail(code);
                }
            }, DateUtils.compareLongTime(course.getReserve_time()) + 2000);
        }



//        if (course.getReservable() == 0) {
//
//            tvState.setText(R.string.do_not_nedd_appointment);
//            tvState.setTextColor(getResources().getColor(R.color.white));
//            llApply.setBackgroundColor(getResources().getColor(R.color.list_line_color));
//            llApply.setClickable(false);
//        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DialogUtils.releaseDialog();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        RichText.clear(this);
        sharePopupWindow.release();
        handler.removeCallbacksAndMessages(null);
    }
}