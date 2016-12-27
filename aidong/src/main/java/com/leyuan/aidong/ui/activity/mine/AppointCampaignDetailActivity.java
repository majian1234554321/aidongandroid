package com.leyuan.aidong.ui.activity.mine;

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
import com.leyuan.aidong.ui.activity.home.AppointSuccessActivity;
import com.leyuan.aidong.ui.mvp.presenter.AppointmentPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.AppointmentPresentImpl;
import com.leyuan.aidong.ui.mvp.view.AppointmentDetailActivityView;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.widget.customview.CustomNestRadioGroup;
import com.leyuan.aidong.widget.customview.ExtendTextView;
import com.leyuan.aidong.widget.customview.SimpleTitleBar;
import com.leyuan.aidong.widget.customview.SwitcherLayout;

/**
 * 活动预约详情
 * Created by song on 2016/9/2.
 */
public class AppointCampaignDetailActivity extends BaseActivity implements AppointmentDetailActivityView, View.OnClickListener, CustomNestRadioGroup.OnCheckedChangeListener {
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
    private SimpleDraweeView dvGoodsCover;
    private TextView tvName;
    private TextView tvInfo;
    private RelativeLayout codeLayout;
    private TextView tvNum;
    private SimpleDraweeView dvQr;

    //预约信息
    private ExtendTextView tvCampaignUser;
    private ExtendTextView tvCampaignPhone;
    private ExtendTextView tvCampaignOrganization;
    private ExtendTextView tvCampaignTime;
    private ExtendTextView tvCampaignAddress;

    //订单信息
    private ExtendTextView tvTotalPrice;
    private ExtendTextView tvExpressPrice;
    private ExtendTextView couponPrice;
    private ExtendTextView campaignPrivilege;
    private ExtendTextView tvAibi;
    private ExtendTextView tvAidou;
    private ExtendTextView tvStartTime;
    private ExtendTextView tvPayTime;
    private ExtendTextView tvPayType;

    //支付方式信息
    private LinearLayout llPay;
    private CustomNestRadioGroup payGroup;
    private RadioButton rbALiPay;
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
    private String orderId;
    private String payType;
    private AppointmentDetailBean detailBean;

    public static void start(Context context,String orderId) {
        Intent starter = new Intent(context, AppointCampaignDetailActivity.class);
        starter.putExtra("orderId",orderId);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_campaign_detail);
        appointmentPresent = new AppointmentPresentImpl(this,this);
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
        dvGoodsCover = (SimpleDraweeView) findViewById(R.id.dv_goods_cover);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvInfo = (TextView) findViewById(R.id.tv_info);
        codeLayout = (RelativeLayout) findViewById(R.id.rl_qr_code);
        tvNum = (TextView) findViewById(R.id.tv_num);
        dvQr = (SimpleDraweeView) findViewById(R.id.dv_qr);

        tvCampaignUser = (ExtendTextView) findViewById(R.id.tv_campaign_user);
        tvCampaignPhone = (ExtendTextView) findViewById(R.id.tv_campaign_phone);
        tvCampaignOrganization = (ExtendTextView) findViewById(R.id.tv_campaign_organization);
        tvCampaignTime = (ExtendTextView) findViewById(R.id.tv_campaign_time);
        tvCampaignAddress = (ExtendTextView) findViewById(R.id.tv_campaign_address);

        tvTotalPrice = (ExtendTextView) findViewById(R.id.tv_total_price);
        tvExpressPrice = (ExtendTextView) findViewById(R.id.tv_express_price);
        couponPrice = (ExtendTextView) findViewById(R.id.coupon_price);
        campaignPrivilege = (ExtendTextView) findViewById(R.id.campaign_privilege);
        tvAibi = (ExtendTextView) findViewById(R.id.tv_aibi);
        tvAidou = (ExtendTextView) findViewById(R.id.tv_aidou);
        tvStartTime = (ExtendTextView) findViewById(R.id.tv_start_time);
        tvPayTime = (ExtendTextView) findViewById(R.id.tv_pay_time);
        tvPayType = (ExtendTextView) findViewById(R.id.tv_pay_type);

        llPay = (LinearLayout) findViewById(R.id.ll_pay);
        payGroup = (CustomNestRadioGroup) findViewById(R.id.radio_group);
        rbALiPay = (RadioButton) findViewById(R.id.cb_alipay);
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
    }

    private void setListener(){
        titleBar.setOnClickListener(this);
        payGroup.setOnCheckedChangeListener(this);
        tvPay.setOnClickListener(this);
    }

    @Override
    public void setAppointmentDetail(AppointmentDetailBean bean) {
        detailBean = bean;
        payType = bean.getPay().getPayType();
        if(PAY_ALI.equals(payType)){
            rbALiPay.setChecked(true);
        }else {
            rbWeiXinPay.setChecked(true);
        }

        //与订单状态无关: 订单信息
        dvGoodsCover.setImageURI(bean.getCover());
        tvName.setText(bean.getName());
        tvInfo.setText(bean.getSubName());

        tvCampaignUser.setRightContent(bean.getAppoint().getName());
        tvCampaignPhone.setRightContent(bean.getAppoint().getMobile());
        tvCampaignOrganization.setRightContent("组织者");
        tvCampaignTime.setRightContent(bean.getAppoint().getClassTime());
        tvCampaignAddress.setRightContent(bean.getAppoint().getAddress());

        tvTotalPrice.setRightContent(bean.getPay().getTotal());
        couponPrice.setRightContent(String.format(getString(R.string.minus_rmb),bean.getPay().getCoupon()));
        campaignPrivilege.setRightContent(String.format(getString(R.string.minus_rmb),"0"));
        tvAibi.setRightContent(String.format(getString(R.string.minus_rmb),bean.getPay().getCoin()));
        tvAidou.setRightContent(String.format(getString(R.string.minus_rmb),bean.getPay().getIntegral()));
        tvStartTime.setRightContent(bean.getPay().getCreatedAt());


        //与订单状态有关: 预约状态信息 课程预约信息/活动预约信息 支付方式信息 底部预约操作状态及价格信息
        switch (bean.getPay().getStatus()){
            case UN_PAID:
                tvState.setText(getString(R.string.un_paid));
                tvTimeOrNum.setText(bean.getPay().getCreatedAt());
                codeLayout.setVisibility(View.GONE);
                break;
            case UN_JOIN:
                tvState.setText(getString(R.string.appointment_un_joined));
                tvTimeOrNum.setText(bean.getId());
                codeLayout.setVisibility(View.VISIBLE);

                break;
            case JOINED:
                tvState.setText(getString(R.string.appointment_joined));
                tvTimeOrNum.setText(bean.getId());
                codeLayout.setVisibility(View.VISIBLE);
                break;
            case CLOSE:
                tvState.setText(getString(R.string.order_close));
                tvTimeOrNum.setText(bean.getId());
                codeLayout.setVisibility(View.VISIBLE);
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
                    break;
            }
            Toast.makeText(AppointCampaignDetailActivity.this,tip,Toast.LENGTH_LONG).show();
            Logger.w("AppointCourseActivity","failed:" + code + object.toString());
        }

        @Override
        public void success(String code, Object object) {
            Toast.makeText(AppointCampaignDetailActivity.this,"支付成功啦啦啦啦啦绿",Toast.LENGTH_LONG).show();
            startActivity(new Intent(AppointCampaignDetailActivity.this,AppointSuccessActivity.class));
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
