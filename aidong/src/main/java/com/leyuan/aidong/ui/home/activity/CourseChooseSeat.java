package com.leyuan.aidong.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.course.CourseChooseSeatAdapter;
import com.leyuan.aidong.adapter.course.CourseChooseSeatIndexAdapter;
import com.leyuan.aidong.entity.course.CourseBeanNew;
import com.leyuan.aidong.entity.course.CourseSeat;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.utils.ToastGlobal;
import com.leyuan.aidong.widget.CommonTitleLayout;

public class CourseChooseSeat extends BaseActivity implements View.OnClickListener, CourseChooseSeatAdapter.OnItemClickListener {
    private FrameLayout activityCourseChooseSeat;
    private CommonTitleLayout layoutTitle;
    private TextView txtClassName;
    private TextView txtCourseTime;
    private RecyclerView recyclerViewLeft;
    private RecyclerView recyclerViewRight;

    CourseBeanNew course;
    private CourseChooseSeatIndexAdapter adapterIndex ;
    private CourseChooseSeatAdapter adapterSeat;
    private String positionSeat;

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
    }

    private void initView() {
        activityCourseChooseSeat = (FrameLayout) findViewById(R.id.activity_course_choose_seat);
        layoutTitle = (CommonTitleLayout) findViewById(R.id.layout_title);
        txtClassName = (TextView) findViewById(R.id.txt_class_name);
        txtCourseTime = (TextView) findViewById(R.id.txt_course_time);
        recyclerViewLeft = (RecyclerView) findViewById(R.id.recyclerView_left);
        recyclerViewRight = (RecyclerView) findViewById(R.id.recyclerView_right);
        findViewById(R.id.txt_confirm_choose).setOnClickListener(this);
    }

    private void initData() {
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
        }
    }

    @Override
    public void onSeatChoosed(String positionSeat) {
        course.setSeatChoosed(positionSeat);
        this.positionSeat = positionSeat;
    }
}
