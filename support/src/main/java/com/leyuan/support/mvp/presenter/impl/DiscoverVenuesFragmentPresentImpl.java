package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.support.entity.VenuesBean;
import com.leyuan.support.http.subscriber.RefreshSubscriber;
import com.leyuan.support.http.subscriber.RequestMoreSubscriber;
import com.leyuan.support.mvp.model.VenuesModel;
import com.leyuan.support.mvp.model.impl.VenuesModelImpl;
import com.leyuan.support.mvp.presenter.DiscoverVenuesFragmentPresent;
import com.leyuan.support.mvp.view.DiscoverVenuesFragmentView;

import java.util.List;

/**
 * 发现-场馆
 * Created by Song on 2016/8/2.
 */
public class DiscoverVenuesFragmentPresentImpl implements DiscoverVenuesFragmentPresent {
    private Context context;
    private DiscoverVenuesFragmentView discoverVenuesFragmentView;
    private VenuesModel venuesModel;


    public DiscoverVenuesFragmentPresentImpl(Context context,DiscoverVenuesFragmentView discoverVenuesFragmentView) {
        this.context = context;
        this.discoverVenuesFragmentView = discoverVenuesFragmentView;
        venuesModel = new VenuesModelImpl();
    }
    
    @Override
    public void pullToRefreshData(RecyclerView recyclerView) {
        venuesModel.getVenues(new RefreshSubscriber<List<VenuesBean>>(context,recyclerView) {
            @Override
            public void onNext(List<VenuesBean> venuesBeanList) {
                if(venuesBeanList != null && !venuesBeanList.isEmpty()){
                    discoverVenuesFragmentView.updateRecyclerView(venuesBeanList);
                }
            }
        },1);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView,int size,int page) {
        venuesModel.getVenues(new RequestMoreSubscriber<List<VenuesBean>>(context,recyclerView,size) {
            @Override
            public void onNext(List<VenuesBean> venuesBeanList) {
                if(venuesBeanList != null && !venuesBeanList.isEmpty()){
                    discoverVenuesFragmentView.updateRecyclerView(venuesBeanList);
                }else{
                    discoverVenuesFragmentView.showEndFooterView();
                }
            }
        },page);
    }

    @Override
    public void searchVenues() {

    }
}
