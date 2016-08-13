package com.leyuan.support.http;


import com.leyuan.support.entity.BaseBean;
import com.leyuan.support.http.api.exception.ServerException;

import rx.functions.Func1;

/**
 * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
 * @param <T>  Subscriber真正需要的数据类型也就是Data部分的数据类型
 */
public class HttpResultFunc<T> implements Func1<BaseBean<T>, T> {

    @Override
    public T call(BaseBean<T> httpResult) {
        if (httpResult.getCode() != 1) {
            throw new ServerException(100);
        }
        return httpResult.getData();
    }
}
