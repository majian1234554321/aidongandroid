package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.CouponBean;

/**
 * 领取优惠劵
 * Created by song on 2016/9/21.
 */
public interface CouponExchangeActivityView {

    /**
     * 领取优惠劵
     * @param baseBean
     */
    void obtainCouponResult(CouponBean baseBean);
}
