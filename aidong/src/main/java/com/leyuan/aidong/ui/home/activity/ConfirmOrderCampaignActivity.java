package com.leyuan.aidong.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CampaignDetailBean;
import com.leyuan.aidong.entity.CouponBean;
import com.leyuan.aidong.entity.model.UserCoach;
import com.leyuan.aidong.module.pay.PayInterface;
import com.leyuan.aidong.module.pay.SimplePayListener;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.activity.AppointmentMineActivityNew;
import com.leyuan.aidong.ui.mine.activity.SelectCouponActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.CampaignPresentImpl;
import com.leyuan.aidong.ui.mvp.view.AppointCampaignActivityView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DialogUtils;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.TelephoneManager;
import com.leyuan.aidong.utils.ToastGlobal;
import com.leyuan.aidong.utils.constant.PayType;
import com.leyuan.aidong.widget.CommonTitleLayout;
import com.leyuan.aidong.widget.CustomNestRadioGroup;

import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.utils.Constant.PAY_ALI;
import static com.leyuan.aidong.utils.Constant.PAY_WEIXIN;
import static com.leyuan.aidong.utils.Constant.REQUEST_SELECT_COUPON;

/**
 * Created by user on 2017/11/2.
 * 详情确认订单
 */

public class ConfirmOrderCampaignActivity extends BaseActivity implements AppointCampaignActivityView, View.OnClickListener, CustomNestRadioGroup.OnCheckedChangeListener {

    private static final String TAG = "ConfirmOrderCourseActivity";
    private CommonTitleLayout layoutTitle;
    private RelativeLayout layoutCourseCoach;
    private RelativeLayout layoutCourseLocation;
    private RelativeLayout layoutCourseCoupon;
    private TextView txtCoupon;
    private TextView txtPhone;
    private TextView txtPriceTotal;
    private TextView txtCouponSubtract;
    private TextView txtPriceReal;
    private CustomNestRadioGroup radioGroup;
    private RadioButton cbAlipay;
    private RadioButton cbWeixin;
    private ImageView imgCourse;
    private TextView txtCourseName;
    private TextView txtCoachName;
    private TextView txtCourseTime;
    private TextView txtRoomName;
    private TextView txtCourseLocation;
    private ImageView img_telephone;
    private EditText edit_remark;


    private String couponId;
    private float integral;
    private
    @PayType
    String payType;
    private String userName;

    private CampaignDetailBean course;
    private CampaignPresentImpl campaignPresent;
    private List<CouponBean> usableCoupons = new ArrayList<>();
    private String selectedUserCouponId;
    private double totalPrice;
    private double realPrice;
    private UserCoach userCoach;


    public static void start(Context context, CampaignDetailBean campaignBean) {
        Intent intent = new Intent(context, ConfirmOrderCampaignActivity.class);
        intent.putExtra("bean", campaignBean);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_confirm_order_campaign);

        if (getIntent() != null) {
            course = getIntent().getParcelableExtra("bean");
        }

        initView();
        initData();

