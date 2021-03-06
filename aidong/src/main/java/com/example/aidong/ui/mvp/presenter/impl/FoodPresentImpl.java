package com.example.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.aidong .entity.FoodAndVenuesBean;
import com.example.aidong .http.subscriber.CommonSubscriber;
import com.example.aidong .http.subscriber.RefreshSubscriber;
import com.example.aidong .http.subscriber.RequestMoreSubscriber;
import com.example.aidong .ui.mvp.model.FoodModel;
import com.example.aidong .ui.mvp.model.impl.FoodModelImpl;
import com.example.aidong .ui.mvp.presenter.FoodPresenter;
import com.example.aidong .ui.mvp.view.FoodActivityView;
import com.example.aidong .utils.Constant;
import com.example.aidong .widget.SwitcherLayout;

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
    public void commonLoadData(final SwitcherLayout switcherLayout) {
        foodModel.getFoods(new CommonSubscriber<FoodAndVenuesBean>(context,switcherLayout) {
            @Override
            public void onNext(FoodAndVenuesBean foodAndVenuesBean) {
                if (foodAndVenuesBean != null && (foodAndVenuesBean.getFood() != null || foodAndVenuesBean.getPick_up_gym() != null)) {
                    if((foodAndVenuesBean.getFood() != null && !foodAndVenuesBean.getFood().isEmpty()) ||
                            (foodAndVenuesBean.getPick_up_gym()!= null &&!foodAndVenuesBean.getPick_up_gym().isEmpty())){
                        foodActivityView.updateRecyclerView(foodAndVenuesBean);
                        switcherLayout.showContentLayout();
                    }else{
                        switcherLayout.showEmptyLayout();
                    }
                }else{
                    switcherLayout.showEmptyLayout();
                }
            }
        },Constant.PAGE_FIRST);
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
        }, Constant.PAGE_FIRST);
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
