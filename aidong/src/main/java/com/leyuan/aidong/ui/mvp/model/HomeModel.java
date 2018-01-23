package com.leyuan.aidong.ui.mvp.model;

import com.leyuan.aidong.entity.BannerBean;
import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.entity.VenuesBean;
import com.leyuan.aidong.entity.data.BrandData;
import com.leyuan.aidong.entity.data.HomeData;
import com.leyuan.aidong.entity.data.HomeDataOld;

import java.util.List;

import rx.Subscriber;

/**
 * 首页
 * Created by song on 2016/8/13.
 */
public interface HomeModel {
    /**
     * 获取首页推荐列表
     * @param subscriber Subscriber
     * @param page 页码
     */
    void getRecommendList(Subscriber<HomeDataOld> subscriber, int page, String list);

    /**
     * 获取首页分类详情
     * @param subscriber Subscriber
     * @param id 小分类id
     * @param page 页码
     */
    void getBrandDetail(Subscriber<BrandData> subscriber, String id, int page);

    void getRecommendList(Subscriber<HomeData> subscriber);

    /**
     * 获取首页Banner
     */
    List<BannerBean> getHomeBanners();

    /**
     * 获取商城Banner
     */
    List<BannerBean> getStoreBanners();

    /**
     * 获取首页弹出式广告
     * @return
     */
    List<BannerBean> getHomePopupBanners();

    /**
     * 获取运动足记
     * @return
     */
    List<VenuesBean> getSportsHistory();
    /**
     * 获取开通城市
     * @return
     */
    List<String> getOpenCity();


    List<CategoryBean>  getCourseCategory();
}
