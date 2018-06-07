package com.example.aidong.entity.data;

import com.example.aidong .entity.VenuesBean;

import java.util.ArrayList;

/**
 * 场馆
 * Created by song on 2016/8/30.
 */
public class VenuesData {
    private ArrayList<VenuesBean> gym;

    public ArrayList<VenuesBean> getGym() {
        return gym;
    }

    public void setGym(ArrayList<VenuesBean> gym) {
        this.gym = gym;
    }

    @Override
    public String toString() {
        return "VenuesData{" +
                "gym=" + gym +
                '}';
    }
}

