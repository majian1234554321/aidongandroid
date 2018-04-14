package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.leyuan.aidong.entity.data.HomeData;
import com.leyuan.aidong.http.subscriber.ProgressSubscriber;
import com.leyuan.aidong.ui.mvp.model.impl.HomeModelImpl;
import com.leyuan.aidong.ui.mvp.view.HomeRecommendView;
import com.leyuan.aidong.widget.SwitcherLayout;

/**
 * Created by user on 2018/1/22.
 */
public class HomeRecommendPresentImpl {
    private final HomeRecommendView HomeRecommendView;
    private Context context;
    private HomeModelImpl homeModel;

    public HomeRecommendPresentImpl(Context context, HomeRecommendView view) {
        this.context = context;
        this.HomeRecommendView = view;
        if (homeModel == null) {
            homeModel = new HomeModelImpl(context);
        }
    }

    public void getRecommendList(final SwitcherLayout switcherLayout) {
        homeModel.getRecommendList(new ProgressSubscriber<HomeData>(context) {
            @Override
            public void onNext(HomeData homeData) {
                switcherLayout.showContentLayout();
                HomeRecommendView.onGetData(homeData);

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                HomeRecommendView.onGetData(null);
            }
        });
    }


    public void getRecommendList2() {
        homeModel.getRecommendList(new ProgressSubscriber<HomeData>(context) {
            @Override
            public void onNext(HomeData homeData) {

                HomeRecommendView.onGetData(homeData);

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                HomeRecommendView.onGetData(null);
            }
        });
    }



}
