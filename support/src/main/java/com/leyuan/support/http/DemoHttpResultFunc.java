package com.leyuan.support.http;


import com.leyuan.support.entity.DomeBaseBean;
import com.leyuan.support.http.api.exception.ServerException;

import rx.functions.Func1;

/**
 * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
 * @param <T>  Subscriber真正需要的数据类型也就是Data部分的数据类型
 */
public class DemoHttpResultFunc<T> implements Func1<DomeBaseBean<T>,T> {
    @Override
    public T call(DomeBaseBean<T> domeHttpResult) {
        if (!"false".equals(domeHttpResult.getError())) {
            throw new ServerException(100);
        }
        return domeHttpResult.getResults();
    }
}
