package com.leyuan.support.mvp.model.impl;

import com.leyuan.support.entity.BaseBean;
import com.leyuan.support.entity.data.AddressData;
import com.leyuan.support.entity.data.AddressListData;
import com.leyuan.support.http.RetrofitHelper;
import com.leyuan.support.http.RxHelper;
import com.leyuan.support.http.api.AddressService;
import com.leyuan.support.mvp.model.AddressModel;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 收货地址
 * Created by song on 2016/9/21.
 */
public class AddressModelImpl implements AddressModel{
    private AddressService addressService;

    public AddressModelImpl() {
        addressService = RetrofitHelper.createApi(AddressService.class);
    }

    @Override
    public void getAddress(Subscriber<AddressListData> subscriber) {
        addressService.getAddress()
                .compose(RxHelper.<AddressListData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void addAddress(Subscriber<AddressData> subscriber, String name, String phone, String address) {
        addressService.addAddress(name,phone,address)
                .compose(RxHelper.<AddressData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void updateAddress(Subscriber<AddressData> subscriber, String id, String name, String phone, String address) {
        addressService.updateAddress(id,name,phone,address)
                .compose(RxHelper.<AddressData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void deleteAddress(Subscriber<BaseBean> subscriber, String id) {
        addressService.deleteAddress(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
