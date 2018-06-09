package com.example.aidong.http.subscriber;

import android.app.Activity;
import android.content.Context;

import com.example.aidong .http.subscriber.handler.ProgressDialogHandler;

/**
 * 用于普通Http请求时，在页面中显示一个ProgressDialog
 * 在onNext中返回调用者需要的数据,
 * onStart和onCompleted中显示并隐藏正在加载Progress
 * onError中对错误进行统一处理
 * //todo 去掉Presenter中Subscriber和控件的耦合,交由View来实现这部分逻辑
 */
public abstract class Progress2Subscriber<T> extends BaseSubscriber<T> implements ProgressDialogHandler.ProgressCancelListener{
    private boolean showDialog = true;


    private ProgressDialogFragment progressDialogFragment= new ProgressDialogFragment();

    public Progress2Subscriber(Context context) {
        super(context);
        this.context = context;

    }

    public Progress2Subscriber(Context context, boolean showDialog) {
        super(context);
        this.context = context;
        this.showDialog = showDialog;
        progressDialogFragment = new ProgressDialogFragment();
    }



    /**
     * 订阅开始时调用显示ProgressDialog
     */
    @Override
    public void onStart() {
        if(showDialog){
            showDialog();
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


    public void  showDialog(){
        if (progressDialogFragment!=null){
            progressDialogFragment.show(((Activity)context).getFragmentManager(),"TAG");
        }

    }

    public void dismissProgressDialog(){
        if (progressDialogFragment!=null){
            progressDialogFragment.dismiss();
        }
    }

}