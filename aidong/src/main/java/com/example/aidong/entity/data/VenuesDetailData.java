package com.example.aidong.entity.data;

import com.example.aidong .entity.VenuesDetailBean;

/**
 * 场馆详情
 * Created by song on 2016/8/30.
 */
public class VenuesDetailData {

    private VenuesDetailBean gym;

    public VenuesDetailBean getGym() {
        return gym;
    }

    public void setGym(VenuesDetailBean gym) {
        this.gym = gym;
    }

    @Override
    public String toString() {
        return "VenuesDetailData{" +
                "gym=" + gym +
                '}';
    }
}
