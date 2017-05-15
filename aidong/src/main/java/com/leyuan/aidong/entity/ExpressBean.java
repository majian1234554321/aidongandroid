package com.leyuan.aidong.entity;

import java.util.List;

/**
 * 快递信息
 * Created by song on 2017/5/12.
 */
public class ExpressBean {

    private List<ExpressListBean> express;
    private String cover;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public List<ExpressListBean> getExpress() {
        return express;
    }

    public void setExpress(List<ExpressListBean> express) {
        this.express = express;
    }
}
