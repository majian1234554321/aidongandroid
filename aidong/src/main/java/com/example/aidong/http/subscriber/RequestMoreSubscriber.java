package com.example.aidong.http.subscriber;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.aidong .utils.Logger;
import com.example.aidong .utils.NetworkUtil;
import com.example.aidong .widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.example.aidong .widget.endlessrecyclerview.weight.LoadingFooter;

/**
 * 用于上拉加载更多时的Http请求,
 * 在onNext中返回需要的数据,
 * onStart和onCompleted中显示并隐藏正在加载的脚布局
 * onError中显示提示网络错误的脚布局
 * 目前仅适用于RecyclerView的上拉加载更多
 * //todo 去掉Presenter中Subscriber和控件的耦合,交由View来实现这部分逻辑
 * Created by song on 2016/8/12
 */
public abstract class RequestMoreSubscriber<T> extends BaseSubscriber<T>{

    private Context context;
    private RecyclerView recyclerView;
    private int pageSize;

    public RequestMoreSubscriber(Context context, RecyclerView recyclerView, int pageSize) {
        super(context);
        this.context = context;
        this.recyclerView = recyclerView;
        this.pageSize = pageSize;
    }

    /**
     * 显示正在加载更多的脚布局
     */
    private void showLoadingFooterView(){
        LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(recyclerView);
        if(state == LoadingFooter.State.Loading) {
            Logger.d("RecyclerView", "the state is Loading, just wait..");
            return;
        }
        RecyclerViewStateUtils.setFooterViewState((Activity)context, recyclerView, pageSize, LoadingFooter.State.Loading, null);
    }

    /**
     * 显示网络错误的脚布局
     */
    private void showErrorFooterView(){
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.NetWorkError);
    }

    /**
     * 隐藏脚布局
     */
    private void hideFooterView(){
        LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(recyclerView);
        if(state == LoadingFooter.State.TheEnd){
            Logger.d("RecyclerView", "the state is the end, don't hide the end tip");
            return;
        }
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.Normal);
    }

    /**
     * 请求开始时显示加载更多的脚布局
     */
    @Override
    public void onStart() {
        showLoadingFooterView();
    }

    /**
     * 请求结束时隐藏加载更多的脚步局
     */
    @Override
    public void onCompleted() {
        hideFooterView();
    }

    /**
     * 对错误进行统一处理
     * @param e 异常信息
     */
    @Override
    public void onError(Throwable e) {
        super.onError(e);
        if(!NetworkUtil.isConnected(context)){
            showErrorFooterView();
        }else{
            hideFooterView();
        }
    }

    /**
     * 将onNext方法中的返回结果交给调用者处理
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public abstract void onNext(T t);

}