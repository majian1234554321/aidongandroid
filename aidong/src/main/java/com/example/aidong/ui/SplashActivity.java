package com.example.aidong.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.example.aidong.utils.PermissionManager2;
import com.hyphenate.chat.EMClient;
import com.example.aidong.entity.BannerBean;
import com.example.aidong.entity.VersionInformation;
import com.example.aidong.ui.home.AdvertisementActivity;
import com.example.aidong.ui.mvp.presenter.impl.CourseConfigPresentImpl;
import com.example.aidong.ui.mvp.presenter.impl.MineInfoPresenterImpl;
import com.example.aidong.ui.mvp.presenter.impl.SplashPresenterImpl;
import com.example.aidong.ui.mvp.presenter.impl.SystemPresentImpl;
import com.example.aidong.ui.mvp.presenter.impl.VersionPresenterImpl;
import com.example.aidong.ui.mvp.view.LoginAutoView;
import com.example.aidong.ui.mvp.view.RequestCountInterface;
import com.example.aidong.ui.mvp.view.SplashView;
import com.example.aidong.ui.mvp.view.VersionViewListener;
import com.example.aidong.utils.LogAidong;
import com.example.aidong.utils.Logger;
import com.example.aidong.utils.PermissionManager;
import com.example.aidong.utils.RequestResponseCount;
import com.example.aidong.utils.SharePrefUtils;
import com.example.aidong.utils.ToastGlobal;
import com.example.aidong.utils.UiManager;
import com.example.aidong.utils.VersionManager;
import com.example.aidong.widget.dialog.BaseDialog;
import com.example.aidong.widget.dialog.ButtonCancelListener;
import com.example.aidong.widget.dialog.ButtonOkListener;
import com.example.aidong.widget.dialog.DialogDoubleButton;
import com.example.aidong.widget.dialog.DialogSingleButton;
import com.example.aidong.R;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

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
                mPermissionList.clear();
                for (int i = 0; i < permissions.length; i++) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                        mPermissionList.add(permissions[i]);
                    }
                }


                /**
                 * 判断是否为空
                 */
                if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了

                    delayEntryPage();


                } else {//请求权限方法
                    String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
                    ActivityCompat.requestPermissions(SplashActivity.this, permissions, 1);
                }
            }
        }
    };


    List<String> mPermissionList = new ArrayList<>();


    String[] permissions = new String[]{Manifest.permission.CALL_PHONE, WRITE_EXTERNAL_STORAGE, ACCESS_FINE_LOCATION,
            RECORD_AUDIO, Manifest.permission.CAMERA
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {

        App.getInstance().startLocation();
        isFirstEnter = SharePrefUtils.getBoolean(SplashActivity.this, "isFirstEnter", true);
        EMClient.getInstance().chatManager().loadAllConversations();
        requestData();
    }

    private void requestData() {
        splashPresenter = new SplashPresenterImpl(this);
        splashPresenter.setLoginAutoListener(this);
        versionPresenter = new VersionPresenterImpl(this, this);

        RequestResponseCount requestResponse = new RequestResponseCount(this);
        SystemPresentImpl systemPresent = new SystemPresentImpl(this);
        systemPresent.setOnRequestResponse(requestResponse);
        systemPresent.setSplashView(this);
        systemPresent.getSystemInfo("android");

        CourseConfigPresentImpl coursePresentNew = new CourseConfigPresentImpl(this);
        coursePresentNew.setOnRequestResponse(requestResponse);
        coursePresentNew.getCourseFilterConfig();

        httpRequestIndex = 2;
        if (App.getInstance().isLogin()) {

            SplashPresenterImpl splashPresenter = new SplashPresenterImpl(this);
            splashPresenter.setOnRequestResponse(requestResponse);
            splashPresenter.autoLogin();

            MineInfoPresenterImpl mineInfoPresenter = new MineInfoPresenterImpl(this);
            mineInfoPresenter.setOnRequestResponse(requestResponse);
            mineInfoPresenter.getMineInfo();

//            FollowPresentImpl followPresent = new FollowPresentImpl(this);
//            followPresent.setOnRequestResponse(requestResponse);
//            followPresent.getFollowList();
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



    boolean mShowRequestPermission = true;//用户是否禁止权限
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        //判断是否勾选禁止后不再询问
                        boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this, permissions[i]);
                        if (showRequestPermission) {
                            new AlertDialog.Builder(SplashActivity.this)
                                    .setMessage("去开启相关权限")
                                    .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //引导用户到设置中去进行设置
                                            Intent intent = new Intent();
                                            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                            intent.setData(Uri.fromParts("package", getPackageName(), null));
                                            startActivityForResult(intent,10086);
                                            finish();

                                        }
                                    })
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            delayEntryPage();
                                        }
                                    })
                                    .create()
                                    .show();
                            return;
                        } else {
                            mShowRequestPermission = false;//已经禁止
                        }
                    }
                }
                delayEntryPage();
                break;
            default:
                delayEntryPage();
                break;
        }

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


    void delayEntryPage() {
        if (isFirstEnter) {
            UiManager.activityJump(SplashActivity.this, GuideActivity.class);
        } else if (startingBanner != null) {

            AdvertisementActivity.start(SplashActivity.this, startingBanner);
        } else {
            UiManager.activityJump(SplashActivity.this, MainActivity.class);
//                UiManager.activityJump(SplashActivity.this, LocationTestActivity.class);
        }
        finish();
    }
}
