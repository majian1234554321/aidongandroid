package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.AddressBean;

import java.util.List;

/**
 * 选择收货地址
 * Created by song on 2016/9/21.
 */
public interface SelectAddressActivityView {

    /**
     * 设置收货地址信息
     * @param addressBeanList AddressBean集合
     */
    void setAddress(List<AddressBean> addressBeanList);



    /**
     * 显示没有地址视图
     */
    void showEmptyView();
}
