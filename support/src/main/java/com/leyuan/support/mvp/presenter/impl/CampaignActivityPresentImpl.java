package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.support.entity.CampaignBean;
import com.leyuan.support.http.subscriber.RefreshSubscriber;
import com.leyuan.support.http.subscriber.RequestMoreSubscriber;
import com.leyuan.support.mvp.model.CampaignModel;
import com.leyuan.support.mvp.model.impl.CampaignModelImpl;
import com.leyuan.support.mvp.presenter.CampaignActivityPresent;
import com.leyuan.support.mvp.view.CampaignActivityView;
import com.leyuan.support.util.Constant;

import java.util.List;

/**
 * 活动
 * Created by song on 2016/8/18.
 */
public class CampaignActivityPresentImpl implements CampaignActivityPresent{
    private Context context;
    private CampaignActivityView campaignActivityView;
    private CampaignModel campaignModel;

    public CampaignActivityPresentImpl(Context context, CampaignActivityView campaignActivityView) {
        this.context = context;
        this.campaignActivityView = campaignActivityView;
        campaignModel = new CampaignModelImpl();
    }

    @Override
    public void pullToRefreshData(RecyclerView recyclerView) {
        campaignModel.getCampaigns(new RefreshSubscriber<List<CampaignBean>>(context,recyclerView) {
            @Override
            public void onNext(List<CampaignBean> campaignBean) {
                //不考虑空值情况
                campaignActivityView.updateRecyclerView(campaignBean);
            }
        }, Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, final int pageSize, int page) {
        campaignModel.getCampaigns(new RequestMoreSubscriber<List<CampaignBean>>(context,recyclerView,pageSize) {
            @Override
            public void onNext(List<CampaignBean> campaignBeanList) {
                if(campaignBeanList != null && !campaignBeanList.isEmpty()){
                    campaignActivityView.updateRecyclerView(campaignBeanList);
                }

                //没有更多数据了显示到底提示
                if(campaignBeanList != null && campaignBeanList.size() < pageSize){
                    campaignActivityView.showEndFooterView();
                }
            }
        },page);
    }
}
