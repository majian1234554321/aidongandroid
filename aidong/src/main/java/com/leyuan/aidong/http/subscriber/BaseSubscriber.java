package com.leyuan.aidong.http.subscriber;

import android.content.Context;

import com.leyuan.aidong.R;
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
        Logger.e("BaseSubscriber",e.toString());
        if (e instanceof SocketTimeoutException) {
            ToastGlobal.showShortConsecutive(R.string.connect_timeout);
        } else if (e instanceof ConnectException) {
            ToastGlobal.showShortConsecutive(R.string.connect_break);
        } else {
            ToastGlobal.showShortConsecutive("" + e.getMessage());
        }

    }

    @Override
    public void onCompleted() {

    }
}
