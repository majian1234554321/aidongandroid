package com.leyuan.aidong.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.PayOptionBean;
import com.leyuan.aidong.entity.course.CouponCourseShareBean;
import com.leyuan.aidong.entity.course.CourseAppointBean;
import com.leyuan.aidong.entity.course.CourseAppointResult;
import com.leyuan.aidong.entity.course.CourseBeanNew;
import com.leyuan.aidong.entity.course.CourseStore;
import com.leyuan.aidong.module.pay.PayInterface;
import com.leyuan.aidong.module.pay.PayUtils;
import com.leyuan.aidong.module.pay.SimplePayListener;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.home.activity.AppointCourseSuccessActivity;
import com.leyuan.aidong.ui.home.activity.CourseDetailNewActivity;
import com.leyuan.aidong.ui.home.activity.MapActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.AppointmentCoursePresentImpl;
import com.leyuan.aidong.ui.mvp.view.AppointmentCourseDetailView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DateUtils;
import com.leyuan.aidong.utils.DensityUtil;
import com.leyuan.aidong.utils.DialogUtils;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.ImageRectUtils;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.QRCodeUtil;
import com.leyuan.aidong.utils.SystemInfoUtils;
import com.leyuan.aidong.utils.TelephoneManager;
import com.leyuan.aidong.utils.ToastGlobal;
import com.leyuan.aidong.widget.CustomNestRadioGroup;
import com.leyuan.aidong.widget.SimpleTitleBar;
import com.leyuan.aidong.widget.richtext.RichWebView;

import cn.iwgang.countdownview.CountdownView;

import static com.leyuan.aidong.R.id.ll_timer;
import static com.leyuan.aidong.utils.Constant.PAY_ALI;
import static com.leyuan.aidong.utils.Constant.PAY_WEIXIN;

/**
 * Created by user on 2017/11/2.
 */

public class AppointDetailCourseNewActivity extends BaseActivity implements AppointmentCourseDetailView, View.OnClickListener, CustomNestRadioGroup.OnCheckedChangeListener {

    private static final java.lang.String TAG = "AppointDetailCourseAndEventActivity";
    private SimpleTitleBar titleBar;
    private ScrollView scrollView;
    private TextView tvState;
    private LinearLayout llTimer;
    private CountdownView timer;
    private TextView tvOrderNum;
    private RelativeLayout rlQrCode;
    private TextView tvQrNum;
    private ImageView dvQr;
    private RichWebView webView;
    private LinearLayout llBottom;
    private TextView tvPriceTip;
    private TextView tvPrice;
    private TextView tvCancelJoin;
    private TextView tv_cancel_appoint;
    private TextView tvConfirm;
    private TextView tvDelete;
    private TextView tvPay;

    private ImageView imgCourse;
    private TextView txtCourseName;
    private TextView txtCoachName;
    private TextView txtCourseTime;

    private TextView txtRoomName;
    private TextView txtCourseLocation;

    String appointId, type;
    private AppointmentCoursePresentImpl appointmentCourseDetailPresenter;
    private String courseId;
    private long appointCountdownMill;
    private CustomNestRadioGroup radioGroup;
    private RadioButton cbAlipay;
    private RadioButton cbWeixin;
    private String payType = PAY_ALI;
    private CourseAppointBean appointBean;
    private PayOptionBean payOptionBean;
    private LinearLayout layout_pay;
    private CourseBeanNew course;
    private CouponCourseShareBean courseShareBean;


    public static void appointStart(Context context, String appointId) {
        Intent intent = new Intent(context, AppointDetailCourseNewActivity.class);
        intent.putExtra("appointId", appointId);
        intent.putExtra("type", "appoint");
        context.startActivity(intent);
    }

    public static void courseStart(Context context, String courseId) {
        Intent intent = new Intent(context, AppointDetailCourseNewActivity.class);
        intent.putExtra("courseId", courseId);
        intent.putExtra("type", "course");
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appointId = getIntent().getStringExtra("appointId");
        courseId = getIntent().getStringExtra("courseId");
        type = getIntent().getStringExtra("type");

        setContentView(R.layout.activity_appoint_detail_course_and_event);
        initView();
        initData();

        appointmentCourseDetailPresenter = new AppointmentCoursePresentImpl(this, this);
        DialogUtils.showDialog(this,"",false);
        if ("appoint".equals(type)) {
            appointmentCourseDetailPresenter.getCourseAppointDetail(appointId);
        } else {
            appointmentCourseDetailPresenter.checkCourseAppoint(courseId);
        }

    }

