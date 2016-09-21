package com.leyuan.support.mvp.model;

import com.leyuan.support.entity.BaseBean;
import com.leyuan.support.entity.data.AddressData;
import com.leyuan.support.entity.data.AddressListData;

import rx.Subscriber;

/**
 * 我的地址
 * Created by song on 2016/9/21.
 */
public interface AddressModel {
    /**
     * 获取地址列表
     * @param subscriber Subscriber
     */
    void getAddress(Subscriber<AddressListData> subscriber);

    /**
     * 添加地址
     * @param subscriber Subscriber
     * @param name 名字
     * @param phone 电话
     * @param address 地址
     */
    void addAddress(Subscriber<AddressData> subscriber, String name, String phone, String address);

    /**
     * 更新地址
     * @param subscriber Subscriber
     * @param id 地址id
     * @param name 名字 没变化传入null
     * @param phone 电话 没变化传入null
     * @param address 地址 没变化传入null
     */
    void updateAddress(Subscriber<AddressData> subscriber,String id,String name,String phone,String address);

    /**
     * 删除地址
     * @param subscriber Subscriber
     * @param id 地址id
     */
    void deleteAddress(Subscriber<BaseBean> subscriber, String id);
}
