package com.example.aidong.ui.mvp.model.impl;

import android.content.Context;

import com.example.aidong .entity.BannerBean;
import com.example.aidong .entity.CategoryBean;
import com.example.aidong .entity.VenuesBean;
import com.example.aidong .entity.data.BrandData;
import com.example.aidong .entity.data.HomeData;
import com.example.aidong .entity.data.HomeDataOld;
import com.example.aidong .http.RetrofitHelper;
import com.example.aidong .http.RxHelper;
import com.example.aidong .http.api.HomeService;
import com.example.aidong .ui.App;
import com.example.aidong .ui.mvp.model.HomeModel;
import com.example.aidong .utils.SystemInfoUtils;

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
    public void getRecommendList(Subscriber<HomeDataOld> subscriber, int page, String list) {
        homeService.getRecommendList(page, list)
                .compose(RxHelper.<HomeDataOld>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getBrandDetail(Subscriber<BrandData> subscriber, String id, int page) {
        homeService.getTypeDetail(id, page)
                .compose(RxHelper.<BrandData>transform())
                .subscribe(subscriber);
    }
    @Override
    public void getRecommendList(Subscriber<HomeData> subscriber){
        homeService.getRecommendList()
                .compose(RxHelper.<HomeData>transform())
                .subscribe(subscriber);
    }




    @Override
    public List<BannerBean> getHomeBanners() {
        return SystemInfoUtils.getHomeBanner(context);
    }

    @Override
    public List<BannerBean> getStoreBanners() {
        return SystemInfoUtils.getStoreBanner(context);
    }

    @Override
    public List<VenuesBean> getSportsHistory() {
        return App.getInstance().getSportsHistory();
    }

    @Override
    public List<BannerBean> getHomePopupBanners() {
        return SystemInfoUtils.getHomePopupBanner(context);
    }

    @Override
    public List<String> getOpenCity() {
        return SystemInfoUtils.getOpenCity(context);
    }

    @Override
    public List<CategoryBean> getCourseCategory() {
        return SystemInfoUtils.getCourseCategory(context);
    }
}
