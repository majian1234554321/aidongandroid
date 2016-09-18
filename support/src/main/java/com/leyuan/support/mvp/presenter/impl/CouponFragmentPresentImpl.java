package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.support.entity.CouponBean;
import com.leyuan.support.entity.data.CouponData;
import com.leyuan.support.http.subscriber.CommonSubscriber;
import com.leyuan.support.http.subscriber.RefreshSubscriber;
import com.leyuan.support.http.subscriber.RequestMoreSubscriber;
import com.leyuan.support.mvp.model.CouponModel;
import com.leyuan.support.mvp.model.impl.CouponModelImpl;
import com.leyuan.support.mvp.presenter.CouponFragmentPresent;
import com.leyuan.support.mvp.view.CouponFragmentView;
import com.leyuan.support.util.Constant;
import com.leyuan.support.widget.customview.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 优惠劵
 * Created by song on 2016/9/14.
 */
public class CouponFragmentPresentImpl implements CouponFragmentPresent{
    private Context context;
    private CouponModel couponModel;
    private CouponFragmentView couponView;
    private List<CouponBean> couponList;

    public CouponFragmentPresentImpl(Context context, CouponFragmentView couponView) {
        this.context = context;
        this.couponView = couponView;
        couponModel = new CouponModelImpl();
        couponList = new ArrayList<>();
    }

    @Override
    public void commonLoadData(final SwitcherLayout switcherLayout, String type) {
        couponModel.getCoupons(new CommonSubscriber<CouponData>(switcherLayout) {
            @Override
            public void onNext(CouponData couponData) {
                if(couponData != null){
                    couponList = couponData.getCoupon();
                }

                if(!couponList.isEmpty()){
                    switcherLayout.showContentLayout();
                    couponView.updateRecyclerView(couponList);
                }else {
                    switcherLayout.showEmptyLayout();
                }
            }
        },type, Constant.FIRST_PAGE);
    }

    @Override
    public void pullToRefreshData(String type) {
        couponModel.getCoupons(new RefreshSubscriber<CouponData>(context) {
            @Override
            public void onNext(CouponData couponData) {
                if(couponData != null){
                    couponList = couponData.getCoupon();
                }

                if(!couponList.isEmpty()){
                    couponView.updateRecyclerView(couponList);
                }
            }
        },type,Constant.FIRST_PAGE);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, String type,final int pageSize, int page) {
        couponModel.getCoupons(new RequestMoreSubscriber<CouponData>(context,recyclerView,pageSize) {
            @Override
            public void onNext(CouponData couponData) {
                if(couponData != null){
                    couponList = couponData.getCoupon();
                }

                if(!couponList.isEmpty()){
                    couponView.updateRecyclerView(couponList);
                }
                //没有更多数据了显示到底提示
                if(couponList.size() < pageSize){
                    couponView.showEndFooterView();
                }
            }
        },type,page);
    }
}