    private void initView() {
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        scrollView = (ScrollView) findViewById(R.id.scroll_view);
        tvState = (TextView) findViewById(R.id.tv_state);
        llTimer = (LinearLayout) findViewById(ll_timer);
        timer = (CountdownView) findViewById(R.id.timer);
        tvOrderNum = (TextView) findViewById(R.id.tv_order_num);
        rlQrCode = (RelativeLayout) findViewById(R.id.rl_qr_code);
        tvQrNum = (TextView) findViewById(R.id.tv_qr_num);
        dvQr = (ImageView) findViewById(R.id.dv_qr);
        webView = (RichWebView) findViewById(R.id.web_view);
        llBottom = (LinearLayout) findViewById(R.id.ll_bottom);
        tvPriceTip = (TextView) findViewById(R.id.tv_price_tip);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvCancelJoin = (TextView) findViewById(R.id.tv_cancel_join);
        tv_cancel_appoint = (TextView) findViewById(R.id.tv_cancel_appoint);
        tvConfirm = (TextView) findViewById(R.id.tv_confirm);
        tvDelete = (TextView) findViewById(R.id.tv_delete);
        tvPay = (TextView) findViewById(R.id.tv_pay);
        imgCourse = (ImageView) findViewById(R.id.img_course);
        txtCourseName = (TextView) findViewById(R.id.txt_course_name);
        txtCoachName = (TextView) findViewById(R.id.txt_coach_name);
        txtCourseTime = (TextView) findViewById(R.id.txt_course_time);
        txtRoomName = (TextView) findViewById(R.id.txt_room_name);
        txtCourseLocation = (TextView) findViewById(R.id.txt_course_location);

        layout_pay = (LinearLayout) findViewById(R.id.layout_pay);
        radioGroup = (CustomNestRadioGroup) findViewById(R.id.radio_group);
        cbAlipay = (RadioButton) findViewById(R.id.cb_alipay);
        cbWeixin = (RadioButton) findViewById(R.id.cb_weixin);

    }


    private void initData() {

        radioGroup.setOnCheckedChangeListener(this);
        appointCountdownMill = SystemInfoUtils.getAppointmentCountdown(this) * 60 * 1000;
        findViewById(R.id.img_telephone).setOnClickListener(this);
        findViewById(R.id.layout_course_coach).setOnClickListener(this);
        findViewById(R.id.layout_course_location).setOnClickListener(this);
        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvCancelJoin.setOnClickListener(this);
        tv_cancel_appoint.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        tvDelete.setOnClickListener(this);
        tvPay.setOnClickListener(this);
        dvQr.setOnClickListener(this);
    }

    @Override
    public void onGetAppointDetailResult(CourseAppointResult appointResult) {
        DialogUtils.dismissDialog();
        if (appointResult == null) return;
        this.appointBean = appointResult.getAppointment();
        this.course = appointBean.getTimetable();
        payOptionBean = appointResult.getPayment();
        this.courseShareBean = appointResult.getShare();
        if (appointBean == null) {
            //show Empty Layout
            return;
        }

        GlideLoader.getInstance().displayImage(appointBean.getTimetable().getCover(), imgCourse);
        tvOrderNum.setText("预约码: " + appointBean.getId());
        txtCourseName.setText(appointBean.getTimetable().getName());
        txtCoachName.setText(appointBean.getTimetable().getCoach().getName());

        txtCourseTime.setText(appointBean.getTimetable().getClass_time());
        webView.setRichText(appointBean.getIntroduce());
        String seat = "";
        if (!TextUtils.isEmpty(appointBean.getSeat())) {
            seat = " (" + appointBean.getSeat() + ")";
        }

        txtRoomName.setText(appointBean.getTimetable().getStore().getName()+"-" +appointBean.getTimetable().getStore().getRoom() + seat);
        txtCourseLocation.setText(appointBean.getTimetable().getStore().getAddress());
        tvPrice.setText(appointBean.getPay_amount());
        llTimer.setVisibility(View.GONE);

        tvQrNum.setText(appointBean.getId());
        tvQrNum.setTextColor(Color.BLACK);
        dvQr.setImageBitmap(QRCodeUtil.createBarcode(this, Color.BLACK, appointBean.getId(),
                DensityUtil.dp2px(this, 294), DensityUtil.dp2px(this, 73), false));
        tv_cancel_appoint.setText("取消预约");

        switch (appointBean.getCourseFinalStatus()) {

            case CourseAppointBean.pending:
                rlQrCode.setVisibility(View.VISIBLE);
                llTimer.setVisibility(View.VISIBLE);
                timer.start(DateUtils.getCountdown(appointBean.getCreated_at(), appointCountdownMill));
                timer.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
                    @Override
                    public void onEnd(CountdownView cv) {
                        appointmentCourseDetailPresenter.getCourseAppointDetail(appointBean.getId());
                    }
                });

