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
import com.leyuan.aidong.ui.mvp.view.CartActivityView;
import com.leyuan.aidong.ui.mvp.view.EquipmentActivityView;
import com.leyuan.aidong.ui.mvp.view.NurtureActivityView;
import com.leyuan.aidong.utils.Constant;
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

    public RecommendPresentImpl(Context context, NurtureActivityView view) {
        this.context = context;
        this.nurtureActivityView = view;
        if(recommendModel == null){
            recommendModel = new RecommendModelImpl();
        }
    }

    public RecommendPresentImpl(Context context, EquipmentActivityView view) {
        this.context = context;
        this.equipmentActivityView = view;
        if(recommendModel == null){
            recommendModel = new RecommendModelImpl();
        }
    }

    public RecommendPresentImpl(Context context, CartActivityView view) {
        this.context = context;
        this.cartActivityView = view;
        if(recommendModel == null){
            recommendModel = new RecommendModelImpl();
        }
    }



    @Override
    public void commendLoadData(final SwitcherLayout switcherLayout, String type) {
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
                    switcherLayout.showEmptyLayout();
                }
            }
        },type, Constant.PAGE_FIRST);
    }

    @Override
    public void pullToRefreshData(String type) {
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
    public void requestMoreData(RecyclerView recyclerView,final int pageSize, int page, String type) {
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
           // cartActivityView.showEmptyGoodsView();
        }
    }
}
