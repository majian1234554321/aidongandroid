package com.leyuan.support.mvp.presenter.impl;

import android.content.Context;

import com.leyuan.support.mvp.model.VenuesModel;
import com.leyuan.support.mvp.model.impl.VenuesModelImpl;
import com.leyuan.support.mvp.presenter.DiscoverVenuesFragmentPresent;
import com.leyuan.support.mvp.view.DiscoverVenuesFragmentView;

/**
 * 发现-场馆
 * Created by Song on 2016/8/2.
 */
public class DiscoverVenuesFragmentPresentImpl implements DiscoverVenuesFragmentPresent {
    private Context context;
    private DiscoverVenuesFragmentView discoverVenuesFragmentView;
    private VenuesModel venuesModel;


    public DiscoverVenuesFragmentPresentImpl(Context context,DiscoverVenuesFragmentView discoverVenuesFragmentView) {
        this.context = context;
        this.discoverVenuesFragmentView = discoverVenuesFragmentView;
        venuesModel = new VenuesModelImpl();
    }
    


    @Override
    public void searchVenues() {

    }
}
