package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.support.entity.FoodAndVenuesBean;
import com.leyuan.support.http.subscriber.RefreshSubscriber;
import com.leyuan.support.http.subscriber.RequestMoreSubscriber;
import com.leyuan.support.mvp.model.FoodModel;
import com.leyuan.support.mvp.model.impl.FoodModelImpl;
import com.leyuan.support.mvp.presenter.FoodActivityPresenter;
import com.leyuan.support.mvp.view.FoodActivityView;
import com.leyuan.support.util.Constant;

/**
 * 健康餐饮
 * Created by song on 2016/8/15.
 */
public class FoodActivityPresentImpl implements FoodActivityPresenter {
    private Context context;
    private FoodModel foodModel;
    private FoodActivityView foodActivityView;

    public FoodActivityPresentImpl(Context context, FoodActivityView foodActivityView) {
        this.context = context;
        this.foodActivityView = foodActivityView;
        foodModel = new FoodModelImpl();
    }

    @Override
    public void pullToRefreshData(RecyclerView recyclerView) {
        foodModel.getFoods(new RefreshSubscriber<FoodAndVenuesBean>(context,recyclerView) {
            @Override
            public void onNext(FoodAndVenuesBean bean) {
                if(bean != null && bean.getFood() != null){
                    foodActivityView.updateRecyclerView(bean);
                }
            }
        }, Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, final int pageSize, int page) {
        foodModel.getFoods(new RequestMoreSubscriber<FoodAndVenuesBean>(context,recyclerView,pageSize) {
            @Override
            public void onNext(FoodAndVenuesBean bean) {
                if(bean != null && bean.getFood() != null &&!bean.getFood().isEmpty()){
                    foodActivityView.updateRecyclerView(bean);
                }

                //没有更多数据了显示到底提示
                if( bean!= null && bean.getFood() != null && bean.getFood().size() < pageSize){
                    foodActivityView.showEndFooterView();
                }
            }
        },page);
    }
}
