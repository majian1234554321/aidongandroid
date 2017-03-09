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
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.AppointmentDetailBean;
import com.leyuan.aidong.module.pay.AliPay;
import com.leyuan.aidong.module.pay.PayInterface;
import com.leyuan.aidong.module.pay.SimplePayListener;
import com.leyuan.aidong.module.pay.WeiXinPay;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.home.activity.AppointSuccessActivity;
import com.leyuan.aidong.ui.home.activity.CourseDetailActivity;
import com.leyuan.aidong.ui.mvp.presenter.AppointmentPresent;
import com.leyuan.aidong.ui.mvp.presenter.CoursePresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.AppointmentPresentImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.CoursePresentImpl;
import com.leyuan.aidong.ui.mvp.view.AppointmentDetailActivityView;
import com.leyuan.aidong.utils.DensityUtil;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.QRCodeUtil;
import com.leyuan.aidong.utils.constant.PayType;
import com.leyuan.aidong.widget.CustomNestRadioGroup;
import com.leyuan.aidong.widget.ExtendTextView;
import com.leyuan.aidong.widget.SimpleTitleBar;
import com.leyuan.aidong.widget.SwitcherLayout;

import cn.iwgang.countdownview.CountdownView;

import static com.leyuan.aidong.ui.App.context;


/**
 * 课程预约详情
 * Created by song on 2016/9/2.
 */
