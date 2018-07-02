package com.example.aidong.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.example.aidong.widget.dialog.BaseDialog;
import com.example.aidong.widget.dialog.ButtonCancelListener;
import com.example.aidong.widget.dialog.ButtonOkListener;
import com.example.aidong.widget.dialog.DialogDoubleButton;
import com.facebook.stetho.common.LogUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2017/3/6.
 */
public class PermissionManager2 {

    private static final int REQUEST_PERMISSION_CODE = 102;

    private Activity context;
    private List<String> list;
    private OnCheckPermissionListener listener;

    public PermissionManager2(List<String> list, Activity context, OnCheckPermissionListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    private boolean checkPermissionCompat(String permission) {
        boolean result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            result = context.checkSelfPermission(permission)
                    == PackageManager.PERMISSION_GRANTED;
        } else {
            PackageManager pm = context.getPackageManager();
            result = pm.checkPermission(permission, context.getPackageName())
                    == PackageManager.PERMISSION_GRANTED;

            LogUtil.i("permission", "pm.checkPermission = " + pm.checkPermission(permission, context.getPackageName()) + " name = " + permission);

        }

        LogUtil.i("permission", "Build.VERSION.SDK_INT = " + Build.VERSION.SDK_INT + "  result = " + result + " pname = " + context.getPackageName());
        return result;
    }

    private void checkPermission(String permission) {
        LogUtil.i("permission", " permission status =  " + checkPermissionCompat(permission));

        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
                ActivityCompat.requestPermissions(context, new String[]{permission}, REQUEST_PERMISSION_CODE);
            } else {

                ActivityCompat.requestPermissions(context, new String[]{permission}, REQUEST_PERMISSION_CODE);

            }
        } else {

            list.remove(permission);
            checkPermissionList();
        }

    }



    public void checkPermissionList() {
//
        if (list == null || list.isEmpty()) {
            listener.checkOver();
            return;
        }


        for (int i = 0; i < list.size(); i++) {
            checkPermission(list.get(i));
        }


    }




    public interface OnCheckPermissionListener {
        void checkOver();
    }
}
