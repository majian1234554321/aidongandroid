package com.leyuan.aidong.ui.mvp.model.impl;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.ShareData;
import com.leyuan.aidong.entity.data.CouponData;
import com.leyuan.aidong.entity.user.CouponDataSingle;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.CouponService;
import com.leyuan.aidong.ui.mvp.model.CouponModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 优惠劵Model层实现类
 * Created by song on 2016/9/14.
 */
public class CouponModelImpl implements CouponModel {
    private CouponService couponService;

    public CouponModelImpl() {
        couponService = RetrofitHelper.createApi(CouponService.class);
    }

    @Override
    public void getCoupons(Subscriber<CouponData> subscriber, String type, int page) {
        couponService.getCoupons(type, page)
                .compose(RxHelper.<CouponData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getShareCoupon(Subscriber<ShareData> subscriber, String order_no) {
        couponService.getShareCoupon(order_no)
                .compose(RxHelper.<ShareData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void obtainCoupon(Subscriber<BaseBean> subscriber, String id) {
        couponService.obtainCoupon(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void exchangeCoupon(Subscriber<CouponDataSingle> subscriber, String id) {
        couponService.exchangeCoupon(id)
                .compose(RxHelper.<CouponDataSingle>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getSpecifyGoodsCoupon(Subscriber<CouponData> subscriber, String from, String... id) {
        couponService.getSpecifyGoodsCoupon(from, id)
                .compose(RxHelper.<CouponData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getGoodsAvailableCoupon(Subscriber<CouponData> subscriber, String... items) {
        couponService.getGoodsAvailableCoupon(items)
                .compose(RxHelper.<CouponData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getGoodsAvailableCoupon(Subscriber<CouponData> subscriber, ArrayList<String> items,Map<String, String> gym_ids) {
        JSONObject root = new JSONObject();

        JSONArray array = new JSONArray(items);
        JSONObject gymJson = new JSONObject();

        try {
            root.put("items",array);

            for (Map.Entry<String, String> code : gym_ids.entrySet()) {
                gymJson.put(code.getKey(),code.getValue());
            }
            root.put("gym_id",gymJson);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),root.toString());

        couponService.getGoodsAvailableCoupon(requestBody)
                .compose(RxHelper.<CouponData>transform())
                .subscribe(subscriber);
    }


    @Override
    public void getGoodsDetailCoupon(Subscriber<CouponData> subscriber, String id) {
        couponService.getGoodsTakableCoupon(id)
                .compose(RxHelper.<CouponData>transform())
                .subscribe(subscriber);
    }
}
