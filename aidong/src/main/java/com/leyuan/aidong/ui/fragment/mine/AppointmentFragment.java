package com.leyuan.aidong.ui.fragment.mine;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.AppointmentBean;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.activity.mine.adapter.AppointmentAdapter;
import com.leyuan.aidong.ui.mvp.presenter.AppointmentPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.AppointmentPresentImpl;
import com.leyuan.aidong.ui.mvp.view.AppointmentFragmentView;
import com.leyuan.aidong.widget.customview.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * 预约
 * Created by song on 2016/8/31.
 */
public class AppointmentFragment extends BaseFragment implements AppointmentFragmentView{
    public static final String ALL = "all";
    public static final String JOINED = "joined";
    public static final String UN_JOIN = "unJoin";
    private String type;
    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private int currPage = 1;
    private List<AppointmentBean> data;
    private AppointmentAdapter appointmentAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    private AppointmentPresent present;

    public static AppointmentFragment newInstance(String type){
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        AppointmentFragment fragment = new AppointmentFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        present = new AppointmentPresentImpl(getContext(),this);
        Bundle bundle = getArguments();
        if(bundle != null){
            type = bundle.getString("type");
        }
        return inflater.inflate(R.layout.fragment_appointment,null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
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
        switcherLayout = new SwitcherLayout(getContext(),refreshLayout);
        switcherLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present.commonLoadData(switcherLayout,type);
            }
        });
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_appointment);
        data = new ArrayList<>();
        appointmentAdapter = new AppointmentAdapter(getContext());
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(appointmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (data != null && data.size() >= pageSize) {
                present.requestMoreData(recyclerView,type,pageSize,currPage);
            }
        }
    };

    @Override
    public void updateRecyclerView(List<AppointmentBean> appointmentBeanList) {
        if(refreshLayout.isRefreshing()){
            data.clear();
            refreshLayout.setRefreshing(false);
        }
        data.addAll(appointmentBeanList);
        appointmentAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyView() {
        switcherLayout.showEmptyLayout();
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }
}
