package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.VenuesBean;

import java.util.ArrayList;

/**
 * Created by user on 2018/1/30.
 */
public interface SelectedLocationView {
    void onRefreshData(ArrayList<VenuesBean> gym);

    void onGetMoreData(ArrayList<VenuesBean> gym);
}
