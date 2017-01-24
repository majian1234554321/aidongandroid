package com.leyuan.aidong.entity.data;

import com.leyuan.aidong.entity.BrandBean;
import com.leyuan.aidong.entity.NewsBean;
import com.leyuan.aidong.entity.UserBean;

import java.util.List;

/**
 * 发现首页实体
 * Created by song on 2016/12/12.
 */
public class DiscoverData {

    private List<BrandBean> brand;
    private List<UserBean> user;
    private List<NewsBean> news;

    public List<BrandBean> getBrand() {
        return brand;
    }

    public void setBrand(List<BrandBean> brand) {
        this.brand = brand;
    }

    public List<UserBean> getUser() {
        return user;
    }

    public void setUser(List<UserBean> user) {
        this.user = user;
    }

    public List<NewsBean> getNews() {
        return news;
    }

    public void setNews(List<NewsBean> news) {
        this.news = news;
    }
}
