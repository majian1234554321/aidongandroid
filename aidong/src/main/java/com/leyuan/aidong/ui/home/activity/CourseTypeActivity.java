package com.leyuan.aidong.ui.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.CourseTypeAdapter;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.widget.SimpleTitleBar;

/**
 * 小团体课类型界面
 * Created by song on 2017/4/12.
 */
public class CourseTypeActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_type);

        SimpleTitleBar titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_course);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CourseTypeAdapter adapter = new CourseTypeAdapter(this);
        recyclerView.setAdapter(adapter);



        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
