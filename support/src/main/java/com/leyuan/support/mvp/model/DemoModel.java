package com.leyuan.support.mvp.model;


import com.leyuan.support.entity.DemoBean;

import java.util.List;

import rx.Subscriber;

/**
 * Created by song on 2016/8/9.
 */
public interface DemoModel {
    void getDemoNews(Subscriber<List<DemoBean>> subscriber, int page);
}
