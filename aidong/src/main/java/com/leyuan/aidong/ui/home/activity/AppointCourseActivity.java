package com.leyuan.aidong.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CouponBean;
import com.leyuan.aidong.entity.CourseDetailBean;
import com.leyuan.aidong.module.pay.PayInterface;
import com.leyuan.aidong.module.pay.SimplePayListener;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.activity.AppointmentActivity;
import com.leyuan.aidong.ui.mine.activity.SelectCouponActivity;
import com.leyuan.aidong.ui.mine.activity.setting.PhoneBindingActivity;
import com.leyuan.aidong.ui.mine.activity.setting.PhoneUnBindingActivity;
import com.leyuan.aidong.ui.mvp.presenter.CoursePresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.CoursePresentImpl;
import com.leyuan.aidong.ui.mvp.view.AppointCourseActivityView;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.ToastGlobal;
import com.leyuan.aidong.utils.UiManager;
import com.leyuan.aidong.utils.constant.PayType;
import com.leyuan.aidong.widget.CustomNestRadioGroup;
import com.leyuan.aidong.widget.ExtendTextView;
import com.leyuan.aidong.widget.SimpleTitleBar;

import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.utils.Constant.PAY_ALI;
import static com.leyuan.aidong.utils.Constant.PAY_WEIXIN;
import static com.leyuan.aidong.utils.Constant.REQUEST_SELECT_COUPON;

/**
 * 预约课程信息
 * Created by song on 2016/9/12.
 */
