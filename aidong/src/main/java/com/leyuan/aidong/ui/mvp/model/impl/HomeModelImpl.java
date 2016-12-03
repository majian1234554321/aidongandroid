package com.leyuan.aidong.ui.mvp.model.impl;

import android.content.Context;

import com.leyuan.aidong.entity.BannerBean;
import com.leyuan.aidong.entity.data.BrandData;
import com.leyuan.aidong.entity.data.HomeData;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.HomeService;
import com.leyuan.aidong.ui.mvp.model.HomeModel;
import com.leyuan.aidong.utils.SystemInfoUtils;

import java.util.List;

import rx.Subscriber;

/**
 * 首页
 * Created by song on 2016/8/13.
 */
public class HomeModelImpl implements HomeModel {
    private Context context;
    private HomeService homeService;

    public HomeModelImpl(Context context) {
        this.context = context;
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
        return SystemInfoUtils.getHomeBanner(context);
    }

    @Override
    public List<BannerBean> getPopupBanners() {
        return SystemInfoUtils.getHomePopupBanner(context);
    }
}
