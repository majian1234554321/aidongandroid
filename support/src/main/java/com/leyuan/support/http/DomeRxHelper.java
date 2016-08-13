package com.leyuan.support.http;

import android.util.Log;

import com.leyuan.support.entity.DomeBaseBean;
import com.leyuan.support.http.api.exception.ServerException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Rx帮助类
 * Created by song on 2016/8/12.
 */
public class DomeRxHelper {

    /**
     * 对结果进行预处理
     * @param <T> 调用者需要的数据结构
     * @return Observable
     */
    public static <T> Observable.Transformer<DomeBaseBean<T>, T> transform() {
        return new Observable.Transformer<DomeBaseBean<T>, T>() {
            @Override
            public Observable<T> call(Observable<DomeBaseBean<T>> tObservable) {
                return tObservable.flatMap(new Func1<DomeBaseBean<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(DomeBaseBean<T> result) {
                        Log.i("http","result from network : " + result);
                        if (!"false".equals(result.getError())) {
                            return Observable.error(new ServerException(result.getError()));
                        }
                        return createData(result.getResults());
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * 创建成功的数据
     * @param data 调用者需要的数据结构
     * @param <T> 调用者需要的数据结构
     * @return Observable
     */
    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                   // subscriber.onStart();
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
