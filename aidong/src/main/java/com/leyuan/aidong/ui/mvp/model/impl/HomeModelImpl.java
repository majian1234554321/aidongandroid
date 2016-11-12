package com.leyuan.aidong.ui.mvp.model.impl;

import com.leyuan.aidong.entity.BannerBean;
import com.leyuan.aidong.entity.data.BrandData;
import com.leyuan.aidong.entity.data.HomeData;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.HomeService;
import com.leyuan.aidong.ui.mvp.model.HomeModel;
import com.leyuan.aidong.utils.Constant;

import java.util.List;

import rx.Subscriber;

/**
 * 首页
 * Created by song on 2016/8/13.
 */
public class HomeModelImpl implements HomeModel {
    private HomeService homeService;

    public HomeModelImpl() {
        homeService = RetrofitHelper.createApi(HomeService.class);
    }

    @Override
    public void getRecommendList(Subscriber<HomeData> subscriber, int page) {
        homeService.getRecommendList(page)
                .compose(RxHelper.<HomeData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getBrandDetail(Subscriber<BrandData> subscriber, String id, int page) {
        homeService.getTypeDetail(id,page)
                .compose(RxHelper.<BrandData>transform())
                .subscribe(subscriber);

    }

    @Override
    public List<BannerBean> getBanners() {
       //Todo 改成成工具类统一获取
        return Constant.systemInfoBean.getBanner();
    }
}
