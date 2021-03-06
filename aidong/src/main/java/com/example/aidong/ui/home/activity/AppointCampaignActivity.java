//package com.example.aidong.ui.home.activity;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.example.aidong.R;
//import com.example.aidong .entity.CampaignDetailBean;
//import com.example.aidong .entity.CouponBean;
//import com.example.aidong .module.pay.PayInterface;
//import com.example.aidong .module.pay.SimplePayListener;
//import com.example.aidong .ui.App;
//import com.example.aidong .ui.BaseActivity;
//import com.example.aidong .ui.mine.activity.AppointmentMineActivityNew;
//import com.example.aidong .ui.mine.activity.SelectCouponActivity;
//import com.example.aidong .ui.mine.activity.setting.PhoneBindingActivity;
//import com.example.aidong .ui.mine.activity.setting.PhoneUnBindingActivity;
//import com.example.aidong .ui.mvp.presenter.CampaignPresent;
//import com.example.aidong .ui.mvp.presenter.impl.CampaignPresentImpl;
//import com.example.aidong .ui.mvp.view.AppointCampaignActivityView;
//import com.example.aidong .utils.Constant;
//import com.example.aidong .utils.FormatUtil;
//import com.example.aidong .utils.GlideLoader;
//import com.example.aidong .utils.ToastGlobal;
//import com.example.aidong .utils.UiManager;
//import com.example.aidong .utils.constant.PayType;
//import com.example.aidong .widget.CustomNestRadioGroup;
//import com.example.aidong .widget.ExtendTextView;
//import com.example.aidong .widget.SimpleTitleBar;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.example.aidong .utils.Constant.PAY_ALI;
//import static com.example.aidong .utils.Constant.PAY_WEIXIN;
//import static com.example.aidong .utils.Constant.REQUEST_SELECT_COUPON;
//
///**
// * 预约活动
// * Created by song on 2016/9/12.
// */
////todo 与课程预约界面合成一个界面
//public class AppointCampaignActivity extends BaseActivity implements View.OnClickListener,
//        CustomNestRadioGroup.OnCheckedChangeListener, AppointCampaignActivityView {
//    private SimpleTitleBar titleBar;
//
//    //预约人信息
//    private EditText tvUserName;
//    private TextView tvUserPhone;
//
//    //活动信息
//    private ImageView dvCover;
//    private TextView tvCampaignName;
//    private TextView tvOrganizer;
//    private ExtendTextView tvTime;
//    private TextView tvAddress;
//    private TextView tvAddressOrganizer;
//    private TextView tvCoupon;
//    private LinearLayout goldLayout;
//
//    //订单信息
//    private ExtendTextView tvTotalPrice;
//    private ExtendTextView tvCouponPrice;
//    private ExtendTextView tvDiscountPrice;
//    private ExtendTextView tvAibi;
//    private ExtendTextView tvAidou;
//
//    //支付
//    private CustomNestRadioGroup radioGroup;
//    private TextView tvTip;
//    private TextView tvPrice;
//    private TextView tvPay;
//
//    private String couponId;
//    private float integral;
//    private
//    @PayType
//    String payType;
//    private String userName;
//    private String contactMobile;
//
//    private CampaignDetailBean bean;
//    private CampaignPresent campaignPresent;
//    private List<CouponBean> usableCoupons = new ArrayList<>();
//    private String selectedUserCouponId;
//
//    public static void start(Context context, CampaignDetailBean campaignBean) {
//        Intent starter = new Intent(context, AppointCampaignActivity.class);
//        starter.putExtra("bean", campaignBean);
//        context.startActivity(starter);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_appoint_campaign);
//        campaignPresent = new CampaignPresentImpl(this, this);
//        payType = PAY_ALI;
//        if (getIntent() != null) {
//            bean = getIntent().getParcelableExtra("bean");
//        }
//        initView();
//        setListener();
//
//        campaignPresent.getCampaignAvailableCoupon(bean.getCampaignId());
//    }
//
//    private void initView() {
//        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
//        tvUserName = (EditText) findViewById(R.id.tv_input_name);
//        tvUserPhone = (TextView) findViewById(R.id.tv_input_phone);
//        dvCover = (ImageView) findViewById(R.id.dv_cover);
//        tvCampaignName = (TextView) findViewById(R.id.tv_name);
//        tvOrganizer = (TextView) findViewById(R.id.tv_organizer);
//        tvTime = (ExtendTextView) findViewById(R.id.tv_time);
//        tvAddress = (TextView) findViewById(R.id.tv_address);
//        tvAddressOrganizer = (TextView) findViewById(R.id.tv_address_organizer);
//        tvCoupon = (TextView) findViewById(R.id.tv_coupon);
//        goldLayout = (LinearLayout) findViewById(R.id.ll_gold);
//        tvTotalPrice = (ExtendTextView) findViewById(R.id.tv_total_price);
//        tvCouponPrice = (ExtendTextView) findViewById(R.id.tv_coupon_price);
//        tvDiscountPrice = (ExtendTextView) findViewById(R.id.tv_discount_price);
//        tvAibi = (ExtendTextView) findViewById(R.id.tv_aibi);
//        tvAidou = (ExtendTextView) findViewById(R.id.tv_aidou);
//        radioGroup = (CustomNestRadioGroup) findViewById(R.id.radio_group);
//        tvTip = (TextView) findViewById(R.id.tv_tip);
//        tvPrice = (TextView) findViewById(R.id.tv_price);
//        tvPay = (TextView) findViewById(R.id.tv_pay);
//
//        tvCampaignName.setText(bean.getName());
//        tvOrganizer.setText(bean.getOrganizer());
//        if (bean.getImage() != null && !bean.getImage().isEmpty()) {
//            GlideLoader.getInstance().displayImage(bean.getImage().get(0), dvCover);
//        }
//
//        tvTime.setRightContent(String.format(getString(R.string.campaign_during), bean.getStartTime(),
//                bean.getEndTime().substring(bean.getEndTime().lastIndexOf(Constant.EMPTY_STR))));
//        tvAddress.setText(bean.getAddress());
//        tvAddressOrganizer.setText(bean.getOrganizer());
//        tvTotalPrice.setRightContent(String.format(getString(R.string.rmb_price), bean.getPrice()));
//        tvPrice.setText(String.format(getString(R.string.rmb_price), bean.getPrice()));
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        userName = App.getInstance().getUser().getName();
//        contactMobile = App.getInstance().getUser().getMobile();
//        if (!TextUtils.isEmpty(userName))
//            tvUserName.setText(userName);
//        if (!TextUtils.isEmpty(contactMobile))
//            tvUserPhone.setText(contactMobile);
//        if (!TextUtils.isEmpty(userName))
//        tvUserName.setSelection(userName.length());
//    }
//
//    private void setListener() {
//        titleBar.setOnClickListener(this);
//        tvCoupon.setOnClickListener(this);
//        tvPay.setOnClickListener(this);
//        tvUserPhone.setOnClickListener(this);
//        radioGroup.setOnCheckedChangeListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.iv_back:
//                finish();
//                break;
//            case R.id.tv_input_phone:
//                contactMobile = App.getInstance().getUser().getMobile();
//                if (TextUtils.isEmpty(contactMobile)) {
//                    UiManager.activityJump(this, PhoneBindingActivity.class);
//                } else {
//                    UiManager.activityJump(this, PhoneUnBindingActivity.class);
//                }
//                break;
//            case R.id.tv_coupon:
//                if (usableCoupons != null && !usableCoupons.isEmpty()) {
//                    SelectCouponActivity.startForResult(this, bean.getPrice(), couponId,selectedUserCouponId, usableCoupons, REQUEST_SELECT_COUPON);
//                }
//                break;
//            case R.id.tv_pay:
//                String userRealName = tvUserName.getText().toString().trim();
//                if (TextUtils.isEmpty(userRealName)) {
//                    ToastGlobal.showLong("姓名不能为空");
//                } else if (TextUtils.isEmpty(contactMobile)) {
//                    ToastGlobal.showLong("请先绑定手机");
//                } else {
//                    campaignPresent.buyCampaign(bean.getCampaignId(), couponId, integral,
//                            payType, userRealName, contactMobile, payListener, "1",null);
//                }
//                break;
//            default:
//                break;
//        }
//    }
//
//    private PayInterface.PayListener payListener = new SimplePayListener(this) {
//        @Override
//        public void onSuccess(String code, Object object) {
//            AppointSuccessActivity.start(AppointCampaignActivity.this, bean.getStartTime(), false,campaignPresent.getShareInfo());
//            ToastGlobal.showLong("支付成功");
//        }
//
//        @Override
//        public void onFail(String code, Object object) {
//            super.onFail(code, object);
//            AppointmentMineActivityNew.start(AppointCampaignActivity.this,3);
////            AppointmentMineActivityNew.start(AppointCampaignActivity.this,3);
//
////            startActivity(new Intent(AppointCampaignActivity.this, AppointmentActivity.class));
//            finish();
//        }
//
//        @Override
//        public void onFree() {
//            AppointSuccessActivity.start(AppointCampaignActivity.this, bean.getStartTime(), false, campaignPresent.getShareInfo());
//            ToastGlobal.showLong("预约成功");
//        }
//    };
//
//    @Override
//    public void onCheckedChanged(CustomNestRadioGroup group, int checkedId) {
//        switch (checkedId) {
//            case R.id.cb_alipay:
//                payType = PAY_ALI;
//                break;
//            case R.id.cb_weixin:
//                payType = PAY_WEIXIN;
//                break;
//            default:
//                break;
//        }
//    }
//
//    @Override
//    public void setCampaignCouponResult(List<CouponBean> usableCoupons) {
//        this.usableCoupons = usableCoupons;
//        if (usableCoupons == null || usableCoupons.isEmpty()) {
//            tvCoupon.setText("无可用");
//            tvCoupon.setCompoundDrawables(null, null, null, null);
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (data != null) {
//            if (requestCode == REQUEST_SELECT_COUPON) {
//                CouponBean couponBean = data.getParcelableExtra("coupon");
//                selectedUserCouponId = couponBean.getUser_coupon_id();
//                couponId = couponBean.getId();
//                tvCoupon.setText(FormatUtil.parseDouble(couponBean.getActual()) >= 0
//                        ? String.format(getString(R.string.rmb_minus_price_double),
//                        FormatUtil.parseDouble(couponBean.getActual())) : getString(R.string.please_select));
//                tvCouponPrice.setRightContent(String.format(getString(R.string.rmb_minus_price_double),
//                        FormatUtil.parseDouble(couponBean.getActual())));
//                tvPrice.setText(String.format(getString(R.string.rmb_price_double),
//                        FormatUtil.parseDouble(bean.getPrice()) - FormatUtil.parseDouble(couponBean.getActual())));
//            }
//        }
//    }
//}
