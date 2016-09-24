package com.example.aidong.ui.mvp.view;

import com.example.aidong.entity.AddressBean;

/**
 * 新建或修改地址
 * Created by song on 2016/9/21.
 */
public interface AddAddressActivityView {

    /**
     * 设置更新地址
     * @param addressBean AddressBean
     */
    void setUpdateAddress(AddressBean addressBean);

    /**
     * 设置添加地址
     * @param addressBean AddressBean
     */
    void setAddAddress(AddressBean addressBean);
}
