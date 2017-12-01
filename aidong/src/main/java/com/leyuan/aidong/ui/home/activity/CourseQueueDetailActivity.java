package com.leyuan.aidong.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.leyuan.aidong.entity.model.UserCoach;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.AppointmentCoursePresentImpl;
import com.leyuan.aidong.ui.mvp.view.CourseQueueView;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.widget.CommonTitleLayout;
import com.leyuan.aidong.widget.CustomNestRadioGroup;

import static com.leyuan.aidong.R.id.txt_queue_location;

/**
 * Created by user on 2017/10/31.
 */

public class CourseQueueDetailActivity extends BaseActivity implements View.OnClickListener, CourseQueueView {

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
        findViewById(R.id.bt_queue_immediately).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_queue_immediately:
                if (queue == null) return;
                switch (queue.getStatus()) {
                    case CourseQueueBean.queued:
                        coursePresent.cancelCourseQueue(queue.getId());
                        break;
                    case CourseQueueBean.canceled:
                        coursePresent.deleteCourseQueue(queue.getId());

                        break;
                    case CourseQueueBean.appointed:
                        break;
                    case CourseQueueBean.suspended:
                        break;
                }

                break;
        }
    }

    CourseBeanNew course;

    @Override
    public void ongetCourseQueueDetail(CourseQueueBean queue) {
        if (queue == null) return;
        this.queue = queue;



        UserCoach userCoach = App.getInstance().getUser();
        course = queue.getTimetable();
        realPrice = course.getPrice();
        txtCourseName.setText(course.getName());
        txtCoachName.setText(course.getStore().getName());
        GlideLoader.getInstance().displayImage(course.getCover(), imgCourse);

        txtCourseTime.setText(course.getClass_time());
        txtRoomName.setText(course.getStore().getRoom());
        txtCourseLocation.setText(course.getStore().getAddress());
        txtPhone.setText(userCoach.getMobile());

        txtCouponSubtract.setText("-￥" +queue.getCoupon());
        txtPriceTotal.setText("￥" + queue.getTotal());
        txtPriceReal.setText("￥" + queue.getPay_amount());


        switch (queue.getStatus()) {
            case CourseQueueBean.queued:
                txtqueuelocation.setText("当前排队: 第"+queue.getPosition()+"位");
                button.setText(R.string.cancel_queue);
                break;
            case CourseQueueBean.canceled:
                txtqueuelocation.setText("排队已取消");
                button.setText(R.string.delete);

                break;
            case CourseQueueBean.appointed:
                break;
            case CourseQueueBean.suspended:
                break;
        }

    }

    @Override
    public void onCancelCourseQueue(BaseBean baseBean) {
        if (baseBean != null && baseBean.getStatus() ==1 ) {
            button.setText(R.string.delete);
            txtqueuelocation.setText("排队已取消");
            queue.setStatus(CourseQueueBean.canceled);
        }
    }

    @Override
    public void onDeleteCourseQueue(boolean b) {
        if (b) {
            finish();
        }
    }
}
