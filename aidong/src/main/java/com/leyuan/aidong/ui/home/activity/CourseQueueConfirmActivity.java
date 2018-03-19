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

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CouponBean;
import com.leyuan.aidong.entity.course.CourseAppointBean;
import com.leyuan.aidong.entity.course.CourseBeanNew;
import com.leyuan.aidong.entity.course.CourseQueueBean;
import com.leyuan.aidong.entity.course.CourseStore;
import com.leyuan.aidong.entity.model.UserCoach;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.activity.AppointDetailCourseNewActivity;
import com.leyuan.aidong.ui.mine.activity.SelectCouponActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.ConfirmCourseQueuePresentImpl;
import com.leyuan.aidong.ui.mvp.view.ConfirmCourseQueueView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DialogUtils;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.TelephoneManager;
import com.leyuan.aidong.utils.ToastGlobal;
import com.leyuan.aidong.widget.CommonTitleLayout;
import com.leyuan.aidong.widget.CustomNestRadioGroup;

import java.util.List;

import static com.leyuan.aidong.R.id.layout_course_coupon;
import static com.leyuan.aidong.R.id.txt_queue_location;
import static com.leyuan.aidong.utils.Constant.REQUEST_SELECT_COUPON;

/**
 * Created by user on 2017/10/31.
 */

public class CourseQueueConfirmActivity extends BaseActivity implements View.OnClickListener, ConfirmCourseQueueView {

    private static final java.lang.String TAG = "CourseQueueConfirmActivity";
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

    private ConfirmCourseQueuePresentImpl confirmOrderCoursePresent;

    CourseBeanNew course;
    private double realPrice;
    private List<CouponBean> usableCoupons;
    private String couponId;
    private String selectedUserCouponId;
    private TextView txtQueueLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        course = getIntent().getParcelableExtra("course");
        setContentView(R.layout.activity_course_queue_confirm);
        initView();
        initData();

        setListener();
    }


    private void initView() {
        layoutTitle = (CommonTitleLayout) findViewById(R.id.layout_title);
        layoutCourseCoach = (RelativeLayout) findViewById(R.id.layout_course_coach);

        txtQueueLocation = (TextView) findViewById(txt_queue_location);
        imgCourse = (ImageView) findViewById(R.id.img_course);
        txtCourseName = (TextView) findViewById(R.id.txt_course_name);
        txtCoachName = (TextView) findViewById(R.id.txt_coach_name);
        txtCourseTime = (TextView) findViewById(R.id.txt_course_time);

        txtRoomName = (TextView) findViewById(R.id.txt_room_name);
        txtCourseLocation = (TextView) findViewById(R.id.txt_course_location);

        layoutCourseLocation = (RelativeLayout) findViewById(R.id.layout_course_location);
        layoutCourseCoupon = (RelativeLayout) findViewById(layout_course_coupon);
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
        if (course.isMember()) {
            realPrice = course.getMember_price();
            txtPriceTotal.setText("￥" + course.getMember_price());
            txtPriceReal.setText("￥" + realPrice);
        } else {
            realPrice = course.getPrice();
            txtPriceTotal.setText("￥" + course.getPrice());
            txtPriceReal.setText("￥" + realPrice);
        }


        UserCoach userCoach = App.getInstance().getUser();

        confirmOrderCoursePresent = new ConfirmCourseQueuePresentImpl(this, this);
        confirmOrderCoursePresent.getCourseAvaliableCoupons(course.getId());

        txtQueueLocation.setText("当前排队:第" + course.getMyQueue_number() + "位");
        txtCourseName.setText(course.getName());
        txtCoachName.setText(course.getCoach().getName());

        if (course.getImage() != null && course.getImage().size() > 0) {
            GlideLoader.getInstance().displayImage(course.getImage().get(0), imgCourse);
        }

        txtCourseTime.setText(course.getClass_time());
        txtRoomName.setText(course.getStore().getName());
        txtCourseLocation.setText(course.getStore().getAddress());


        if (userCoach != null)
            txtPhone.setText(userCoach.getMobile());


    }

    private void setListener() {
        layoutTitle.setLeftIconListener(this);
        findViewById(R.id.bt_queue_immediately).setOnClickListener(this);
        findViewById(R.id.layout_course_coach).setOnClickListener(this);
        findViewById(R.id.layout_course_location).setOnClickListener(this);
        findViewById(R.id.img_telephone).setOnClickListener(this);
        findViewById(R.id.layout_course_coupon).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_queue_immediately:
                DialogUtils.showDialog(this, "", false);
                confirmOrderCoursePresent.submitCourseQueue(course.getId(), couponId);
                break;
            case R.id.img_left:
                finish();
                break;
            case R.id.layout_course_coach:
                if (course != null)
                    CourseDetailNewActivity.start(this, course.getId());
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
                    SelectCouponActivity.startForResult(this, course.getPrice() + "",
                            couponId, selectedUserCouponId, usableCoupons, REQUEST_SELECT_COUPON);
                }else {
                    ToastGlobal.showLongConsecutive("无可用优惠券");
                }
                break;

        }
    }

    public static void start(Context context, CourseBeanNew course) {
        Intent intent = new Intent(context, CourseQueueConfirmActivity.class);
        intent.putExtra("course", course);
        context.startActivity(intent);
    }

    @Override
    public void onGetsubmitCourseQueue(CourseQueueBean queue) {
        DialogUtils.dismissDialog();
        if (queue != null) {
            ToastGlobal.showLongConsecutive("排队成功");
            startActivity(new Intent(this, CourseQueueSuccessActivity.class));
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constant.BROADCAST_ACTION_COURSE_QUEUE_SUCCESS));
            finish();
        }
    }

    @Override
    public void onGetCourseAvaliableCoupons(List<CouponBean> coupon) {
        this.usableCoupons = coupon;
    }

    @Override
    public void onQueueAppointSuccess(CourseAppointBean appointment) {
        if (appointment != null) {
            AppointDetailCourseNewActivity.appointStart(this, appointment.getId());
        }
    }


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
