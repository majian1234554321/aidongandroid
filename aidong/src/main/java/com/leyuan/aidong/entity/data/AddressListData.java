package com.leyuan.aidong.entity.data;

import com.leyuan.aidong.entity.AddressBean;

import java.util.List;

/**
 * 地址列表实体
 * Created by song on 2016/9/20.
 */
public class AddressListData {
    private List<AddressBean> address;

    public List<AddressBean> getAddress() {
        return address;
    }

    public void setAddress(List<AddressBean> address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "AddressListData{" +
                "address=" + address +
                '}';
    }
}
