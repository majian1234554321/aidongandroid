package com.leyuan.aidong.entity.data;

import com.leyuan.aidong.entity.NewsBean;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.entity.VenuesBean;

import java.util.List;

/**
 * 发现首页实体
 * Created by song on 2016/12/12.
 */
public class DiscoverData {

    private List<VenuesBean> gym;
    private List<UserBean> person;
    private List<NewsBean> news;

    public List<VenuesBean> getGym() {
        return gym;
    }

    public void setGym(List<VenuesBean> gym) {
        this.gym = gym;
    }

    public List<UserBean> getPerson() {
        return person;
    }

    public void setPerson(List<UserBean> person) {
        this.person = person;
    }

    public List<NewsBean> getNews() {
        return news;
    }

    public void setNews(List<NewsBean> news) {
        this.news = news;
    }
}
