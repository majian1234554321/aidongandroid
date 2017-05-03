package com.leyuan.aidong.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

import com.hyphenate.chat.EMClient;
import com.leyuan.aidong.entity.VersionInformation;
import com.leyuan.aidong.ui.mvp.presenter.FollowPresent;
import com.leyuan.aidong.ui.mvp.presenter.SystemPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.FollowPresentImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.MineInfoPresenterImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.SplashPresenterImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.SystemPresentImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.VersionPresenterImpl;
import com.leyuan.aidong.ui.mvp.view.LoginAutoView;
import com.leyuan.aidong.ui.mvp.view.VersionViewListener;
import com.leyuan.aidong.utils.LogAidong;
import com.leyuan.aidong.utils.PermissionManager;
import com.leyuan.aidong.utils.SharePrefUtils;
import com.leyuan.aidong.utils.ToastGlobal;
import com.leyuan.aidong.utils.UiManager;
import com.leyuan.aidong.utils.VersionManager;
import com.leyuan.aidong.widget.dialog.BaseDialog;
import com.leyuan.aidong.widget.dialog.ButtonCancelListener;
import com.leyuan.aidong.widget.dialog.ButtonOkListener;
import com.leyuan.aidong.widget.dialog.DialogDoubleButton;
import com.leyuan.aidong.widget.dialog.DialogSingleButton;

//todo fix
public class SplashActivity extends BaseActivity implements VersionViewListener, LoginAutoView {

    private static final int MESSAGE = 1;
    private static final int DURATION = 2000;
    private static final String OS = "android";
    private boolean isFirstEnter = true;

    private SplashPresenterImpl splashPresenter;
    private VersionPresenterImpl versionPresenter;

    private VersionInformation versionInfomation;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MESSAGE) {
                splashPresenter.checkPermission(permissinListener);
            }
        }
    };

    private PermissionManager.OnCheckPermissionListener permissinListener = new PermissionManager.OnCheckPermissionListener() {
        @Override
        public void checkOver() {
            if (isFirstEnter) {
                UiManager.activityJump(SplashActivity.this, GuideActivity.class);
            } else {
                UiManager.activityJump(SplashActivity.this, MainActivity.class);
            }
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);


        SystemPresent systemPresent = new SystemPresentImpl(this);
        systemPresent.getSystemInfo(OS);
        LogAidong.i("mLocationClient   systemPresent.getSystemInfo(OS);");

        splashPresenter = new SplashPresenterImpl(this);
        splashPresenter.setLoginAutoListener(this);
        versionPresenter = new VersionPresenterImpl(this, this);

        App.getInstance().startLocation();
        isFirstEnter = SharePrefUtils.getBoolean(SplashActivity.this, "isFirstEnter", true);
        EMClient.getInstance().chatManager().loadAllConversations();
        initData();
    }

    private void initData() {
        versionPresenter.getVersionInfo();
    }

    @Override
    public void onGetVersionInfo(VersionInformation versionInfomation) {
        this.versionInfomation = versionInfomation;
        if (versionInfomation != null && VersionManager.shouldUpdate(versionInfomation.getVersion(), this)) {
            showUpdateDialog(versionInfomation);
        } else {
            if (App.mInstance.isLogin()) {
                //先自动登录
                splashPresenter.autoLogin();
            } else {
                handler.removeCallbacksAndMessages(null);
                handler.sendEmptyMessageDelayed(MESSAGE, DURATION);
            }
        }

    }

    private void showUpdateDialog(VersionInformation versionInformation) {
        if (versionInformation.isUpdate_force() || VersionManager.mustUpdate(versionInformation.getVersion(), this)) {
            showForceUpdateDialog(versionInformation);
        } else {
            showNormalUpdateDialog(versionInformation);
        }
    }

    private void showNormalUpdateDialog(VersionInformation versionInformation) {
        final String downloadUrl = versionInformation.getApk_url();
        new DialogDoubleButton(this).setContentDesc("新版本 V" + versionInformation.getVersion() + " 已发布,请下载")
                .setBtnCancelListener(new ButtonCancelListener() {
                    @Override
                    public void onClick(BaseDialog dialog) {
                        if (App.mInstance.isLogin()) {
                            //先自动登录
                            splashPresenter.autoLogin();
                        } else {
                            handler.removeCallbacksAndMessages(null);
                            handler.sendEmptyMessageDelayed(MESSAGE, DURATION);
                        }
                        dialog.dismiss();
                    }
                })
                .setBtnOkListener(new ButtonOkListener() {
                    @Override
                    public void onClick(BaseDialog dialog) {
                        startDownload(downloadUrl);
                    }
                }).show();


    }

    private void startDownload(String downloadUrl) {
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri content_url = Uri.parse(downloadUrl);
            intent.setData(content_url);
            startActivity(Intent.createChooser(intent, "请选择浏览器"));
        } catch (Exception e) {
            e.printStackTrace();
            ToastGlobal.showShort("下载地址解析失败");
        }
    }

    private void showForceUpdateDialog(VersionInformation versionInformation) {
        final String downloadUrl = versionInformation.getApk_url();
        new DialogSingleButton(this).setContentDesc("新版本 V" + versionInformation.getVersion() + " 已发布,请下载")
                .setBtnOkListener(new ButtonOkListener() {
                    @Override
                    public void onClick(BaseDialog dialog) {
                        startDownload(downloadUrl);
                    }
                }).show();
    }

    @Override
    public void onAutoLoginResult(boolean success) {
        handler.removeCallbacksAndMessages(null);
        handler.sendEmptyMessageDelayed(MESSAGE, DURATION);
        if (App.getInstance().isLogin()) {
            FollowPresent followPresent = new FollowPresentImpl(this);  //获取关注列表
            followPresent.getFollowList();

            MineInfoPresenterImpl infoPresent = new MineInfoPresenterImpl(this);    //初始化运动足记
            infoPresent.getMineInfo();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        splashPresenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

}
