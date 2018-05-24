package com.leyuan.aidong.http.subscriber;

import android.app.Activity;
import android.content.Context;

import com.leyuan.aidong.http.subscriber.handler.ProgressDialogHandler;

/**
 * 用于普通Http请求时，在页面中显示一个ProgressDialog
 * 在onNext中返回调用者需要的数据,
 * onStart和onCompleted中显示并隐藏正在加载Progress
 * onError中对错误进行统一处理
 * //todo 去掉Presenter中Subscriber和控件的耦合,交由View来实现这部分逻辑
 */
public abstract class ProgressSubscriber<T> extends BaseSubscriber<T> implements ProgressDialogHandler.ProgressCancelListener{
    private boolean showDialog = true;
    private ProgressDialogHandler progressDialogHandler;

    public ProgressSubscriber(Context context) {
        super(context);
        this.context = context;
        progressDialogHandler = new ProgressDialogHandler(context, this, true);
    }

    public ProgressSubscriber(Context context,boolean showDialog) {
        super(context);
        this.context = context;
        this.showDialog = showDialog;
        progressDialogHandler = new ProgressDialogHandler(context, this, true);
    }

    private void showProgressDialog(){
        if (progressDialogHandler != null) {
            progressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog(){
        if (progressDialogHandler != null) {
            progressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            progressDialogHandler = null;
        }
    }

    /**
     * 订阅开始时调用显示ProgressDialog
     */
    @Override
    public void onStart() {
        if(showDialog){
           showProgressDialog();
        }
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
     * @param e 异常信息
     */
    @Override
    public void onError(Throwable e) {
        super.onError(e);
        dismissProgressDialog();
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public abstract void onNext(T t);

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    @Override
    public void onCancelProgress() {
//        if (!this.isUnsubscribed()) {
//            this.unsubscribe();
//        }
        dismissProgressDialog();
        if (context!=null)
        ((Activity)context).finish();
    }

}