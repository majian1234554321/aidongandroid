package com.leyuan.aidong.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
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
import com.leyuan.aidong.utils.ToastUtil;
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
    private ExtendTextView tvAddress;
    private TextView tvCoupon;
    private LinearLayout goldLayout;

    //会员身份信息
    private TextView tvVip;
    private TextView tvNoVip;
    private TextView tvNoVipTip;
    private LinearLayout vipTipLayout;

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
    private @PayType String payType;
    private String userName;
    private String contactMobile;

    private CourseDetailBean bean;
    private CoursePresent coursePresent;
    private List<CouponBean> usableCoupons = new ArrayList<>();

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
        tvAddress = (ExtendTextView) findViewById(R.id.tv_address);
        tvCoupon = (TextView) findViewById(R.id.tv_coupon);
        goldLayout = (LinearLayout) findViewById(R.id.ll_gold);
        tvVip = (TextView) findViewById(R.id.tv_vip);
        tvNoVip = (TextView) findViewById(R.id.tv_no_vip);
        tvNoVipTip = (TextView) findViewById(R.id.tv_vip_tip);
        vipTipLayout = (LinearLayout) findViewById(R.id.ll_vip_tip);
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
        tvClassroom.setText(bean.getClassroom()+"-"+bean.getCoach().getName());
        tvTime.setRightContent(String.format(getString(R.string.detail_time),
                bean.getClassDate(), bean.getClassTime(), bean.getBreakTime()));
        tvAddress.setRightContent(bean.getGym().getAddress());
        tvTotalPrice.setRightContent(String.format(getString(R.string.rmb_price), bean.getPrice()));
        tvPrice.setText(String.format(getString(R.string.rmb_price), bean.getPrice()));
        tvNoVipTip.setText(Html.fromHtml(String.format(getString(R.string.no_vip_tip),
                bean.getGym().getAdmission())));
    }

    private void setListener() {
        titleBar.setOnClickListener(this);
        tvCoupon.setOnClickListener(this);
        tvVip.setOnClickListener(this);
        tvNoVip.setOnClickListener(this);
        tvPay.setOnClickListener(this);
        tvUserPhone.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        userName = App.getInstance().getUser().getName();
        contactMobile = App.getInstance().getUser().getMobile();
        if (!TextUtils.isEmpty(userName))
            tvUserName.setText(userName);
        if (!TextUtils.isEmpty(contactMobile))
            tvUserPhone.setText(contactMobile);
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
                    Intent intent = new Intent(this, SelectCouponActivity.class);
                    startActivityForResult(intent, REQUEST_SELECT_COUPON);
                }
                break;
            case R.id.tv_vip:
                tvNoVip.setTextColor(Color.parseColor("#000000"));
                tvVip.setTextColor(Color.parseColor("#ffffff"));
                tvNoVip.setBackgroundResource(R.drawable.shape_solid_corner_white);
                tvVip.setBackgroundResource(R.drawable.shape_solid_corner_black);
                vipTipLayout.setVisibility(View.GONE);
                break;
            case R.id.tv_no_vip:
                tvVip.setTextColor(Color.parseColor("#000000"));
                tvNoVip.setTextColor(Color.parseColor("#ffffff"));
                tvVip.setBackgroundResource(R.drawable.shape_solid_corner_white);
                tvNoVip.setBackgroundResource(R.drawable.shape_solid_corner_black);
                vipTipLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_pay:
                String userRealName = tvUserName.getText().toString().trim();
                if (TextUtils.isEmpty(userRealName)) {
                    ToastUtil.showShort(this, "姓名不能为空");
                } else if (TextUtils.isEmpty(contactMobile)) {
                    ToastUtil.showShort(this, "请先绑定手机");
                } else {
                    coursePresent.buyCourse(bean.getCode(), couponId, integral, payType,
                            userRealName, contactMobile, payListener);
                }
                break;
            default:
                break;
        }
    }

    private PayInterface.PayListener payListener = new SimplePayListener(this) {
        @Override
        public void onSuccess(String code, Object object) {
            Toast.makeText(AppointCourseActivity.this, "支付成功", Toast.LENGTH_LONG).show();
            startActivity(new Intent(AppointCourseActivity.this, AppointSuccessActivity.class));
        }

        @Override
        public void onFail(String code, Object object) {
            super.onFail(code, object);
            startActivity(new Intent(AppointCourseActivity.this, AppointmentActivity.class));
        }
    };

    @Override
    public void onFreeCourseAppointed() {
        AppointSuccessActivity.start(this, bean.getClassDate() + bean.getClassTime());
        Toast.makeText(AppointCourseActivity.this, "预约成功", Toast.LENGTH_LONG).show();
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
    public void setCourseCouponResult(List<CouponBean> usableCoupons) {
        this.usableCoupons = usableCoupons;
        if (usableCoupons == null || usableCoupons.isEmpty()) {
            tvCoupon.setText("无可用");
            tvCoupon.setCompoundDrawables(null, null, null, null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(data != null) {
            if (requestCode == REQUEST_SELECT_COUPON) {
                CouponBean couponBean = data.getParcelableExtra("coupon");
                tvCoupon.setText(String.format(getString(R.string.rmb_minus_price_double),
                        FormatUtil.parseDouble(couponBean.getDiscount())));
                tvCouponPrice.setRightContent(String.format(getString(R.string.rmb_minus_price_double),
                        FormatUtil.parseDouble(couponBean.getDiscount())));
                tvPrice.setText(String.format(getString(R.string.rmb_price_double),
                        FormatUtil.parseDouble(bean.getPrice()) - FormatUtil.parseDouble(couponBean.getDiscount())));
            }
        }
    }
}
