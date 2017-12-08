package com.leyuan.aidong.ui.home.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.ApplicantAdapter;
import com.leyuan.aidong.config.ConstantUrl;
import com.leyuan.aidong.entity.course.CourseBeanNew;
import com.leyuan.aidong.entity.course.CourseStore;
import com.leyuan.aidong.module.share.SharePopupWindow;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.activity.AppointDetailCourseAndEventActivity;
import com.leyuan.aidong.ui.mine.activity.UserInfoActivity;
import com.leyuan.aidong.ui.mine.activity.account.LoginActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.CourseDetailPresentImpl;
import com.leyuan.aidong.ui.mvp.view.CourseDetailViewNew;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DateUtils;
import com.leyuan.aidong.utils.DialogUtils;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.ToastGlobal;
import com.leyuan.aidong.widget.richtext.RichWebView;
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
    private TextView txtCoachDesc;
    private ImageView imgCoachAvatar;
    private TextView txtNormalPrice;
    private TextView txtMemberPrice;
    private TextView txtCourseTime;
    private TextView txtCourseRoom;
    private TextView txtCourseLocation;
    //    private TextView tvDesc;
    private RelativeLayout layout_course_coach;
    private ImageView ivBack;
    private TextView tvTitle;
    private ImageView ivShare;
    private LinearLayout llApply;
    private TextView tvPrice;
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
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivShare = (ImageView) findViewById(R.id.iv_share);
        llApply = (LinearLayout) findViewById(R.id.ll_apply);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvState = (TextView) findViewById(R.id.tv_state);
        layout_course_location = (LinearLayout) findViewById(R.id.layout_course_location);
        layout_course_coach = (RelativeLayout) findViewById(R.id.layout_course_coach);
        webView = (RichWebView) findViewById(R.id.web_view);
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

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);


        ivBack.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        llApply.setOnClickListener(this);
        layout_course_coach.setOnClickListener(this);
        layout_course_location.setOnClickListener(this);
        banner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                GlideLoader.getInstance().displayImage((String) model, (ImageView) view);
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
            case R.id.iv_share:
                String  image = "";
                if(course.getImage()!=null && course.getImage().size() > 0){
                    image = course.getImage().get(0);
                }

                sharePopupWindow.showAtBottom(course.getName(), course.getIntroduce(),
                      image  , ConstantUrl.URL_SHARE_COURSE+course.getId()+"/course");

                break;
            case R.id.layout_course_location:
                CourseStore store = course.getStore();
                if (store != null) {
                    MapActivity.start(this, course.getName(), store.getName(), store.getAddress(),
                            store.getCoordinate()[0] + "", store.getCoordinate()[1] + "");
                }

                break;
            case R.id.ll_apply:
                if (!App.getInstance().isLogin()) {
                    startActivity(new Intent(this, LoginActivity.class));
                    return;
                }

                if (course.isMember_only() && !course.isMember()) {
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
                        AppointDetailCourseAndEventActivity.courseStart(this, course.getId());

                        break;
                    case CourseBeanNew.APPOINTED_NO_PAY:
                        ///跳查看预约
                        AppointDetailCourseAndEventActivity.courseStart(this, course.getId());

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
    public void onGetCourseDetail(CourseBeanNew course) {
        DialogUtils.dismissDialog();
        if (course == null) return;
        this.course = course;
        banner.setData(course.getImage(), null);
        txtCourseName.setText(course.getName());


        if (course.getCoach() != null) {
            txtCoachName.setText(course.getCoach().getName());
            if (!TextUtils.isEmpty(course.getCoach().getIntroduce())) {
                RichText.from(course.getCoach().getIntroduce()).placeHolder(R.drawable.place_holder_logo)
                        .error(R.drawable.place_holder_logo).into(txtCoachDesc);
            }
            GlideLoader.getInstance().displayImage(course.getCoach().getAvatar(), imgCoachAvatar);
        }

        tvPrice.setVisibility(View.VISIBLE);
        txtNormalPrice.setText("￥ " + course.getPrice());
        txtMemberPrice.setText("会员价: ￥" + course.getMember_price());
        txtCourseTime.setText(course.getClass_time());
        txtCourseRoom.setText(course.getStore().getName());
        txtCourseLocation.setText(course.getStore().getAddress());
        webView.setRichText(course.getIntroduce());

        switch (course.getStatus()) {

            case CourseBeanNew.NORMAL:
            case CourseBeanNew.FEW:
                tvPrice.setVisibility(View.VISIBLE);
                tvPrice.setText("￥ " + course.getPrice());
                tvState.setText(R.string.appointment_immediately);
                tvPrice.setTextColor(getResources().getColor(R.color.white));
                tvState.setTextColor(getResources().getColor(R.color.white));
                llApply.setBackgroundColor(getResources().getColor(R.color.main_red));
                llApply.setClickable(true);
                if (course.getSeat() != null && course.getSeat().isNeed()) {
                    tvState.setText(R.string.appointment_choose_seat);
                }

                break;
            case CourseBeanNew.APPOINTED:

                tvPrice.setVisibility(View.GONE);
                tvState.setText(R.string.appointmented_look_detail);
                tvPrice.setTextColor(getResources().getColor(R.color.white));
                tvState.setTextColor(getResources().getColor(R.color.white));
                llApply.setBackgroundColor(getResources().getColor(R.color.main_red));
                llApply.setClickable(true);

                break;
            case CourseBeanNew.APPOINTED_NO_PAY:
                tvPrice.setVisibility(View.GONE);
                tvState.setText(R.string.wait_pay_appointed);
                tvPrice.setTextColor(getResources().getColor(R.color.white));
                tvState.setTextColor(getResources().getColor(R.color.white));
                llApply.setBackgroundColor(getResources().getColor(R.color.main_red));
                llApply.setClickable(true);
                break;
            case CourseBeanNew.QUEUED:
                tvPrice.setVisibility(View.GONE);
                tvState.setText(R.string.queueing_look_detail);
                tvPrice.setTextColor(getResources().getColor(R.color.white));
                tvState.setTextColor(getResources().getColor(R.color.white));
                llApply.setBackgroundColor(getResources().getColor(R.color.main_red));
                llApply.setClickable(true);
                break;

            case CourseBeanNew.QUEUEABLE:
                tvPrice.setVisibility(View.GONE);
                tvState.setText(R.string.appoint_full_queue_immedialtely);
                tvPrice.setTextColor(getResources().getColor(R.color.white));
                tvState.setTextColor(getResources().getColor(R.color.white));
                llApply.setBackgroundColor(getResources().getColor(R.color.main_red));
                llApply.setClickable(true);
                break;
            case CourseBeanNew.FULL:
                tvPrice.setVisibility(View.GONE);
                tvState.setText(R.string.fulled);
                tvPrice.setTextColor(getResources().getColor(R.color.white));
                tvState.setTextColor(getResources().getColor(R.color.white));
                llApply.setBackgroundColor(getResources().getColor(R.color.list_line_color));
                llApply.setClickable(false);
                break;
            case CourseBeanNew.END:
                tvPrice.setVisibility(View.GONE);
                tvState.setText(R.string.ended);
                tvPrice.setTextColor(getResources().getColor(R.color.white));
                tvState.setTextColor(getResources().getColor(R.color.white));
                llApply.setBackgroundColor(getResources().getColor(R.color.list_line_color));
                llApply.setClickable(false);
                break;
            default:
                break;
        }


        if (DateUtils.compareLongTime(course.getReserve_time()) > 0) {
            tvPrice.setVisibility(View.GONE);
            tvState.setText("预约开始时间:" + course.getReserve_time());
            tvState.setTextColor(getResources().getColor(R.color.white));
            llApply.setBackgroundColor(getResources().getColor(R.color.black));
            llApply.setClickable(false);

            Logger.i(TAG,"DateUtils.compareLongTime(course.getReserve_time()) = " + DateUtils.compareLongTime(course.getReserve_time()));
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    coursePresent.getCourseDetail(code);
                }
            },DateUtils.compareLongTime(course.getReserve_time())+2000);
        }

        if (course.isMember_only()) {
            txtNormalPrice.setVisibility(View.GONE);

            tvPrice.setText("￥ " + course.getMember_price());

        }

        if (course.isMember()) {
            tvPrice.setText("￥ " + course.getMember_price());
        }

        if (course.getReservable() == 0) {
            tvPrice.setText("");
            tvState.setText(R.string.do_not_nedd_appointment);
            tvState.setTextColor(getResources().getColor(R.color.white));
            llApply.setBackgroundColor(getResources().getColor(R.color.list_line_color));
            llApply.setClickable(false);
        }


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
