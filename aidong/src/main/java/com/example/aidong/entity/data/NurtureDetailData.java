package com.example.aidong.entity.data;

import com.example.aidong .entity.GoodsDetailBean;
import com.example.aidong .entity.NurtureDetailBean;

/**
 * 营养品详情
 * Created by song on 2016/9/22.
 */
public class NurtureDetailData {
    private GoodsDetailBean nutrition;

    public GoodsDetailBean getNurture() {
        return nutrition;
    }

    public void setNurture(NurtureDetailBean nurture) {
        this.nutrition = nurture;
    }

    @Override
    public String toString() {
        return "GoodsDetailBean{" +
                "nurture=" + nutrition +
                '}';
    }
}
