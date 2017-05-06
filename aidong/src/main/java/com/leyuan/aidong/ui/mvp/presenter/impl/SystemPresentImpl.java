package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.leyuan.aidong.entity.SystemBean;
import com.leyuan.aidong.entity.user.MineInfoBean;
import com.leyuan.aidong.http.subscriber.ProgressSubscriber;
import com.leyuan.aidong.ui.mvp.model.SystemModel;
import com.leyuan.aidong.ui.mvp.model.UserInfoModel;
import com.leyuan.aidong.ui.mvp.model.impl.SystemModelImpl;
import com.leyuan.aidong.ui.mvp.model.impl.UserInfoModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.SystemPresent;
import com.leyuan.aidong.ui.mvp.view.SystemView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.LogAidong;
import com.leyuan.aidong.utils.SystemInfoUtils;

import rx.Subscriber;

import static com.leyuan.aidong.utils.Constant.systemInfoBean;


/**
 * 系统配置
 * Created by song on 2016/11/10.
 */
public class SystemPresentImpl implements SystemPresent {
    private Context context;
    private SystemModel systemModel;
    private SystemView systemView;

    public SystemPresentImpl(Context context) {
        this.context = context;
        systemModel = new SystemModelImpl();
    }

    public void setSystemView(SystemView systemView) {
        this.systemView = systemView;
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
                if (systemBean != null) {
                    systemInfoBean = systemBean;
                    SystemInfoUtils.putSystemInfoBean(context, systemBean, SystemInfoUtils.KEY_SYSTEM);  //保存到本地

                    LogAidong.i("mLocationClient   SystemInfoUtils.putSystemInfoBean");
                }
            }
        }, os);
    }

    @Override
    public void getSystemInfoSelected(String os) {
        systemModel.getSystem(new ProgressSubscriber<SystemBean>(context) {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                //网络获取配置发生错误需要从本地读取配置信息
                if (systemView != null) {
                    systemView.onGetSystemConfiguration(false);
                }
            }

            @Override
            public void onNext(SystemBean systemBean) {
                systemInfoBean = systemBean;
                SystemInfoUtils.putSystemInfoBean(context, systemBean, SystemInfoUtils.KEY_SYSTEM);  //保存到本地 为NULL时不会保存 需修改

                UserInfoModel model = new UserInfoModelImpl(context);
                model.getMineInfo(new ProgressSubscriber<MineInfoBean>(context) {
                    @Override
                    public void onNext(MineInfoBean mineInfoBean) {
                        if (systemView != null) {
                            Constant.gyms = mineInfoBean.getGyms();
                            systemView.onGetSystemConfiguration(true);
                        }
                    }
                });
                LogAidong.i("mLocationClient   SystemInfoUtils.putSystemInfoBean");
            }
        }, os);
    }
}
