package com.leyuan.aidong.ui.mine.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.mine.FollowAdapter;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.ui.BaseLazyFragment;
import com.leyuan.aidong.ui.mine.activity.UserInfoActivity;
import com.leyuan.aidong.ui.mvp.presenter.FollowPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.FollowPresentImpl;
import com.leyuan.aidong.ui.mvp.view.FollowFragmentView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.SystemInfoUtils;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * 粉丝
 * Created by song on 2016/9/10.
 */
public class FollowerFragment extends BaseLazyFragment implements FollowFragmentView {
    public static final String FOLLOWER = "followers";

    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private int currPage = 1;
    private List<UserBean> data;
    private FollowAdapter followAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private int position;
    private FollowPresent present;

    @Override
    public View initView() {
        present = new FollowPresentImpl(getContext(),this);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_follow,null);
        initSwipeRefreshLayout(view);
        initSwitcherLayout();
        initRecyclerView(view);
        return view;
    }

    @Override
    public void fetchData() {
        present.commonLoadData(switcherLayout,FOLLOWER);
    }

    private void initSwipeRefreshLayout(View view) {
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refreshLayout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                RecyclerViewStateUtils.resetFooterViewState(recyclerView);
                present.pullToRefreshData(FOLLOWER);
            }
        });
    }

    private void initSwitcherLayout(){
        switcherLayout = new SwitcherLayout(getContext(), refreshLayout);
        switcherLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present.commonLoadData(switcherLayout,FOLLOWER);
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
        followAdapter.setFollowListener(new FollowCallback());
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (data != null && data.size() >= pageSize) {
                present.requestMoreData(recyclerView,pageSize,FOLLOWER,currPage);
            }
        }
    };


    @Override
    public void onRefreshData(List<UserBean> userBeanList) {
        if(refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
        data.clear();
        data.addAll(userBeanList);
        followAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreData(List<UserBean> userBeanList) {
        data.addAll(userBeanList);
        followAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }

    private class FollowCallback extends FollowAdapter.SimpleFollowListener{
        @Override
        public void onAddFollow(String id,int position) {
            FollowerFragment.this.position = position;
            present.addFollow(id);
        }

        @Override
        public void onCancelFollow(String id,int position) {
            FollowerFragment.this.position = position;
            present.cancelFollow(id);
        }

        @Override
        public void onItemClick(String id) {
            UserInfoActivity.start(getContext(),id);
        }
    }

    @Override
    public void addFollowResult(BaseBean baseBean) {
        if(baseBean.getStatus() == Constant.OK){
            SystemInfoUtils.addFollow(data.get(position));
            followAdapter.notifyDataSetChanged();
            Toast.makeText(getContext(),R.string.follow_success,Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getContext(),R.string.follow_fail + baseBean.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void cancelFollowResult(BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
            data.remove(data.get(position));
            SystemInfoUtils.removeFollow(data.get(position));
            followAdapter.notifyDataSetChanged();
            Toast.makeText(getContext(), R.string.cancel_follow_success, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), R.string.cancel_follow_fail + baseBean.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void showEmptyView() {
        View view = View.inflate(getContext(),R.layout.empty_follower,null);
        switcherLayout.addCustomView(view,"emptyFollower");
        switcherLayout.showCustomLayout("emptyFollower");
    }
}
