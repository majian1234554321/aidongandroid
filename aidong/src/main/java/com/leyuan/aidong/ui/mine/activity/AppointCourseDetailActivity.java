package com.leyuan.aidong.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.AppointmentDetailBean;
import com.leyuan.aidong.module.pay.AliPay;
import com.leyuan.aidong.module.pay.PayInterface;
import com.leyuan.aidong.module.pay.WeiXinPay;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.home.activity.AppointSuccessActivity;
import com.leyuan.aidong.ui.home.activity.CourseDetailActivity;
import com.leyuan.aidong.ui.mvp.presenter.AppointmentPresent;
import com.leyuan.aidong.ui.mvp.presenter.CoursePresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.AppointmentPresentImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.CoursePresentImpl;
import com.leyuan.aidong.ui.mvp.view.AppointmentDetailActivityView;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.widget.CustomNestRadioGroup;
import com.leyuan.aidong.widget.ExtendTextView;
import com.leyuan.aidong.widget.SimpleTitleBar;
import com.leyuan.aidong.widget.SwitcherLayout;

/**
 * 课程预约详情
 * Created by song on 2016/9/2.
 */
public class AppointCourseDetailActivity extends BaseActivity implements AppointmentDetailActivityView, View.OnClickListener, CustomNestRadioGroup.OnCheckedChangeListener {
    private static final String UN_PAID = "pending";         //待付款
    private static final String UN_JOIN= "purchased";        //待参加
    private static final String JOINED = "signed";           //已参加
    private static final String CLOSE = "canceled";          //已关闭
    private static final String PAY_ALI = "alipay";
    private static final String PAY_WEIXIN = "weixin";

    private SimpleTitleBar titleBar;
    private SwitcherLayout switcherLayout;
    private ScrollView scrollView;

    //预约状态信息
    private TextView tvState;
    private TextView tvTimeOrNum;
    private RelativeLayout courseLayout;
    private SimpleDraweeView courseCover;
    private TextView tvCourseName;
    private TextView tvCourseInfo;
    private RelativeLayout qrCodeLayout;
    private TextView tvNum;
    private SimpleDraweeView dvQr;

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
    private LinearLayout llPay;
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
    private TextView tvReceiving;
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
        tvTimeOrNum = (TextView) findViewById(R.id.tv_time_or_num);
        courseLayout = (RelativeLayout) findViewById(R.id.rl_detail);
        courseCover = (SimpleDraweeView) findViewById(R.id.dv_goods_cover);
        tvCourseName = (TextView) findViewById(R.id.tv_name);
        tvCourseInfo = (TextView) findViewById(R.id.tv_info);
        qrCodeLayout = (RelativeLayout) findViewById(R.id.rl_qr_code);
        tvNum = (TextView) findViewById(R.id.tv_num);
        dvQr = (SimpleDraweeView) findViewById(R.id.dv_qr);

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

        llPay = (LinearLayout) findViewById(R.id.ll_pay);
        payGroup = (CustomNestRadioGroup) findViewById(R.id.radio_group);
        rbAliPay = (RadioButton) findViewById(R.id.cb_alipay);
        rbWeiXinPay = (RadioButton) findViewById(R.id.cb_weixin);

        tvGoodsCount = (TextView) findViewById(R.id.tv_goods_count);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvPayTip = (TextView) findViewById(R.id.tv_pay_tip);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvPay = (TextView) findViewById(R.id.tv_pay);
        tvExpress = (TextView) findViewById(R.id.tv_express);
        tvReceiving = (TextView) findViewById(R.id.tv_receiving);
        tvDelete = (TextView) findViewById(R.id.tv_delete);
        tvAgainBuy = (TextView) findViewById(R.id.tv_again_buy);

