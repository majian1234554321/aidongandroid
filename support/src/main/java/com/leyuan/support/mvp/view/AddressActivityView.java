package com.leyuan.support.mvp.view;

import com.leyuan.support.entity.AddressBean;
import com.leyuan.support.entity.BaseBean;

import java.util.List;

/**
 * 收货地址
 * Created by song on 2016/9/21.
 */
public interface AddressActivityView {

    /**
     * 设置收货地址信息
     * @param addressBeanList AddressBean集合
     */
    void setAddress(List<AddressBean> addressBeanList);

    /**
     * 设置删除地址信息
     * @param baseBean BaseBean
     */
    void setDeleteAddress(BaseBean baseBean);

    /**
     * 显示没有地址视图
     */
    void showEmptyView();
}
