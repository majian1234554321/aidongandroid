package com.leyuan.aidong.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.course.CourseBeanNew;
import com.leyuan.aidong.entity.course.CourseQueueBean;
import com.leyuan.aidong.entity.course.CourseStore;
import com.leyuan.aidong.entity.model.UserCoach;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.AppointmentCoursePresentImpl;
import com.leyuan.aidong.ui.mvp.view.CourseQueueView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DialogUtils;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.TelephoneManager;
import com.leyuan.aidong.widget.CommonTitleLayout;
import com.leyuan.aidong.widget.CustomNestRadioGroup;

import static com.leyuan.aidong.R.id.txt_queue_location;

/**
 * Created by user on 2017/10/31.
 */

public class CourseQueueDetailActivity extends BaseActivity implements View.OnClickListener, CourseQueueView {

    private CommonTitleLayout layoutTitle;
    private RelativeLayout layoutCourseCoach,rl_empty;
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
    Button button;
    String queueId, courseId;
    private AppointmentCoursePresentImpl coursePresent;
    private double realPrice;
    private CourseQueueBean queue;
    private TextView txtqueuelocation;

    public static void startFromAppoint(Context context, String queueId) {
        Intent intent = new Intent(context, CourseQueueDetailActivity.class);
        intent.putExtra("queueId", queueId);
        context.startActivity(intent);
    }

    public static void startFromCourse(Context context, String courseId) {
        Intent intent = new Intent(context, CourseQueueDetailActivity.class);
        intent.putExtra("courseId", courseId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_queue);
        queueId = getIntent().getStringExtra("queueId");
        courseId = getIntent().getStringExtra("courseId");
        initView();

        coursePresent = new AppointmentCoursePresentImpl(this);
        coursePresent.setCourseQueueViewCallback(this);

        if (queueId != null) {
            coursePresent.getCourseQueueDetailFromQueue(queueId);
        } else {
            coursePresent.getCourseQueueDetailFromCourse(courseId);
        }

        initData();
    }


    private void initView() {
        layoutTitle = (CommonTitleLayout) findViewById(R.id.layout_title);
        rl_empty = (RelativeLayout) findViewById(R.id.rl_empty);
        txtqueuelocation = (TextView) findViewById(txt_queue_location);

        layoutCourseCoach = (RelativeLayout) findViewById(R.id.layout_course_coach);

        imgCourse = (ImageView) findViewById(R.id.img_course);
        txtCourseName = (TextView) findViewById(R.id.txt_course_name);
        txtCoachName = (TextView) findViewById(R.id.txt_coach_name);
        txtCourseTime = (TextView) findViewById(R.id.txt_course_time);

        txtRoomName = (TextView) findViewById(R.id.txt_room_name);
        txtCourseLocation = (TextView) findViewById(R.id.txt_course_location);

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
        layoutCourseCoupon.setVisibility(View.GONE);
        button = (Button) findViewById(R.id.bt_queue_immediately);

        layoutTitle.setLeftIconListener(this);
        findViewById(R.id.bt_queue_immediately).setOnClickListener(this);
        findViewById(R.id.layout_course_coach).setOnClickListener(this);
        findViewById(R.id.layout_course_location).setOnClickListener(this);
        findViewById(R.id.img_telephone).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.bt_queue_immediately:
                if (queue == null) return;
                switch (queue.getStatus()) {
                    case CourseQueueBean.queued:
                        DialogUtils.showDialog(this, "", false);
                        coursePresent.cancelCourseQueue(queue.getId());
                        break;
                    case CourseQueueBean.canceled:
                        DialogUtils.showDialog(this, "", false);
                        coursePresent.deleteCourseQueue(queue.getId());

                        break;
                    case CourseQueueBean.appointed:
                        break;
                    case CourseQueueBean.suspended:
                        DialogUtils.showDialog(this, "", false);
                        coursePresent.deleteCourseQueue(queue.getId());

                        break;
                }

                break;


            case R.id.img_left:
                finish();
                break;
            case R.id.layout_course_coach:
                if (course != null)
                    CourseDetailNewActivity.start(this, course.getId());
                break;
            case R.id.layout_course_location:
                if (course != null) {
                    CourseStore store = course.getStore();
                    if (store != null) {
                        MapActivity.start(this, store.getName(), store.getName(), store.getAddress(),
                                store.getCoordinate()[0] + "", store.getCoordinate()[1] + "");
                    }
                }

                break;
            case R.id.img_telephone:
                if (course != null && course.getStore() != null) {
                    TelephoneManager.callImmediate(this, course.getStore().getTel());
                }
                break;
        }
    }

    CourseBeanNew course;

    @Override
    public void ongetCourseQueueDetail(CourseQueueBean queue) {
        if (queue == null) {
            rl_empty.setVisibility(View.VISIBLE);
            button.setVisibility(View.GONE);
            return;
        }

        rl_empty.setVisibility(View.GONE);
        button.setVisibility(View.VISIBLE);

        this.queue = queue;


        UserCoach userCoach = App.getInstance().getUser();
        course = queue.getTimetable();
        realPrice = course.getPrice();
        txtCourseName.setText(course.getName());
        txtCoachName.setText(course.getCoach().getName());
        GlideLoader.getInstance().displayImage(course.getCover(), imgCourse);

        txtCourseTime.setText(course.getClass_time());
        txtRoomName.setText(course.getStore().getRoom());
        txtCourseLocation.setText(course.getStore().getAddress());
        txtPhone.setText(userCoach.getMobile());

        txtCouponSubtract.setText("-￥" + queue.getCoupon());
        txtPriceTotal.setText("￥" + queue.getTotal());
        txtPriceReal.setText("￥" + queue.getPay_amount());
        button.setVisibility(View.VISIBLE);

        switch (queue.getStatus()) {
            case CourseQueueBean.queued:
                txtqueuelocation.setText("当前排队: 第" + queue.getPosition() + "位");
                button.setText(R.string.cancel_queue);
                break;
            case CourseQueueBean.canceled:
                txtqueuelocation.setText("排队已取消");
                button.setText(R.string.delete);

                break;
            case CourseQueueBean.appointed:
                txtqueuelocation.setText(R.string.appointed);
                button.setVisibility(View.GONE);

                break;
            case CourseQueueBean.suspended:
                txtqueuelocation.setText(R.string.suspended);
                button.setText(R.string.delete);

                break;
        }

    }

    @Override
    public void onCancelCourseQueue(BaseBean baseBean) {
        DialogUtils.dismissDialog();
        if (baseBean != null && baseBean.getStatus() == 1) {
            button.setText(R.string.delete);
            txtqueuelocation.setText("排队已取消");
            queue.setStatus(CourseQueueBean.canceled);

            LocalBroadcastManager.getInstance(CourseQueueDetailActivity.this).sendBroadcast(new Intent(Constant.BROADCAST_ACTION_COURSE_QUEUE_CANCELED));

        }
    }

    @Override
    public void onDeleteCourseQueue(boolean b) {
        DialogUtils.dismissDialog();
        if (b) {

            LocalBroadcastManager.getInstance(CourseQueueDetailActivity.this).sendBroadcast(new Intent(Constant.BROADCAST_ACTION_COURSE_QUEUE_DELETED));

            finish();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        DialogUtils.releaseDialog();
    }
}
