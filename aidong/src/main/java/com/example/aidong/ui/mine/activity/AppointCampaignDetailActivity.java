package com.example.aidong.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aidong.R;
import com.example.aidong .entity.AppointmentDetailBean;
import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.video.AppointmentDetailBean2;
import com.example.aidong .module.pay.AliPay;
import com.example.aidong .module.pay.PayInterface;
import com.example.aidong .module.pay.SimplePayListener;
import com.example.aidong .module.pay.WeiXinPay;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong.ui.DisplayActivity;
import com.example.aidong .ui.home.activity.ActivityCircleDetailActivity;
import com.example.aidong .ui.home.activity.AppointSuccessActivity;
import com.example.aidong .ui.home.activity.MapActivity;
import com.example.aidong .ui.mvp.presenter.impl.AppointmentPresentImpl;
import com.example.aidong .ui.mvp.view.AppointmentDetailActivityView;
import com.example.aidong .utils.Constant;
import com.example.aidong .utils.DateUtils;
import com.example.aidong .utils.DensityUtil;
import com.example.aidong .utils.FormatUtil;
import com.example.aidong .utils.GlideLoader;
import com.example.aidong .utils.ImageRectUtils;
import com.example.aidong .utils.Logger;
import com.example.aidong .utils.QRCodeUtil;
import com.example.aidong .utils.SystemInfoUtils;
import com.example.aidong .utils.TelephoneManager;
import com.example.aidong .utils.ToastGlobal;
import com.example.aidong .utils.constant.PayType;
import com.example.aidong .widget.CustomNestRadioGroup;
import com.example.aidong .widget.ExtendTextView;
import com.example.aidong .widget.SimpleTitleBar;
import com.example.aidong .widget.SwitcherLayout;
import com.example.aidong .widget.dialog.BaseDialog;
import com.example.aidong .widget.dialog.ButtonCancelListener;
import com.example.aidong .widget.dialog.ButtonOkListener;
import com.example.aidong .widget.dialog.DialogDoubleButton;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import cn.iwgang.countdownview.CountdownView;

import static com.example.aidong.R.id.iv;
import static com.example.aidong.R.id.txt_course_time;
import static com.example.aidong .ui.App.context;
import static com.example.aidong .utils.Constant.PAY_ALI;
import static com.example.aidong .utils.Constant.PAY_WEIXIN;

/**
 * 活动预约详情
 * Created by song on 2016/9/2.
 */
