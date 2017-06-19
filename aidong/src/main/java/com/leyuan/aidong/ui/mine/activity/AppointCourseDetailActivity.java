package com.leyuan.aidong.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.AppointmentDetailBean;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.module.pay.AliPay;
import com.leyuan.aidong.module.pay.PayInterface;
import com.leyuan.aidong.module.pay.SimplePayListener;
import com.leyuan.aidong.module.pay.WeiXinPay;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.home.activity.AppointSuccessActivity;
import com.leyuan.aidong.ui.home.activity.CourseDetailActivity;
import com.leyuan.aidong.ui.mvp.presenter.AppointmentPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.AppointmentPresentImpl;
import com.leyuan.aidong.ui.mvp.view.AppointmentDetailActivityView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DateUtils;
import com.leyuan.aidong.utils.DensityUtil;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.ImageRectUtils;
import com.leyuan.aidong.utils.QRCodeUtil;
import com.leyuan.aidong.utils.SystemInfoUtils;
import com.leyuan.aidong.utils.ToastGlobal;
import com.leyuan.aidong.utils.constant.PayType;
import com.leyuan.aidong.widget.CustomNestRadioGroup;
import com.leyuan.aidong.widget.ExtendTextView;
import com.leyuan.aidong.widget.SimpleTitleBar;
import com.leyuan.aidong.widget.SwitcherLayout;

import cn.iwgang.countdownview.CountdownView;

import static com.leyuan.aidong.ui.App.context;
import static com.leyuan.aidong.utils.Constant.PAY_ALI;
import static com.leyuan.aidong.utils.Constant.PAY_WEIXIN;


/**
 * 课程预约详情
 * Created by song on 2016/9/2.
 */
