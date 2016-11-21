package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.leyuan.aidong.entity.AddressBean;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.AddressData;
import com.leyuan.aidong.entity.data.AddressListData;
import com.leyuan.aidong.http.subscriber.CommonSubscriber;
import com.leyuan.aidong.http.subscriber.ProgressSubscriber;
import com.leyuan.aidong.ui.mvp.model.AddressModel;
import com.leyuan.aidong.ui.mvp.model.impl.AddressModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.AddressPresent;
import com.leyuan.aidong.ui.mvp.view.AddAddressActivityView;
import com.leyuan.aidong.ui.mvp.view.AddressActivityView;
import com.leyuan.aidong.ui.mvp.view.UpdateAddressActivityView;
import com.leyuan.aidong.widget.customview.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 地址
 * Created by song on 2016/9/21.
 */
public class AddressPresentImpl implements AddressPresent{
    private Context context;
    private AddressModel addressModel;

    private AddressActivityView addressActivityView;                //收货地址列表View层
    private AddAddressActivityView addAddressActivityView;          //新建地址View层
    private UpdateAddressActivityView updateAddressActivityView;    //更新地址View层

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

    public AddressPresentImpl(Context context, UpdateAddressActivityView view) {
        this.context = context;
        this.updateAddressActivityView = view;
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
                if(addressListData != null && addressListData.getAddress() != null){
                    addressList = addressListData.getAddress();
                }
                if(addressList.isEmpty()){
                    switcherLayout.showEmptyLayout();
                }else{
                    switcherLayout.showContentLayout();
                    addressActivityView.setAddress(addressList);
                }
            }
        });
    }

    @Override
    public void addAddress(String id,String name, String phone,String province,String city,String district,  final String address) {
        addressModel.addAddress(new ProgressSubscriber<AddressData>(context,false) {
            @Override
            public void onNext(AddressData addressData) {
                if(addressData != null && addressData.getAddress() != null){
                    addAddressActivityView.setAddAddress(addressData.getAddress());
                }
            }
        },id,name,phone,province,city,district,address);
    }

    @Override
    public void updateAddress(String id, String name, String phone,String province,String city,String district,  String address) {
        addressModel.updateAddress(new ProgressSubscriber<AddressData>(context,false) {
            @Override
            public void onNext(AddressData addressData) {
                if(addressData != null && addressData.getAddress() != null){
                    updateAddressActivityView.setUpdateAddress(addressData.getAddress());
                }
            }
        },id,name,phone,province,city,district,address);
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
