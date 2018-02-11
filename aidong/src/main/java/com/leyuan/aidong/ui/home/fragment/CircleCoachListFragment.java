package com.leyuan.aidong.ui.home.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.CircleCoachListAdapter;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.mvp.presenter.impl.FollowPresentImpl;
import com.leyuan.aidong.ui.mvp.view.FollowView;
import com.leyuan.aidong.ui.mvp.view.UserInfoView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.ToastGlobal;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018/1/5.
 */
public class CircleCoachListFragment extends BaseFragment implements UserInfoView, CircleCoachListAdapter.OnAttentionClickListener, FollowView {

    private CustomRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private SwitcherLayout switcherLayout;
    private int currPage = 1;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private CircleCoachListAdapter adapter;
    FollowPresentImpl present;
    private int clickedFollowPosition;
    ArrayList<UserBean> data = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_circle_activity_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initSwipeRefreshLayout(view);
        initRecyclerView(view);
        initSwitcherLayout();
        present = new FollowPresentImpl(getActivity());
        present.setUserViewListener(this);
        present.getRecommendCoachList(currPage);
        present.setFollowListener(this);
//        if()
//        present.getUserFollow("");
    }


    private void initSwipeRefreshLayout(View view) {
        refreshLayout = (CustomRefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                RecyclerViewStateUtils.resetFooterViewState(recyclerView);
                present.getRecommendCoachList(currPage);
            }
        });
    }

    private void initSwitcherLayout() {
        switcherLayout = new SwitcherLayout(getContext(), refreshLayout);
        switcherLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_order);
        adapter = new CircleCoachListAdapter(getActivity());

        adapter.setOnAttentionClickListener(this);

        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
    }


    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            currPage++;
            present.getRecommendCoachList(currPage);
        }
    };


    @Override
    public void onGetUserData(List<UserBean> followings) {
        data.clear();
        data.addAll(followings);
        adapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCourseAttentionClick(String id, int position, boolean followed) {
        if (followed) {
            present.cancelFollow(id, Constant.COACH);
        } else {
            present.addFollow(id, Constant.COACH);
        }
        this.clickedFollowPosition = position;
    }

    @Override
    public void addFollowResult(BaseBean baseBean) {
        if (baseBean.getStatus() == 1) {
            if (clickedFollowPosition < data.size() - 1) {
                wrapperAdapter.notifyDataSetChanged();
                ToastGlobal.showShortConsecutive(R.string.attention_success);
            }
        }
    }

    @Override
    public void cancelFollowResult(BaseBean baseBean) {
        if (baseBean.getStatus() == 1) {
            Logger.i("Myattention  cancelFollowResult clickedFollowPosition = " + clickedFollowPosition);

            if (clickedFollowPosition < data.size()) {
                Logger.i("Myattention  cancelFollowResult");
                data.remove(clickedFollowPosition);
                adapter.notifyDataSetChanged();
                ToastGlobal.showShortConsecutive(R.string.attention_cancel_success);
            } else {
                if (TextUtils.isEmpty(baseBean.getMessage())) {
                    ToastGlobal.showShortConsecutive(R.string.cancel_follow_fail);
                } else {
                    ToastGlobal.showShortConsecutive(baseBean.getMessage());
                }
            }
        }
    }
}
