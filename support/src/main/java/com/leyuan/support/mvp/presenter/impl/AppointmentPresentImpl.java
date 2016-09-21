package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.support.entity.AppointmentBean;
import com.leyuan.support.entity.data.AppointmentData;
import com.leyuan.support.http.subscriber.CommonSubscriber;
import com.leyuan.support.http.subscriber.RefreshSubscriber;
import com.leyuan.support.http.subscriber.RequestMoreSubscriber;
import com.leyuan.support.mvp.model.AppointmentModel;
import com.leyuan.support.mvp.model.impl.AppointmentModelImpl;
import com.leyuan.support.mvp.presenter.AppointmentPresent;
import com.leyuan.support.mvp.view.AppointmentFragmentView;
import com.leyuan.support.util.Constant;
import com.leyuan.support.widget.customview.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 预约
 * Created by song on 2016/9/2.
 */
public class AppointmentPresentImpl implements AppointmentPresent {
    private Context context;
    private AppointmentModel appointmentModel;
    private AppointmentFragmentView appointmentFragmentView;
    private List<AppointmentBean> appointmentBeanList = new ArrayList<>();

    public AppointmentPresentImpl(Context context, AppointmentFragmentView appointmentFragmentView) {
        this.context = context;
        this.appointmentFragmentView = appointmentFragmentView;
        appointmentModel = new AppointmentModelImpl();
    }

    @Override
    public void commonLoadData(final SwitcherLayout switcherLayout, String type) {
        appointmentModel.getAppointments(new CommonSubscriber<AppointmentData>(switcherLayout) {
            @Override
            public void onNext(AppointmentData appointmentData) {
                if(appointmentData != null){
                    appointmentBeanList = appointmentData.getAppointment();
                }
                if(appointmentBeanList.isEmpty()){
                    appointmentFragmentView.showEmptyView();
                }else{
                    switcherLayout.showContentLayout();
                    appointmentFragmentView.updateRecyclerView(appointmentBeanList);
                }
            }
        },type,Constant.FIRST_PAGE);
    }

    @Override
    public void pullToRefreshData(String type) {
        appointmentModel.getAppointments(new RefreshSubscriber<AppointmentData>(context) {
            @Override
            public void onNext(AppointmentData appointmentData) {
                if(appointmentData != null){
                    appointmentBeanList = appointmentData.getAppointment();
                }
                if(!appointmentBeanList.isEmpty()){
                    appointmentFragmentView.updateRecyclerView(appointmentBeanList);
                }
            }
        },type, Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, String type, final int pageSize, int page) {
        appointmentModel.getAppointments(new RequestMoreSubscriber<AppointmentData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(AppointmentData appointmentData) {
                if(appointmentData != null){
                    appointmentBeanList = appointmentData.getAppointment();
                }
                if(!appointmentBeanList.isEmpty()){
                    appointmentFragmentView.updateRecyclerView(appointmentBeanList);
                }
                //没有更多数据了显示到底提示
                if(appointmentBeanList.size() < pageSize){
                    appointmentFragmentView.showEndFooterView();
                }
            }
        },type,page);
    }
}