//todo 与活动预约界面合成一个界面
public class AppointCourseActivity extends BaseActivity implements View.OnClickListener,
        CustomNestRadioGroup.OnCheckedChangeListener, AppointCourseActivityView {
    private SimpleTitleBar titleBar;

    //预约人信息
    private EditText tvUserName;
    private TextView tvUserPhone;

    //课程信息
    private TextView tvType;
    private ImageView dvCover;
    private TextView tvCourseName;
    private TextView tvClassroom;
    private ExtendTextView tvTime;
    private TextView tvVenuesName;
    private TextView tvAddress;
    private TextView tvCoupon;
    private LinearLayout goldLayout;

    //订单信息
    private ExtendTextView tvTotalPrice;
    private ExtendTextView tvCouponPrice;
    private ExtendTextView tvDiscountPrice;
    private ExtendTextView tvAibi;
    private ExtendTextView tvAidou;

    //支付
    private CustomNestRadioGroup radioGroup;
    private TextView tvTip;
    private TextView tvPrice;
    private TextView tvPay;

    private String couponId;
    private String integral;
    private
    @PayType
    String payType;
    private String userName;
    private String contactMobile;

    private CourseDetailBean bean;
    private CoursePresent coursePresent;
    private List<CouponBean> usableCoupons = new ArrayList<>();
    private boolean isVip = false;
    private String selectedUserCouponId;

    public static void start(Context context, CourseDetailBean courseDetailBean) {
        Intent starter = new Intent(context, AppointCourseActivity.class);
        starter.putExtra("bean", courseDetailBean);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_course);
        coursePresent = new CoursePresentImpl(this, this);
        payType = PAY_ALI;
        if (getIntent() != null) {
            bean = getIntent().getParcelableExtra("bean");
        }
        initView();
        setListener();
        coursePresent.getSpecifyCourseCoupon(bean.getCode());
    }

    private void initView() {
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        tvUserName = (EditText) findViewById(R.id.tv_input_name);
        tvUserPhone = (TextView) findViewById(R.id.tv_input_phone);
        tvType = (TextView) findViewById(R.id.tv_type);
        dvCover = (ImageView) findViewById(R.id.dv_cover);
        tvCourseName = (TextView) findViewById(R.id.tv_name);
        tvClassroom = (TextView) findViewById(R.id.tv_shop);
        tvTime = (ExtendTextView) findViewById(R.id.tv_time);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        tvVenuesName = (TextView) findViewById(R.id.tv_venues_name);
        tvCoupon = (TextView) findViewById(R.id.tv_coupon);
        goldLayout = (LinearLayout) findViewById(R.id.ll_gold);
        tvTotalPrice = (ExtendTextView) findViewById(R.id.tv_total_price);
        tvCouponPrice = (ExtendTextView) findViewById(R.id.tv_coupon_price);
        tvDiscountPrice = (ExtendTextView) findViewById(R.id.tv_discount_price);
        tvAibi = (ExtendTextView) findViewById(R.id.tv_aibi);
        tvAidou = (ExtendTextView) findViewById(R.id.tv_aidou);
        radioGroup = (CustomNestRadioGroup) findViewById(R.id.radio_group);
        tvTip = (TextView) findViewById(R.id.tv_tip);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvPay = (TextView) findViewById(R.id.tv_pay);

        GlideLoader.getInstance().displayImage(bean.getCover().get(0), dvCover);
        tvCourseName.setText(bean.getName());

        tvVenuesName.setText(bean.getGym().getName());
        tvClassroom.setText(bean.getClassroom()+"-"+bean.getCoach().getName());
        tvTime.setRightContent(String.format(getString(R.string.detail_time),
                bean.getClassDate(), bean.getClassTime(), bean.getBreakTime()));
        tvAddress.setText(bean.getGym().getAddress());
        tvTotalPrice.setRightContent(String.format(getString(R.string.rmb_price), bean.getPrice()));
        tvPrice.setText(String.format(getString(R.string.rmb_price), bean.getPrice()));
    }

    private void setListener() {
        titleBar.setOnClickListener(this);
        tvCoupon.setOnClickListener(this);
        tvPay.setOnClickListener(this);
        tvUserPhone.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        userName = App.getInstance().getUser().getName();
        contactMobile = App.getInstance().getUser().getMobile();
        if (!TextUtils.isEmpty(userName)) {
            tvUserName.setText(userName);
            tvUserName.setSelection(userName.length());
        }
        if (!TextUtils.isEmpty(contactMobile)) {
            tvUserPhone.setText(contactMobile);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_input_phone:
                contactMobile = App.getInstance().getUser().getMobile();
                if (TextUtils.isEmpty(contactMobile)) {
                    UiManager.activityJump(this, PhoneBindingActivity.class);
                } else {
                    UiManager.activityJump(this, PhoneUnBindingActivity.class);
                }
                break;
            case R.id.tv_coupon:
                if (usableCoupons != null && !usableCoupons.isEmpty()) {
                    SelectCouponActivity.startForResult(this, bean.getPrice(), couponId,selectedUserCouponId, usableCoupons, REQUEST_SELECT_COUPON);
                }
                break;
            case R.id.tv_pay:
                String userRealName = tvUserName.getText().toString().trim();
                if (TextUtils.isEmpty(userRealName)) {
                    ToastGlobal.showLong("姓名不能为空");
                } else if (TextUtils.isEmpty(contactMobile)) {
                    ToastGlobal.showLong("请先绑定手机");
                } else {
                    String vip = isVip ? "1" : "0";
                    coursePresent.buyCourse(bean.getCode(), couponId, integral, payType,
                            userRealName, contactMobile, payListener, vip);
                }
                break;
            default:
                break;
        }
    }

    private PayInterface.PayListener payListener = new SimplePayListener(this) {
        @Override
        public void onSuccess(String code, Object object) {
            AppointSuccessActivity.start(AppointCourseActivity.this, bean.getClassDate() + " " + bean.getClassTime(), true, coursePresent.getShareInfo());
            Toast.makeText(AppointCourseActivity.this, "支付成功", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFail(String code, Object object) {
            super.onFail(code, object);
            startActivity(new Intent(AppointCourseActivity.this, AppointmentActivity.class));
            finish();
        }

        @Override
        public void onFree() {
            AppointSuccessActivity.start(AppointCourseActivity.this, bean.getClassDate() + " " + bean.getClassTime(), true, coursePresent.getShareInfo());
            Toast.makeText(AppointCourseActivity.this, "预约成功", Toast.LENGTH_LONG).show();
            Logger.i("AppointCourseActivity", "share Info =   " + coursePresent.getShareInfo().toString());
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
    public void setCourseCouponResult(List<CouponBean> usableCoupons) {
        this.usableCoupons = usableCoupons;
        if (usableCoupons == null || usableCoupons.isEmpty()) {
            tvCoupon.setText("无可用");
            tvCoupon.setCompoundDrawables(null, null, null, null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (requestCode == REQUEST_SELECT_COUPON) {
                CouponBean couponBean = data.getParcelableExtra("coupon");
                selectedUserCouponId = couponBean.getUser_coupon_id();
                couponId = couponBean.getId();
                tvCoupon.setText(FormatUtil.parseDouble(couponBean.getDiscount()) != 0
                        ? String.format(getString(R.string.rmb_minus_price_double),
                        FormatUtil.parseDouble(couponBean.getDiscount())) : getString(R.string.please_select));
                tvCouponPrice.setRightContent(String.format(getString(R.string.rmb_minus_price_double),
                        FormatUtil.parseDouble(couponBean.getDiscount())));
                tvPrice.setText(String.format(getString(R.string.rmb_price_double),
                        FormatUtil.parseDouble(bean.getPrice()) - FormatUtil.parseDouble(couponBean.getDiscount())));
            }
        }
    }
}
