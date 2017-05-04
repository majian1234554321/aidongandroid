package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.entity.data.GoodsData;
import com.leyuan.aidong.http.subscriber.CommonSubscriber;
import com.leyuan.aidong.http.subscriber.RefreshSubscriber;
import com.leyuan.aidong.http.subscriber.RequestMoreSubscriber;
import com.leyuan.aidong.ui.mvp.model.RecommendModel;
import com.leyuan.aidong.ui.mvp.model.impl.RecommendModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.RecommendPresent;
import com.leyuan.aidong.ui.mvp.view.AppointSuccessActivityView;
import com.leyuan.aidong.ui.mvp.view.CartActivityView;
import com.leyuan.aidong.ui.mvp.view.EquipmentActivityView;
import com.leyuan.aidong.ui.mvp.view.NurtureActivityView;
import com.leyuan.aidong.ui.mvp.view.PaySuccessActivityView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.constant.RecommendGoodsPosition;
import com.leyuan.aidong.widget.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐
 * Created by song on 2017/1/19.
 */
public class RecommendPresentImpl implements RecommendPresent{
    private Context context;
    private RecommendModel recommendModel;
    private NurtureActivityView nurtureActivityView;
    private EquipmentActivityView equipmentActivityView;
    private CartActivityView cartActivityView;
    private AppointSuccessActivityView appointSuccessActivityView;
    private PaySuccessActivityView paySuccessActivityView;

    public RecommendPresentImpl(Context context, NurtureActivityView view) {
        this.context = context;
        this.nurtureActivityView = view;
    }

    public RecommendPresentImpl(Context context, EquipmentActivityView view) {
        this.context = context;
        this.equipmentActivityView = view;
    }

    public RecommendPresentImpl(Context context, CartActivityView view) {
        this.context = context;
        this.cartActivityView = view;
    }

    public RecommendPresentImpl(Context context, AppointSuccessActivityView view) {
        this.context = context;
        this.appointSuccessActivityView = view;
    }

    public RecommendPresentImpl(Context context, PaySuccessActivityView view) {
        this.context = context;
        this.paySuccessActivityView = view;
    }

    @Override
    public void commendLoadRecommendData(final SwitcherLayout switcherLayout, @RecommendGoodsPosition String type) {
        if(recommendModel == null){
            recommendModel = new RecommendModelImpl();
        }
        recommendModel.getRecommendGoods(new CommonSubscriber<GoodsData>(switcherLayout) {
            @Override
            public void onNext(GoodsData goodsData) {
                List<GoodsBean> goodsList = new ArrayList<>();
                if(goodsData != null && goodsData.getProduct() != null){
                    goodsList = goodsData.getProduct();
                }
                if(!goodsList.isEmpty()){
                    updateRecyclerView(goodsList);
                    switcherLayout.showContentLayout();
                }else{
                    showEmptyView();
                }
            }
        },type, Constant.PAGE_FIRST);
    }

    @Override
    public void pullToRefreshRecommendData(@RecommendGoodsPosition String type) {
        if(recommendModel == null){
            recommendModel = new RecommendModelImpl();
        }
        recommendModel.getRecommendGoods(new RefreshSubscriber<GoodsData>(context) {
            @Override
            public void onNext(GoodsData goodsData) {
                List<GoodsBean> goodsList = new ArrayList<>();
                if(goodsData != null && goodsData.getProduct() != null){
                    goodsList = goodsData.getProduct();
                }
                if(!goodsList.isEmpty()){
                    updateRecyclerView(goodsList);
                }else {
                    showEmptyView();
                }
            }
        },type,Constant.PAGE_FIRST);
    }

    @Override
    public void requestMoreRecommendData(RecyclerView recyclerView, final int pageSize, int page,
                                         @RecommendGoodsPosition String type) {
        if(recommendModel == null){
            recommendModel = new RecommendModelImpl();
        }
        recommendModel.getRecommendGoods(new RequestMoreSubscriber<GoodsData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(GoodsData goodsData) {
                List<GoodsBean> goodsList = new ArrayList<>();
                if(goodsData != null && goodsData.getProduct() != null){
                    goodsList = goodsData.getProduct();
                }
                if(!goodsList.isEmpty()){
                    updateRecyclerView(goodsList);
                }
                //没有更多数据了显示到底提示
                if( goodsList.size() < pageSize){
                   showEndFooterView();
                }
            }
        },type,page);
    }

    private void updateRecyclerView(List<GoodsBean> goodsList){
        if(nurtureActivityView != null){
            nurtureActivityView.updateRecyclerView(goodsList);
        }
        if(equipmentActivityView != null){
            equipmentActivityView.updateRecyclerView(goodsList);
        }
        if(cartActivityView != null){
            cartActivityView.updateRecommendGoods(goodsList);
        }
        if (appointSuccessActivityView != null){
            appointSuccessActivityView.updateRecyclerView(goodsList);
        }
        if(paySuccessActivityView != null){
            paySuccessActivityView.updateRecyclerView(goodsList);
        }
    }

    private void showEndFooterView(){
        if(nurtureActivityView != null){
            nurtureActivityView.showEndFooterView();
        }
        if(equipmentActivityView != null){
            equipmentActivityView.showEndFooterView();
        }
        if(cartActivityView != null){
            cartActivityView.showEndFooterView();
        }
    }

    private void showEmptyView(){
        if(nurtureActivityView != null){
            nurtureActivityView.showEmptyView();
        }
        if(equipmentActivityView != null){
            equipmentActivityView.showEmptyView();
        }
        if(cartActivityView != null){
           cartActivityView.showEmptyRecommendView();
        }

        if (appointSuccessActivityView != null){
            appointSuccessActivityView.showEmptyView();
        }
    }
}
