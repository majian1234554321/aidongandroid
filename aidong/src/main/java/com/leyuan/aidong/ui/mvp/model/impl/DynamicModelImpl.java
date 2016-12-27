package com.leyuan.aidong.ui.mvp.model.impl;


import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.DynamicService;
import com.leyuan.aidong.ui.mvp.model.DynamicModel;

import java.util.List;

import rx.Subscriber;

public class DynamicModelImpl implements DynamicModel{
    private DynamicService dynamicService;

    public DynamicModelImpl() {
        dynamicService = RetrofitHelper.createApi(DynamicService.class);
    }

    @Override
    public void getDynamics(Subscriber<List<DynamicBean>> subscriber, int page) {
        dynamicService.getDynamics(page)
                .compose(RxHelper.<List<DynamicBean>>transform())
                .subscribe(subscriber);
    }
}
