package com.leyuan.aidong.ui.mine.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.mine.AppointmentAdapter;
import com.leyuan.aidong.entity.AppointmentBean;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.ui.BaseLazyFragment;
import com.leyuan.aidong.ui.mine.activity.AppointCampaignDetailActivity;
import com.leyuan.aidong.ui.mine.activity.AppointCourseDetailActivity;
import com.leyuan.aidong.ui.mvp.presenter.AppointmentPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.AppointmentPresentImpl;
import com.leyuan.aidong.ui.mvp.view.AppointmentFragmentView;
import com.leyuan.aidong.utils.Constant;
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
public class AppointmentFragment extends BaseLazyFragment implements AppointmentFragmentView{
    public static final String ALL = "all";
    public static final String JOINED = "signed";
    public static final String UN_JOIN = "unsigned";
    private String type;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private RelativeLayout emptyLayout;

    private int currPage = 1;
    private List<AppointmentBean> data;
    private AppointmentAdapter appointmentAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    private AppointmentPresent present;

    @Override
    public View initView() {
        Bundle bundle = getArguments();
        if(bundle != null){
            type = bundle.getString("type");
        }
        present = new AppointmentPresentImpl(getContext(),this);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_appointment,null);
        initSwipeRefreshLayout(view);
        initRecyclerView(view);
        return view;
    }

    @Override
    public void initData() {
        present.commonLoadData(type);
    }

    private void initSwipeRefreshLayout(View view) {
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refreshLayout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                RecyclerViewStateUtils.resetFooterViewState(recyclerView);
                present.pullToRefreshData(refreshLayout,type);
            }
        });
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_appointment);
        emptyLayout = (RelativeLayout) view.findViewById(R.id.rl_empty);
        data = new ArrayList<>();
        appointmentAdapter = new AppointmentAdapter(getContext());
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(appointmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
        appointmentAdapter.setAppointmentListener(new AppointCallback());
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
    public void onRecyclerViewRefresh(List<AppointmentBean> appointmentBeanList) {
        if(refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
        data.clear();
        data.addAll(appointmentBeanList);
        appointmentAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRecyclerViewLoadMore(List<AppointmentBean> appointmentBeanList) {
        data.addAll(appointmentBeanList);
        appointmentAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyView() {
        emptyLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyView() {
        emptyLayout.setVisibility(View.GONE);
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }

    private class AppointCallback implements AppointmentAdapter.AppointmentListener{

        @Override
        public void onPayOrder(String type, String id) {
            if("course".equals(type)){
                AppointCourseDetailActivity.start(getContext(),id);
            }else {
                AppointCampaignDetailActivity.start(getContext(),id);
            }
        }

        @Override
        public void onDeleteOrder(String id) {
            present.deleteAppoint(id);
        }

        @Override
        public void onConfirmJoin(String id) {
            present.confirmAppoint(id);
        }

        @Override
        public void onCancel(String id) {
            present.cancelAppoint(id);
        }

    }

    @Override
    public void cancelAppointmentResult(BaseBean baseBean) {
        if(baseBean.getStatus() == Constant.OK){
            present.commonLoadData(type);
            Toast.makeText(getContext(),"取消成功",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getContext(),"取消失败" + baseBean.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void confirmAppointmentResult(BaseBean baseBean) {
        if(baseBean.getStatus() == Constant.OK){
            present.commonLoadData(type);
            Toast.makeText(getContext(),"确认成功",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getContext(),"确认失败" + baseBean.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void deleteAppointmentResult(BaseBean baseBean) {
        if(baseBean.getStatus() == Constant.OK){
            present.commonLoadData(type);
            Toast.makeText(getContext(),"删除成功",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getContext(),"删除失败" + baseBean.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
