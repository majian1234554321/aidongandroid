package com.example.aidong.http.subscriber;

import android.content.Context;
import android.widget.Toast;

import com.example.aidong.R;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;



/**
 * 用于上拉加载更多时的Http请求,
 * onNext中返回需要的数据,
 * onError中对错误进行统一处理,其他方法空实现
 */
public abstract class RefreshSubscriber<T> extends Subscriber<T> {
    private Context context;

    public RefreshSubscriber(Context context) {
        this.context = context;
    }


    @Override
    public void onStart() {
    }


    @Override
    public void onCompleted() {

    }

    /**
     * 对错误进行统一处理
     * @param e 异常信息
     */
    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(context, R.string.connect_timeout, Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(context, R.string.connect_break, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        onErrorView();
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public abstract void onNext(T t);

    /**
     * 下拉刷新需要显示加载错误的提示界面可以自己重写该方法,
     * 默认空实现
     */
    public void onErrorView(){

    }
}