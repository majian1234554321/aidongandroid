package com.leyuan.aidong.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.discover.UserFollowCacheAdapter;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.entity.data.FollowData;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.activity.UserInfoActivity;
import com.leyuan.aidong.ui.mine.activity.account.LoginActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.FollowPresentImpl;
import com.leyuan.aidong.ui.mvp.view.AppointmentUserActivityView;
import com.leyuan.aidong.ui.mvp.view.FollowCacheView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DialogUtils;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.widget.SimpleTitleBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动与课程已报名的人
 * Created by song on 2016/9/23.
 */
public class AppointmentUserActivity extends BaseActivity implements UserFollowCacheAdapter.FollowListener, AppointmentUserActivityView, FollowCacheView {
    private SimpleTitleBar titleBar;
    private RecyclerView rvUser;
    private RelativeLayout emptyLayout;
    private List<UserBean> data;
    private int position;
    private UserFollowCacheAdapter userAdapter;
    private FollowPresentImpl present;
    private String title;
    private int itemClickedPosition;

    public static void start(Context context, List<UserBean> userList, String title) {
        Intent starter = new Intent(context, AppointmentUserActivity.class);
        starter.putParcelableArrayListExtra("userList", (ArrayList) userList);
        starter.putExtra("title", title);
        context.startActivity(starter);
    }

    public static void start(Context context, List<UserBean> userList) {
        Intent starter = new Intent(context, AppointmentUserActivity.class);
        starter.putParcelableArrayListExtra("userList", (ArrayList) userList);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_user);
        present = new FollowPresentImpl(this, this);
        if (getIntent() != null) {
            data = getIntent().getParcelableArrayListExtra("userList");
            title = getIntent().getStringExtra("title");

        }
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        rvUser = (RecyclerView) findViewById(R.id.rv_user);
        emptyLayout = (RelativeLayout) findViewById(R.id.rl_empty);
        rvUser.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserFollowCacheAdapter(this);
        userAdapter.setFollowListener(this);
        rvUser.setAdapter(userAdapter);

        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (!TextUtils.isEmpty(title)) {
            titleBar.setTitle(title);
        }

//        present = new FollowPresentImpl(this);

        present.setFollowCacheView(this);
        DialogUtils.showDialog(this, "", true);
        present.getFollowCahceList();

    }

    @Override
    protected void onResume() {
        super.onResume();
        userAdapter.refreshClickedPosition();
    }


    @Override
    public void addFollowResult(BaseBean baseBean) {
//

        Logger.i("Appointuseractivity follow"," addFollowResult ");
        if (baseBean.getStatus() == Constant.OK) {
//            SystemInfoUtils.addFollow(data.get(position));

            data.get(position).followed = true;
            userAdapter.notifyDataSetChanged();

        } else {
            Toast.makeText(this,  baseBean.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void cancelFollowResult(BaseBean baseBean) {


        Logger.i("Appointuseractivity follow"," cancelFollowResult ");

        if (baseBean.getStatus() == Constant.OK) {
//            SystemInfoUtils.removeFollow(data.get(position));
//            data.remove(data.get(position));
            data.get(position).followed = false;
            userAdapter.notifyDataSetChanged();

        } else {
            Toast.makeText(this, baseBean.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onGetFollowCacheList(ArrayList<String> following_ids) {

        DialogUtils.dismissDialog();

//      userAdapter.refreshFollowCacheData(following_ids);

        if (data != null && !data.isEmpty()) {

            for (UserBean user : data) {
                user.followed = FollowData.isFollow(user.getId(), following_ids);
            }

            userAdapter.setData(data);
            emptyLayout.setVisibility(View.GONE);
        } else {
            emptyLayout.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onAddFollow(String id, int position) {
        if (!App.mInstance.isLogin()) {
            startActivityForResult(new Intent(this, LoginActivity.class), Constant.REQUEST_LOGIN);
            return;
        }
        this.position = position;

        UserBean userBean = data.get(position);
        present.addFollow(id, userBean.getTypeByType());

    }

    @Override
    public void onCancelFollow(String id, int position) {
        if (!App.mInstance.isLogin()) {
            startActivityForResult(new Intent(this, LoginActivity.class), Constant.REQUEST_LOGIN);
            return;
        }
        this.position = position;
        UserBean userBean = data.get(position);
        present.cancelFollow(id, userBean.getTypeByType());
    }

    @Override
    public void onItemClick(UserBean userBean, int position) {
        this.itemClickedPosition = position;
        UserInfoActivity.startForResult(this, userBean.getId(), Constant.REQUEST_USER_INFO);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.i("follow onActivityResult", "requestCode = " + requestCode + ", resultCode = " + resultCode);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.REQUEST_USER_INFO:

                    this.data.get(itemClickedPosition).followed =
                            data.getBooleanExtra(Constant.FOLLOW, this.data.get(itemClickedPosition).followed);
                    Logger.i("follow", "onActivityResult follow = " + this.data.get(itemClickedPosition).followed);

                    userAdapter.notifyItemChanged(itemClickedPosition);
                    break;
                case  Constant.REQUEST_LOGIN:
                    present.getFollowCahceList();
                    break;
            }
        }

    }
}
