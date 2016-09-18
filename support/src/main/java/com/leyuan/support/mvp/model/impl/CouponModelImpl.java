package com.leyuan.support.mvp.model.impl;

import com.leyuan.support.entity.BaseBean;
import com.leyuan.support.entity.data.CouponData;
import com.leyuan.support.http.RetrofitHelper;
import com.leyuan.support.http.RxHelper;
import com.leyuan.support.http.api.CouponService;
import com.leyuan.support.mvp.model.CouponModel;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 优惠劵Model层实现类
 * Created by song on 2016/9/14.
 */
public class CouponModelImpl implements CouponModel{
    private CouponService couponService;

    public CouponModelImpl() {
        couponService = RetrofitHelper.createApi(CouponService.class);
    }

    @Override
    public void getCoupons(Subscriber<CouponData> subscriber, String type, int page) {
        couponService.getCoupons(type,page)
                .compose(RxHelper.<CouponData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void obtainCoupon(Subscriber<BaseBean> subscriber, String id) {
        couponService.obtainCoupon(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
