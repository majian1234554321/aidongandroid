package com.leyuan.aidong.ui.home.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.course.CourseChooseSeatAdapter;
import com.leyuan.aidong.adapter.course.CourseChooseSeatIndexAdapter;
import com.leyuan.aidong.entity.course.CourseBeanNew;
import com.leyuan.aidong.entity.course.CourseSeat;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.ToastGlobal;
import com.leyuan.aidong.widget.CommonTitleLayout;

public class CourseChooseSeat extends BaseActivity implements View.OnClickListener, CourseChooseSeatAdapter.OnItemClickListener {
    private FrameLayout activityCourseChooseSeat;
    private CommonTitleLayout layoutTitle;
    private TextView txtClassName;
    private TextView txtCourseTime;
    private RecyclerView recyclerViewLeft;
    private RecyclerView recyclerViewRight;
    private LinearLayout layoutCourseChoosed;
    private TextView txtSeatChoosed;
    private LinearLayout layoutShowChooseType;

    CourseBeanNew course;
    private CourseChooseSeatIndexAdapter adapterIndex ;
    private CourseChooseSeatAdapter adapterSeat;
    private String positionSeat;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) return;

            switch (action) {
                case Constant.BROADCAST_ACTION_COURSE_PAY_SUCCESS:
                    finish();
                    break;
                case Constant.BROADCAST_ACTION_COURSE_PAY_SFAIL:
                    finish();
                    break;
                case Constant.BROADCAST_ACTION_COURSE_QUEUE_SUCCESS:
                    finish();
                    break;
            }
        }
    };

    public static void start(Context context, CourseBeanNew course) {
        Intent intent = new Intent(context, CourseChooseSeat.class);
        intent.putExtra("course", course);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_choose_seat);
        course = getIntent().getParcelableExtra("course");

        initView();
        initData();
        setListener();
    }

    private void initView() {
        activityCourseChooseSeat = (FrameLayout) findViewById(R.id.activity_course_choose_seat);
        layoutTitle = (CommonTitleLayout) findViewById(R.id.layout_title);
        txtClassName = (TextView) findViewById(R.id.txt_class_name);
        txtCourseTime = (TextView) findViewById(R.id.txt_course_time);
        recyclerViewLeft = (RecyclerView) findViewById(R.id.recyclerView_left);
        recyclerViewRight = (RecyclerView) findViewById(R.id.recyclerView_right);
        findViewById(R.id.txt_confirm_choose).setOnClickListener(this);

        layoutCourseChoosed = (LinearLayout) findViewById(R.id.layout_course_choosed);
        txtSeatChoosed = (TextView) findViewById(R.id.txt_seat_choosed);
        findViewById(R.id.bt_clear_choosed).setOnClickListener(this);
        layoutShowChooseType = (LinearLayout) findViewById(R.id.layout_show_choose_type);
    }

    private void setListener() {

        IntentFilter filter = new IntentFilter();

        filter.addAction(Constant.BROADCAST_ACTION_COURSE_PAY_SUCCESS);
        filter.addAction(Constant.BROADCAST_ACTION_COURSE_PAY_SFAIL);
        filter.addAction(Constant.BROADCAST_ACTION_COURSE_QUEUE_SUCCESS);

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
    }

    private void initData() {
        layoutShowChooseType.setVisibility(View.VISIBLE);
        layoutCourseChoosed.setVisibility(View.GONE);

        layoutTitle.setTxtTitle(course.getName());
        txtClassName.setText(course.getClass_room());
        txtCourseTime.setText(course.getClass_time());

        CourseSeat seat = course.getSeat();
        recyclerViewRight.setLayoutManager(new GridLayoutManager(this,seat.getCol()));

        adapterSeat = new CourseChooseSeatAdapter(this,seat,this);
        recyclerViewRight.setAdapter(adapterSeat);

        recyclerViewLeft.setLayoutManager(new LinearLayoutManager(this));
        adapterIndex = new CourseChooseSeatIndexAdapter(this,seat);
        recyclerViewLeft.setAdapter(adapterIndex);

        recyclerViewLeft.setNestedScrollingEnabled(false);
        recyclerViewRight.setNestedScrollingEnabled(false);
        layoutTitle.setLeftIconListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_confirm_choose:
                if(positionSeat == null){
                    ToastGlobal.showLongConsecutive("必须选择座位");
                    return;
                }
                ConfirmOrderCourseActivity.start(this, course);
                break;
            case R.id.bt_clear_choosed:
                adapterSeat.resetChoosedState();
                layoutShowChooseType.setVisibility(View.VISIBLE);
                layoutCourseChoosed.setVisibility(View.GONE);

                course.setSeatChoosed(null);
                this.positionSeat = null;

                break;
        }
    }

    @Override
    public void onSeatChoosed(String positionSeat) {
        course.setSeatChoosed(positionSeat);
        this.positionSeat = positionSeat;
        txtSeatChoosed.setText("已选"+positionSeat);
        layoutShowChooseType.setVisibility(View.GONE);
        layoutCourseChoosed.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
}