                tvOrderNum.setVisibility(View.GONE);
                tvState.setText(getString(R.string.un_paid));
                tv_cancel_appoint.setVisibility(View.VISIBLE);
                tvPay.setVisibility(View.VISIBLE);
                layout_pay.setVisibility(View.VISIBLE);

                break;

            case CourseAppointBean.paid:
            case CourseAppointBean.appointed:
                rlQrCode.setVisibility(View.VISIBLE);
                tvState.setText(getString(R.string.with_sign_in));
                tv_cancel_appoint.setVisibility(View.VISIBLE);

                Logger.i(TAG, FormatUtil.parseDouble(appointBean.getPay_amount()) + "");
                if (FormatUtil.parseDouble(appointBean.getTotal()) > 0) {
                    tv_cancel_appoint.setText("取消预约-申请退款");
                } else {
                    tv_cancel_appoint.setText("取消预约");
                }
                break;

            case CourseAppointBean.canceled:
                rlQrCode.setVisibility(View.GONE);
                tvState.setText(getString(R.string.canceled));

                tv_cancel_appoint.setVisibility(View.GONE);
                llTimer.setVisibility(View.GONE);
                layout_pay.setVisibility(View.GONE);
                tvPay.setVisibility(View.GONE);

                tvDelete.setVisibility(View.VISIBLE);

                break;

            case CourseAppointBean.absent:
                tvQrNum.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                tvQrNum.setTextColor(getResources().getColor(R.color.txt_appoint_num_gray));
                dvQr.setImageBitmap(QRCodeUtil.createBarcode(this, 0xFFebebeb, appointBean.getId(),
                        DensityUtil.dp2px(this, 294), DensityUtil.dp2px(this, 73), false));

                rlQrCode.setVisibility(View.VISIBLE);
                tvState.setText(getString(R.string.absent));
                tvDelete.setVisibility(View.VISIBLE);

                break;
            case CourseAppointBean.signed:
                tvQrNum.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                tvQrNum.setTextColor(getResources().getColor(R.color.txt_appoint_num_gray));
                dvQr.setImageBitmap(QRCodeUtil.createBarcode(this, 0xFFebebeb, appointBean.getId(),
                        DensityUtil.dp2px(this, 294), DensityUtil.dp2px(this, 73), false));

                rlQrCode.setVisibility(View.VISIBLE);
                tvState.setText(getString(R.string.signed));
                tvDelete.setVisibility(View.VISIBLE);
                break;
            case CourseAppointBean.suspended:
                rlQrCode.setVisibility(View.GONE);
                tvState.setText(getString(R.string.suspended));
                tvDelete.setVisibility(View.VISIBLE);
                break;
//            case CourseAppointBean.paid:
//                rlQrCode.setVisibility(View.VISIBLE);
//                tvState.setText(getString(R.string.with_sign_in));
//                tv_cancel_appoint.setVisibility(View.VISIBLE);
//                break;
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dv_qr:
                if (!TextUtils.equals(appointBean.getCourseFinalStatus(), CourseAppointBean.signed)
                        && !TextUtils.equals(appointBean.getCourseFinalStatus(), CourseAppointBean.absent)) {
                    BarcodeActivity.start(this, appointBean.getId(), ImageRectUtils.getDrawableBoundsInView(dvQr));
                }

