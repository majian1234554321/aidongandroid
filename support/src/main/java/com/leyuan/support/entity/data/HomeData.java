package com.leyuan.support.entity.data;

import com.leyuan.support.entity.HomeBean;

import java.util.List;

/**
 * 首页数据实体
 * Created by song on 2016/8/22.
 */
public class HomeData {
    private List<HomeBean> home;

    public List<HomeBean> getHome() {
        return home;
    }

    public void setHome(List<HomeBean> home) {
        this.home = home;
    }

    @Override
    public String toString() {
        return "HomeData{" +
                "home=" + home +
                '}';
    }
}
