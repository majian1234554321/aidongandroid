package com.leyuan.support.entity.data;

import com.leyuan.support.entity.BannerBean;

import java.util.ArrayList;

/**
 * Banner
 * Created by song on 2016/8/30.
 */
public class BannerData {
    private ArrayList<BannerBean> banners;

    public ArrayList<BannerBean> getBanners() {
        return banners;
    }

    public void setBanners(ArrayList<BannerBean> banners) {
        this.banners = banners;
    }

    @Override
    public String toString() {
        return "BannerData{" +
                "banners=" + banners +
                '}';
    }
}
