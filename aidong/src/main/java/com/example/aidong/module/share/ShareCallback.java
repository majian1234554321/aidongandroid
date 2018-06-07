package com.example.aidong.module.share;


/**
 * Created by user on 2017/3/13.
 */

public interface ShareCallback {
    public void onComplete(Object o);

    public void onError();

    public void onCancel();
}
