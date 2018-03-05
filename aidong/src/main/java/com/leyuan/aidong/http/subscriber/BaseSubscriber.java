package com.leyuan.aidong.http.subscriber;

import android.content.Context;

import com.leyuan.aidong.R;
import com.leyuan.aidong.http.api.exception.NotLoginException;
import com.leyuan.aidong.http.api.exception.ZeroException;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.ToastGlobal;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;


public abstract class BaseSubscriber<T> extends Subscriber<T> {
    protected Context context;

    public BaseSubscriber(Context context) {
        this.context = context;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        Logger.e("BaseSubscriber", e.toString());
        if (e instanceof SocketTimeoutException) {
            ToastGlobal.showShortConsecutive(R.string.connect_timeout);
        } else if (e instanceof ConnectException) {

            ToastGlobal.showShortConsecutive(R.string.connect_break);
        } else if (e instanceof ZeroException) {
            ToastGlobal.showShortConsecutive(e.getMessage());
        } else if (e instanceof NotLoginException) {
            ToastGlobal.showShortConsecutive("您还未登陆");
        } else {
            ToastGlobal.showShortConsecutive("请求失败");
        }
    }

    @Override
    public void onCompleted() {

    }
}
