package com.leyuan.aidong.ui.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.discover.UserAdapter;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BasePageFragment;
import com.leyuan.aidong.ui.mine.activity.account.LoginActivity;
import com.leyuan.aidong.ui.mvp.presenter.SearchPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.SearchPresentImpl;
import com.leyuan.aidong.ui.mvp.view.SearchUserFragmentView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.SystemInfoUtils;
import com.leyuan.aidong.utils.ToastGlobal;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户搜索结果
 * Created by song on 2016/9/12.
 */
public class SearchUserFragment extends BasePageFragment implements SearchUserFragmentView, UserAdapter.FollowListener {

    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private int currPage = 1;
    private List<UserBean> data;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private UserAdapter userAdapter;

    private SearchPresent present;
    private String keyword;
    private boolean needLoad;

    private int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        present = new SearchPresentImpl(getContext(),this);
        Bundle bundle = getArguments();
        if(bundle != null){
            keyword = bundle.getString("keyword");
            needLoad = bundle.getBoolean("needLoad");
        }
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        initSwipeRefreshLayout(view);
        initRecyclerView(view);
        return view;
    }

    @Override
    public void fetchData() {
        if(needLoad)
        present.commonUserData(switcherLayout,keyword);
    }

    private void initSwipeRefreshLayout(View view) {
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(getContext(), refreshLayout);
        //setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                RecyclerViewStateUtils.resetFooterViewState(recyclerView);
                present.pullToRefreshUserData(keyword);
            }
        });

        switcherLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present.commonUserData(switcherLayout,keyword);
            }
        });
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_result);
        data = new ArrayList<>();
        userAdapter = new UserAdapter(getContext());
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(userAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
        userAdapter.setFollowListener(this);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (data != null && data.size() >= pageSize) {
                present.requestMoreUserData(recyclerView,keyword,pageSize,currPage);
            }
        }
    };

    @Override
    public void onRecyclerViewRefresh(List<UserBean> userBeanList) {
        data.clear();
        if(refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
        data.addAll(userBeanList);
        userAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRecyclerViewLoadMore(List<UserBean> userBeanList) {
        data.addAll(userBeanList);
        userAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyView() {
        View view = View.inflate(getContext(),R.layout.empty_search,null);
        switcherLayout.addCustomView(view,"empty");
        switcherLayout.showCustomLayout("empty");
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }

    public void refreshData(String keyword){
        data.clear();
        this.keyword = keyword;
        present.commonUserData(switcherLayout,keyword);
    }

    @Override
    public void onFollowClicked(int position) {
        this.position = position;
        if(App.mInstance.isLogin()){
            UserBean userBean = data.get(position);
            if(SystemInfoUtils.isFollow(getContext(),userBean)){
                present.cancelFollow(userBean.getId());
            }else {
                present.addFollow(userBean.getId());
            }
        }else {
            startActivityForResult(new Intent(getContext(), LoginActivity.class), Constant.REQUEST_LOGIN);
        }
    }

    @Override
    public void addFollowResult(BaseBean baseBean) {
        if(baseBean.getStatus() == Constant.OK){
            SystemInfoUtils.addFollow(data.get(position));
            userAdapter.notifyDataSetChanged();
            ToastGlobal.showLong(R.string.follow_success);
        }else {
            ToastGlobal.showLong(R.string.follow_fail);
        }
    }

    @Override
    public void cancelFollowResult(BaseBean baseBean) {
        if(baseBean.getStatus() == Constant.OK){
            SystemInfoUtils.removeFollow(data.get(position));
            userAdapter.notifyDataSetChanged();
            ToastGlobal.showLong(R.string.cancel_follow_success);
        }else {
            ToastGlobal.showLong(R.string.cancel_follow_fail);
        }
    }
}
