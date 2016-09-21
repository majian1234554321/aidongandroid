package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;

import com.leyuan.support.entity.AddressBean;
import com.leyuan.support.entity.BaseBean;
import com.leyuan.support.entity.data.AddressData;
import com.leyuan.support.entity.data.AddressListData;
import com.leyuan.support.http.subscriber.CommonSubscriber;
import com.leyuan.support.http.subscriber.ProgressSubscriber;
import com.leyuan.support.mvp.model.AddressModel;
import com.leyuan.support.mvp.model.impl.AddressModelImpl;
import com.leyuan.support.mvp.presenter.AddressPresent;
import com.leyuan.support.mvp.view.AddAddressActivityView;
import com.leyuan.support.mvp.view.AddressActivityView;
import com.leyuan.support.widget.customview.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 地址
 * Created by song on 2016/9/21.
 */
public class AddressPresentImpl implements AddressPresent{
    private Context context;
    private AddressModel addressModel;

    //收货地址列表View层
    private AddressActivityView addressActivityView;

    //新建或修改地址View层
    private AddAddressActivityView addAddressActivityView;

    public AddressPresentImpl(Context context, AddressActivityView view) {
        this.context = context;
        this.addressActivityView = view;
        if(addressModel == null){
            addressModel = new AddressModelImpl();
        }
    }

    public AddressPresentImpl(Context context, AddAddressActivityView view) {
        this.context = context;
        this.addAddressActivityView = view;
        if(addressModel == null){
            addressModel = new AddressModelImpl();
        }
    }

    @Override
    public void getAddress(final SwitcherLayout switcherLayout) {
        addressModel.getAddress(new CommonSubscriber<AddressListData>(switcherLayout) {
            @Override
            public void onNext(AddressListData addressListData) {
                List<AddressBean> addressList = new ArrayList<>();
                if(addressListData != null){
                    addressList = addressListData.getAddress();
                }
                if(addressList.isEmpty()){
                    addressActivityView.showEmptyView();
                }else{
                    switcherLayout.showContentLayout();
                    addressActivityView.setAddress(addressList);
                }
            }
        });
    }

    @Override
    public void addAddress(String name, String phone, final String address) {
        addressModel.addAddress(new ProgressSubscriber<AddressData>(context,false) {
            @Override
            public void onNext(AddressData addressData) {
                if(addressData != null){
                    //注意: 这里传回去的Bean可能为空
                    addAddressActivityView.setAddAddress(addressData.getAddress());
                }
            }
        },name,phone,address);
    }

    @Override
    public void updateAddress(String id, String name, String phone, String address) {
        addressModel.updateAddress(new ProgressSubscriber<AddressData>(context,false) {
            @Override
            public void onNext(AddressData addressData) {
                if(addressData != null){
                    //注意: 这里传回去的Bean可能为空
                    addAddressActivityView.setAddAddress(addressData.getAddress());
                }
            }
        },id,name,phone,address);
    }

    @Override
    public void deleteAddress(String id) {
        addressModel.deleteAddress(new ProgressSubscriber<BaseBean>(context,false) {
            @Override
            public void onNext(BaseBean baseBean) {
                addressActivityView.setDeleteAddress(baseBean);
            }
        },id);
    }
}
