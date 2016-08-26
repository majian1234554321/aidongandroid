package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.support.entity.CampaignBean;
import com.leyuan.support.entity.data.CampaignData;
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
    private List<CampaignBean> campaignBeanList;

    public CampaignActivityPresentImpl(Context context, CampaignActivityView campaignActivityView) {
        this.context = context;
        this.campaignActivityView = campaignActivityView;
        campaignModel = new CampaignModelImpl();
    }

    @Override
    public void pullToRefreshData(RecyclerView recyclerView) {
        campaignModel.getCampaigns(new RefreshSubscriber<CampaignData>(context,recyclerView) {
            @Override
            public void onNext(CampaignData campaignBean) {
                if(campaignBean != null){
                    campaignBeanList = campaignBean.getCampaign();
                }
                //不考虑空值情况
                campaignActivityView.updateRecyclerView(campaignBeanList);
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
