package com.leyuan.aidong.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.discover.UserAdapter;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.ui.mine.activity.account.LoginActivity;
import com.leyuan.aidong.ui.mvp.presenter.FollowPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.FollowPresentImpl;
import com.leyuan.aidong.ui.mvp.view.AppointmentUserActivityView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.SystemInfoUtils;
import com.leyuan.aidong.widget.SimpleTitleBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动与课程已报名的人
 * Created by song on 2016/9/23.
 */
public class AppointmentUserActivity extends BaseActivity implements UserAdapter.FollowListener,AppointmentUserActivityView {
    private SimpleTitleBar titleBar;
    private RecyclerView rvUser;
    private RelativeLayout emptyLayout;
    private List<UserBean> data;
    private int position;
    private UserAdapter userAdapter;
    private FollowPresent followPresent;

    public static void start(Context context,List<UserBean> userList) {
        Intent starter = new Intent(context, AppointmentUserActivity.class);
        starter.putParcelableArrayListExtra("userList",(ArrayList)userList);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_user);
        followPresent = new FollowPresentImpl(this,this);
        if(getIntent() != null){
            data = getIntent().getParcelableArrayListExtra("userList");
        }
        titleBar = (SimpleTitleBar)findViewById(R.id.title_bar);
        rvUser = (RecyclerView) findViewById(R.id.rv_user);
        emptyLayout = (RelativeLayout) findViewById(R.id.rl_empty);
        rvUser.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(this);
        userAdapter.setFollowListener(this);
        rvUser.setAdapter(userAdapter);
        if(data != null && !data.isEmpty()) {
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

    @Override
    public void onFollowClicked(int position) {
        this.position = position;
        if(App.mInstance.isLogin()){
            UserBean userBean = data.get(position);
            if(SystemInfoUtils.isFollow(this,userBean)){
                followPresent.cancelFollow(userBean.getId());
            }else {
                followPresent.addFollow(userBean.getId());
            }
        }else {
            startActivityForResult(new Intent(this, LoginActivity.class), Constant.REQUEST_LOGIN);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        userAdapter.refreshClickedPosition();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == Constant.REQUEST_LOGIN){
                userAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void addFollowResult(BaseBean baseBean) {
        if(baseBean.getStatus() == Constant.OK){
            SystemInfoUtils.addFollow(data.get(position));
            userAdapter.notifyDataSetChanged();
            Toast.makeText(this,R.string.follow_success,Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,R.string.follow_fail + baseBean.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void cancelFollowResult(BaseBean baseBean) {
        if(baseBean.getStatus() == Constant.OK){
            SystemInfoUtils.removeFollow(data.get(position));
            userAdapter.notifyDataSetChanged();
            Toast.makeText(this,R.string.cancel_follow_success,Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,R.string.cancel_follow_fail + baseBean.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

}