                break;
            case R.id.layout_course_coach:
                if (course != null) {
                    CourseDetailNewActivity.start(this, course.getId());
                }
//                    UserInfoActivity.start(this, course.getCoach().getContact());

                break;
            case R.id.layout_course_location:
                if (course != null && course.getStore() != null) {
                    CourseStore store = course.getStore();
                    MapActivity.start(this, store.getName(), store.getName(), store.getAddress(),
                            store.getCoordinate()[0] + "", store.getCoordinate()[1] + "");
                }


                break;

            case R.id.img_telephone:
                if (course != null && course.getStore() != null) {
                    TelephoneManager.callImmediate(this, course.getStore().getTel());
                }

                break;
            case R.id.tv_cancel_join:

                break;
            case R.id.tv_cancel_appoint:
                DialogUtils.showDialog(this, "", false);
                appointmentCourseDetailPresenter.cancelCourseAppoint(appointBean.getId());

                break;
            case R.id.tv_confirm:

                break;
            case R.id.tv_delete:
                DialogUtils.showDialog(this, "", false);
                appointmentCourseDetailPresenter.deleteCourseAppoint(appointBean.getId());

                break;
            case R.id.tv_pay:

                PayUtils.pay(this, payType, payOptionBean, payListener);

                break;
        }

    }

    private PayInterface.PayListener payListener = new SimplePayListener(this) {

        @Override
        public void onSuccess(String code, Object object) {

            LocalBroadcastManager.getInstance(AppointDetailCourseNewActivity.this).sendBroadcast(new Intent(Constant.BROADCAST_ACTION_COURSE_PAY_SUCCESS));

            AppointCourseSuccessActivity.start(AppointDetailCourseNewActivity.this, appointBean.getTimetable().getClass_time(), true, courseShareBean);
            Toast.makeText(AppointDetailCourseNewActivity.this, "支付成功", Toast.LENGTH_LONG).show();
            finish();
        }

        @Override
        public void onFail(String code, Object object) {
            super.onFail(code, object);

            LocalBroadcastManager.getInstance(AppointDetailCourseNewActivity.this).sendBroadcast(new Intent(Constant.BROADCAST_ACTION_COURSE_PAY_SUCCESS));

            Toast.makeText(AppointDetailCourseNewActivity.this, "支付失败", Toast.LENGTH_LONG).show();
//            startActivity(new Intent(AppointDetailCourseAndEventActivity.this, AppointmentMineActivityNew.class));
            finish();
        }

        @Override
        public void onFree() {
            LocalBroadcastManager.getInstance(AppointDetailCourseNewActivity.this).sendBroadcast(new Intent(Constant.BROADCAST_ACTION_COURSE_PAY_SUCCESS));

            AppointCourseSuccessActivity.start(AppointDetailCourseNewActivity.this, appointBean.getTimetable().getClass_time(), true, courseShareBean);
            Toast.makeText(AppointDetailCourseNewActivity.this, "预约成功", Toast.LENGTH_LONG).show();
            finish();
        }
    };


    @Override
    public void oncancelCourseAppointResult(BaseBean baseBean) {
        DialogUtils.dismissDialog();

        if (baseBean != null && baseBean.getStatus() == 1) {
            appointmentCourseDetailPresenter.getCourseAppointDetail(appointBean.getId());
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constant.BROADCAST_ACTION_COURSE_APPOINT_CANCEL));
        } else {
            ToastGlobal.showShortConsecutive(baseBean.getMessage());
        }

    }

    @Override
    public void onDeleteCourseAppoint(boolean courseAppointResult) {
        DialogUtils.dismissDialog();
        if (courseAppointResult) {
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constant.BROADCAST_ACTION_COURSE_APPOINT_DELETE));
            finish();
        } else {
            ToastGlobal.showShortConsecutive("删除失败");
        }
    }

    @Override
    public void onCheckedChanged(CustomNestRadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.cb_alipay:
                payType = PAY_ALI;
                break;
            case R.id.cb_weixin:
                payType = PAY_WEIXIN;
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DialogUtils.releaseDialog();
    }
}
