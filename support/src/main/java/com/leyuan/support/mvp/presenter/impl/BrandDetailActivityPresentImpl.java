package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.support.entity.BrandBean;
import com.leyuan.support.http.subscriber.RefreshSubscriber;
import com.leyuan.support.mvp.model.HomeModel;
import com.leyuan.support.mvp.model.impl.HomeModelImpl;
import com.leyuan.support.mvp.presenter.BrandDetailActivityPresent;
import com.leyuan.support.mvp.view.BrandDetailActivityView;
import com.leyuan.support.util.Constant;

/**
 * 品牌详情
 * Created by song on 2016/8/18.
 */
public class BrandDetailActivityPresentImpl implements BrandDetailActivityPresent{

    private Context context;
    private HomeModel homeModel;
    private BrandDetailActivityView brandDetailActivityView;


    public BrandDetailActivityPresentImpl(Context context,BrandDetailActivityView brandDetailActivityView) {
        this.context = context;
        homeModel = new HomeModelImpl();
        this.brandDetailActivityView = brandDetailActivityView;
    }


    @Override
    public void pullToRefreshData(RecyclerView recyclerView,int id) {
        homeModel.getBrandDetail(new RefreshSubscriber<BrandBean>(context,recyclerView) {
            @Override
            public void onNext(BrandBean brandBean) {
                brandDetailActivityView.updateRecyclerView(brandBean);
            }
        },id, Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, int pageSize, int page, int id) {
        homeModel.getBrandDetail(new RefreshSubscriber<BrandBean>(context,recyclerView) {
            @Override
            public void onNext(BrandBean brandBean) {
                /*if(brandBean != null && !brandBean.isEmpty()){
                    homeFragmentView.refreshData(homeBeanList);
                }

                //没有更多数据了显示到底提示
                if(homeBeanList != null && homeBeanList.size() < pageSize){
                    homeFragmentView.showEndFooterView();
                }*/
            }
        },id,page);
    }
}
