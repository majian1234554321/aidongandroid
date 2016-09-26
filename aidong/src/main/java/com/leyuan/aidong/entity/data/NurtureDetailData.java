package com.leyuan.aidong.entity.data;

import com.leyuan.aidong.entity.NurtureDetailBean;

/**
 * 营养品详情
 * Created by song on 2016/9/22.
 */
public class NurtureDetailData {
    private NurtureDetailBean nurture;

    public NurtureDetailBean getNurture() {
        return nurture;
    }

    public void setNurture(NurtureDetailBean nurture) {
        this.nurture = nurture;
    }

    @Override
    public String toString() {
        return "NurtureDetailData{" +
                "nurture=" + nurture +
                '}';
    }
}
