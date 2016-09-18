package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.support.entity.FoodBean;
import com.leyuan.support.entity.data.FoodData;
import com.leyuan.support.http.subscriber.CommonSubscriber;
import com.leyuan.support.http.subscriber.RefreshSubscriber;
import com.leyuan.support.http.subscriber.RequestMoreSubscriber;
import com.leyuan.support.mvp.model.SearchModel;
import com.leyuan.support.mvp.model.impl.SearchModelImpl;
import com.leyuan.support.mvp.presenter.SearchFragmentPresent;
import com.leyuan.support.mvp.view.SearchFoodFragmentView;
import com.leyuan.support.util.Constant;
import com.leyuan.support.widget.customview.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索健康餐饮
 * Created by song on 2016/9/18.
 */
public class SearchFoodFragmentPresentImpl implements SearchFragmentPresent{
    private Context context;
    private SearchModel searchModel;
    private SearchFoodFragmentView searchView;
    private List<FoodBean> foodList;

    public SearchFoodFragmentPresentImpl(Context context, SearchFoodFragmentView searchView) {
        this.context = context;
        this.searchView = searchView;
        searchModel = new SearchModelImpl();
        foodList = new ArrayList<>();
    }

    @Override
    public void commonLoadData(final SwitcherLayout switcherLayout, String keyword) {
        searchModel.searchFood(new CommonSubscriber<FoodData>(switcherLayout) {
            @Override
            public void onNext(FoodData foodData) {
                if(foodData != null){
                    foodList = foodData.getFood();
                }
                if(foodList.isEmpty()){
                    switcherLayout.showEmptyLayout();
                }else {
                    switcherLayout.showContentLayout();
                    searchView.updateRecyclerView(foodList);
                }
            }
        },keyword, Constant.FIRST_PAGE);
    }

    @Override
    public void pullToRefreshData(String keyword) {
        searchModel.searchFood(new RefreshSubscriber<FoodData>(context) {
            @Override
            public void onNext(FoodData foodData) {
                if(foodData != null){
                    foodList = foodData.getFood();
                }
                if(!foodList.isEmpty()){
                    searchView.updateRecyclerView(foodList);
                }
            }
        },keyword,Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, String keyword, final int pageSize, int page) {
        searchModel.searchFood(new RequestMoreSubscriber<FoodData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(FoodData foodData) {
                if(foodData != null){
                    foodList = foodData.getFood();
                }
                if(!foodList.isEmpty()){
                    searchView.updateRecyclerView(foodList);
                }
                if(foodList.size() < pageSize){
                    searchView.showEndFooterView();
                }
            }
        },keyword,page);
    }
}
