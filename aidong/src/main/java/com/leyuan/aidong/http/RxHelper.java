package com.leyuan.aidong.http;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.http.api.exception.NotLoginException;
import com.leyuan.aidong.http.api.exception.ServerException;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.Logger;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Rx帮助类
 * 将服务器返回的带有状态码的结果进行预处理
 * 若状态异常,返回带异常信息的可观察者
 * 若状态正常,返回与处理过(去掉了实体的外层包装)的可观察者
 * Created by song on 2016/8/12.
 */
public class RxHelper {

    /**
     * 对结果进行预处理
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<BaseBean<T>, T> transform() {
        return new Observable.Transformer<BaseBean<T>, T>() {
            @Override
            public Observable<T> call(Observable<BaseBean<T>> tObservable) {
                return tObservable.flatMap(new Func1<BaseBean<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(BaseBean<T> result) {
                        Logger.i("retrofit","result from network : " + result);
                        if (result.getStatus() == Constant.OK) {
                            return createDataObservable(result.getData());
                        } else if (result.getCode() == Constant.NOT_LOGIN) {
                            return Observable.error(new NotLoginException(result.getMessage()));
                        } else {
                            // the exception will callback at Subscriber's onError
                            return Observable.error(new ServerException(result.getMessage()));
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * 创建成功的数据
     * @param data
     * @param <T>
     * @return
     */
    private static <T> Observable<T> createDataObservable(final T data) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onStart();
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
