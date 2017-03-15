package com.leyuan.aidong.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.discover.UserAdapter;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.widget.SimpleTitleBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动与课程已报名的人
 * Created by song on 2016/9/23.
 */
public class AppointmentUserActivity extends BaseActivity{
    private SimpleTitleBar titleBar;
    private RecyclerView rvUser;
    private RelativeLayout emptyLayout;
    private List<UserBean> data;

    public static void start(Context context,List<UserBean> userList) {
        Intent starter = new Intent(context, AppointmentUserActivity.class);
        starter.putParcelableArrayListExtra("userList",(ArrayList)userList);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_user);
        if(getIntent() != null){
            data = getIntent().getParcelableArrayListExtra("userList");
        }
        titleBar = (SimpleTitleBar)findViewById(R.id.title_bar);
        rvUser = (RecyclerView) findViewById(R.id.rv_user);
        emptyLayout = (RelativeLayout) findViewById(R.id.rl_empty);
        rvUser.setLayoutManager(new LinearLayoutManager(this));
        UserAdapter userAdapter = new UserAdapter(this);
        rvUser.setAdapter(userAdapter);
        if(!data.isEmpty()) {
            userAdapter.setData(data);
            emptyLayout.setVisibility(View.GONE);
        }else {
            emptyLayout.setVisibility(View.VISIBLE);
        }

        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
