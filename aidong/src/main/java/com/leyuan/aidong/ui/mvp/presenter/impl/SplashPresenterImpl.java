package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.Manifest;
import android.app.Activity;

import com.leyuan.aidong.entity.model.result.LoginResult;
import com.leyuan.aidong.http.subscriber.IsLoginSubscriber;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.mvp.model.impl.LoginModel;
import com.leyuan.aidong.ui.mvp.view.LoginAutoView;
import com.leyuan.aidong.utils.PermissionManager;
import com.leyuan.aidong.utils.RequestResponseCount;

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
    private RequestResponseCount requestResponse;

    public SplashPresenterImpl(Activity context) {
        this.context = context;
        loginModel = new LoginModel();
    }

    public void setLoginAutoListener(LoginAutoView loginAutoListener) {
        this.loginAutoListener = loginAutoListener;
    }

    public void autoLogin() {
        loginModel.autoLogin(new IsLoginSubscriber<LoginResult>(context) {
            @Override
            public void onNext(LoginResult user) {
                App.mInstance.setUser(user.getUser());
                if (loginAutoListener != null)
                    loginAutoListener.onAutoLoginResult(true);

                if (requestResponse != null) {
                    requestResponse.onRequestResponse();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (loginAutoListener != null)
                    loginAutoListener.onAutoLoginResult(false);

                if (requestResponse != null) {
                    requestResponse.onRequestResponse();
                }
            }
        });

    }

    public void checkPermission(PermissionManager.OnCheckPermissionListener permissionListener) {
        //        PermissionsUtil.checkAndRequestPermissions(this, null);
        Map<String, String> map = new HashMap<>();
        map.put(Manifest.permission.ACCESS_FINE_LOCATION, "请打开定位服务，以正常使用应用");
        map.put(Manifest.permission.CALL_PHONE, "请打开电话服务，以正常使用应用");
        map.put(Manifest.permission.CAMERA, "请打开相机权限,以正常使用应用");
        map.put(Manifest.permission.RECORD_AUDIO, "请打开录音权限,以正常使用应用");
        map.put(Manifest.permission.READ_EXTERNAL_STORAGE, "请打开读取权限,以正常使用应用");

        permissionManager = new PermissionManager(map, context, permissionListener);
        permissionManager.checkPermissionList();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void setOnRequestResponse(RequestResponseCount requestResponse) {
        this.requestResponse = requestResponse;
    }
}
