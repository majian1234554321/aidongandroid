package com.example.aidong.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.activity.discover.adapter.UserAdapter;
import com.leyuan.support.entity.UserBean;
import com.leyuan.support.widget.customview.SimpleTitleBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动与课程已报名的人
 * Created by song on 2016/9/23.
 */
public class AppointmentUserActivity extends BaseActivity{

    private SimpleTitleBar titleBar;
    private RecyclerView rvUser;
    private List<UserBean> data;

    public static void actionStart(Context context, ArrayList<UserBean> userList){
        Intent intent = new Intent(context, AppointmentUserActivity.class);
        intent.putParcelableArrayListExtra("userList",userList);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_user);
        if(getIntent() != null){
            data = getIntent().getParcelableExtra("userList");
        }
        titleBar = (SimpleTitleBar)findViewById(R.id.title_bar);
        rvUser = (RecyclerView) findViewById(R.id.rv_user);
        rvUser.setLayoutManager(new LinearLayoutManager(this));
        rvUser.setAdapter(new UserAdapter(this,data));
        titleBar.setBackListener(new SimpleTitleBar.OnBackClickListener() {
            @Override
            public void onBack() {
                finish();
            }
        });
    }
}
