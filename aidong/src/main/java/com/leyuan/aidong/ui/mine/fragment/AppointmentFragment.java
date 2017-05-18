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
import com.leyuan.aidong.utils.DateUtils;
import com.leyuan.aidong.utils.ToastGlobal;
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
    public void fetchData() {
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
        data.clear();
        wrapperAdapter.notifyDataSetChanged();
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
        public void onConfirmJoin(int position) {
            AppointmentBean bean = data.get(position);
            if(DateUtils.bigThanOneHour(bean.getStart())) {
                if("course".equals(bean.getAppointmentType())) {
                    ToastGlobal.showLong("未到课程时间，请稍后确认");
                }else {
                    ToastGlobal.showLong("未到活动时间，请稍后确认");
                }
            }else {
                present.confirmAppoint(bean.getId());
            }
        }

        @Override
        public void onCancelJoin(int position) {
            AppointmentBean bean = data.get(position);
            if(DateUtils.started(bean.getStart())){
                if("course".equals(bean.getAppointmentType())) {
                    ToastGlobal.showLong("课程已开始，无法取消");
                }else {
                    ToastGlobal.showLong("活动已开始，无法取消");
                }
            }else {
                present.cancelAppoint(bean.getId());
            }
        }

        @Override
        public void onCancelPay(String id) {
            present.cancelAppoint(id);
        }

        @Override
        public void onCountdownEnd(int position) {
            AppointmentBean appointmentBean = data.get(position);
            appointmentBean.setStatus(AppointmentAdapter.CLOSE);
            appointmentAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void cancelAppointmentResult(BaseBean baseBean) {
        if(baseBean.getStatus() == Constant.OK){
            present.commonLoadData(type);
            Toast.makeText(getContext(),"取消成功",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getContext(), baseBean.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void confirmAppointmentResult(BaseBean baseBean) {
        if(baseBean.getStatus() == Constant.OK){
            present.commonLoadData(type);
            Toast.makeText(getContext(),"确认成功",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getContext(), baseBean.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void deleteAppointmentResult(BaseBean baseBean) {
        if(baseBean.getStatus() == Constant.OK){
            present.commonLoadData(type);
            Toast.makeText(getContext(),"删除成功",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getContext(), baseBean.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
