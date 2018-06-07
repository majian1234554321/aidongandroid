package com.example.aidong.ui.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.aidong.R;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .ui.mine.activity.AppointmentMineActivityNew;

/**
 * Created by user on 2017/10/31.
 */

public class CourseQueueSuccessActivity extends BaseActivity implements View.OnClickListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_queue_success);


        findViewById(R.id.tv_home).setOnClickListener(this);
        findViewById(R.id.tv_look_queue).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_home:
                CourseListActivityNew.start(this,null);
                finish();

                break;
            case R.id.tv_look_queue:
                AppointmentMineActivityNew.start(this,0,1);
                finish();
                break;
        }
    }
}
