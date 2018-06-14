package com.example.aidong.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aidong.R;
import com.example.aidong .config.ConstantUrl;
import com.example.aidong .entity.CouponBean;
import com.example.aidong .entity.course.CouponCourseShareBean;
import com.example.aidong .entity.course.CourseAppointResult;
import com.example.aidong .entity.course.CourseBeanNew;
import com.example.aidong .entity.course.CourseStore;
import com.example.aidong .entity.model.UserCoach;
import com.example.aidong .module.pay.PayInterface;
import com.example.aidong .module.pay.SimplePayListener;
import com.example.aidong .ui.App;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .ui.mine.activity.AppointmentMineActivityNew;
import com.example.aidong .ui.mine.activity.SelectCouponActivity;
import com.example.aidong .ui.mine.fragment.CouponFragment;
import com.example.aidong .ui.mvp.presenter.impl.ConfirmOrderCoursePresentImpl;
import com.example.aidong .ui.mvp.presenter.impl.CouponPresentImpl;
import com.example.aidong .ui.mvp.view.ConfirmOrderCourseView;
import com.example.aidong .ui.mvp.view.CouponFragmentView;
import com.example.aidong .utils.Constant;
import com.example.aidong .utils.DialogUtils;
import com.example.aidong .utils.FormatUtil;
import com.example.aidong .utils.GlideLoader;
import com.example.aidong .utils.Logger;
import com.example.aidong .utils.TelephoneManager;
import com.example.aidong .utils.ToastGlobal;
import com.example.aidong .utils.constant.PayType;
import com.example.aidong .widget.CommonTitleLayout;
import com.example.aidong .widget.CustomNestRadioGroup;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.List;

import static com.example.aidong .ui.App.context;
import static com.example.aidong .utils.Constant.PAY_ALI;
import static com.example.aidong .utils.Constant.PAY_WEIXIN;
import static com.example.aidong .utils.Constant.REQUEST_SELECT_COUPON;

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
    private CouponCourseShareBean courseShareBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        course = getIntent().getParcelableExtra("course");
        setContentView(R.layout.activity_confirm_order_course);
        initView();
        initData();


//        CouponPresentImpl presents = new CouponPresentImpl(this, this);
//        presents.pullToRefreshData(CouponFragment.VALID);
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
        findViewById(R.id.layout_remark).setVisibility(View.GONE);
        confirmOrderCoursePresent = new ConfirmOrderCoursePresentImpl(this, this);

        DialogUtils.showDialog(this, "", false);
        confirmOrderCoursePresent.getCourseAvaliableCoupons(course.getId());

        if (course.isMember()) {
            realPrice = course.getMember_price();

            txtPriceReal.setText(String.format(getString(R.string.rmb_price_double),
                    realPrice));
            txtPriceTotal.setText(String.format(getString(R.string.rmb_price_double),
                    course.getMember_price()));
        } else {
            realPrice = course.getPrice();
            txtPriceReal.setText(String.format(getString(R.string.rmb_price_double),
                    realPrice));
            txtPriceTotal.setText(String.format(getString(R.string.rmb_price_double),
                    course.getMember_price()));
        }

        payType = PAY_ALI;
        UserCoach userCoach = App.getInstance().getUser();


        txtCourseName.setText(course.getName());
        txtCoachName.setText(course.getCoach().getName());
        if (course.getImage() != null && course.getImage().size() > 0) {
            GlideLoader.getInstance().displayImage2(course.getImage().get(0), imgCourse);
        }

        txtCourseTime.setText(course.getClass_time());
        txtRoomName.setText(course.getStore().getName() + "-" + course.getStore().getClassroom() + (TextUtils.isEmpty(course.getSeatChoosed()) ? "" : course.getSeatChoosed()));
        txtCourseLocation.setText(course.getStore().getAddress());

        if (userCoach != null)
            txtPhone.setText(userCoach.getMobile());

        radioGroup.setOnCheckedChangeListener(this);
        findViewById(R.id.bt_pay_immediately).setOnClickListener(this);
        layoutCourseCoupon.setOnClickListener(this);
        layoutCourseCoach.setOnClickListener(this);
        layoutCourseLocation.setOnClickListener(this);
        img_telephone.setOnClickListener(this);
        layoutTitle.setLeftIconListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_left:
                finish();
                break;
            case R.id.layout_course_coach:
                CourseDetailNewActivity.start(this, course.getId());

