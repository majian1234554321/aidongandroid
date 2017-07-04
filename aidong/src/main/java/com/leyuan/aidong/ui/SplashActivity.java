package com.leyuan.aidong.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

import com.hyphenate.chat.EMClient;
import com.leyuan.aidong.entity.BannerBean;
import com.leyuan.aidong.entity.VersionInformation;
import com.leyuan.aidong.ui.home.AdvertisementActivity;
import com.leyuan.aidong.ui.mine.activity.test.LocationTestActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.FollowPresentImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.MineInfoPresenterImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.SplashPresenterImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.SystemPresentImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.VersionPresenterImpl;
import com.leyuan.aidong.ui.mvp.view.LoginAutoView;
import com.leyuan.aidong.ui.mvp.view.RequestCountInterface;
import com.leyuan.aidong.ui.mvp.view.SplashView;
import com.leyuan.aidong.ui.mvp.view.VersionViewListener;
import com.leyuan.aidong.utils.LogAidong;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.PermissionManager;
import com.leyuan.aidong.utils.RequestResponseCount;
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
public class SplashActivity extends BaseActivity implements VersionViewListener, LoginAutoView, SplashView, RequestCountInterface {

    private static final int MESSAGE = 1;
    private static final int DURATION = 2000;
    private static final String OS = "android";
    private boolean isFirstEnter = true;
    private int httpRequestIndex;
    private BannerBean startingBanner;

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
            } else if (startingBanner != null) {

                AdvertisementActivity.start(SplashActivity.this, startingBanner);
            } else {
//                UiManager.activityJump(SplashActivity.this, MainActivity.class);
                UiManager.activityJump(SplashActivity.this, LocationTestActivity.class);
            }
            finish();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);

//        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0){
//            finish();
//            return;
//        }

        splashPresenter = new SplashPresenterImpl(this);
        splashPresenter.setLoginAutoListener(this);
        versionPresenter = new VersionPresenterImpl(this, this);

        initData();
    }

    private void initData() {

        App.getInstance().startLocation();
        isFirstEnter = SharePrefUtils.getBoolean(SplashActivity.this, "isFirstEnter", true);
        EMClient.getInstance().chatManager().loadAllConversations();

        RequestResponseCount requestResponse = new RequestResponseCount(this);

        SystemPresentImpl systemPresent = new SystemPresentImpl(this);
        systemPresent.setOnRequestResponse(requestResponse);
        systemPresent.setSplashView(this);
        systemPresent.getSystemInfo("android");
        httpRequestIndex = 1;
        if (App.getInstance().isLogin()) {

            SplashPresenterImpl splashPresenter = new SplashPresenterImpl(this);
            splashPresenter.setOnRequestResponse(requestResponse);
            splashPresenter.autoLogin();

            MineInfoPresenterImpl mineInfoPresenter = new MineInfoPresenterImpl(this);
            mineInfoPresenter.setOnRequestResponse(requestResponse);
            mineInfoPresenter.getMineInfo();

            FollowPresentImpl followPresent = new FollowPresentImpl(this);
            followPresent.setOnRequestResponse(requestResponse);
            followPresent.getFollowList();
            httpRequestIndex = 4;
        }

    }

    @Override
    public void onGetVersionInfo(VersionInformation versionInfomation) {
//        httpRequestIndex++;
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

    @Override
    public void onAutoLoginResult(boolean success) {
        handler.removeCallbacksAndMessages(null);
        handler.sendEmptyMessageDelayed(MESSAGE, DURATION);
    }

    @Override
    public void onGetStartingBanner(BannerBean banner) {
        this.startingBanner = banner;

        LogAidong.i(" LocationClient  onGetStartingBanner SystemInfo");
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        splashPresenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
//        httpRequestIndex = 0;
    }


    @Override
    public void onRequestCount(int requestCount) {

        Logger.i("requestCount = " + requestCount);
        if (requestCount >= httpRequestIndex) {
            handler.removeCallbacksAndMessages(null);
            handler.sendEmptyMessageDelayed(MESSAGE, DURATION);
        }
    }
}
