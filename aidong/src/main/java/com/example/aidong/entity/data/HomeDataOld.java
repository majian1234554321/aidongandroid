package com.example.aidong.entity.data;

import com.example.aidong .entity.HomeBean;

import java.util.List;

/**
 * 首页数据实体
 * Created by song on 2016/8/22.
 */
public class HomeDataOld {
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