public class AppointCourseDetailActivity extends BaseActivity implements AppointmentDetailActivityView,
        View.OnClickListener, CustomNestRadioGroup.OnCheckedChangeListener {
    private static final String UN_PAID = "pending";         //待付款
    private static final String UN_JOIN= "purchased";        //待参加
    private static final String JOINED = "signed";           //已参加
    private static final String CLOSE = "canceled";          //已关闭
    private static final String REFUNDING = "4";             //退款中
    private static final String REFUNDED = "5";              //已退款

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
    private ExtendTextView tvAibi;
    private ExtendTextView tvAidou;
    private ExtendTextView tvStartTime;
    private ExtendTextView tvPayTime;
    private ExtendTextView tvPayType;

    //支付方式信息
    private LinearLayout payLayout;
    private CustomNestRadioGroup payGroup;
    private RadioButton rbAliPay;
    private RadioButton rbWeiXinPay;

    //底部预约操作状态及价格信息
    private TextView tvGoodsCount;
    private TextView tvPrice;
    private TextView tvPayTip;
    private TextView tvCancel;
    private TextView tvPay;
    private TextView tvExpress;
    private TextView tvConfirm;
    private TextView tvDelete;
    private TextView tvAgainBuy;

    //Present层对象
    private AppointmentPresent appointmentPresent;
    private CoursePresent coursePresent;
    private AppointmentDetailBean detailBean;

    private String orderId;
    private String payType;

    public static void start(Context context,String orderId) {
        Intent starter = new Intent(context, AppointCourseDetailActivity.class);
        starter.putExtra("orderId",orderId);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_course_detail);
        appointmentPresent = new AppointmentPresentImpl(this,this);
        coursePresent = new CoursePresentImpl(this);
        if(getIntent() != null){
            orderId = getIntent().getStringExtra("orderId");
        }

        initView();
        setListener();
        appointmentPresent.getAppointmentDetail(switcherLayout,orderId);
    }

    private void initView() {
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        scrollView = (ScrollView) findViewById(R.id.scroll_view);
        switcherLayout = new SwitcherLayout(this,scrollView);

        tvState = (TextView) findViewById(R.id.tv_state);
        tvOrderNo = (TextView) findViewById(R.id.tv_order_num);
        timerLayout = (LinearLayout) findViewById(R.id.ll_timer);
        timer = (CountdownView) findViewById(R.id.timer);
        courseLayout = (RelativeLayout) findViewById(R.id.rl_detail);
        courseCover = (ImageView) findViewById(R.id.dv_goods_cover);
        tvCourseName = (TextView) findViewById(R.id.tv_name);
        tvCourseInfo = (TextView) findViewById(R.id.tv_info);
        codeLayout = (RelativeLayout) findViewById(R.id.rl_qr_code);
        tvCodeNum = (TextView) findViewById(R.id.tv_num);
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
        tvAibi = (ExtendTextView) findViewById(R.id.tv_aibi);
        tvAidou = (ExtendTextView) findViewById(R.id.tv_aidou);
        tvStartTime = (ExtendTextView) findViewById(R.id.tv_start_time);
        tvPayTime = (ExtendTextView) findViewById(R.id.tv_pay_time);
        tvPayType = (ExtendTextView) findViewById(R.id.tv_pay_type);

        payLayout = (LinearLayout) findViewById(R.id.ll_pay);
        payGroup = (CustomNestRadioGroup) findViewById(R.id.radio_group);
        rbAliPay = (RadioButton) findViewById(R.id.cb_alipay);
        rbWeiXinPay = (RadioButton) findViewById(R.id.cb_weixin);

        tvGoodsCount = (TextView) findViewById(R.id.tv_goods_count);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvPayTip = (TextView) findViewById(R.id.tv_pay_tip);
        tvCancel = (TextView) findViewById(R.id.tv_cancel_join);
        tvPay = (TextView) findViewById(R.id.tv_pay);
        tvExpress = (TextView) findViewById(R.id.tv_express);
        tvConfirm = (TextView) findViewById(R.id.tv_confirm);
        tvDelete = (TextView) findViewById(R.id.tv_delete);
        tvAgainBuy = (TextView) findViewById(R.id.tv_again_buy);
    }

    private void setListener(){
        titleBar.setOnClickListener(this);
        payGroup.setOnCheckedChangeListener(this);
        tvPay.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        tvDelete.setOnClickListener(this);
        tvAgainBuy.setOnClickListener(this);
        tvExpress.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        courseLayout.setOnClickListener(this);
    }

    @Override
    public void setAppointmentDetail(AppointmentDetailBean bean) {
        detailBean = bean;
        payType = bean.getPay().getPayType();
        if(PayType.ALI.equals(payType)){
            rbAliPay.setChecked(true);
        }else {
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
        tvCourseAddress.setRightContent(bean.getAppoint().getAddress());
        tvTotalPrice.setRightContent(String.format(getString(R.string.rmb_price_double),
                FormatUtil.parseDouble(bean.getPay().getTotal())));
        tvStartTime.setRightContent(bean.getPay().getCreatedAt());

        //与订单状态有关: 预约状态信息 课程预约信息/活动预约信息 支付方式信息 底部预约操作状态及价格信息
        switch (bean.getPay().getStatus()) {
            case UN_PAID:           //待付款
                tvState.setText(context.getString(R.string.un_paid));
                //timer.start(Long.parseLong(bean.getPayInfo().getLimitTime()) * 1000);
                timerLayout.setVisibility(View.VISIBLE);
                tvOrderNo.setVisibility(View.GONE);
                codeLayout.setVisibility(View.GONE);
                tvCancel.setVisibility(View.VISIBLE);
                tvPay.setVisibility(View.VISIBLE);
                tvDelete.setVisibility(View.GONE);
                tvConfirm.setVisibility(View.GONE);
                payLayout.setVisibility(View.VISIBLE);
                break;
            case UN_JOIN:           //待参加
                tvState.setText(context.getString(R.string.appointment_un_joined));
                tvOrderNo.setText(String.format(getString(R.string.order_no),bean.getId()));
                tvOrderNo.setVisibility(View.VISIBLE);
                timerLayout.setVisibility(View.GONE);
                tvCancel.setVisibility(FormatUtil.parseDouble(bean.getPay().getPayAmount()) == 0 ?
                        View.VISIBLE : View.GONE);
                tvConfirm.setVisibility(View.VISIBLE);
                tvPay.setVisibility(View.GONE);
                tvDelete.setVisibility(View.GONE);
                codeLayout.setVisibility(View.VISIBLE);
                payLayout.setVisibility(View.GONE);
                tvCodeNum.setTextColor(Color.parseColor("#000000"));
                ivCode.setImageBitmap(QRCodeUtil.createBarcode(this,0xFF000000,bean.getId(),
                        DensityUtil.dp2px(this,294),DensityUtil.dp2px(this,73),false));
                break;
            case JOINED:            //已参加
                tvState.setText(context.getString(R.string.appointment_joined));
                tvOrderNo.setText(String.format(getString(R.string.order_no),bean.getId()));
                tvOrderNo.setVisibility(View.VISIBLE);
                timerLayout.setVisibility(View.GONE);
                tvDelete.setVisibility(View.VISIBLE);
                tvPay.setVisibility(View.GONE);
                tvCancel.setVisibility(View.GONE);
                tvConfirm.setVisibility(View.GONE);
                codeLayout.setVisibility(View.VISIBLE);
                payLayout.setVisibility(View.GONE);
                tvCodeNum.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG );
                tvCodeNum.setTextColor(Color.parseColor("#ebebeb"));
                ivCode.setImageBitmap(QRCodeUtil.createBarcode(this,0xFFebebeb,bean.getId(),
                        DensityUtil.dp2px(this,294),DensityUtil.dp2px(this,73),false));
                break;
            case CLOSE:             //已关闭
                tvState.setText(context.getString(R.string.order_close));
                tvOrderNo.setText(String.format(getString(R.string.order_no),bean.getId()));
                tvOrderNo.setVisibility(View.VISIBLE);
                timerLayout.setVisibility(View.GONE);
                tvDelete.setVisibility(View.VISIBLE);
                tvPay.setVisibility(View.GONE);
                tvCancel.setVisibility(View.GONE);
                tvConfirm.setVisibility(View.GONE);
                codeLayout.setVisibility(View.VISIBLE);
                payLayout.setVisibility(View.GONE);
                tvCodeNum.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG );
                tvCodeNum.setTextColor(Color.parseColor("#ebebeb"));
                ivCode.setImageBitmap(QRCodeUtil.createBarcode(this,0xFFebebeb,bean.getId(),
                        DensityUtil.dp2px(this,294),DensityUtil.dp2px(this,73),false));
                break;
            case REFUNDING:           //退款中
                tvState.setText(context.getString(R.string.order_refunding));
                tvOrderNo.setText(String.format(getString(R.string.order_no),bean.getId()));
                tvOrderNo.setVisibility(View.VISIBLE);
                timerLayout.setVisibility(View.GONE);
                tvDelete.setVisibility(View.VISIBLE);
                tvPay.setVisibility(View.GONE);
                tvCancel.setVisibility(View.GONE);
                tvConfirm.setVisibility(View.GONE);
                codeLayout.setVisibility(View.VISIBLE);
                payLayout.setVisibility(View.GONE);
                tvCodeNum.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG );
                tvCodeNum.setTextColor(Color.parseColor("#ebebeb"));
                ivCode.setImageBitmap(QRCodeUtil.createBarcode(this,0xFFebebeb,bean.getId(),
                        DensityUtil.dp2px(this,294),DensityUtil.dp2px(this,73),false));
                break;
            case REFUNDED:             //已退款
                tvState.setText(context.getString(R.string.order_refunded));
                tvOrderNo.setText(String.format(getString(R.string.order_no),bean.getId()));
                tvOrderNo.setVisibility(View.VISIBLE);
                timerLayout.setVisibility(View.GONE);
                tvDelete.setVisibility(View.VISIBLE);
                tvPay.setVisibility(View.GONE);
                tvCancel.setVisibility(View.GONE);
                tvConfirm.setVisibility(View.GONE);
                codeLayout.setVisibility(View.VISIBLE);
                payLayout.setVisibility(View.GONE);
                tvCodeNum.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG );
                tvCodeNum.setTextColor(Color.parseColor("#ebebeb"));
                ivCode.setImageBitmap(QRCodeUtil.createBarcode(this,0xFFebebeb,bean.getId(),
                        DensityUtil.dp2px(this,294),DensityUtil.dp2px(this,73),false));
                break;
            default:
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_pay:
                PayInterface payInterface = payType.equals(detailBean.getPay().getPayType()) ?
                        new AliPay(this,payListener) : new WeiXinPay(this,payListener);
                payInterface.payOrder(detailBean.getPay());
                break;
            case R.id.rl_detail:
                CourseDetailActivity.start(this,detailBean.getLinkId());
                break;
            default:
                break;
        }
    }

    private PayInterface.PayListener payListener = new SimplePayListener(this) {
        @Override
        public void onSuccess(String code, Object object) {
            Toast.makeText(AppointCourseDetailActivity.this,"支付成功啦",Toast.LENGTH_LONG).show();
            startActivity(new Intent(AppointCourseDetailActivity.this,AppointSuccessActivity.class));
        }
    };

    @Override
    public void onCheckedChanged(CustomNestRadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.cb_alipay:
                payType = PayType.ALI;
                break;
            case R.id.cb_weixin:
                payType = PayType.WEIXIN;
                break;
            default:
                break;
        }
    }
}
