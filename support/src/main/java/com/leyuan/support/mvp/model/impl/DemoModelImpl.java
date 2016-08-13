package com.leyuan.support.mvp.model.impl;


import com.leyuan.support.entity.DemoBean;
import com.leyuan.support.http.DemoHttpResultFunc;
import com.leyuan.support.http.RetrofitHelper;
import com.leyuan.support.http.api.DemoService;
import com.leyuan.support.mvp.model.DemoModel;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DemoModelImpl implements DemoModel {
    private DemoService demoService;

    public DemoModelImpl() {
        demoService = RetrofitHelper.createApi(DemoService.class);
    }

    @Override
    public void getDemoNews(Subscriber<List<DemoBean>> subscriber, int page) {
      demoService.getDomeNews(page)
              .map(new DemoHttpResultFunc<List<DemoBean>>())    //返回调用者关心的数据
              .subscribeOn(Schedulers.io())                     //在io线程中请求
              .observeOn(AndroidSchedulers.mainThread())        //在主线程回调
              .subscribe(subscriber);
    }
}
