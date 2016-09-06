package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.support.entity.AppointmentBean;
import com.leyuan.support.entity.data.AppointmentData;
import com.leyuan.support.http.subscriber.RefreshSubscriber;
import com.leyuan.support.http.subscriber.RequestMoreSubscriber;
import com.leyuan.support.mvp.model.AppointmentModel;
import com.leyuan.support.mvp.model.impl.AppointmentModelImpl;
import com.leyuan.support.mvp.presenter.AppointmentFragmentPresent;
import com.leyuan.support.mvp.view.AppointmentFragmentView;
import com.leyuan.support.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * 预约
 * Created by song on 2016/9/2.
 */
public class AppointmentFragmentPresentImpl implements AppointmentFragmentPresent {
    private Context context;
    private AppointmentModel appointmentModel;
    private AppointmentFragmentView appointmentFragmentView;
    private List<AppointmentBean> appointmentBeanList;

    public AppointmentFragmentPresentImpl(Context context, AppointmentFragmentView appointmentFragmentView) {
        this.context = context;
        this.appointmentFragmentView = appointmentFragmentView;
        appointmentModel = new AppointmentModelImpl();
        appointmentBeanList = new ArrayList<>();
    }

    @Override
    public void pullToRefreshData(RecyclerView recyclerView, String type) {
        appointmentModel.getAppointments(new RefreshSubscriber<AppointmentData>(context,recyclerView) {
            @Override
            public void onNext(AppointmentData appointmentData) {
                if(appointmentData != null){
                    appointmentBeanList = appointmentData.getAppointment();
                }

                if(appointmentBeanList.isEmpty()){
                    appointmentFragmentView.showEmptyView();
                }else {
                    appointmentFragmentView.hideEmptyView();
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
                if(appointmentBeanList != null && appointmentBeanList.size() < pageSize){
                    appointmentFragmentView.showEndFooterView();
                }
            }
        },type,page);
    }
}
