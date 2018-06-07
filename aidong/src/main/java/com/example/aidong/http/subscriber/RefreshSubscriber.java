package com.example.aidong.http.subscriber;

import android.content.Context;



/**
 * 用于上拉加载更多时的Http请求,
 * onNext中返回需要的数据,
 * onError中对错误进行统一处理,其他方法空实现
 * //todo 去掉Presenter中Subscriber和控件的耦合,交由View来实现这部分逻辑
 */
public abstract class RefreshSubscriber<T> extends BaseSubscriber<T> {

    public RefreshSubscriber(Context context) {
        super(context);
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public abstract void onNext(T t);


}