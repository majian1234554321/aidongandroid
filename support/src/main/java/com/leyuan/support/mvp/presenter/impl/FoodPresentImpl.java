package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.support.entity.FoodAndVenuesBean;
import com.leyuan.support.http.subscriber.CommonSubscriber;
import com.leyuan.support.http.subscriber.RefreshSubscriber;
import com.leyuan.support.http.subscriber.RequestMoreSubscriber;
import com.leyuan.support.mvp.model.FoodModel;
import com.leyuan.support.mvp.model.impl.FoodModelImpl;
import com.leyuan.support.mvp.presenter.FoodPresenter;
import com.leyuan.support.mvp.view.FoodActivityView;
import com.leyuan.support.util.Constant;
import com.leyuan.support.widget.customview.SwitcherLayout;

/**
 * 健康餐饮
 * Created by song on 2016/8/15.
 */
public class FoodPresentImpl implements FoodPresenter {
    private Context context;
    private FoodModel foodModel;
    private FoodActivityView foodActivityView;

    public FoodPresentImpl(Context context, FoodActivityView foodActivityView) {
        this.context = context;
        this.foodActivityView = foodActivityView;
        foodModel = new FoodModelImpl();
    }

    @Override
    public void commonLoadData(SwitcherLayout switcherLayout) {
        foodModel.getFoods(new CommonSubscriber<FoodAndVenuesBean>(switcherLayout) {
            @Override
            public void onNext(FoodAndVenuesBean foodAndVenuesBean) {
                if (foodAndVenuesBean != null && (foodAndVenuesBean.getFood() != null || foodAndVenuesBean.getPick_up_gym() != null)) {
                    if(!foodAndVenuesBean.getFood().isEmpty() || !foodAndVenuesBean.getPick_up_gym().isEmpty()){
                        foodActivityView.updateRecyclerView(foodAndVenuesBean);
                    }else{
                        foodActivityView.showEmptyView();
                    }
                }else{
                    foodActivityView.showEmptyView();
                }
            }
        },Constant.FIRST_PAGE);
    }

    @Override
    public void pullToRefreshData() {
        foodModel.getFoods(new RefreshSubscriber<FoodAndVenuesBean>(context) {
            @Override
            public void onNext(FoodAndVenuesBean foodAndVenuesBean) {
                if(foodAndVenuesBean != null){
                    foodActivityView.updateRecyclerView(foodAndVenuesBean);
                }
            }
        }, Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, final int pageSize, int page) {
        foodModel.getFoods(new RequestMoreSubscriber<FoodAndVenuesBean>(context,recyclerView,pageSize) {
            @Override
            public void onNext(FoodAndVenuesBean foodAndVenuesBean) {
                if(foodAndVenuesBean != null && foodAndVenuesBean.getFood() != null &&!foodAndVenuesBean.getFood().isEmpty()){
                    foodActivityView.updateRecyclerView(foodAndVenuesBean);
                }
                //没有更多数据了显示到底提示
                if( foodAndVenuesBean!= null && foodAndVenuesBean.getFood() != null && foodAndVenuesBean.getFood().size() < pageSize){
                    foodActivityView.showEndFooterView();
                }
            }
        },page);
    }
}
