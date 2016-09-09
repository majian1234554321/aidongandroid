package com.leyuan.support.http.subscriber;

import com.leyuan.support.widget.customview.SwitcherLayout;

import rx.Subscriber;

/**
 * 用于第一次正常加载数据时的Http请求
 * 慎用:该Subscriber需与SwitcherLayout结合使用
 */
public abstract class CommonSubscriber<T> extends Subscriber<T> {

    private SwitcherLayout switcherLayout;

    public CommonSubscriber(SwitcherLayout switcherLayout) {
        this.switcherLayout = switcherLayout;
    }

    /**
     * 订阅开始时调用显示加载中界面
     */
    @Override
    public void onStart() {
        switcherLayout.showLoadingLayout();
    }

    /**
     * 正常加载完成，隐藏其他界面显示正常结果界面
     */
    @Override
    public void onCompleted() {
        switcherLayout.showNormalContentView();
    }

    /**
     * 显示错误提示界面
     * @param e 异常信息
     */
    @Override
    public void onError(Throwable e) {
        switcherLayout.showExceptionLayout();
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     * 通过判断界面来实现显示正常界面还是空值界面的逻辑
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public abstract void onNext(T t);

}