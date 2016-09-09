package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.support.entity.VenuesBean;
import com.leyuan.support.entity.data.DiscoverVenuesData;
import com.leyuan.support.http.subscriber.RefreshSubscriber;
import com.leyuan.support.http.subscriber.RequestMoreSubscriber;
import com.leyuan.support.mvp.model.DiscoverModel;
import com.leyuan.support.mvp.model.impl.DiscoverModelImpl;
import com.leyuan.support.mvp.presenter.DiscoverVenuesActivityPresent;
import com.leyuan.support.mvp.view.DiscoverVenuesActivityView;
import com.leyuan.support.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现 - 人
 * Created by song on 2016/8/29.
 */
public class DiscoverVenuesActivityPresentImpl implements DiscoverVenuesActivityPresent {
    private Context context;
    private DiscoverVenuesActivityView discoverVenuesActivityView;
    private DiscoverModel discoverModel;
    private List<VenuesBean> venuesBeanList;

    public DiscoverVenuesActivityPresentImpl(Context context, DiscoverVenuesActivityView discoverVenuesActivityView) {
        this.context = context;
        this.discoverVenuesActivityView = discoverVenuesActivityView;
        discoverModel = new DiscoverModelImpl();
        venuesBeanList = new ArrayList<>();
    }

    @Override
    public void pullToRefreshData(double lat, double lng) {
        discoverModel.getVenues(new RefreshSubscriber<DiscoverVenuesData>(context) {
            @Override
            public void onNext(DiscoverVenuesData discoverVenuesData) {
                if(discoverVenuesData != null){
                    venuesBeanList = discoverVenuesData.getGym();
                }

                if(venuesBeanList != null && !venuesBeanList.isEmpty()){
                    discoverVenuesActivityView.hideEmptyView();
                    discoverVenuesActivityView.showRecyclerView();
                    discoverVenuesActivityView.updateRecyclerView(venuesBeanList);
                }else {
                    discoverVenuesActivityView.showEmptyView();
                    discoverVenuesActivityView.hideRecyclerView();
                }
            }
        },lat,lng, Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, double lat, double lng,final int pageSize, int page) {
        discoverModel.getVenues(new RequestMoreSubscriber<DiscoverVenuesData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(DiscoverVenuesData discoverVenuesData) {
                if(discoverVenuesData != null){
                    venuesBeanList = discoverVenuesData.getGym();
                }

                if(venuesBeanList != null && !venuesBeanList.isEmpty()){
                    discoverVenuesActivityView.updateRecyclerView(venuesBeanList);
                }

                //没有更多数据了显示到底提示
                if(venuesBeanList != null && venuesBeanList.size() < pageSize){
                    discoverVenuesActivityView.showEndFooterView();
                }
            }
        },lat,lng,page);
    }
}
