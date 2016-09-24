package com.example.aidong.entity.data;

import com.example.aidong.entity.AddressBean;

/**
 * 地址
 * Created by song on 2016/9/21.
 */
public class AddressData {
    private AddressBean address;

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "AddressData{" +
                "address=" + address +
                '}';
    }
}
