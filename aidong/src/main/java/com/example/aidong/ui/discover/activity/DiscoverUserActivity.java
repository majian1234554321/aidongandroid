package com.example.aidong.ui.discover.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .adapter.discover.UserAdapter;
import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.UserBean;
import com.example.aidong .ui.App;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .ui.mine.activity.account.LoginActivity;
import com.example.aidong .ui.mvp.presenter.DiscoverPresent;
import com.example.aidong .ui.mvp.presenter.impl.DiscoverPresentImpl;
import com.example.aidong .ui.mvp.view.DiscoverUserActivityView;
import com.example.aidong .utils.Constant;
import com.example.aidong .utils.ToastGlobal;
import com.example.aidong .widget.SwitcherLayout;
import com.example.aidong .widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.example.aidong .widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.example.aidong .widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.example.aidong .widget.endlessrecyclerview.weight.LoadingFooter;
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现-用户
 * Created by song on 2016/8/29.
 */
public class DiscoverUserActivity extends BaseActivity implements DiscoverUserActivityView, View.OnClickListener,
        RadioGroup.OnCheckedChangeListener, UserAdapter.FollowListener, OnRefreshListener {
    private static final String TYPE_ALL_IDENTIFY = "";
    private static final String TYPE_USER = "0";
    private static final String TYPE_COACH = "1";
    private static final String GENDER_ALL_GENDER = "";
    private static final String GENDER_MAN = "0";
    private static final String GENDER_WOMAN = "1";

    private ImageView ivBack;
    private TextView tvFilter;
    private SwitcherLayout switcherLayout;
    private CustomRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private DrawerLayout drawerLayout;
    private ScrollView filterLayout;
    private TextView tvFinishFilter;
    private RadioGroup identifyGroup;
    private RadioGroup genderGroup;

    private int currPage = 1;
    private List<UserBean> data = new ArrayList<>();
    private UserAdapter userAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    private String type = TYPE_ALL_IDENTIFY;
    private String gender = GENDER_ALL_GENDER;
    private DiscoverPresentImpl userPresent;
    private boolean isChange = false;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_discover_user);
        userPresent = new DiscoverPresentImpl(this, this);

        initView();
        setListener();
        userPresent.commonLoadUserData(switcherLayout, App.lat, App.lon, gender, type);
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvFilter = (TextView) findViewById(R.id.tv_filter);
        refreshLayout = (CustomRefreshLayout) findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(this, refreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.rv_user);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        filterLayout = (ScrollView) findViewById(R.id.ll_filter);
        tvFinishFilter = (TextView) findViewById(R.id.tv_finish_filter);
        identifyGroup = (RadioGroup) findViewById(R.id.rg_identify);
        genderGroup = (RadioGroup) findViewById(R.id.rg_gender);
        data = new ArrayList<>();
        userAdapter = new UserAdapter(this);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(userAdapter);
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnScrollListener(onScrollListener);
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        tvFilter.setOnClickListener(this);
        tvFinishFilter.setOnClickListener(this);
        identifyGroup.setOnCheckedChangeListener(this);
        genderGroup.setOnCheckedChangeListener(this);
        refreshLayout.setOnRefreshListener(this);
        userAdapter.setFollowListener(this);
        drawerLayout.addDrawerListener(new SimpleDrawerDrawerListener());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (userAdapter != null) {
            userAdapter.refreshClickedPosition();
        }
    }

    @Override
    public void onRefresh() {
        currPage = 1;
        RecyclerViewStateUtils.resetFooterViewState(recyclerView);
        userPresent.pullToRefreshUserData(App.lat, App.lon, gender, type);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            currPage++;
            if (data != null && data.size() >= pageSize) {
                userPresent.requestMoreUserData(recyclerView, App.lat, App.lon, gender, type, pageSize, currPage);
            }
        }
    };

    @Override
    public void updateRecyclerView(List<UserBean> userList) {
        //todo shit presenter 设计有大问题
        if (switcherLayout != null) {
            switcherLayout.showContentLayout();
        }
        if (refreshLayout.isRefreshing()) {
            data.clear();
            refreshLayout.setRefreshing(false);
        }
        data.addAll(userList);
        userAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyView() {
        View view = View.inflate(this, R.layout.empty_discover_user, null);
        switcherLayout.addCustomView(view, "empty");
        switcherLayout.showCustomLayout("empty");
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_finish_filter:
                drawerLayout.closeDrawer(filterLayout);
                break;
            case R.id.tv_filter:
                drawerLayout.openDrawer(filterLayout);
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        isChange = true;
        if (group.getId() == R.id.rg_identify) {
            switch (checkedId) {
                case R.id.rb_all_identify:
                    type = TYPE_ALL_IDENTIFY;
                    break;
                case R.id.rb_user:
                    type = TYPE_USER;
                    break;
                case R.id.rb_coach:
                    type = TYPE_COACH;
                    break;
                default:
                    break;
            }
        } else if (group.getId() == R.id.rg_gender) {
            switch (checkedId) {
                case R.id.rb_all_gender:
                    gender = GENDER_ALL_GENDER;
                    break;
                case R.id.rb_man:
                    gender = GENDER_MAN;
                    break;
                case R.id.rb_woman:
                    gender = GENDER_WOMAN;
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(filterLayout)) {
            drawerLayout.closeDrawer(filterLayout);
        } else {
            finish();
        }
    }

    @Override
    public void onFollowClicked(int position) {
        this.position = position;
        if (App.mInstance.isLogin()) {
            UserBean userBean = data.get(position);
            if (userBean.followed) {
                userPresent.cancelFollow(userBean.getId(), userBean.type);
            } else {
                userPresent.addFollow(userBean.getId(), userBean.type);
            }
        } else {
            startActivityForResult(new Intent(this, LoginActivity.class), Constant.REQUEST_LOGIN);
        }
    }

    @Override
    public void addFollowResult(BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
//            SystemInfoUtils.addFollow(data.get(position));


            data.get(position).followed = true;
            userAdapter.notifyItemChanged(position);

//            userAdapter.notifyDataSetChanged();



        } else {
            ToastGlobal.showLong(R.string.follow_fail);
        }
    }

    @Override
    public void cancelFollowResult(BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {

//            SystemInfoUtils.removeFollow(data.get(position));
//            userAdapter.notifyDataSetChanged();

            data.get(position).followed = false;
            userAdapter.notifyItemChanged(position);



        } else {
            ToastGlobal.showLong(R.string.cancel_follow_fail);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constant.REQUEST_LOGIN) {
                userAdapter.notifyDataSetChanged();
            }
        }
    }

    private class SimpleDrawerDrawerListener extends DrawerLayout.SimpleDrawerListener {
        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            isChange = false;
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            if (isChange) {
                currPage = 1;
                refreshLayout.setRefreshing(true);
                RecyclerViewStateUtils.resetFooterViewState(recyclerView);
                userPresent.pullToRefreshUserData(App.lat, App.lon, gender, type);
            }
        }
    }
}
