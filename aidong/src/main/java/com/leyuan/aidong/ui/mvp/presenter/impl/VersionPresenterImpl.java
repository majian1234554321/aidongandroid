package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.leyuan.aidong.entity.VersionInformation;
import com.leyuan.aidong.http.subscriber.IsLoginSubscriber;
import com.leyuan.aidong.ui.mvp.model.VersionModel;
import com.leyuan.aidong.ui.mvp.view.VersionViewListener;
import com.leyuan.aidong.utils.ToastGlobal;
import com.leyuan.aidong.utils.VersionManager;
import com.leyuan.aidong.widget.dialog.BaseDialog;
import com.leyuan.aidong.widget.dialog.ButtonCancelListener;
import com.leyuan.aidong.widget.dialog.ButtonOkListener;
import com.leyuan.aidong.widget.dialog.DialogDoubleButton;
import com.leyuan.aidong.widget.dialog.DialogSingleButton;

/**
 * Created by user on 2017/3/6.
 */
public class VersionPresenterImpl {
    private Context context;
    private VersionViewListener listener;
    private VersionModel model;

    public VersionPresenterImpl(Context context, VersionViewListener listener) {
        this.context = context;
        this.listener = listener;
        model = new VersionModel();
    }

    public VersionPresenterImpl(Context context) {
        this.context = context;
        model = new VersionModel();
    }

//    public void getVersionInfo() {
//        model.getVersionInfo(new IsLoginSubscriber<VersionResult>(context) {
//            @Override
//            public void onNext(VersionResult versionResult) {
//                listener.onGetVersionInfo(versionResult.getData());
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                super.onError(e);
//                listener.onGetVersionInfo(null);
//            }
//        });
//    }
//
//    public void checkVersionAndShow() {
//        model.getVersionInfo(new IsLoginSubscriber<VersionResult>(context) {
//            @Override
//            public void onNext(VersionResult versionResult) {
//                VersionInformation versionInfomation = versionResult.getData();
//                if (versionInfomation != null && VersionManager.shouldUpdate(versionInfomation.getVersion(), context)) {
//                    showUpdateDialog(versionInfomation);
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                super.onError(e);
//            }
//        });
//    }

    public void getVersionInfo() {
        model.getVersionInfo(new IsLoginSubscriber<VersionInformation>(context) {
            @Override
            public void onNext(VersionInformation versionInfomation) {
                listener.onGetVersionInfo(versionInfomation);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                listener.onGetVersionInfo(null);
            }
        });
    }

    public void checkVersionAndShow() {
        model.getVersionInfo(new IsLoginSubscriber<VersionInformation>(context) {
            @Override
            public void onNext(VersionInformation versionInfomation) {
                if (versionInfomation != null && VersionManager.shouldUpdate(versionInfomation.getVersion(), context)) {
                    showUpdateDialog(versionInfomation);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    private void showUpdateDialog(VersionInformation versionInformation) {
        if (versionInformation == null) return;
        if (versionInformation.isUpdate_force() || VersionManager.mustUpdate(versionInformation.getVersion(), context)) {
            showForceUpdateDialog(versionInformation);
        } else {
            showNormalUpdateDialog(versionInformation);
        }
    }


    private void showNormalUpdateDialog(VersionInformation versionInformation) {
        final String downloadUrl = versionInformation.getApk_url();


        new DialogDoubleButton(context).setContentDesc("新版本 V" + versionInformation.getVersion() + " 已发布,请下载")
                .setBtnCancelListener(new ButtonCancelListener() {
                    @Override
                    public void onClick(BaseDialog dialog) {
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
            context.startActivity(Intent.createChooser(intent, "请选择浏览器"));
        } catch (Exception e) {
            e.printStackTrace();
            ToastGlobal.showShort("下载地址解析失败");
        }
    }

    private void showForceUpdateDialog(VersionInformation versionInformation) {
        final String downloadUrl = versionInformation.getApk_url();
        new DialogSingleButton(context).setContentDesc("新版本 V" + versionInformation.getVersion() + " 已发布,请下载")
                .setBtnOkListener(new ButtonOkListener() {
                    @Override
                    public void onClick(BaseDialog dialog) {
                        startDownload(downloadUrl);
                    }
                }).show();
    }
}
