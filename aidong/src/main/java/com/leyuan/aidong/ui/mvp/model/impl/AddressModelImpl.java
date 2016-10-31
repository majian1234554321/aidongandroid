package com.leyuan.aidong.ui.mvp.model.impl;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.AddressData;
import com.leyuan.aidong.entity.data.AddressListData;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.AddressService;
import com.leyuan.aidong.ui.mvp.model.AddressModel;

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
    public void addAddress(Subscriber<AddressData> subscriber,String id, String name, String phone, String address) {
        addressService.addAddress(id,name,phone,address)
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
