package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.leyuan.aidong.entity.CampaignBean;
import com.leyuan.aidong.entity.data.CampaignData;
import com.leyuan.aidong.entity.data.CampaignDetailData;
import com.leyuan.aidong.entity.data.PayOrderData;
import com.leyuan.aidong.http.subscriber.CommonSubscriber;
import com.leyuan.aidong.http.subscriber.ProgressSubscriber;
import com.leyuan.aidong.http.subscriber.RefreshSubscriber;
import com.leyuan.aidong.http.subscriber.RequestMoreSubscriber;
import com.leyuan.aidong.module.pay.AliPay;
import com.leyuan.aidong.module.pay.PayInterface;
import com.leyuan.aidong.module.pay.WeiXinPay;
import com.leyuan.aidong.ui.mvp.model.CampaignModel;
import com.leyuan.aidong.ui.mvp.model.impl.CampaignModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.CampaignPresent;
import com.leyuan.aidong.ui.mvp.view.CampaignDetailActivityView;
import com.leyuan.aidong.ui.mvp.view.CampaignFragmentView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.widget.customview.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 活动
 * Created by song on 2016/8/18.
 */
public class CampaignPresentImpl implements CampaignPresent {
    private Context context;
    private CampaignModel campaignModel;

    private List<CampaignBean> campaignBeanList;
    private CampaignFragmentView campaignActivityView;          //活动列表View层对象
    private CampaignDetailActivityView campaignDetailView;      //活动详情View层对象

    public CampaignPresentImpl(Context context) {
        this.context = context;
        if(campaignModel == null){
            campaignModel = new CampaignModelImpl();
        }
    }

    public CampaignPresentImpl(Context context, CampaignFragmentView view) {
        this.context = context;
        this.campaignActivityView = view;
        campaignBeanList = new ArrayList<>();
        if(campaignModel == null){
            campaignModel = new CampaignModelImpl();
        }
    }

    public CampaignPresentImpl(Context context, CampaignDetailActivityView view) {
        this.context = context;
        this.campaignDetailView = view;
        if(campaignModel == null){
            campaignModel = new CampaignModelImpl();
        }
    }

    @Override
    public void commonLoadData(final SwitcherLayout switcherLayout) {
        campaignModel.getCampaigns(new CommonSubscriber<CampaignData>(switcherLayout) {
            @Override
            public void onNext(CampaignData campaignData) {
                if(campaignData.getCampaign() != null && !campaignData.getCampaign().isEmpty()){
                    campaignActivityView.updateRecyclerView(campaignData.getCampaign());
                    switcherLayout.showContentLayout();
                }else{
                    switcherLayout.showEmptyLayout();
                }
            }
        },Constant.FIRST_PAGE);
    }

    @Override
    public void pullToRefreshData() {
        campaignModel.getCampaigns(new RefreshSubscriber<CampaignData>(context) {
            @Override
            public void onNext(CampaignData campaignBean) {
                if(campaignBean != null && !campaignBean.getCampaign().isEmpty()){
                    campaignActivityView.updateRecyclerView(campaignBean.getCampaign());
                }
            }
        }, Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, final int pageSize, int page) {
        campaignModel.getCampaigns(new RequestMoreSubscriber<CampaignData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(CampaignData campaignDataBean) {
                if(campaignDataBean != null){
                    campaignBeanList = campaignDataBean.getCampaign();
                }else{
                    campaignActivityView.showEndFooterView();
                }
                if(!campaignBeanList.isEmpty()){
                    campaignActivityView.updateRecyclerView(campaignBeanList);
                }
                //没有更多数据了显示到底提示
                if( campaignBeanList.size() < pageSize){
                    campaignActivityView.showEndFooterView();
                }
            }
        },page);
    }

    @Override
    public void getCampaignDetail(final SwitcherLayout switcherLayout,String id) {
        campaignModel.getCampaignDetail(new Subscriber<CampaignDetailData>() {
            @Override
            public void onStart() {
                switcherLayout.showLoadingLayout();
            }

            @Override
            public void onNext(CampaignDetailData campaignDetailData) {
                if(campaignDetailData.getCampaign() != null){
                    switcherLayout.showContentLayout();
                    campaignDetailView.setCampaignDetail(campaignDetailData.getCampaign());
                }else{
                    switcherLayout.showEmptyLayout();
                }
            }

            @Override
            public void onCompleted() {
                switcherLayout.showContentLayout();
            }

            @Override
            public void onError(Throwable e) {
                Logger.w("CommonSubscriber","error:" + e.toString());
                Toast.makeText(context, "error:" + e.toString(), Toast.LENGTH_LONG).show();
                switcherLayout.showExceptionLayout();
            }
        }, id);
    }

    @Override
    public void buyCampaign(String id, String couponId, float integral, String payType, String contactName,
                            String contactMobile, final PayInterface.PayListener listener) {
        campaignModel.buyCampaign(new ProgressSubscriber<PayOrderData>(context) {
            @Override
            public void onNext(PayOrderData payOrderData) {
                String payType = payOrderData.getOrder().getPayType();
                PayInterface payInterface = "alipay".equals(payType) ? new AliPay(context,listener)
                        : new WeiXinPay(context,listener);
                payInterface.payOrder(payOrderData.getOrder());
            }
        },id,couponId,integral,payType,contactName,contactMobile);
    }
}
