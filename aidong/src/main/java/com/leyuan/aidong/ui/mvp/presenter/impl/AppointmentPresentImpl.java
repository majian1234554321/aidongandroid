package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.entity.AppointmentBean;
import com.leyuan.aidong.entity.AppointmentDetailBean;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.ShareData;
import com.leyuan.aidong.entity.data.AppointmentData;
import com.leyuan.aidong.entity.data.AppointmentDetailData;
import com.leyuan.aidong.http.subscriber.CommonSubscriber;
import com.leyuan.aidong.http.subscriber.ProgressSubscriber;
import com.leyuan.aidong.http.subscriber.RefreshSubscriber;
import com.leyuan.aidong.http.subscriber.RequestMoreSubscriber;
import com.leyuan.aidong.ui.mvp.model.AppointmentModel;
import com.leyuan.aidong.ui.mvp.model.CampaignModel;
import com.leyuan.aidong.ui.mvp.model.CourseModel;
import com.leyuan.aidong.ui.mvp.model.impl.AppointmentModelImpl;
import com.leyuan.aidong.ui.mvp.model.impl.CampaignModelImpl;
import com.leyuan.aidong.ui.mvp.model.impl.CourseModelImpl;
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
    private CourseModel courseModel;
    private CampaignModel campaignModel;

    //预约列表View层
    private AppointmentFragmentView appointmentFragmentView;
    private List<AppointmentBean> appointmentBeanList;

    //预约详情View层
    private AppointmentDetailActivityView appointmentDetailActivityView;
    private ShareData.ShareCouponInfo shareCouponInfo = new ShareData().new ShareCouponInfo();

    public AppointmentPresentImpl(Context context, AppointmentFragmentView view) {
        this.context = context;
        this.appointmentFragmentView = view;
        appointmentBeanList = new ArrayList<>();
        if (appointmentModel == null) {
            appointmentModel = new AppointmentModelImpl();
        }
    }

    public AppointmentPresentImpl(Context context, AppointmentDetailActivityView view) {
        this.context = context;
        this.appointmentDetailActivityView = view;
        if (appointmentModel == null) {
            appointmentModel = new AppointmentModelImpl();
        }
    }

    @Override
    public void commonLoadData(String type) {
        appointmentModel.getAppointments(new ProgressSubscriber<AppointmentData>(context) {
            @Override
            public void onNext(AppointmentData appointmentData) {
                if (appointmentData != null && appointmentData.getAppointment() != null) {
                    appointmentBeanList = appointmentData.getAppointment();
                }
                if (!appointmentBeanList.isEmpty()) {
                    appointmentFragmentView.hideEmptyView();
                    appointmentFragmentView.onRecyclerViewRefresh(appointmentBeanList);
                } else {
                    appointmentFragmentView.showEmptyView();
                }
            }
        }, type, Constant.PAGE_FIRST);
    }

    @Override
    public void pullToRefreshData(final SwipeRefreshLayout refreshLayout, final String type) {

        appointmentModel.getAppointments(new RefreshSubscriber<AppointmentData>(context) {
            @Override
            public void onStart() {
                super.onStart();
                refreshLayout.setRefreshing(true);
            }

            @Override
            public void onNext(AppointmentData appointmentData) {
                if (appointmentData != null && appointmentData.getAppointment() != null) {
                    appointmentBeanList = appointmentData.getAppointment();
                }
                if (!appointmentBeanList.isEmpty()) {
                    appointmentFragmentView.hideEmptyView();
                    appointmentFragmentView.onRecyclerViewRefresh(appointmentBeanList);
                } else {
                    if (refreshLayout.isRefreshing()) {
                        refreshLayout.setRefreshing(false);
                    }
                    appointmentFragmentView.showEmptyView();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                }
            }
        }, type, Constant.PAGE_FIRST);
    }


    @Override
    public void requestMoreData(RecyclerView recyclerView, String type, final int pageSize, int page) {
        appointmentModel.getAppointments(new RequestMoreSubscriber<AppointmentData>(context, recyclerView, pageSize) {
            @Override
            public void onNext(AppointmentData appointmentData) {
                if (appointmentData != null && appointmentData.getAppointment() != null) {
                    appointmentBeanList = appointmentData.getAppointment();
                }
                if (!appointmentBeanList.isEmpty()) {
                    appointmentFragmentView.onRecyclerViewLoadMore(appointmentBeanList);
                }
                //没有更多数据了显示到底提示
                if (appointmentBeanList.size() < pageSize) {
                    appointmentFragmentView.showEndFooterView();
                }
            }
        }, type, page);
    }

    @Override
    public void getAppointmentDetail(final SwitcherLayout switcherLayout, String id) {
        appointmentModel.getAppointmentDetail(new CommonSubscriber<AppointmentDetailData>(context, switcherLayout) {
            @Override
            public void onNext(AppointmentDetailData appointmentDetailData) {
                AppointmentDetailBean appointmentDetailBean = null;
                if (appointmentDetailData != null && appointmentDetailData.getAppoint() != null) {
                    appointmentDetailBean = appointmentDetailData.getAppoint();
                }
                if (appointmentDetailBean != null) {
                    switcherLayout.showContentLayout();
                    appointmentDetailActivityView.setAppointmentDetail(appointmentDetailBean);

                    createShareBeanByOrder(appointmentDetailData.getAppoint().getId(),
                            appointmentDetailData.getAppoint().getPay().getCreatedAt());
                } else {
                    switcherLayout.showEmptyLayout();
                }
            }
        }, id);
    }

    @Override
    public void cancelAppoint(String id) {
        appointmentModel.cancelAppointment(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if (appointmentFragmentView != null)
                    appointmentFragmentView.cancelAppointmentResult(baseBean);
                if (appointmentDetailActivityView != null)
                    appointmentDetailActivityView.cancelAppointmentResult(baseBean);
            }
        }, id);
    }

    @Override
    public void confirmAppoint(String id) {
        appointmentModel.confirmAppointment(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if (appointmentFragmentView != null)
                    appointmentFragmentView.confirmAppointmentResult(baseBean);
                if (appointmentDetailActivityView != null)
                    appointmentDetailActivityView.confirmAppointmentResult(baseBean);
            }
        }, id);
    }

    @Override
    public void deleteAppoint(String id) {
        appointmentModel.deleteAppointment(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if (appointmentFragmentView != null)
                    appointmentFragmentView.deleteAppointmentResult(baseBean);
                if (appointmentDetailActivityView != null)
                    appointmentDetailActivityView.deleteAppointmentResult(baseBean);
            }
        }, id);
    }

    @Override
    public void getCourseAppointDetail(final SwitcherLayout switcherLayout, String id) {
        if (courseModel == null) {
            courseModel = new CourseModelImpl(context);
        }
        courseModel.getCourseAppointDetail(new CommonSubscriber<AppointmentDetailData>(context, switcherLayout) {
            @Override
            public void onNext(AppointmentDetailData appointmentDetailData) {
                if (appointmentDetailData != null) {
                    switcherLayout.showContentLayout();
                    appointmentDetailActivityView.setAppointmentDetail(appointmentDetailData.getAppoint());

                    if (appointmentDetailData.getAppoint() != null) {
                        createShareBeanByOrder(appointmentDetailData.getAppoint().getId(),
                                appointmentDetailData.getAppoint().getPay().getCreatedAt());
                    }
                }
            }
        }, id);
    }

    @Override
    public void getCampaignAppointDetail(final SwitcherLayout switcherLayout, String id) {
        if (campaignModel == null) {
            campaignModel = new CampaignModelImpl();
        }
        campaignModel.getCampaignAppointDetail(new CommonSubscriber<AppointmentDetailData>(context, switcherLayout) {
            @Override
            public void onNext(AppointmentDetailData appointmentDetailData) {
                if (appointmentDetailData != null) {
                    switcherLayout.showContentLayout();
                    appointmentDetailActivityView.setAppointmentDetail(appointmentDetailData.getAppoint());
                    if (appointmentDetailData.getAppoint() != null) {
                        createShareBeanByOrder(appointmentDetailData.getAppoint().getId(),
                                appointmentDetailData.getAppoint().getPay().getCreatedAt());
                    }
                }
            }
        }, id);
    }


    private void createShareBeanByOrder(String createdAt, String id) {
        shareCouponInfo.setCreatedAt(createdAt);
        shareCouponInfo.setNo(id);
    }

    @Override
    public ShareData.ShareCouponInfo getShareInfo() {
        return shareCouponInfo;
    }
}
