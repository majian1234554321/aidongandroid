package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.AddressBean;

import java.util.List;

/**
 * Created by user on 2017/3/16.
 */
public interface AddressListView {


    void onGetAddressList(List<AddressBean> addressList);
}