        campaignPresent = new CampaignPresentImpl(this, this);
        campaignPresent.getCampaignAvailableCoupon(course.skucode, course.amount);
    }


    private void initView() {
        layoutTitle = (CommonTitleLayout) findViewById(R.id.layout_title);
        layoutCourseCoach = (RelativeLayout) findViewById(R.id.layout_course_coach);

        imgCourse = (ImageView) findViewById(R.id.img_course);
        txtCourseName = (TextView) findViewById(R.id.txt_course_name);
        txtCoachName = (TextView) findViewById(R.id.txt_coach_name);
        txtCourseTime = (TextView) findViewById(R.id.txt_course_time);

        txtRoomName = (TextView) findViewById(R.id.txt_room_name);
        txtCourseLocation = (TextView) findViewById(R.id.txt_course_location);
        img_telephone = (ImageView) findViewById(R.id.img_telephone);
        img_telephone.setVisibility(View.GONE);

        edit_remark = (EditText) findViewById(R.id.edit_remark);

        layoutCourseLocation = (RelativeLayout) findViewById(R.id.layout_course_location);
        layoutCourseCoupon = (RelativeLayout) findViewById(R.id.layout_course_coupon);
        txtCoupon = (TextView) findViewById(R.id.txt_coupon);
        txtPhone = (TextView) findViewById(R.id.txt_phone);
        txtPriceTotal = (TextView) findViewById(R.id.txt_price_total);
        txtCouponSubtract = (TextView) findViewById(R.id.txt_coupon_subtract);
        txtPriceReal = (TextView) findViewById(R.id.txt_price_real);
        radioGroup = (CustomNestRadioGroup) findViewById(R.id.radio_group);
        cbAlipay = (RadioButton) findViewById(R.id.cb_alipay);
        cbWeixin = (RadioButton) findViewById(R.id.cb_weixin);
    }

    private void initData() {
        findViewById(R.id.layout_remark).setVisibility(View.VISIBLE);

        DialogUtils.showDialog(this, "", false);
        totalPrice = FormatUtil.parseInt(course.amount) * FormatUtil.parseDouble(course.skuPrice);
        realPrice = totalPrice;
        txtPriceReal.setText("￥" + realPrice);
        txtPriceTotal.setText("￥" + totalPrice);

        payType = PAY_ALI;
        userCoach = App.getInstance().getUser();

        txtCourseName.setText(course.getName());
        txtCoachName.setText(course.skuTime);
        if (course.getImage() != null && course.getImage().size() > 0) {
            GlideLoader.getInstance().displayImage(course.getImage().get(0), imgCourse);
        }

        txtCourseTime.setText("共" + course.amount + "张票");
        txtRoomName.setText(course.getLandmark());
        txtCourseLocation.setText(course.getAddress());

        if (userCoach != null)
            txtPhone.setText(userCoach.getMobile());

        radioGroup.setOnCheckedChangeListener(this);
        findViewById(R.id.bt_pay_immediately).setOnClickListener(this);
        layoutCourseCoupon.setOnClickListener(this);
        layoutCourseCoach.setOnClickListener(this);
        layoutCourseLocation.setOnClickListener(this);
        img_telephone.setOnClickListener(this);
        layoutTitle.setLeftIconListener(this);
        txtCourseLocation.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_left:
                finish();
                break;
            case R.id.layout_course_coach:
                ActivityCircleDetailActivity.start(this, course.getCampaignId());

                break;
            case R.id.layout_course_location:
                MapActivity.start(this, course.getName(), course.getLandmark(), course.getAddress(),
                        course.getCoordinate().getLat(), course.getCoordinate().getLng() + "");
                break;
            case R.id.img_telephone:

                TelephoneManager.callImmediate(this, "");
                break;
            case R.id.layout_course_coupon:

                Logger.i(TAG, "layout_course_coupon onClick");
                if (usableCoupons != null && !usableCoupons.isEmpty()) {
                    SelectCouponActivity.startForResult(this, course.getPrice() + "", couponId,
                            selectedUserCouponId, usableCoupons, REQUEST_SELECT_COUPON);
                } else {
                    ToastGlobal.showShortConsecutive("无可用优惠券");
                }
                break;
            case R.id.bt_pay_immediately:
                Logger.i(TAG, "bt_pay_immediately onClick");

                if (TextUtils.isEmpty(userCoach.getMobile())) {
                    ToastGlobal.showLong("请先绑定手机");
                } else {
                    DialogUtils.showDialog(this, "", true);
                    campaignPresent.buyCampaign(course.skucode, couponId, integral,
                            payType, userCoach.getName(), userCoach.getMobile(), payListener, course.amount, edit_remark.getText().toString().trim());
                }
                break;
            case R.id.txt_course_location:
                MapActivity.start(this, course.getName(), course.getOrganizer(), course.getAddress(),
                        course.getCoordinate().getLat(), course.getCoordinate().getLng());

                break;
        }
    }


    @Override
    public void setCampaignCouponResult(List<CouponBean> coupon) {
        DialogUtils.dismissDialog();
        this.usableCoupons = coupon;
        if (coupon == null || coupon.isEmpty()) {
            txtCoupon.setText("无可用优惠券");
        } else {
            txtCoupon.setText("请选择");
        }
    }

    @Override
    public void OnBuyError() {
        DialogUtils.dismissDialog();
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

    private PayInterface.PayListener payListener = new SimplePayListener(this) {
        @Override
        public void onSuccess(String code, Object object) {

            DialogUtils.dismissDialog();

            LocalBroadcastManager.getInstance(ConfirmOrderCampaignActivity.this).sendBroadcast(new Intent(Constant.BROADCAST_ACTION_CAMPAIGN_PAY_SUCCESS));

            AppointSuccessActivity.start(ConfirmOrderCampaignActivity.this, txtCoachName.getText().toString().trim(), false, campaignPresent.getShareInfo());

            ToastGlobal.showLongConsecutive("支付成功");
            finish();

        }

        @Override
        public void onFail(String code, Object object) {
            super.onFail(code, object);

            DialogUtils.dismissDialog();

            LocalBroadcastManager.getInstance(ConfirmOrderCampaignActivity.this).sendBroadcast(new Intent(Constant.BROADCAST_ACTION_CAMPAIGN_PAY_FAILED));

            Toast.makeText(ConfirmOrderCampaignActivity.this, "支付失败", Toast.LENGTH_LONG).show();

            AppointmentMineActivityNew.start(ConfirmOrderCampaignActivity.this, 3);
            finish();

        }

        @Override
        public void onFree() {
            DialogUtils.dismissDialog();
            LocalBroadcastManager.getInstance(ConfirmOrderCampaignActivity.this).sendBroadcast(new Intent(Constant.BROADCAST_ACTION_CAMPAIGN_PAY_SUCCESS));
            AppointSuccessActivity.start(ConfirmOrderCampaignActivity.this, txtCoachName.getText().toString().trim(), false, campaignPresent.getShareInfo());

            ToastGlobal.showLongConsecutive("预约成功");
            finish();

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (requestCode == REQUEST_SELECT_COUPON) {
                CouponBean couponBean = data.getParcelableExtra("coupon");
                selectedUserCouponId = couponBean.getUser_coupon_id();
                couponId = couponBean.getId();
                txtCoupon.setText(FormatUtil.parseDouble(couponBean.getActual()) >= 0
                        ? String.format(getString(R.string.rmb_minus_price_double),
                        FormatUtil.parseDouble(couponBean.getActual())) : getString(R.string.please_select));
                txtCouponSubtract.setText(String.format(getString(R.string.rmb_minus_price_double),
                        FormatUtil.parseDouble(couponBean.getActual())));
                txtPriceReal.setText(String.format(getString(R.string.rmb_price_double),
                        realPrice - FormatUtil.parseDouble(couponBean.getActual())));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DialogUtils.releaseDialog();
    }

}
