package com.example.aidong.ui.mvp.model;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.data.AddressData;
import com.example.aidong .entity.data.AddressListData;

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
    void addAddress(Subscriber<AddressData> subscriber,String name, String phone,String province,
                    String city,String district,  String address,String def);

    /**
     * 更新地址
     * @param subscriber Subscriber
     * @param id 地址id
     * @param name 名字 没变化传入null
     * @param phone 电话 没变化传入null
     * @param address 地址 没变化传入null
     */
    void updateAddress(Subscriber<AddressData> subscriber,String id,String name,String phone,
                       String province,String city,String district, String address,String def);

    /**
     * 删除地址
     * @param subscriber Subscriber
     * @param id 地址id
     */
    void deleteAddress(Subscriber<BaseBean> subscriber, String id);
}
