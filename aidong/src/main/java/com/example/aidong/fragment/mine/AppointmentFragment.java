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
import com.example.aidong.activity.mine.adapter.AppointmentAdapter;
import com.leyuan.support.entity.AppointmentBean;
import com.leyuan.support.mvp.presenter.AppointmentFragmentPresent;
import com.leyuan.support.mvp.presenter.impl.AppointmentFragmentPresentImpl;
import com.leyuan.support.mvp.view.AppointmentFragmentView;
import com.leyuan.support.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.support.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.support.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.support.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * 已参加预约
 * Created by song on 2016/8/31.
 */
public class AppointmentFragment extends BaseFragment implements AppointmentFragmentView{
    public static final String ALL = "all";
    public static final String JOINED = "joined";
    public static final String UN_JOIN = "unJoin";


    private String type;

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private int currPage = 1;
    private List<AppointmentBean> data;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private AppointmentAdapter appointmentAdapter;

    private AppointmentFragmentPresent present;

    /**
     * 设置传递给Fragment的参数
     * @param type 预约类型
     */
    public void setArguments(String type){
        Bundle bundle=new Bundle();
        bundle.putString(type, type);
        AppointmentFragment.this.setArguments(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_appointment,null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (getArguments().containsKey(type)) {
            type = getArguments().getString(type);
        }
        type = ALL;
        pageSize = 20;
        present = new AppointmentFragmentPresentImpl(getContext(),this);
        initSwipeRefreshLayout(view);
        initRecyclerView(view);
    }

    private void initSwipeRefreshLayout(View view) {
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refreshLayout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                present.pullToRefreshData(recyclerView,type);
            }
        });

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                present.pullToRefreshData(recyclerView,type);
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

    }

    @Override
    public void hideEmptyView() {

    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }
}
