package com.example.aidong.entity.data;

import com.example.aidong .entity.NewsBean;

import java.util.List;

/**
 * 新闻
 * Created by song on 2016/11/9.
 */
public class DiscoverNewsData {
    private List<NewsBean> news;

    public List<NewsBean> getNews() {
        return news;
    }

    public void setNews(List<NewsBean> news) {
        this.news = news;
    }

    @Override
    public String toString() {
        return "NewsData{" +
                "news=" + news +
                '}';
    }
}
