package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.Manifest;
import android.app.Activity;

import com.leyuan.aidong.entity.model.result.LoginResult;
import com.leyuan.aidong.http.subscriber.BaseSubscriber;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.mvp.model.impl.LoginModel;
import com.leyuan.aidong.ui.mvp.view.LoginAutoView;
import com.leyuan.aidong.utils.PermissionManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2017/3/6.
 */
public class SplashPresenterImpl {

    private LoginModel loginModel;
    private Activity context;
    private LoginAutoView loginAutoListener;
    private PermissionManager permissionManager;

    public SplashPresenterImpl(Activity context) {
        this.context = context;
        loginModel = new LoginModel();
    }

    public void setLoginAutoListener(LoginAutoView loginAutoListener) {
        this.loginAutoListener = loginAutoListener;
    }

    public void autoLogin() {
        loginModel.autoLogin(new BaseSubscriber<LoginResult>(context) {
            @Override
            public void onNext(LoginResult user) {
                App.mInstance.setUser(user.getUser());
                if (loginAutoListener != null)
                    loginAutoListener.onAutoLoginResult(true);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (loginAutoListener != null)
                    loginAutoListener.onAutoLoginResult(false);
            }
        });

    }

    public void checkPermission(PermissionManager.OnCheckPermissionListener permissionListener) {
        //        PermissionsUtil.checkAndRequestPermissions(this, null);
        Map<String, String> map = new HashMap<>();
        map.put(Manifest.permission.ACCESS_FINE_LOCATION, "请打开定位服务，以免造成上课无法正常签到");
        map.put(Manifest.permission.CALL_PHONE, "请打开电话服务，以正常使用应用");

        permissionManager = new PermissionManager(map, context, permissionListener);
        permissionManager.checkPermissionList();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
