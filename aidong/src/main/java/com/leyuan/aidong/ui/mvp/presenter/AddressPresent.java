package com.leyuan.aidong.ui.mvp.presenter;

import com.leyuan.aidong.widget.customview.SwitcherLayout;

/**
 * 收货地址
 * Created by song on 2016/9/1.
 */
public interface AddressPresent {

    /**
     * 获取地址列表
     * @param switcherLayout SwitcherLayout
     */
    void getAddress(SwitcherLayout switcherLayout);

    /**
     * 添加收货地址
     * @param name 名字
     * @param phone 电话
     * @param address 地址
     */
    void addAddress(String id,String name, String phone,String province,String city,String district,String address);

    /**
     * 修改收货地址信息
     * @param id 地址id
     * @param name 名字
     * @param phone 电话
     * @param address 地址
     */
    void updateAddress(String id,String name, String phone,String province,String city,String district,String address);

    /**
     * 删除收货地址
     * @param id 地址id
     */
    void deleteAddress(String id);

}