//todo 与活动预约详情界面合成一个界面
public class AppointCourseDetailActivity extends BaseActivity implements AppointmentDetailActivityView,
        View.OnClickListener, CustomNestRadioGroup.OnCheckedChangeListener, CountdownView.OnCountdownEndListener {
    private static final String UN_PAID = "pending";         //待付款
    private static final String UN_JOIN = "purchased";        //待参加
    private static final String JOINED = "signed";        //已参加
    private static final String CLOSE = "canceled";          //已关闭
    private static final String REFUNDING = "refunding";     //退款中
    private static final String REFUNDED = "returned";      //已退款
    private long appointCountdownMill;

    private SimpleTitleBar titleBar;
    private SwitcherLayout switcherLayout;
    private ScrollView scrollView;

    //预约状态信息
    private TextView tvState;
    private TextView tvOrderNo;
    private LinearLayout timerLayout;
    private CountdownView timer;
    private RelativeLayout courseLayout;
    private ImageView courseCover;
    private TextView tvCourseName;
    private TextView tvCourseInfo;
    private RelativeLayout codeLayout;
    private TextView tvCodeNum;
    private ImageView ivCode;

    //预约信息
    private ExtendTextView tvUserName;
    private ExtendTextView tvPhone;
    private ExtendTextView tvVenues;
    private ExtendTextView tvCourseRoom;
    private ExtendTextView tvCourseTime;
    private ExtendTextView tvCourseAddress;

    //订单信息
    private ExtendTextView tvTotalPrice;
    private ExtendTextView couponPrice;
    private ExtendTextView coursePrivilege;
    private ExtendTextView tvAiBi;
    private ExtendTextView tvAiDou;
    private ExtendTextView tvStartTime;
    private ExtendTextView tvPayType;

    //支付方式信息
    private LinearLayout payLayout;
    private CustomNestRadioGroup payGroup;
    private RadioButton rbAliPay;
    private RadioButton rbWeiXinPay;

    //底部预约操作状态及价格信息
    private LinearLayout bottomLayout;
    private TextView tvPrice;
    private TextView tvPayTip;
    private TextView tvPay;
    private TextView tvCancelPay;
    private TextView tvCancelJoin;
    private TextView tvConfirmJoin;
    private TextView tvDelete;

    //Present层对象
    private AppointmentPresent present;
    private AppointmentDetailBean bean;
    private String orderId;
    @PayType
    private String payType;

    private String code;
    private boolean fromDetail = false;

    public static void start(Context context, String orderId) {
        Intent starter = new Intent(context, AppointCourseDetailActivity.class);
        starter.putExtra("orderId", orderId);
        context.startActivity(starter);
    }

    public static void start(Context context, String code, boolean fromDetail) {
        Intent starter = new Intent(context, AppointCourseDetailActivity.class);
        starter.putExtra("code", code);
        starter.putExtra("fromDetail", fromDetail);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_course_detail);
        appointCountdownMill = SystemInfoUtils.getAppointmentCountdown(this) * 60 * 1000;
        present = new AppointmentPresentImpl(this, this);
        if (getIntent() != null) {
            fromDetail = getIntent().getBooleanExtra("fromDetail", false);
            if (fromDetail) {
                code = getIntent().getStringExtra("code");
            } else {
                orderId = getIntent().getStringExtra("orderId");
            }
        }

        initView();
        setListener();
        getCourseDetailData();
    }

    private void initView() {
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        scrollView = (ScrollView) findViewById(R.id.scroll_view);
        switcherLayout = new SwitcherLayout(this, scrollView);
        tvState = (TextView) findViewById(R.id.tv_state);
        tvOrderNo = (TextView) findViewById(R.id.tv_order_num);
        timerLayout = (LinearLayout) findViewById(R.id.ll_timer);
        timer = (CountdownView) findViewById(R.id.timer);
        courseLayout = (RelativeLayout) findViewById(R.id.rl_detail);
        courseCover = (ImageView) findViewById(R.id.dv_goods_cover);
        tvCourseName = (TextView) findViewById(R.id.tv_name);
        tvCourseInfo = (TextView) findViewById(R.id.tv_info);
        codeLayout = (RelativeLayout) findViewById(R.id.rl_qr_code);
        tvCodeNum = (TextView) findViewById(R.id.tv_qr_num);
        ivCode = (ImageView) findViewById(R.id.dv_qr);
        tvUserName = (ExtendTextView) findViewById(R.id.tv_course_user);
        tvPhone = (ExtendTextView) findViewById(R.id.tv_course_phone);
        tvVenues = (ExtendTextView) findViewById(R.id.tv_venues);
        tvCourseRoom = (ExtendTextView) findViewById(R.id.tv_course_room);
        tvCourseTime = (ExtendTextView) findViewById(R.id.tv_time);
        tvCourseAddress = (ExtendTextView) findViewById(R.id.tv_address);
        tvTotalPrice = (ExtendTextView) findViewById(R.id.tv_total_price);
        couponPrice = (ExtendTextView) findViewById(R.id.coupon_price);
        coursePrivilege = (ExtendTextView) findViewById(R.id.course_privilege);
        tvAiBi = (ExtendTextView) findViewById(R.id.tv_aibi);
        tvAiDou = (ExtendTextView) findViewById(R.id.tv_aidou);
        tvStartTime = (ExtendTextView) findViewById(R.id.tv_start_time);
        tvPayType = (ExtendTextView) findViewById(R.id.tv_pay_type);
        payLayout = (LinearLayout) findViewById(R.id.ll_pay);
        payGroup = (CustomNestRadioGroup) findViewById(R.id.radio_group);
        rbAliPay = (RadioButton) findViewById(R.id.cb_alipay);
        rbWeiXinPay = (RadioButton) findViewById(R.id.cb_weixin);
        bottomLayout = (LinearLayout) findViewById(R.id.ll_bottom);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvPayTip = (TextView) findViewById(R.id.tv_pay_tip);
        tvPay = (TextView) findViewById(R.id.tv_pay);
        tvCancelPay = (TextView) findViewById(R.id.tv_cancel_pay);
        tvCancelJoin = (TextView) findViewById(R.id.tv_cancel_join);
        tvConfirmJoin = (TextView) findViewById(R.id.tv_confirm);
        tvDelete = (TextView) findViewById(R.id.tv_delete);

    }

    private void setListener() {
        titleBar.setOnClickListener(this);
        payGroup.setOnCheckedChangeListener(this);
        tvPay.setOnClickListener(this);
        tvCancelPay.setOnClickListener(this);
        tvCancelJoin.setOnClickListener(this);
        tvConfirmJoin.setOnClickListener(this);
        tvDelete.setOnClickListener(this);
        courseLayout.setOnClickListener(this);
        timer.setOnCountdownEndListener(this);
        ivCode.setOnClickListener(this);
    }

    private void getCourseDetailData(){
        if (fromDetail) {
            present.getCourseAppointDetail(switcherLayout, code);
        } else {
            present.getAppointmentDetail(switcherLayout, orderId);
        }
    }

    @Override
    public void setAppointmentDetail(AppointmentDetailBean bean) {
        this.bean = bean;
        bottomLayout.setVisibility(View.VISIBLE);
        payType = bean.getPay().getPayType();
        if (PAY_ALI.equals(payType)) {
            rbAliPay.setChecked(true);
        } else {
            rbWeiXinPay.setChecked(true);
        }

        //与订单状态无关: 订单信息
        GlideLoader.getInstance().displayImage(bean.getCover(), courseCover);
        tvCourseName.setText(bean.getName());
        tvCourseInfo.setText(bean.getSubName());
        tvUserName.setRightContent(bean.getAppoint().getName());
        tvPhone.setRightContent(bean.getAppoint().getMobile());
        tvVenues.setRightContent(bean.getAppoint().getGym());
        tvCourseRoom.setRightContent(bean.getAppoint().getClassroom());
        tvCourseTime.setRightContent(bean.getAppoint().getClassTime());
        tvCourseTime.setRightContent(bean.getAppoint().getClassTime());
        tvCourseAddress.setRightContent(bean.getAppoint().getAddress());
        tvPrice.setText(String.format(getString(R.string.rmb_price_double),
                FormatUtil.parseDouble(bean.getPay().getPayAmount())));
        couponPrice.setRightContent(String.format(getString(R.string.rmb_minus_price_double),
                FormatUtil.parseDouble(bean.getPay().getCoupon())));
        tvAiBi.setRightContent(String.format(getString(R.string.rmb_minus_price_double),
                FormatUtil.parseDouble(bean.getPay().getCoin())));
        tvAiDou.setRightContent(String.format(getString(R.string.rmb_minus_price_double),
                FormatUtil.parseDouble(bean.getPay().getIntegral())));
        tvTotalPrice.setRightContent(String.format(getString(R.string.rmb_price_double),
                FormatUtil.parseDouble(bean.getPay().getTotal())));
        tvStartTime.setRightContent(bean.getPay().getCreatedAt());
        tvPayType.setRightContent(PAY_ALI.equals(bean.getPay().getPayType()) ? "支付宝" : "微信");

        //todo 通过组合控件控制底部的按钮状态
        switch (bean.getAppoint().getStatus()) {
            case UN_PAID:           //待付款
                tvState.setText(context.getString(R.string.un_paid));
                timerLayout.setVisibility(View.VISIBLE);
                timer.start(DateUtils.getCountdown(bean.getPay().getCreatedAt(), appointCountdownMill));
                tvOrderNo.setVisibility(View.VISIBLE);
                tvOrderNo.setText(String.format(getString(R.string.appoint_no), bean.getId()));
                tvCancelPay.setVisibility(View.VISIBLE);
                tvPay.setVisibility(View.VISIBLE);
                tvDelete.setVisibility(View.GONE);
                tvConfirmJoin.setVisibility(View.GONE);
                tvCancelJoin.setVisibility(View.GONE);
                codeLayout.setVisibility(View.GONE);
                payLayout.setVisibility(View.VISIBLE);
                tvPayType.setVisibility(View.GONE);
                break;
            case UN_JOIN:           //待参加
                tvState.setText(context.getString(R.string.appointment_un_joined));
                tvOrderNo.setText(String.format(getString(R.string.appoint_no), bean.getId()));
                tvOrderNo.setVisibility(View.VISIBLE);
                timerLayout.setVisibility(View.GONE);
                tvCancelJoin.setVisibility(FormatUtil.parseDouble(bean.getPay().getTotal()) == 0d
                        ? View.VISIBLE : View.GONE);
                tvConfirmJoin.setVisibility(View.VISIBLE);
                tvPay.setVisibility(View.GONE);
                tvCancelPay.setVisibility(View.GONE);
                tvDelete.setVisibility(View.GONE);
                codeLayout.setVisibility(View.VISIBLE);
                tvCodeNum.setText(bean.getId());
                tvCodeNum.setTextColor(Color.parseColor("#000000"));
                ivCode.setImageBitmap(QRCodeUtil.createBarcode(this, 0xFF000000, bean.getId(),
                        DensityUtil.dp2px(this, 294), DensityUtil.dp2px(this, 73), false));
                payLayout.setVisibility(View.GONE);
                tvPayType.setVisibility(View.VISIBLE);
                break;
            case JOINED:            //已参加
                tvState.setText(context.getString(R.string.appointment_joined));
                tvOrderNo.setText(String.format(getString(R.string.appoint_no), bean.getId()));
                tvOrderNo.setVisibility(View.VISIBLE);
                timerLayout.setVisibility(View.GONE);
                tvDelete.setVisibility(View.VISIBLE);
                tvPay.setVisibility(View.GONE);
                tvCancelPay.setVisibility(View.GONE);
                tvCancelJoin.setVisibility(View.GONE);
                tvConfirmJoin.setVisibility(View.GONE);
                codeLayout.setVisibility(View.VISIBLE);
                tvCodeNum.setText(bean.getId());
                tvCodeNum.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                tvCodeNum.setTextColor(Color.parseColor("#ebebeb"));
                ivCode.setImageBitmap(QRCodeUtil.createBarcode(this, 0xFFebebeb, bean.getId(),
                        DensityUtil.dp2px(this, 294), DensityUtil.dp2px(this, 73), false));
                payLayout.setVisibility(View.GONE);
                tvPayType.setVisibility(View.VISIBLE);
                break;
            case CLOSE:             //已关闭
                tvState.setText(context.getString(R.string.order_close));
                tvOrderNo.setText(String.format(getString(R.string.appoint_no), bean.getId()));
                tvOrderNo.setVisibility(View.VISIBLE);
                timerLayout.setVisibility(View.GONE);
                tvDelete.setVisibility(View.VISIBLE);
                tvPay.setVisibility(View.GONE);
                tvCancelPay.setVisibility(View.GONE);
                tvCancelJoin.setVisibility(View.GONE);
                tvConfirmJoin.setVisibility(View.GONE);
                codeLayout.setVisibility(View.GONE);
                payLayout.setVisibility(View.GONE);
                tvPayType.setVisibility(View.GONE);
                break;
            case REFUNDING:           //退款中
                tvState.setText(context.getString(R.string.order_refunding));
                tvOrderNo.setText(String.format(getString(R.string.appoint_no), bean.getId()));
                tvOrderNo.setVisibility(View.VISIBLE);
                timerLayout.setVisibility(View.GONE);
                tvDelete.setVisibility(View.VISIBLE);
                tvPay.setVisibility(View.GONE);
                tvCancelPay.setVisibility(View.GONE);
                tvCancelJoin.setVisibility(View.GONE);
                tvConfirmJoin.setVisibility(View.GONE);
                codeLayout.setVisibility(View.VISIBLE);
                tvCodeNum.setText(bean.getId());
                tvCodeNum.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                tvCodeNum.setTextColor(Color.parseColor("#ebebeb"));
                ivCode.setImageBitmap(QRCodeUtil.createBarcode(this, 0xFFebebeb, bean.getId(),
                        DensityUtil.dp2px(this, 294), DensityUtil.dp2px(this, 73), false));
                payLayout.setVisibility(View.GONE);
                tvPayType.setVisibility(View.VISIBLE);
                break;
            case REFUNDED:             //已退款
                tvState.setText(context.getString(R.string.order_refunded));
                tvOrderNo.setText(String.format(getString(R.string.appoint_no), bean.getId()));
                tvOrderNo.setVisibility(View.VISIBLE);
                timerLayout.setVisibility(View.GONE);
                tvDelete.setVisibility(View.VISIBLE);
                tvPay.setVisibility(View.GONE);
                tvCancelPay.setVisibility(View.GONE);
                tvCancelJoin.setVisibility(View.GONE);
                tvConfirmJoin.setVisibility(View.GONE);
                codeLayout.setVisibility(View.VISIBLE);
                tvCodeNum.setText(bean.getId());
                tvCodeNum.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                tvCodeNum.setTextColor(Color.parseColor("#ebebeb"));
                ivCode.setImageBitmap(QRCodeUtil.createBarcode(this, 0xFFebebeb, bean.getId(),
                        DensityUtil.dp2px(this, 294), DensityUtil.dp2px(this, 73), false));
                payLayout.setVisibility(View.GONE);
                tvPayType.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_pay:
                PayInterface payInterface = PAY_ALI.equals(payType) ?
                        new AliPay(this, payListener) : new WeiXinPay(this, payListener);
                payInterface.payOrder(bean.getPay().getpayOption());
                break;
            case R.id.tv_cancel_pay:
                present.cancelAppoint(bean.getId());
                break;
            case R.id.tv_cancel_join:
                if (DateUtils.started(bean.getAppoint().getClassTime())) {
                    ToastGlobal.showLong("课程已开始，无法取消");
                } else {
                    present.cancelAppoint(bean.getId());
                }
                break;
            case R.id.tv_confirm:
                if (DateUtils.bigThanOneHour(bean.getAppoint().getClassTime())) {
                    ToastGlobal.showLong("未到课程时间，请稍后确认");
                } else {
                    present.confirmAppoint(bean.getId());
                }
                break;
            case R.id.tv_delete:
                present.deleteAppoint(bean.getId());
                break;
            case R.id.rl_detail:
                CourseDetailActivity.start(this, bean.getLinkId());
                break;
            case R.id.dv_qr:
                BarcodeActivity.start(this, bean.getId(),ImageRectUtils.getDrawableBoundsInView(ivCode));
                break;
            default:
                break;
        }
    }

    private PayInterface.PayListener payListener = new SimplePayListener(this) {
        @Override
        public void onSuccess(String code, Object object) {
            ToastGlobal.showLong("支付成功");
            AppointSuccessActivity.start(AppointCourseDetailActivity.this, null, false, present.getShareInfo());
        }

        @Override
        public void onFree() {
            ToastGlobal.showLong("预约成功");
            AppointSuccessActivity.start(AppointCourseDetailActivity.this, bean.getAppoint().getClassTime(), false, present.getShareInfo());
        }
    };

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
    public void cancelAppointmentResult(BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
            getCourseDetailData();
            ToastGlobal.showLong("取消成功");
        } else {
            ToastGlobal.showLong(baseBean.getMessage());
        }
    }

    @Override
    public void confirmAppointmentResult(BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
            getCourseDetailData();
            ToastGlobal.showLong("确认成功");
        } else {
            ToastGlobal.showLong(baseBean.getMessage());
        }
    }

    @Override
    public void deleteAppointmentResult(BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
            finish();
            ToastGlobal.showLong("删除成功");
        } else {
            ToastGlobal.showLong(baseBean.getMessage());
        }
    }

    @Override
    public void onEnd(CountdownView cv) {
        bean.getPay().setStatus(CLOSE);
        setAppointmentDetail(bean);
    }
}
