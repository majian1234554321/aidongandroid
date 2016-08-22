package com.leyuan.support.entity;

import java.util.List;

/**
 * 首页实体
 * Created by song on 2016/8/22.
 */
public class HomeBean {
    private List<HomeItemBean> home;

    public List<HomeItemBean> getHome() {
        return home;
    }

    public void setHome(List<HomeItemBean> home) {
        this.home = home;
    }

    @Override
    public String toString() {
        return "HomeBean{" +
                "home=" + home +
                '}';
    }
}