//todo 活动预约详情与课程预约详情合成一个界面
public class AppointCampaignDetailActivity extends BaseActivity implements AppointmentDetailActivityView,
        View.OnClickListener, CustomNestRadioGroup.OnCheckedChangeListener, CountdownView.OnCountdownEndListener {
    private static final String UN_PAID = "pending";         //待付款
    private static final String UN_JOIN = "purchased";        //待参加
    private static final String JOINED = "signed";        //已参加
    private static final String CLOSE = "canceled";          //已关闭
    private static final String REFUNDING = "refunding";     //退款中
    private static final String REFUNDED = "returned";      //已退款
    private long APPOINT_COUNTDOWN_MILL;

    private SimpleTitleBar titleBar;
    private SwitcherLayout switcherLayout;
    private ScrollView scrollView;

    //预约状态信息
    private TextView tvState;
    private TextView tvOrderNo;
    private LinearLayout timerLayout, layout_mark;
    private CountdownView timer;
    private RelativeLayout campaignLayout;
    private ImageView ivCover;
    private TextView tvCampaignName;
    private TextView tvInfo;
    private RelativeLayout codeLayout;
    private TextView tvCodeNum;
    private ImageView ivCode;

    //预约信息
    private ExtendTextView tvUserName;
    private ExtendTextView tvPhone;
    private ExtendTextView tvCampaignOrganization;
    private ExtendTextView tvCampaignTime;
    private ExtendTextView tvCampaignAddress;

    //订单信息
    private ExtendTextView tvTotalPrice;
    private ExtendTextView tvExpressPrice;
    private ExtendTextView tvCouponPrice;
    private ExtendTextView campaignPrivilege;
    private ExtendTextView tvAiBi;
    private ExtendTextView tvAiDou;
    private ExtendTextView tvStartTime;
    private ExtendTextView tvPayTime;
    private ExtendTextView tvPayType, tv_appoint_phone;

    //支付方式信息
    private LinearLayout payLayout;
    private CustomNestRadioGroup payGroup;
    private RadioButton rbALiPay;
    private RadioButton rbWeiXinPay;

    //底部预约操作状态及价格信息
    private LinearLayout bottomLayout;
    private TextView tvPrice;
    private TextView tvPayTip;
    private TextView tvPay, txt_remark;
    private TextView tvCancelPay;
    private TextView tvCancelJoin;
    private TextView tvConfirmJoin;
    private TextView tvDelete;

    //Present层对象
    private AppointmentPresentImpl present;
    private String orderId;
    @PayType
    private String payType;
    private AppointmentDetailBean bean;

    private String campaignId;
    private boolean fromDetail = false;
    private String status,imageUrl;
    private TextView txtCourseTime;

    private TextView txtRoomName;
    private TextView txtCourseLocation;


    public static void start(Context context, String orderId) {
        Intent starter = new Intent(context, AppointCampaignDetailActivity.class);
        starter.putExtra("orderId", orderId);
        context.startActivity(starter);
    }


    public static void start(Context context, String orderId,String imageUrl) {
        Intent starter = new Intent(context, AppointCampaignDetailActivity.class);
        starter.putExtra("orderId", orderId);
        starter.putExtra("imageUrl", imageUrl);
        context.startActivity(starter);
    }


    public static void start(Context context, String campaignId, boolean fromDetail) {
        Intent starter = new Intent(context, AppointCampaignDetailActivity.class);
        starter.putExtra("campaignId", campaignId);
        starter.putExtra("fromDetail", fromDetail);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_campaign_detail);
        APPOINT_COUNTDOWN_MILL = SystemInfoUtils.getAppointmentCountdown(this) * 60 * 1000;
        present = new AppointmentPresentImpl(this, this);
        if (getIntent() != null) {
            fromDetail = getIntent().getBooleanExtra("fromDetail", false);
            imageUrl = getIntent().getStringExtra("imageUrl");
            if (fromDetail) {
                campaignId = getIntent().getStringExtra("campaignId");
            } else {
                orderId = getIntent().getStringExtra("orderId");
            }
        }

        initView();
        setListener();
        getCampaignDetailData();
    }

    private void initView() {
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        scrollView = (ScrollView) findViewById(R.id.scroll_view);
        switcherLayout = new SwitcherLayout(this, scrollView);

        tvState = (TextView) findViewById(R.id.tv_state);
        tvOrderNo = (TextView) findViewById(R.id.tv_order_num);
        timerLayout = (LinearLayout) findViewById(R.id.ll_timer);
        layout_mark = (LinearLayout) findViewById(R.id.layout_mark);

        timer = (CountdownView) findViewById(R.id.timer);
        campaignLayout = (RelativeLayout) findViewById(R.id.rl_detail);
        ivCover = (ImageView) findViewById(R.id.dv_goods_cover);
        tvCampaignName = (TextView) findViewById(R.id.tv_name);
        txtCourseTime = (TextView) findViewById(txt_course_time);

        txtRoomName = (TextView) findViewById(R.id.txt_room_name);
        txtCourseLocation = (TextView) findViewById(R.id.txt_course_location);

        txt_remark = (TextView) findViewById(R.id.txt_remark);
        tvInfo = (TextView) findViewById(R.id.tv_info);
        codeLayout = (RelativeLayout) findViewById(R.id.rl_qr_code);
        tvCodeNum = (TextView) findViewById(R.id.tv_qr_num);
        ivCode = (ImageView) findViewById(R.id.dv_qr);

        tvUserName = (ExtendTextView) findViewById(R.id.tv_campaign_user);
        tvPhone = (ExtendTextView) findViewById(R.id.tv_campaign_phone);
        tvCampaignOrganization = (ExtendTextView) findViewById(R.id.tv_campaign_organization);
        tvCampaignTime = (ExtendTextView) findViewById(R.id.tv_campaign_time);
        tvCampaignAddress = (ExtendTextView) findViewById(R.id.tv_campaign_address);

        tvTotalPrice = (ExtendTextView) findViewById(R.id.tv_total_price);
        tvExpressPrice = (ExtendTextView) findViewById(R.id.tv_express_price);
        tvCouponPrice = (ExtendTextView) findViewById(R.id.coupon_price);
        campaignPrivilege = (ExtendTextView) findViewById(R.id.campaign_privilege);
        tvAiBi = (ExtendTextView) findViewById(R.id.tv_aibi);
        tvAiDou = (ExtendTextView) findViewById(R.id.tv_aidou);
        tvStartTime = (ExtendTextView) findViewById(R.id.tv_start_time);
        tvPayTime = (ExtendTextView) findViewById(R.id.tv_pay_time);
        tvPayType = (ExtendTextView) findViewById(R.id.tv_pay_type);
        tv_appoint_phone = (ExtendTextView) findViewById(R.id.tv_appoint_phone);

        payLayout = (LinearLayout) findViewById(R.id.ll_pay);
        payGroup = (CustomNestRadioGroup) findViewById(R.id.radio_group);
        rbALiPay = (RadioButton) findViewById(R.id.cb_alipay);
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
        campaignLayout.setOnClickListener(this);
        timer.setOnCountdownEndListener(this);
        ivCode.setOnClickListener(this);
        txtRoomName.setOnClickListener(this);
        txtCourseLocation.setOnClickListener(this);
        findViewById(R.id.img_telephone).setOnClickListener(this);
    }

    private void getCampaignDetailData() {
        if (fromDetail) {
            present.getCampaignAppointDetail(switcherLayout, campaignId);
        } else {
            present.getAppointmentDetail(switcherLayout, orderId);
        }
    }

    @Override
    public void setAppointmentDetail(AppointmentDetailBean bean) {
        this.bean = bean;
        this.status = bean.getAppoint().getStatus();
        bottomLayout.setVisibility(View.VISIBLE);
        payType = bean.getPay().getPayType();
        if (PAY_ALI.equals(payType)) {
            rbALiPay.setChecked(true);
        } else {
            rbWeiXinPay.setChecked(true);
        }

        //与订单状态无关: 订单信息
        if(!TextUtils.isEmpty(bean.getCover())){
            GlideLoader.getInstance().displayImage(bean.getCover(), ivCover);
        }else {
            GlideLoader.getInstance().displayImage(imageUrl, ivCover);
        }

        tvCampaignName.setText(bean.getName());
        txtCourseTime.setText(bean.getAppoint().getSpec_value());

        tvUserName.setRightContent(bean.getAppoint().getName());
        tvPhone.setRightContent(bean.getAppoint().getMobile());
        tvCampaignOrganization.setRightContent(bean.getAppoint().getOrganizer());
        tvCampaignTime.setRightContent(bean.getAppoint().getClassTime());
        txtCourseLocation.setText(bean.getAppoint().getAddress());
        txtRoomName.setText(bean.getAppoint().landmark);



        tvInfo.setText("共"+bean.getAppoint().amount +"张票");

        tvCampaignAddress.setRightContent(bean.getAppoint().getAddress());
        tvPrice.setText(String.format(getString(R.string.rmb_price_double),
                FormatUtil.parseDouble(bean.getPay().getPayAmount())));
        tvCouponPrice.setRightContent(String.format(getString(R.string.rmb_minus_price_double),
                FormatUtil.parseDouble(bean.getPay().getCoupon())));
        tvAiBi.setRightContent(String.format(getString(R.string.rmb_minus_price_double),
                FormatUtil.parseDouble(bean.getPay().getCoin())));
        tvAiDou.setRightContent(String.format(getString(R.string.rmb_minus_price_double),
                FormatUtil.parseDouble(bean.getPay().getIntegral())));
        tvTotalPrice.setRightContent(String.format(getString(R.string.rmb_price_double),
                FormatUtil.parseDouble(bean.getPay().getTotal())));
        tv_appoint_phone.setRightContent(bean.getAppoint().getMobile());

        if (TextUtils.isEmpty(bean.getRemark())) {
            layout_mark.setVisibility(View.GONE);
        } else {
            layout_mark.setVisibility(View.VISIBLE);
            txt_remark.setText(bean.getRemark());
        }

        tvStartTime.setRightContent(bean.getPay().getCreatedAt());
        tvPayType.setRightContent(PAY_ALI.equals(bean.getPay().getPayType()) ? "支付宝" : "微信");


        Logger.i("appointdetail", "appoint status = " + bean.getAppoint().getStatus() + ",bean.getAppoint().getVerify_no() = " + bean.getAppoint().getVerify_no() +
                "bean.getAppoint().getVerify_status = " + bean.getAppoint().getVerify_status());
        //todo 通过组合控件来实现底部按钮
        //与订单状态有关: 预约状态信息 课程预约信息/活动预约信息 支付方式信息 底部预约操作状态及价格信息
        switch (bean.getAppoint().getStatus()) {
            case UN_PAID:           //待付款
                tvState.setText(context.getString(R.string.un_paid));
                timerLayout.setVisibility(View.VISIBLE);
                timer.start(DateUtils.getCountdown(bean.getPay().getCreatedAt(), APPOINT_COUNTDOWN_MILL));
                tvOrderNo.setText(String.format(getString(R.string.appoint_no), bean.getId()));
                tvOrderNo.setVisibility(View.VISIBLE);
                tvCancelPay.setVisibility(View.VISIBLE);
                tvPay.setVisibility(View.VISIBLE);
                tvDelete.setVisibility(View.GONE);
                tvStartTime.setLeftTextContent("实付款");


                tvStartTime.setRightTextColor(ContextCompat.getColor(context,R.color.main_blue));
                tvStartTime.setRightContent(String.format(getString(R.string.rmb_price_double),
                        FormatUtil.parseDouble(bean.getPay().getPayAmount())));
                tvConfirmJoin.setVisibility(View.GONE);
                tvCancelJoin.setVisibility(View.GONE);
                codeLayout.setVisibility(View.GONE);
                payLayout.setVisibility(View.VISIBLE);
                tvPayType.setVisibility(View.GONE);


                tvState.setTextColor(ContextCompat.getColor(this,R.color.main_red2));
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
                codeLayout.setVisibility(View.VISIBLE);//1

                payLayout.setVisibility(View.GONE);
                tvPayType.setVisibility(View.VISIBLE);


                tvCodeNum.setText(bean.getAppoint().getVerify_no());
                if (bean.getAppoint().isVerified()) {
                    tvCodeNum.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                }
                tvCodeNum.setTextColor(bean.getAppoint().getverifyColor());
                Logger.i(DensityUtil.dp2px(this, 312)+"");
                ivCode.setImageBitmap(QRCodeUtil.createBarcode(this, bean.getAppoint().getverifyColorQr(), bean.getAppoint().getVerify_no(),
                        DensityUtil.dp2px(this, 312), DensityUtil.dp2px(this, 73), false));

                tvState.setTextColor(ContextCompat.getColor(this,R.color.black));
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
                codeLayout.setVisibility(View.VISIBLE);//1
//                tvCodeNum.setText(bean.getId());
//                tvCodeNum.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//                tvCodeNum.setTextColor(Color.parseColor("#ebebeb"));
//                ivCode.setImageBitmap(QRCodeUtil.createBarcode(this, 0xFFebebeb, bean.getId(),
//                        DensityUtil.dp2px(this, 294), DensityUtil.dp2px(this, 73), false));
                payLayout.setVisibility(View.GONE);
                tvPayType.setVisibility(View.VISIBLE);


                tvCodeNum.setText(bean.getAppoint().getVerify_no());
                if (bean.getAppoint().isVerified()) {
                    tvCodeNum.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                }
                tvCodeNum.setTextColor(bean.getAppoint().getverifyColor());
                ivCode.setImageBitmap(QRCodeUtil.createBarcode(this, bean.getAppoint().getverifyColorQr(), bean.getAppoint().getVerify_no(),
                        DensityUtil.dp2px(this, 312), DensityUtil.dp2px(this, 73), false));
                tvState.setTextColor(ContextCompat.getColor(this,R.color.black));
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

                tvStartTime.setLeftTextContent("实付款");
                tvStartTime.setRightTextColor(ContextCompat.getColor(context,R.color.main_blue));
                tvStartTime.setRightContent(String.format(getString(R.string.rmb_price_double),
                        FormatUtil.parseDouble(bean.getPay().getPayAmount())));
                tvState.setTextColor(ContextCompat.getColor(this,R.color.black));
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
                codeLayout.setVisibility(View.GONE);//1
//                tvCodeNum.setText(bean.getId());
//                tvCodeNum.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//                tvCodeNum.setTextColor(Color.parseColor("#ebebeb"));
//                ivCode.setImageBitmap(QRCodeUtil.createBarcode(this, 0xFFebebeb, bean.getId(),
//                        DensityUtil.dp2px(this, 294), DensityUtil.dp2px(this, 73), false));
                payLayout.setVisibility(View.GONE);
                tvPayType.setVisibility(View.VISIBLE);


                tvCodeNum.setText(bean.getAppoint().getVerify_no());
                if (bean.getAppoint().isVerified()) {
                    tvCodeNum.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                }
                tvCodeNum.setTextColor(bean.getAppoint().getverifyColor());
                ivCode.setImageBitmap(QRCodeUtil.createBarcode(this, bean.getAppoint().getverifyColorQr(), bean.getAppoint().getVerify_no(),
                        DensityUtil.dp2px(this, 312), DensityUtil.dp2px(this, 73), false));
                tvState.setTextColor(ContextCompat.getColor(this,R.color.black));
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
                codeLayout.setVisibility(View.GONE);//1
//                tvCodeNum.setText(bean.getId());
//                tvCodeNum.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//                tvCodeNum.setTextColor(Color.parseColor("#ebebeb"));
//                ivCode.setImageBitmap(QRCodeUtil.createBarcode(this, 0xFFebebeb, bean.getId(),
//                        DensityUtil.dp2px(this, 312), DensityUtil.dp2px(this, 73), false));
                payLayout.setVisibility(View.GONE);
                tvPayType.setVisibility(View.VISIBLE);


                tvCodeNum.setText(bean.getAppoint().getVerify_no());
//                if (bean.getAppoint().isVerified()) {
                tvCodeNum.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//                }
                tvCodeNum.setTextColor(Color.parseColor("#ebebeb"));
                ivCode.setImageBitmap(QRCodeUtil.createBarcode(this, 0xFFebebeb, bean.getAppoint().getVerify_no(),
                        DensityUtil.dp2px(this, 312), DensityUtil.dp2px(this, 73), false));
                tvState.setTextColor(ContextCompat.getColor(this,R.color.black));
                break;
            default:
                tvState.setTextColor(ContextCompat.getColor(this,R.color.black));
                break;
        }
    }

    public final static String WX_APP_ID = "wx365ab323b9269d30";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_pay:

                if (PAY_WEIXIN.equals(payType)){
                    if (api == null) {
                        api = WXAPIFactory.createWXAPI(context, WX_APP_ID, false);
                    }
                    if (!api.isWXAppInstalled()) {
                        Toast.makeText(context, "没有安装微信", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                PayInterface payInterface = PAY_ALI.equals(payType) ?
                        new AliPay(this, payListener) : new WeiXinPay(this, payListener);
                payInterface.payOrder(bean.getPay().getpayOption());
                break;
            case R.id.tv_cancel_pay:
                present.cancelAppoint(bean.getId());
                break;
            case R.id.tv_cancel_join:
                if (DateUtils.started(bean.getAppoint().getClassTime())) {
                    ToastGlobal.showLong("活动已开始，无法取消");
                } else {
                    present.cancelAppoint(bean.getId());
                }
                break;
            case R.id.tv_confirm:
//                if (DateUtils.bigThanOneHour(bean.getAppoint().getClassTime())) {
//                    ToastGlobal.showLong("未到活动时间，请稍后确认");
//                } else {
                    new DialogDoubleButton(this)
                            .setLeftButton("取消")
                            .setRightButton("确定")
                            .setContentDesc(getString(R.string.are_you_sure_have_to_attend))
                            .setBtnCancelListener(new ButtonCancelListener() {
                                @Override
                                public void onClick(BaseDialog dialog) {
                                    dialog.dismiss();
                                }
                            })
                            .setBtnOkListener(new ButtonOkListener() {
                                @Override
                                public void onClick(BaseDialog dialog) {
                                    dialog.dismiss();
                                    present.confirmAppoint(bean.getId());
                                }
                            }).show();

//                }
                break;
            case R.id.tv_delete:
                present.deleteAppoint(bean.getId());
                break;
            case R.id.rl_detail:
              //  ActivityCircleDetailActivity.start(this, bean.getLinkId());



                Intent intent = new Intent(context,DisplayActivity.class);
                intent.putExtra("TYPE","DetailsActivityH5Fragment");
                intent.putExtra("id",bean.getAppoint().campaign_detail);
                context.startActivity(intent);


                break;
            case R.id.dv_qr:
                if (UN_JOIN.equals(status)) {
                    BarcodeActivity.start(this, bean.getAppoint().getVerify_no(), ImageRectUtils.getDrawableBoundsInView(ivCode));
                }
                break;
            case R.id.txt_course_room:
            case R.id.txt_course_location:
                MapActivity.start(this, bean.getName(), bean.getAppoint().landmark, bean.getAppoint().getAddress(),
                        bean.getAppoint().getLat(), bean.getAppoint().getLng());


                break;

            case R.id.img_telephone:
                TelephoneManager.callImmediate(this, bean.getAppoint().getOrganizer_mobile());

                break;
            default:
                break;
        }
    }
    private IWXAPI api;
    private PayInterface.PayListener payListener = new SimplePayListener(this) {
        @Override
        public void onSuccess(String code, Object object) {
           // ToastGlobal.showLong("支付成功");
            AppointSuccessActivity.start(AppointCampaignDetailActivity.this, bean.getAppoint().getSpec_value(), false, present.getShareInfo(),bean.getAppoint().amount);
            LocalBroadcastManager.getInstance(AppointCampaignDetailActivity.this)
                    .sendBroadcast(new Intent(Constant.BROADCAST_ACTION_CAMPAIGN_PAY_SUCCESS));


        }

        @Override
        public void onFree() {
            LocalBroadcastManager.getInstance(AppointCampaignDetailActivity.this)
                    .sendBroadcast(new Intent(Constant.BROADCAST_ACTION_CAMPAIGN_PAY_SUCCESS));
            AppointSuccessActivity.start(AppointCampaignDetailActivity.this, bean.getAppoint().getClassTime(), false, present.getShareInfo(),bean.getAppoint().amount);
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
            getCampaignDetailData();
            ToastGlobal.showLong("取消成功");
            LocalBroadcastManager.getInstance(AppointCampaignDetailActivity.this)
                    .sendBroadcast(new Intent(Constant.BROADCAST_ACTION_CAMPAIGN_APPOINT_CANCEL));
        } else {
            ToastGlobal.showLong(baseBean.getMessage());
        }
    }

    @Override
    public void confirmAppointmentResult(BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
            getCampaignDetailData();
            ToastGlobal.showLong("确认成功");
            LocalBroadcastManager.getInstance(AppointCampaignDetailActivity.this)
                    .sendBroadcast(new Intent(Constant.BROADCAST_ACTION_CAMPAIGN_APPOINT_CONFIRM));
        } else {
            ToastGlobal.showLong(baseBean.getMessage());
        }
    }

    @Override
    public void deleteAppointmentResult(BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
            ToastGlobal.showLong("删除成功");
            LocalBroadcastManager.getInstance(AppointCampaignDetailActivity.this)
                    .sendBroadcast(new Intent(Constant.BROADCAST_ACTION_CAMPAIGN_APPOINT_DELETE));
            finish();


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
