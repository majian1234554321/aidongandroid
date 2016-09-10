package com.example.aidong.fragment.mine;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aidong.BaseFragment;
import com.example.aidong.R;
import com.example.aidong.activity.mine.adapter.FollowAdapter;
import com.leyuan.support.entity.UserBean;
import com.leyuan.support.mvp.presenter.FansFragmentPresent;
import com.leyuan.support.mvp.presenter.impl.FansFragmentPresentImpl;
import com.leyuan.support.mvp.view.FansFragmentView;
import com.leyuan.support.widget.customview.SwitcherLayout;
import com.leyuan.support.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.support.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.support.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.support.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * 粉丝
 * Created by song on 2016/9/10.
 */
public class FansFragment extends BaseFragment implements FansFragmentView{

    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private int currPage = 1;
    private List<UserBean> data;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private FollowAdapter followAdapter;

    private FansFragmentPresent present;



    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fans,null);
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        pageSize = 20;
        present = new FansFragmentPresentImpl(this,getContext());
        initSwipeRefreshLayout(view);
        initRecyclerView(view);
        present.commonLoadData(switcherLayout);
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
                present.pullToRefreshData();
            }
        });

        switcherLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present.commonLoadData(switcherLayout);
            }
        });
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_fans);
        data = new ArrayList<>();
        followAdapter = new FollowAdapter();
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(followAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (data != null && data.size() >= pageSize) {
                present.requestMoreData(recyclerView,pageSize,currPage);
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
        //followAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }
}
