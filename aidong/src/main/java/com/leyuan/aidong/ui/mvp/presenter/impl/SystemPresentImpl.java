package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.leyuan.aidong.entity.SystemBean;
import com.leyuan.aidong.ui.mvp.model.SystemModel;
import com.leyuan.aidong.ui.mvp.model.impl.SystemModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.SystemPresent;
import com.leyuan.aidong.utils.SystemInfoUtils;

import rx.Subscriber;

import static com.leyuan.aidong.utils.Constant.systemInfoBean;


/**
 * 系统配置
 * Created by song on 2016/11/10.
 */
public class SystemPresentImpl implements SystemPresent{
    private Context context;
    private SystemModel systemModel;

    public SystemPresentImpl(Context context) {
        this.context = context;
        systemModel = new SystemModelImpl();
    }

    @Override
    public void getSystemInfo(String os) {
        systemModel.getSystem(new Subscriber<SystemBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                //网络获取配置发生错误需要从本地读取配置信息
            }

            @Override
            public void onNext(SystemBean systemBean) {
                if(systemBean != null){
                    systemInfoBean = systemBean;
                    SystemInfoUtils.putSystemInfoBean(context,systemBean);  //保存到本地
                }
            }
        },os);
    }
}