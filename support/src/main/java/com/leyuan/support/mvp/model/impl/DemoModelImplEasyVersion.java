package com.leyuan.support.mvp.model.impl;


import com.leyuan.support.entity.DemoBean;
import com.leyuan.support.http.DomeRxHelper;
import com.leyuan.support.http.RetrofitHelper;
import com.leyuan.support.http.api.DemoService;
import com.leyuan.support.mvp.model.DemoModel;

import java.util.List;

import rx.Subscriber;

public class DemoModelImplEasyVersion implements DemoModel {
    private DemoService demoService;

    public DemoModelImplEasyVersion() {
        demoService = RetrofitHelper.createApi(DemoService.class);
    }

    @Override
    public void getDemoNews(Subscriber<List<DemoBean>> subscriber, int page) {
      demoService.getDomeNews(page)
              .compose(DomeRxHelper.<List<DemoBean>>transform())
              .subscribe(subscriber);
    }
}
