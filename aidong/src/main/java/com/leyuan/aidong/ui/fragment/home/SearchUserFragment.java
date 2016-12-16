package com.leyuan.aidong.ui.fragment.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.activity.discover.adapter.UserAdapter;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.ui.mvp.presenter.SearchPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.SearchPresentImpl;
import com.leyuan.aidong.ui.mvp.view.SearchUserFragmentView;
import com.leyuan.aidong.widget.customview.SwitcherLayout;
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
public class SearchUserFragment extends BaseFragment implements SearchUserFragmentView {

    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private int currPage = 1;
    private List<UserBean> data;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private UserAdapter userAdapter;

    private SearchPresent present;
    private String keyword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        pageSize = 20;
        present = new SearchPresentImpl(getContext(),this);
        Bundle bundle = getArguments();
        if(bundle != null){
            keyword = bundle.getString("keyword");
        }
        return inflater.inflate(R.layout.fragment_result,null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initSwipeRefreshLayout(view);
        initRecyclerView(view);
        present.commonUserData(switcherLayout,keyword);
    }

    private void initSwipeRefreshLayout(View view) {
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(getContext(), refreshLayout);
        setColorSchemeResources(refreshLayout);
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
    public void updateRecyclerView(List<UserBean> userBeanList) {
        if(refreshLayout.isRefreshing()){
            data.clear();
            refreshLayout.setRefreshing(false);
        }
        data.addAll(userBeanList);
        userAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }
}
