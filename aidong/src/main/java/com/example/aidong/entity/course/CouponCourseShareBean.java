package com.example.aidong.entity.course;

import java.io.Serializable;

/**
 * Created by user on 2017/12/7.
 */
public class CouponCourseShareBean  implements Serializable{
    boolean need;
    CouponShareData data;

    public boolean isNeed() {
        return need;
    }

    public void setNeed(boolean need) {
        this.need = need;
    }

    public CouponShareData getData() {
        return data;
    }

    public void setData(CouponShareData data) {
        this.data = data;
    }
}