        qrCodeLayout.setVisibility(View.GONE);
    }

    private void setListener(){
        titleBar.setOnClickListener(this);
        payGroup.setOnCheckedChangeListener(this);
        tvPay.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        tvDelete.setOnClickListener(this);
        tvAgainBuy.setOnClickListener(this);
        tvExpress.setOnClickListener(this);
        tvReceiving.setOnClickListener(this);
        courseLayout.setOnClickListener(this);
    }

    @Override
    public void setAppointmentDetail(AppointmentDetailBean bean) {
        detailBean = bean;
        payType = bean.getPay().getPayType();
        if(PAY_ALI.equals(payType)){
            rbAliPay.setChecked(true);
        }else {
            rbWeiXinPay.setChecked(true);
        }

        //与订单状态无关:活动信息,预约信息及部分订单信息
        courseCover.setImageURI(bean.getCover());
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
      /*  couponPrice.setRightContent(String.format(getString(R.string.minus_rmb),bean.getPay().getCoupon()));
        coursePrivilege.setRightContent(String.format(getString(R.string.minus_rmb),"0"));
        tvAibi.setRightContent(String.format(getString(R.string.minus_rmb),bean.getPay().getCoin()));
        tvAidou.setRightContent(String.format(getString(R.string.minus_rmb),bean.getPay().getIntegral()));*/
        tvStartTime.setRightContent(bean.getPay().getCreatedAt());

        //与订单状态有关: 预约状态信息及部分订单信息  支付方式信息 底部预约操作状态及价格信息
        switch (bean.getPay().getStatus()){
            case UN_PAID:
                tvState.setText(getString(R.string.un_paid));
                qrCodeLayout.setVisibility(View.GONE);
                tvPayType.setVisibility(View.GONE);
                tvPayTime.setVisibility(View.GONE);
                break;
            case UN_JOIN:
                tvState.setText(getString(R.string.appointment_un_joined));

                break;
            case JOINED:
                tvState.setText(getString(R.string.appointment_joined));
                break;
            case CLOSE:
                tvState.setText(getString(R.string.order_close));

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
               // PayOrderBean bean =  detailBean.getPay();
                //bean.getpayOption().setPay_string("_input_charset=utf-8&body=培训课程4-1&it_b_pay=30m&notify_url=&out_trade_no=483610338301&partner=2088021345411340&payment_type=1&seller_id=2088021345411340&service=mobile.securitypay.pay&subject=培训课程4-1&total_fee=88.8&sign_type=RSA&sign=Oaoqco9BIbH89WT5aCAsXvV23qImwg0CfvXnRZAbrpBlBqq6MSzpIIePVh8oJyXIPmqrhvANbFeILdV7eThU9E3%2BpKZWxNJuK4%2FjGhoCwLYZaDVhZ5%2F9nLu4zxYK72UkcchPT90eVLtMUu2TnvpuakuJJ2aajhFcOWDcwXn14UQ%3D");
                payInterface.payOrder(detailBean.getPay());
                break;
            case R.id.rl_detail:
                CourseDetailActivity.start(this,detailBean.getLinkId());
                break;
            default:
                break;
        }
    }

    private PayInterface.PayListener payListener = new PayInterface.PayListener() {
        @Override
        public void fail(String code, Object object) {
            String tip = "";
            switch (code){
                case "4000":
                    tip = "订单支付失败";
                    break;
                case "5000":
                    tip = "订单重复提交";
                    break;
                case "6001":
                    tip = "订单取消支付";
                    break;
                case "6002":
                    tip = "网络连接出错";
                    break;
                default:
                    tip = code +":::"+ object.toString();
                    break;
            }
            Toast.makeText(AppointCourseDetailActivity.this,tip,Toast.LENGTH_LONG).show();
            Logger.w("AppointCourseActivity","failed:" + code + object.toString());
        }

        @Override
        public void success(String code, Object object) {
            Toast.makeText(AppointCourseDetailActivity.this,"支付成功啦啦啦啦啦绿",Toast.LENGTH_LONG).show();
            startActivity(new Intent(AppointCourseDetailActivity.this,AppointSuccessActivity.class));
            Logger.w("AppointCourseActivity","success:" + code + object.toString());
        }
    };

    @Override
    public void onCheckedChanged(CustomNestRadioGroup group, int checkedId) {
        switch (checkedId){
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
}
