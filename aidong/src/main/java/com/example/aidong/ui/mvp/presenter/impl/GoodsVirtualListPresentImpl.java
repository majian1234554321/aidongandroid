package com.example.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.aidong .entity.data.GoodsData;
import com.example.aidong .http.subscriber.CommonSubscriber;
import com.example.aidong .http.subscriber.RefreshSubscriber;
import com.example.aidong .http.subscriber.RequestMoreSubscriber;
import com.example.aidong .ui.mvp.model.impl.GoodsModelImpl;
import com.example.aidong .ui.mvp.view.GoodsActivityView;
import com.example.aidong .widget.SwitcherLayout;

/**
 * Created by user on 2017/12/29.
 */
public class GoodsVirtualListPresentImpl {
    private final Context context;
    private final GoodsActivityView listener;
    private GoodsModelImpl goodsModel;

    public GoodsVirtualListPresentImpl(Context context, GoodsActivityView listener) {
        this.context = context;
        goodsModel = new GoodsModelImpl(context);
        this.listener = listener;
    }

    public void pullToRefreshData(String product_ids) {
        goodsModel.getVirtualGoodsList(new RefreshSubscriber<GoodsData>(context) {
            @Override
            public void onNext(GoodsData goodsData) {
                if(goodsData  != null && goodsData.getProduct() != null){
                    listener.updateRecyclerView(goodsData.getProduct());
                }else {
                    listener.showEmptyView();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                listener.showEmptyView();
            }
        },product_ids);
    }

    public void commendLoadRecommendData(final SwitcherLayout switcherLayout, String product_ids) {
        goodsModel.getVirtualGoodsList(new CommonSubscriber<GoodsData>(context,switcherLayout) {
            @Override
            public void onNext(GoodsData goodsData) {
                if(goodsData  != null && goodsData.getProduct() != null){
                    switcherLayout.showContentLayout();
                    listener.updateRecyclerView(goodsData.getProduct());
                }else {
                    listener.showEmptyView();
                }

            }
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                listener.showEmptyView();
            }

        },product_ids);
    }

    public void requestMoreRecommendData(RecyclerView recommendView, int pageSize, int currPage, String product_ids) {
        goodsModel.getVirtualGoodsList(new RequestMoreSubscriber<GoodsData>(context,recommendView,pageSize) {
            @Override
            public void onNext(GoodsData goodsData) {
                if(goodsData  != null && goodsData.getProduct() != null){
                    listener.updateRecyclerView(goodsData.getProduct());
                }else {
                    listener.showEndFooterView();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        },product_ids);
    }
}
