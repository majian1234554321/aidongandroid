package com.leyuan.aidong.entity.data;

import com.leyuan.aidong.entity.DynamicBean;

import java.util.List;

/**
 * 爱动圈
 * Created by song on 2017/1/13.
 */
public class DynamicsData {
    private List<DynamicBean> dynamic;
    public String cover;
    public String dynamic_id;

    public List<DynamicBean> getDynamic() {
        return dynamic;
    }

    public void setDynamic(List<DynamicBean> dynamic) {
        this.dynamic = dynamic;
    }
}
