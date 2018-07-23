package com.example.aidong.http.subscriber;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;

import com.example.aidong.http.subscriber.handler.ProgressDialogHandler;

/**
 * 用于普通Http请求时，在页面中显示一个ProgressDialog
 * 在onNext中返回调用者需要的数据,
 * onStart和onCompleted中显示并隐藏正在加载Progress
 * onError中对错误进行统一处理
 * //todo 去掉Presenter中Subscriber和控件的耦合,交由View来实现这部分逻辑
 */
public abstract class Progress2Subscriber<T> extends BaseSubscriber<T> {


    private final ProgressDialogFragment progressDialogFragment;

    public Progress2Subscriber(Context context) {
        super(context);
        this.context = context;
        progressDialogFragment = new ProgressDialogFragment();
        progressDialogFragment.show(((Activity) context).getFragmentManager(), "TAG");


    }


    /**
     * 订阅开始时调用显示ProgressDialog
     */
    @Override
    public void onStart() {
        super.onStart();

    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {

        dismissProgressDialog();
    }

    /**
     * 对错误进行统一处理隐藏ProgressDialog
     *
     * @param e 异常信息
     */
    @Override
    public void onError(Throwable e) {
        super.onError(e);
        dismissProgressDialog();
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public abstract void onNext(T t);


    public void showDialog() {

        progressDialogFragment.show(((Activity) context).getFragmentManager(), "TAG");


    }

    public void dismissProgressDialog() {
        if (progressDialogFragment != null) {
            progressDialogFragment.dismiss();
        }
    }

}