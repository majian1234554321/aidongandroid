package com.leyuan.aidong.ui.discover.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.mine.SelectItUserListAdapter;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.CampaignBean;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.discover.view.SearchHeaderView;
import com.leyuan.aidong.ui.mvp.presenter.impl.SelectedUserPrensenterImpl;
import com.leyuan.aidong.ui.mvp.view.FollowFragmentView;
import com.leyuan.aidong.ui.mvp.view.SelectedCircleView;
import com.leyuan.aidong.utils.DialogUtils;
import com.leyuan.aidong.widget.CommonTitleLayout;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.RecyclerViewUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018/1/9.
 */

public class SelectedUserActivity extends BaseActivity implements OnRefreshListener, SearchHeaderView.OnSearchListener, SelectedCircleView, FollowFragmentView {

    private static final String TYPE = "following_list";
    private CommonTitleLayout layoutTitle;
    private CustomRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private int currPage = 1;
    private SwitcherLayout switcherLayout;

    private SelectItUserListAdapter adapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    SelectedUserPrensenterImpl present;

    boolean isSearch;
    private String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_user_activity);

        layoutTitle = (CommonTitleLayout) findViewById(R.id.layout_title);
        refreshLayout = (CustomRefreshLayout) findViewById(R.id.refreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        initSwipeRefreshLayout();
        initRecyclerView();
        initSwitcherLayout();

        present = new SelectedUserPrensenterImpl(this, this);
        present.commonLoadData(switcherLayout, TYPE);

        layoutTitle.setLeftIconListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initSwipeRefreshLayout() {
        refreshLayout.setOnRefreshListener(this);
    }


    @Override
    public void onRefresh() {
        currPage = 1;
        RecyclerViewStateUtils.resetFooterViewState(recyclerView);
        if (isSearch) {
            present.pullToRefreshUserData(keyword);
        } else {
            present.pullToRefreshData(TYPE);
        }

    }

    private void initSwitcherLayout() {
        switcherLayout = new SwitcherLayout(this, refreshLayout);
        switcherLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void initRecyclerView() {
        adapter = new SelectItUserListAdapter(this);

        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);

        SearchHeaderView headView = new SearchHeaderView(this);
        headView.setOnsearchListner(this);
        headView.setSearchHint(getResources().getString(R.string.search_more_person));
        headView.setTxtSearchTitle(getResources().getString(R.string.hot_recommend));
        headView.setTxtSearchTitleVisible(View.GONE);
        RecyclerViewUtils.setHeaderView(recyclerView, headView);

    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            currPage++;
            if (isSearch) {
                present.requestMoreUserData(recyclerView, keyword, pageSize, currPage);
            } else {
                present.requestMoreData(recyclerView, pageSize, TYPE, currPage);
            }

        }
    };

    @Override
    public void onSearch(String keyword) {
        isSearch = true;
        currPage = 1;
        this.keyword = keyword;
//        DialogUtils.showDialog(this, "", true);
        present.commonSearcjUser(switcherLayout, keyword);

    }

    @Override
    public void onGetRecommendCircle(ArrayList<CampaignBean> items) {
    }

    @Override
    public void onSearchCircleResult(ArrayList<CampaignBean> items) {
        DialogUtils.dismissDialog();
    }

    private ArrayList<UserBean> data = new ArrayList<>();

    @Override
    public void onRefreshData(List<UserBean> userBeanList) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        data.clear();
        if (userBeanList != null)
            data.addAll(userBeanList);
        adapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
        switcherLayout.showContentLayout();
    }

    @Override
    public void onLoadMoreData(List<UserBean> userBeanList) {
        data.addAll(userBeanList);
        adapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }

    @Override
    public void addFollowResult(BaseBean baseBean) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
//        View view = View.inflate(this, R.layout.empty_course, null);
//        CustomRefreshLayout refreshLayout = (CustomRefreshLayout) view.findViewById(R.id.refreshLayout_empty);
//        refreshLayout.setProgressViewOffset(true, 50, 100);
//        refreshLayout.setOnRefreshListener(this);
//        switcherLayout.addCustomView(view, "empty");
//        switcherLayout.showCustomLayout("empty");

    }

    @Override
    public void cancelFollowResult(BaseBean baseBean) {

    }

    @Override
    public void showEmptyView() {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }

        View view = View.inflate(this, R.layout.empty_course, null);
        ((TextView)view.findViewById(R.id.txt_type)).setText("没有关注的人");
        CustomRefreshLayout refreshLayout = (CustomRefreshLayout) view.findViewById(R.id.refreshLayout_empty);
        refreshLayout.setProgressViewOffset(true, 50, 100);
        refreshLayout.setOnRefreshListener(this);
        switcherLayout.addCustomView(view, "empty");
        switcherLayout.showCustomLayout("empty");
    }
}