//                if (course.getCoach() != null)
//                    UserInfoActivity.start(this, course.getCoach().getContact());
                break;
            case R.id.layout_course_location:
                CourseStore store = course.getStore();
                if (store != null) {
                    MapActivity.start(this, store.getName(), store.getName(), store.getAddress(),
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
                } else {
                    ToastGlobal.showShortConsecutive("无可用优惠券");
                }
                break;
            case R.id.bt_pay_immediately:
                Logger.i(TAG, "bt_pay_immediately onClick");


                if (PAY_WEIXIN.equals(payType)) {


                    if (api == null) {
                        api = WXAPIFactory.createWXAPI(context, ConstantUrl.WX_APP_ID, false);
                    }
                    if (!api.isWXAppInstalled()) {
                        Toast.makeText(context, "没有安装微信", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                DialogUtils.showDialog(this, "", false);
                confirmOrderCoursePresent.confirmAppointCourse(course.getId(), couponId, course.getSeatChoosed(), payType, payListener);

                break;

            default:
                break;
        }
    }
    private IWXAPI api;

    public static void start(Context context, CourseBeanNew course) {
        Intent intent = new Intent(context, ConfirmOrderCourseActivity.class);
        intent.putExtra("course", course);
        context.startActivity(intent);
    }

    @Override
    public void onGetCourseAvaliableCoupons(List<CouponBean> coupon) {
        DialogUtils.dismissDialog();
        this.usableCoupons = coupon;
        if (coupon == null || coupon.isEmpty()) {
            txtCoupon.setText("无可用");
            txtCoupon.setTextColor(ContextCompat.getColor(this,R.color.c9));
            layoutCourseCoupon.setVisibility(View.GONE);
        } else {
            if (usableCoupons.size()>0) {
                txtCoupon.setText(usableCoupons.size()+"张可用");
            }

            layoutCourseCoupon.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCourseAppointResult(CourseAppointResult appointment) {
        DialogUtils.dismissDialog();
        if (appointment != null) {
            this.courseShareBean = appointment.getShare();
        }
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

            AppointCourseSuccessActivity.start(ConfirmOrderCourseActivity.this, course.getClass_time(), true, courseShareBean);
            //  Toast.makeText(ConfirmOrderCourseActivity.this, "支付成功", Toast.LENGTH_LONG).show();
            finish();

        }

        @Override
        public void onFail(String code, Object object) {
            super.onFail(code, object);

            DialogUtils.dismissDialog();
            try {
                LocalBroadcastManager.getInstance(ConfirmOrderCourseActivity.this).sendBroadcast(new Intent(Constant.BROADCAST_ACTION_COURSE_PAY_SFAIL));

                // Toast.makeText(ConfirmOrderCourseActivity.this, "支付失败", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                AppointmentMineActivityNew.start(ConfirmOrderCourseActivity.this, 0,0);
                finish();
            }


        }

        @Override
        public void onFree() {
            DialogUtils.dismissDialog();
            LocalBroadcastManager.getInstance(ConfirmOrderCourseActivity.this).sendBroadcast(new Intent(Constant.BROADCAST_ACTION_COURSE_PAY_SUCCESS));
            AppointCourseSuccessActivity.start(ConfirmOrderCourseActivity.this, course.getClass_time(), true, courseShareBean);
            //  Toast.makeText(ConfirmOrderCourseActivity.this, "预约成功", Toast.LENGTH_LONG).show();
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
                txtCoupon.setText(FormatUtil.parseDouble(couponBean.getActual()) > 0&&!TextUtils.isEmpty(couponId)
                        ? String.format(getString(R.string.rmb_minus_price_double),
                        FormatUtil.parseDouble(couponBean.getActual())) :usableCoupons.size()+"张可用");
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

  //  public List<CouponBean> couponBeanList;

//    @Override
//    public void updateRecyclerView(List<CouponBean> couponBeanList) {
//        this.couponBeanList = couponBeanList ;
//
//        if (couponBeanList.size()>0) {
//            txtCoupon.setText(couponBeanList.size()+"张可用");
//        }
//    }
//
//    @Override
//    public void showEmptyView() {
//
//    }
//
//    @Override
//    public void showEndFooterView() {
//
//    }
}
