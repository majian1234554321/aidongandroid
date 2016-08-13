package com.leyuan.support.http.subscriber;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.leyuan.support.R;
import com.leyuan.support.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.support.widget.endlessrecyclerview.weight.LoadingFooter;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * 用于上拉加载更多时的Http请求,
 * 在onNext中返回需要的数据,
 * onError中对错误进行统一处理,其他方法空实现
 * 目前只支持RecyclerView
 */
public abstract class RefreshSubscriber<T> extends Subscriber<T> {
    private Context context;
    private RecyclerView recyclerView;

    public RefreshSubscriber(Context context,RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    public void onStart() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.Normal);
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
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public abstract void onNext(T t);

}