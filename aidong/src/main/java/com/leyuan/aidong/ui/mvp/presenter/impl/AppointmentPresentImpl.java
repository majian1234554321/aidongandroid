package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.entity.AppointmentBean;
import com.leyuan.aidong.entity.AppointmentDetailBean;
import com.leyuan.aidong.entity.data.AppointmentData;
import com.leyuan.aidong.entity.data.AppointmentDetailData;
import com.leyuan.aidong.http.subscriber.CommonSubscriber;
import com.leyuan.aidong.http.subscriber.RefreshSubscriber;
import com.leyuan.aidong.http.subscriber.RequestMoreSubscriber;
import com.leyuan.aidong.ui.mvp.model.AppointmentModel;
import com.leyuan.aidong.ui.mvp.model.impl.AppointmentModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.AppointmentPresent;
import com.leyuan.aidong.ui.mvp.view.AppointmentDetailActivityView;
import com.leyuan.aidong.ui.mvp.view.AppointmentFragmentView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.widget.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 预约
 * Created by song on 2016/9/2.
 */
public class AppointmentPresentImpl implements AppointmentPresent {
    private Context context;
    private AppointmentModel appointmentModel;

    //预约列表View层
    private AppointmentFragmentView appointmentFragmentView;
    private List<AppointmentBean> appointmentBeanList ;

    //预约详情View层
    private AppointmentDetailActivityView appointmentDetailActivityView;

    public AppointmentPresentImpl(Context context, AppointmentFragmentView view) {
        this.context = context;
        this.appointmentFragmentView = view;
        appointmentBeanList = new ArrayList<>();
        if(appointmentModel == null){
            appointmentModel = new AppointmentModelImpl();
        }
    }

    public AppointmentPresentImpl(Context context, AppointmentDetailActivityView view) {
        this.context = context;
        this.appointmentDetailActivityView = view;
        if(appointmentModel == null){
            appointmentModel = new AppointmentModelImpl();
        }
    }

    @Override
    public void commonLoadData(final SwitcherLayout switcherLayout, String type) {
        appointmentModel.getAppointments(new CommonSubscriber<AppointmentData>(switcherLayout) {
            @Override
            public void onNext(AppointmentData appointmentData) {
                if(appointmentData != null &&  appointmentData.getAppointment() != null){
                    appointmentBeanList = appointmentData.getAppointment();
                }
                if(appointmentBeanList.isEmpty()){
                    appointmentFragmentView.showEmptyView();
                }else{
                    switcherLayout.showContentLayout();
                    appointmentFragmentView.onRecyclerViewRefresh(appointmentBeanList);
                }
            }
        },type,Constant.PAGE_FIRST);
    }

    @Override
    public void pullToRefreshData(String type) {
        appointmentModel.getAppointments(new RefreshSubscriber<AppointmentData>(context) {
            @Override
            public void onNext(AppointmentData appointmentData) {
                if(appointmentData != null &&  appointmentData.getAppointment() != null){
                    appointmentBeanList = appointmentData.getAppointment();
                }
                if(!appointmentBeanList.isEmpty()){
                    appointmentFragmentView.onRecyclerViewRefresh(appointmentBeanList);
                }
            }
        },type, Constant.PAGE_FIRST);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, String type, final int pageSize, int page) {
        appointmentModel.getAppointments(new RequestMoreSubscriber<AppointmentData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(AppointmentData appointmentData) {
                if(appointmentData != null &&  appointmentData.getAppointment() != null){
                    appointmentBeanList = appointmentData.getAppointment();
                }
                if(!appointmentBeanList.isEmpty()){
                    appointmentFragmentView.onRecyclerViewLoadMore(appointmentBeanList);
                }
                //没有更多数据了显示到底提示
                if(appointmentBeanList.size() < pageSize){
                    appointmentFragmentView.showEndFooterView();
                }
            }
        },type,page);
    }

    @Override
    public void getAppointmentDetail(final SwitcherLayout switcherLayout, String id) {
        appointmentModel.getAppointmentDetail(new CommonSubscriber<AppointmentDetailData>(switcherLayout) {
            @Override
            public void onNext(AppointmentDetailData appointmentDetailData) {
                AppointmentDetailBean appointmentDetailBean = null;
                if(appointmentDetailData != null && appointmentDetailData.getAppoint() != null){
                    appointmentDetailBean = appointmentDetailData.getAppoint();
                }
                if(appointmentDetailBean != null){
                    switcherLayout.showContentLayout();
                    appointmentDetailActivityView.setAppointmentDetail(appointmentDetailBean);
                }else {
                    switcherLayout.showEmptyLayout();
                }
            }
        },id);
    }
}
