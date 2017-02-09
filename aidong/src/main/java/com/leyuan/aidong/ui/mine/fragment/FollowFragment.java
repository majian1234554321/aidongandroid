package com.leyuan.aidong.ui.mine.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.adapter.mine.FollowAdapter;
import com.leyuan.aidong.ui.mvp.presenter.FollowPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.FollowPresentImpl;
import com.leyuan.aidong.ui.mvp.view.FollowFragmentView;
import com.leyuan.aidong.widget.customview.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * 关注
 * Created by song on 2016/9/10.
 */
public class FollowFragment extends BaseFragment implements FollowFragmentView{
    public static final String FOLLOW = "followings";
    public static final String FANS = "followers";
    private String type;

    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private int currPage = 1;
    private List<UserBean> data;
    private FollowAdapter followAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    private FollowPresent present;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        pageSize = 20;
        present = new FollowPresentImpl(getContext(),this);
        Bundle bundle = getArguments();
        if(bundle != null){
            type = bundle.getString("type");
        }
        return inflater.inflate(R.layout.fragment_follow,null);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        initSwipeRefreshLayout(view);
        initSwitcherLayout();
        initRecyclerView(view);
        present.commonLoadData(switcherLayout,type);
    }

    private void initSwipeRefreshLayout(View view) {
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refreshLayout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                RecyclerViewStateUtils.resetFooterViewState(recyclerView);
                present.pullToRefreshData(type);
            }
        });
    }

    private void initSwitcherLayout(){
        switcherLayout = new SwitcherLayout(getContext(), refreshLayout);
        switcherLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present.commonLoadData(switcherLayout,type);
            }
        });
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_follow);
        data = new ArrayList<>();
        followAdapter = new FollowAdapter(getContext());
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
                present.requestMoreData(recyclerView,pageSize,type,currPage);
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
        followAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }

    @Override
    public void addFollow(BaseBean baseBean) {

    }

    @Override
    public void cancelFollow(BaseBean baseBean) {

    }
}
