package com.leyuan.aidong.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CouponBean;
import com.leyuan.aidong.entity.course.CourseAppointBean;
import com.leyuan.aidong.entity.course.CourseBeanNew;
import com.leyuan.aidong.entity.course.CourseStore;
import com.leyuan.aidong.entity.model.UserCoach;
import com.leyuan.aidong.module.pay.PayInterface;
import com.leyuan.aidong.module.pay.SimplePayListener;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.activity.AppointmentMineActivityNew;
import com.leyuan.aidong.ui.mine.activity.SelectCouponActivity;
import com.leyuan.aidong.ui.mine.activity.UserInfoActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.ConfirmOrderCoursePresentImpl;
import com.leyuan.aidong.ui.mvp.view.ConfirmOrderCourseView;
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

import java.util.List;

import static com.leyuan.aidong.utils.Constant.PAY_ALI;
import static com.leyuan.aidong.utils.Constant.PAY_WEIXIN;
import static com.leyuan.aidong.utils.Constant.REQUEST_SELECT_COUPON;

/**
 * Created by user on 2017/11/2.
 */

public class ConfirmOrderCourseActivity extends BaseActivity implements View.OnClickListener, ConfirmOrderCourseView, CustomNestRadioGroup.OnCheckedChangeListener {

    private static final java.lang.String TAG = "ConfirmOrderCourseActivity";
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

    private ConfirmOrderCoursePresentImpl confirmOrderCoursePresent;

    CourseBeanNew course;
    private double realPrice;
    @PayType
    String payType;
    private List<CouponBean> usableCoupons;
    private String couponId;
    private String selectedUserCouponId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        course = getIntent().getParcelableExtra("course");
        setContentView(R.layout.activity_confirm_order_course);
        initView();
        initData();
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

        if (course.isMember_only() && course.isMember()) {
            realPrice = course.getMember_price();
            txtPriceTotal.setText("￥" + course.getMember_price());
            txtPriceReal.setText("￥" + realPrice);
        } else {
            realPrice = course.getPrice();
            txtPriceTotal.setText("￥" + course.getPrice());
            txtPriceReal.setText("￥" + realPrice);
        }

        payType = PAY_ALI;
        UserCoach userCoach = App.getInstance().getUser();

        confirmOrderCoursePresent = new ConfirmOrderCoursePresentImpl(this, this);
        confirmOrderCoursePresent.getCourseAvaliableCoupons(course.getId());

        txtCourseName.setText(course.getName());
        txtCoachName.setText(course.getCoach().getName());
        GlideLoader.getInstance().displayImage(course.getCoach().getAvatar(), imgCourse);
        txtCourseTime.setText(course.getClass_time());
        txtRoomName.setText(course.getStore().getName());
        txtCourseLocation.setText(course.getStore().getAddress());

        if (userCoach != null)
            txtPhone.setText(userCoach.getMobile());

        radioGroup.setOnCheckedChangeListener(this);
        findViewById(R.id.bt_pay_immediately).setOnClickListener(this);
        layoutCourseCoupon.setOnClickListener(this);
        layoutCourseCoach.setOnClickListener(this);
        layoutCourseLocation.setOnClickListener(this);
        img_telephone.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_course_coach:
                if (course.getCoach() != null)
                    UserInfoActivity.start(this, course.getCoach().getContact());
                break;
            case R.id.layout_course_location:
                CourseStore store = course.getStore();
                if (store != null) {
                    MapActivity.start(this, course.getName(), store.getName(), store.getAddress(),
                            store.getCoordinate()[0] + "", store.getCoordinate()[1] + "");
                }
                break;
            case R.id.img_telephone:
                if (course.getStore() != null) {
                    TelephoneManager.callImmediate(this, course.getStore().getTel());
                }
                break;
            case R.id.layout_course_coupon:
                Logger.i(TAG, "layout_course_coupon onClick");
                if (usableCoupons != null && !usableCoupons.isEmpty()) {
                    SelectCouponActivity.startForResult(this, course.getPrice() + "", couponId,
                            selectedUserCouponId, usableCoupons, REQUEST_SELECT_COUPON);
                }else {
                    ToastGlobal.showShortConsecutive("无可用优惠券");
                }
                break;
            case R.id.bt_pay_immediately:
                Logger.i(TAG, "bt_pay_immediately onClick");
                DialogUtils.showDialog(this, "", false);
                confirmOrderCoursePresent.confirmAppointCourse(course.getId(), couponId, course.getSeatChoosed(), payType, payListener);
                break;
        }
    }

    public static void start(Context context, CourseBeanNew course) {
        Intent intent = new Intent(context, ConfirmOrderCourseActivity.class);
        intent.putExtra("course", course);
        context.startActivity(intent);
    }

    @Override
    public void onGetCourseAvaliableCoupons(List<CouponBean> coupon) {
        this.usableCoupons = coupon;
        if(coupon ==null || coupon.isEmpty()){
            txtCoupon.setText("无可用优惠券");
        }
    }

    @Override
    public void onCourseAppointResult(CourseAppointBean appointment) {
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
            LocalBroadcastManager.getInstance(ConfirmOrderCourseActivity.this).sendBroadcast(new Intent(Constant.BROADCAST_ACTION_COURSE_PAY_SUCCESS));

            AppointSuccessActivity.start(ConfirmOrderCourseActivity.this, course.getClass_time(), true, null);
            Toast.makeText(ConfirmOrderCourseActivity.this, "支付成功", Toast.LENGTH_LONG).show();
            finish();

        }

        @Override
        public void onFail(String code, Object object) {
            super.onFail(code, object);
            DialogUtils.dismissDialog();

            LocalBroadcastManager.getInstance(ConfirmOrderCourseActivity.this).sendBroadcast(new Intent(Constant.BROADCAST_ACTION_COURSE_PAY_SFAIL));

            Toast.makeText(ConfirmOrderCourseActivity.this, "支付失败", Toast.LENGTH_LONG).show();

            AppointmentMineActivityNew.start(ConfirmOrderCourseActivity.this,0);
            finish();

        }

        @Override
        public void onFree() {
            DialogUtils.dismissDialog();
            LocalBroadcastManager.getInstance(ConfirmOrderCourseActivity.this).sendBroadcast(new Intent(Constant.BROADCAST_ACTION_COURSE_PAY_SUCCESS));
            AppointSuccessActivity.start(ConfirmOrderCourseActivity.this, course.getClass_time(), true, null);
            Toast.makeText(ConfirmOrderCourseActivity.this, "预约成功", Toast.LENGTH_LONG).show();
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
                txtCoupon.setText(FormatUtil.parseDouble(couponBean.getActual()) != 0
                        ? String.format(getString(R.string.rmb_minus_price_double),
                        FormatUtil.parseDouble(couponBean.getActual())) : getString(R.string.please_select));
                txtCouponSubtract.setText(String.format(getString(R.string.rmb_minus_price_double),
                        FormatUtil.parseDouble(couponBean.getActual())));
                txtPriceReal.setText(String.format(getString(R.string.rmb_price_double),
                        course.getPrice() - FormatUtil.parseDouble(couponBean.getActual())));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DialogUtils.releaseDialog();
    }
}
