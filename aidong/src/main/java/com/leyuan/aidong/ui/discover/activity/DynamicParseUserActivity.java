package com.leyuan.aidong.ui.discover.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.discover.UserAdapter;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.activity.account.LoginActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.DynamicParsePresent;
import com.leyuan.aidong.ui.mvp.view.DynamicParseUserView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.SystemInfoUtils;
import com.leyuan.aidong.widget.SimpleTitleBar;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;

import java.util.List;

/**
 * 动态里面点赞的人列表
 * Created by song on 2017/3/23.
 */
public class DynamicParseUserActivity extends BaseActivity implements DynamicParseUserView, UserAdapter.FollowListener {
    private SimpleTitleBar titleBar;
    private RecyclerView rvUser;
    private RelativeLayout emptyLayout;
    private List<UserBean> data;
    private String dynamicId;

    private DynamicParsePresent dynamicPresent;
    private int page = 1;
    private UserAdapter userAdapter;
    private int position;
    private HeaderAndFooterRecyclerViewAdapter wrapAdapter;

    public static void start(Context context, String dynamicId) {
        Intent starter = new Intent(context, DynamicParseUserActivity.class);
        starter.putExtra("dynamicId", dynamicId);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_parse_user);
        if (getIntent() != null) {
            dynamicId = getIntent().getStringExtra("dynamicId");
        }
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        rvUser = (RecyclerView) findViewById(R.id.rv_user);
        emptyLayout = (RelativeLayout) findViewById(R.id.rl_empty);
        rvUser.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(this);
        userAdapter.setFollowListener(this);

         wrapAdapter = new HeaderAndFooterRecyclerViewAdapter(userAdapter);

        rvUser.setAdapter(wrapAdapter);
        rvUser.addOnScrollListener(onScrollListener);
        dynamicPresent = new DynamicParsePresent(this, this);
        dynamicPresent.getLikes(dynamicId, page);

        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private RecyclerView.OnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);
            page ++;
            dynamicPresent.getLikes(dynamicId, page);
        }
    };

    @Override
    public void onGetUserData(List<UserBean> data, int page) {

        if (page == 1) {
            this.data = data;
            if (data != null && !data.isEmpty()) {
                userAdapter.setData(data);
                wrapAdapter.notifyDataSetChanged();
                emptyLayout.setVisibility(View.GONE);
            } else {
                emptyLayout.setVisibility(View.VISIBLE);
            }
        } else {
            this.data.addAll(data);
            if (data != null && !data.isEmpty()) {
                userAdapter.addMoreData(data);
                wrapAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void addFollowResult(BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
            SystemInfoUtils.addFollow(data.get(position));
            userAdapter.notifyDataSetChanged();
            Toast.makeText(this, R.string.follow_success, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.follow_fail + baseBean.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void cancelFollowResult(BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
            SystemInfoUtils.removeFollow(data.get(position));
            userAdapter.notifyDataSetChanged();
            Toast.makeText(this, R.string.cancel_follow_success, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.cancel_follow_fail + baseBean.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onFollowClicked(int position) {
        this.position = position;
        if (App.mInstance.isLogin()) {
            UserBean userBean = data.get(position);
            if (SystemInfoUtils.isFollow(this, userBean)) {
                dynamicPresent.cancelFollow(userBean.getId());
            } else {
                dynamicPresent.addFollow(userBean.getId());
            }
        } else {
            startActivityForResult(new Intent(this, LoginActivity.class), Constant.REQUEST_LOGIN);
        }
    }
}
